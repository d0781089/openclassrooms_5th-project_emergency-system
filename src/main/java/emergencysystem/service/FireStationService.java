package emergencysystem.service;

import emergencysystem.dao.FireStationRepository;
import emergencysystem.model.FireStation;
import emergencysystem.model.Person;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Optional;

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

        logger.debug("[Persons list covered by the firestation] Given station number: " + station);

        String fireStationAddress = fireStationRepository.getByStation(station).getAddress();
        List<Person> personsCovered = personService.getPersonsByAddress(fireStationAddress);
        Map<String, Integer> numberOfChildrenAndAdults = medicalRecordService
                .getNumberOfChildrenAndAdults(personsCovered);
        logger.debug("[Persons list covered by the firestation] Accountability: " + numberOfChildrenAndAdults);

        Map<String, List<Person>> result = new HashMap<String, List<Person>>();
        result.put("Liste des personnes couvertent par la caserne n°" + station + " comportant "
                + numberOfChildrenAndAdults.get("children") + " enfant(s) et "
                + numberOfChildrenAndAdults.get("adults") + " adulte(s):", personsCovered);

        return result;
    }
}
