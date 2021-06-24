package emergencysystem.service;

import emergencysystem.dao.FireStationRepository;
import emergencysystem.model.FireStation;
import emergencysystem.model.Person;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class FireStationService {

    @Autowired
    private FireStationRepository fireStationRepository;

    private PersonService personService;

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

    public List<Person> getPersonsCoveredByFireStation(int station) {

        return personService.getPersonsByFireStationAddress(fireStationRepository.getByStation(station).getAddress());
    }
}
