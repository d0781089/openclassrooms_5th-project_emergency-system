package com.safetynet.emergencysystem.service.firestation;

import com.safetynet.emergencysystem.dao.FireStationRepository;
import com.safetynet.emergencysystem.model.FireStation;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Optional;

@Service
public class FireStationUpdateService {

    @Autowired
    private FireStationRepository fireStationRepository;

    private static final Logger logger = LogManager.getLogger(FireStationReadService.class);

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
        logger.debug("Updated FireStation: " + fireStationUpdated);
        return fireStationUpdated;
    }
}
