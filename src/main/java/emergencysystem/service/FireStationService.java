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

    public FireStation createFireStation(FireStation fireStation) {

        return fireStationRepository.save(fireStation);
    }

    public List<FireStation> createFireStations(List<FireStation> fireStations) {

        return fireStationRepository.saveAll(fireStations);
    }

    public FireStation getFireStationById(Long id) {

        return fireStationRepository.findById(id).get();
    }

    public FireStation getFireStationByAddress(String address) {

        return fireStationRepository.getByAddress(address);
    }

    public List<FireStation> getFireStations() {

        return fireStationRepository.findAll();
    }

    public FireStation updateFireStation(FireStation fireStation) {

        FireStation fireStationToUpdate;
        Optional<FireStation> optionalFireStation = fireStationRepository.findById(fireStation.getId());

        if (optionalFireStation.isPresent()) {
            fireStationToUpdate = optionalFireStation.get();
            //fireStationToUpdate.setStation(fireStation.getStation()); // Todo: Fire Station number must not change
            fireStationToUpdate.setAddress(fireStation.getAddress());
            fireStationRepository.save(fireStationToUpdate);
        } else {
            return new FireStation();
        }
        return fireStationToUpdate;
    }

    public String deleteFireStation(Long id) {

        fireStationRepository.deleteById(id);

        return "The fire station was DELETED successfully!";
    }

    public Map<String, List<Person>> getPersonsCoveredByFireStation(int station) {

        logger.debug("[COVERED] Given station number: " + station);

        String fireStationAddress = fireStationRepository.getByStation(station).getAddress();
        List<Person> personsCovered = personService.getPersonsByAddress(fireStationAddress);
        Map<String, Integer> numberOfChildrenAndAdults = medicalRecordService
                .getNumberOfChildrenAndAdults(personsCovered);
        logger.debug("[COVERED] Accountability: " + numberOfChildrenAndAdults);

        Map<String, List<Person>> result = new HashMap<String, List<Person>>();
        result.put("Liste des personnes couvertent par la caserne n°" + station + " comportant "
                + numberOfChildrenAndAdults.get("children") + " enfant(s) et "
                + numberOfChildrenAndAdults.get("adults") + " adulte(s):", personsCovered);

        return result;
    }

    public Map<String, List<String>> getPhoneNumbersCoveredByFireStation(int station) {

        List<Person> personsCovered = personService.getPersonsByAddress(fireStationRepository
                .getByStation(station)
                .getAddress());
        List<String> phoneNumbersCovered = new ArrayList<>();

        phoneNumbersCovered = personsCovered.stream()
                .map(person -> person.getPhone())
                .collect(Collectors.toList());

        logger.debug("[PHONEALERT] Retrieved phone numbers: " + phoneNumbersCovered);

        Map<String, List<String>> result = new HashMap<String, List<String>>();
        result.put("Liste des numéros de téléphones desservis par la caserne n°" + station + " comportant " + phoneNumbersCovered.size() + " numéro(s) :", phoneNumbersCovered);

        return result;
    }
}
