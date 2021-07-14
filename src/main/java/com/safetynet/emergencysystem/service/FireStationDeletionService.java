package com.safetynet.emergencysystem.service;

import com.safetynet.emergencysystem.dao.FireStationRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class FireStationDeletionService {

    @Autowired
    private FireStationRepository fireStationRepository;

    public void deleteFireStation(Long id) {

        fireStationRepository.deleteById(id);
    }
}
