package emergencysystem.service;

import emergencysystem.dao.FireStationRepository;
import emergencysystem.model.FireStation;
import emergencysystem.model.Person;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.*;
import java.util.stream.Collectors;

@Service
public class FireStationService {

    @Autowired
    private FireStationRepository fireStationRepository;

    @Autowired
    private PersonService personService;

    @Autowired
    private MedicalRecordService medicalRecordService;

    private static final Logger logger = LogManager.getLogger(FireStationService.class);

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

    public FireStation createFireStation(FireStation fireStation) {

        return fireStationRepository.save(fireStation);
    }

    public List<FireStation> createFireStations(List<FireStation> fireStations) {

        return fireStationRepository.saveAll(fireStations);
    }

    public FireStation updateFireStation(FireStation fireStation) {

        FireStation fireStationUpdated;
        Optional<FireStation> optionalFireStation = fireStationRepository.findById(fireStation.getId());

        if (optionalFireStation.isPresent()) {
            fireStationUpdated = optionalFireStation.get();
            fireStationUpdated.setAddress(fireStation.getAddress());
            fireStationRepository.save(fireStationUpdated);
        } else {
            return new FireStation();
        }
        return fireStationUpdated;
    }

    public String deleteFireStation(Long id) {

        fireStationRepository.deleteById(id);

        return "The fire station was deleted successfully.";
    }

    public Map<Map<String, Integer>, List<Person>> getPersonsByFireStation(int station) {

        //Todo: Hide non-requested elements

        logger.debug("[COVERED] Get station: " + station);

        List<FireStation> fireStations = fireStationRepository.getByStation(station);

        Set<String> addressesByStation = fireStations.stream()
                .map(FireStation::getAddress)
                .collect(Collectors.toSet());

        List<Person> persons = new ArrayList<>();
        Map<String, Integer> countOfChildrenAndAdults = new TreeMap<>();

        addressesByStation.forEach(address -> {
            persons.addAll(personService.getPersonsByAddress(address));
            logger.debug("[COVERED] Count children and adults: " + countOfChildrenAndAdults);
        });

        countOfChildrenAndAdults.putAll(medicalRecordService.getCountOfChildrenAndAdults(persons));

        Map<Map<String, Integer>, List<Person>> result = new HashMap<Map<String, Integer>, List<Person>>();
        result.put(countOfChildrenAndAdults, persons);

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
            persons.addAll(personService.getPersonsByAddress(address));
            phones.addAll(persons.stream()
                    .map(person -> person.getPhone())
                    .collect(Collectors.toList()));

            logger.debug("[PHONEALERT] Retrieve phones: " + phones);
        });

        return phones;
    }
}
