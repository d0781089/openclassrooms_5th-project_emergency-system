package com.safetynet.emergencysystem.controller;

import com.safetynet.emergencysystem.model.FireStation;
import com.safetynet.emergencysystem.service.firestation.FireStationCreationService;
import com.safetynet.emergencysystem.service.firestation.FireStationDeletionService;
import com.safetynet.emergencysystem.service.firestation.FireStationReadService;
import com.safetynet.emergencysystem.service.firestation.FireStationUpdateService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.converter.json.MappingJacksonValue;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
public class FireStationController {

    @Autowired
    private FireStationCreationService fireStationCreationService;
    @Autowired
    private FireStationReadService fireStationReadService;
    @Autowired
    private FireStationUpdateService fireStationUpdateService;
    @Autowired
    private FireStationDeletionService fireStationDeletionService;

    @GetMapping("/fireStations")
    public List<FireStation> getFireStations() {

        return fireStationReadService.getFireStations();
    }

    @GetMapping("/fireStations/{id}")
    public FireStation getFireStationById(@PathVariable Long id) {

        return fireStationReadService.getFireStationById(id);
    }

    @PostMapping("/createFireStation")
    public FireStation createFireStation(@RequestBody FireStation fireStation) {

        return fireStationCreationService.createFireStation(fireStation);
    }

    @PostMapping("/createFireStations")
    public List<FireStation> createFireStation(@RequestBody List<FireStation> fireStations) {

        return fireStationCreationService.createFireStations(fireStations);
    }

    @PutMapping("/updateFireStation")
    public FireStation updateFireStation(@RequestBody FireStation fireStation) {

        return fireStationUpdateService.updateFireStation(fireStation);
    }

    @DeleteMapping("/fireStations/{id}")
    public void deleteFireStationById(@PathVariable Long id) {

        fireStationDeletionService.deleteFireStation(id);
    }

    @GetMapping("/firestation")
    MappingJacksonValue getPersonsByFireStation(@RequestParam int stationNumber) {

        return fireStationReadService.getPersonsByFireStation(stationNumber);
    }

    @GetMapping("/phoneAlert")
    List<String> getPhonesByFireStation(@RequestParam int firestation) {

        return fireStationReadService.getPhonesByFireStation(firestation);
    }
}
