package emergencysystem.service;

import com.fasterxml.jackson.databind.ser.FilterProvider;
import com.fasterxml.jackson.databind.ser.impl.SimpleBeanPropertyFilter;
import com.fasterxml.jackson.databind.ser.impl.SimpleFilterProvider;
import emergencysystem.dao.FireStationRepository;
import emergencysystem.model.FireStation;
import emergencysystem.model.Person;
import emergencysystem.model.ResultByFireStation;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.converter.json.MappingJacksonValue;
import org.springframework.stereotype.Service;

import java.util.*;
import java.util.stream.Collectors;

@Service
public class FireStationReadService {

    @Autowired
    private PersonReadService personReadService;

    @Autowired
    private FireStationRepository fireStationRepository;

    @Autowired
    private MedicalRecordReadService medicalRecordReadService;

    private static final Logger logger = LogManager.getLogger(FireStationReadService.class);

    public List<FireStation> getFireStations() {

        return fireStationRepository.findAll();
    }

    public FireStation getFireStationById(Long id) {

        return fireStationRepository.findById(id).get();
    }

    public FireStation getFireStationByAddress(String address) {

        return fireStationRepository.getByAddress(address);
    }

    public List<FireStation> getFireStationByStation(int station) {

        return fireStationRepository.getByStation(station);
    }

    public MappingJacksonValue getPersonsByFireStation(int station) {

        logger.debug("[COVERED] Get station: " + station);

        List<FireStation> fireStations = fireStationRepository.getByStation(station);

        Set<String> addressesByStation = fireStations.stream()
                .map(FireStation::getAddress)
                .collect(Collectors.toSet());

        List<Person> personsByAddress = new ArrayList<>();
        List<Person> persons = new ArrayList<>();

        addressesByStation.forEach(address -> {
            personsByAddress.addAll(personReadService.getPersonsByAddress(address));

            personsByAddress.forEach(personByAddress -> {
                Person person = new Person();

                person.setFirstName(personByAddress.getFirstName());
                person.setLastName(personByAddress.getLastName());
                person.setAddress(personByAddress.getAddress());
                person.setPhone(personByAddress.getPhone());

                persons.add(person);
            });
        });

        Map<String, Integer> countOfChildrenAndAdults = new TreeMap<>();
        countOfChildrenAndAdults.putAll(medicalRecordReadService.getCountOfChildrenAndAdults(personsByAddress));
        logger.debug("[COVERED] Count children and adults: " + countOfChildrenAndAdults);

        ResultByFireStation resultByFireStation = new ResultByFireStation();
        resultByFireStation.setChildren(countOfChildrenAndAdults.get("children"));
        resultByFireStation.setAdults(countOfChildrenAndAdults.get("adults"));
        resultByFireStation.setList(persons);

        SimpleBeanPropertyFilter personByStationFilter = SimpleBeanPropertyFilter.filterOutAllExcept(
                "firstName", "lastName", "address", "phone");
        FilterProvider filterList = new SimpleFilterProvider().addFilter("personFilter", personByStationFilter);
        MappingJacksonValue result = new MappingJacksonValue(resultByFireStation);
        result.setFilters(filterList);

        return result;
    }

    public List<String> getPhonesByFireStation(int station) {

        List<FireStation> fireStations = fireStationRepository.getByStation(station);
        List<Person> persons = new ArrayList<>();
        List<String> phones = new ArrayList<>();

        Set<String> addressesByFireStation = fireStations.stream()
                .map(FireStation::getAddress)
                .collect(Collectors.toSet());

        addressesByFireStation.forEach(address -> {
            persons.addAll(personReadService.getPersonsByAddress(address));
            phones.addAll(persons.stream()
                    .map(person -> person.getPhone())
                    .collect(Collectors.toList()));

            logger.debug("[PHONEALERT] Retrieve phones: " + phones);
        });

        return phones;
    }
}
