package com.safetynet.emergencysystem.service.firestation;

import com.safetynet.emergencysystem.dao.FireStationRepository;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class FireStationDeletionService {

    @Autowired
    private FireStationRepository fireStationRepository;

    private static final Logger logger = LogManager.getLogger(FireStationReadService.class);

    public void deleteFireStation(Long id) {

        logger.debug("Delete FireStations ID: " + id);

        fireStationRepository.deleteById(id);
    }
}
