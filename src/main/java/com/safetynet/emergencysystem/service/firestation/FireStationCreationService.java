package com.safetynet.emergencysystem.service.firestation;

import com.safetynet.emergencysystem.dao.FireStationRepository;
import com.safetynet.emergencysystem.model.FireStation;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class FireStationCreationService {

    @Autowired
    private FireStationRepository fireStationRepository;

    private static final Logger logger = LogManager.getLogger(FireStationReadService.class);

    public FireStation createFireStation(FireStation fireStation) {

        logger.debug("Create FireStation: " + fireStation);

        return fireStationRepository.save(fireStation);
    }

    public List<FireStation> createFireStations(List<FireStation> fireStations) {

        logger.debug("Create FireStations: " + fireStations);

        return fireStationRepository.saveAll(fireStations);
    }
}
