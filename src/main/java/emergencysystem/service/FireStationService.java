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
    private PersonReadService personReadService;

    @Autowired
    private MedicalRecordReadService medicalRecordReadService;

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

    public Map<Map<String, Integer>, List<Map<String, String>>> getPersonsByFireStation(int station) {

        logger.debug("[COVERED] Get station: " + station);

        List<FireStation> fireStations = fireStationRepository.getByStation(station);

        Set<String> addressesByStation = fireStations.stream()
                .map(FireStation::getAddress)
                .collect(Collectors.toSet());

        List<Person> personsByAddress = new ArrayList<>();
        Map<String, Integer> countOfChildrenAndAdults = new TreeMap<>();
        List<Map<String, String>> persons = new ArrayList<>();

        addressesByStation.forEach(address -> {
            personsByAddress.addAll(personReadService.getPersonsByAddress(address));

            personsByAddress.forEach(personByAddress -> {
                Map<String, String> person = new TreeMap<>();

                person.put("firstName", personByAddress.getFirstName());
                person.put("lastName", personByAddress.getLastName());
                person.put("address", personByAddress.getAddress());
                person.put("phone", personByAddress.getPhone());

                persons.add(person);
            });
        });

        countOfChildrenAndAdults.putAll(medicalRecordReadService.getCountOfChildrenAndAdults(personsByAddress));
        logger.debug("[COVERED] Count children and adults: " + countOfChildrenAndAdults);

        Map<Map<String, Integer>, List<Map<String, String>>> result
                = new HashMap<Map<String, Integer>, List<Map<String, String>>>();
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
            persons.addAll(personReadService.getPersonsByAddress(address));
            phones.addAll(persons.stream()
                    .map(person -> person.getPhone())
                    .collect(Collectors.toList()));

            logger.debug("[PHONEALERT] Retrieve phones: " + phones);
        });

        return phones;
    }
}
