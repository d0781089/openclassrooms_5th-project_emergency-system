package emergencysystem.service;

import emergencysystem.dao.FireStationRepository;
import emergencysystem.model.FireStation;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Optional;

@Service
public class FireStationUpdateService {

    @Autowired
    private FireStationRepository fireStationRepository;

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
}
