package emergencysystem.controller;

import emergencysystem.model.FireStation;
import emergencysystem.model.Person;
import emergencysystem.service.FireStationService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Map;

@RestController
public class FireStationController {

    @Autowired
    private FireStationService fireStationService;

    @GetMapping("/fireStations")
    public List<FireStation> getFireStations() {

        return fireStationService.getFireStations();
    }

    @GetMapping("/fireStations/{id}")
    public FireStation getFireStationById(@PathVariable Long id) {

        return fireStationService.getFireStationById(id);
    }

    @PostMapping("/createFireStation")
    public FireStation createFireStation(@RequestBody FireStation fireStation) {

        return fireStationService.createFireStation(fireStation);
    }

    @PostMapping("/createFireStations")
    public List<FireStation> createFireStation(@RequestBody List<FireStation> fireStations) {

        return fireStationService.createFireStations(fireStations);
    }

    @PutMapping("/updateFireStation")
    public FireStation updateFireStation(@RequestBody FireStation fireStation) {

        return fireStationService.updateFireStation(fireStation);
    }

    @DeleteMapping("/fireStations/{id}")
    public String deleteFireStationById(@PathVariable Long id) {

        return fireStationService.deleteFireStation(id);
    }

    @GetMapping("/firestation")
    Map<Map<String, Integer>, List<Map<String, String>>> getPersonsByFireStation(@RequestParam int stationNumber) {

        return fireStationService.getPersonsByFireStation(stationNumber);
    }

    @GetMapping("/phoneAlert")
    List<String> getPhonesByFireStation(@RequestParam int firestation) {

        return fireStationService.getPhonesByFireStation(firestation);
    }
}
