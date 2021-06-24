package emergencysystem.controller;

import emergencysystem.model.FireStation;
import emergencysystem.model.Person;
import emergencysystem.service.FireStationService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
//@Controller
//@RequestMapping("/fireStations")
public class FireStationController {

    @Autowired
    private FireStationService fireStationService;

    @PostMapping("/createFireStation")
    public FireStation createFireStation(@RequestBody FireStation fireStation) {

        return fireStationService.createFireStation(fireStation);
    }

    @PostMapping("/createFireStations")
    public List<FireStation> createFireStation(@RequestBody List<FireStation> fireStations) {

        return fireStationService.createFireStations(fireStations);
    }

    @GetMapping("/fireStations/{id}")
    public FireStation getFireStationById(@PathVariable Long id) {

        return fireStationService.getFireStationById(id);
    }

    @GetMapping("/fireStations")
    public List<FireStation> getFireStations() {

        return fireStationService.getFireStations();
    }

    @PutMapping("/updateFireStation")
    public FireStation updateFireStation(@RequestBody FireStation fireStation) {

        return fireStationService.updateFireStation(fireStation);
    }

    @DeleteMapping("fireStations/{id}")
    public String deleteFireStation(@PathVariable Long id) {

        return fireStationService.deleteFireStation(id);
    }

    @GetMapping("fireStation?stationNumber={station}")
    List<Person> getPersonsCoveredByFireStation(@PathVariable int station) {

        return fireStationService.getPersonsCoveredByFireStation(station);
    }

    /*private static final String sort = "all";

    JsonData jsonData = new JsonData();
    private FireStationRepository fireStationRepository;

    @Autowired
    public FireStationController(FireStationRepository fireStationRepository) {
        this.fireStationRepository = fireStationRepository;
    }

    @RequestMapping(method= RequestMethod.GET)
    public String sortFireStations(Model model) throws IOException {
        List<FireStation> fireStations = fireStationRepository.findBySort(sort);
        fireStations.addAll(JsonService.getData(jsonData).getFireStations());

        if (fireStations != null) {
            model.addAttribute("fireStations", fireStations);
        }
        return "fireStations";
    }

    @RequestMapping(method=RequestMethod.POST)
    public String addToFireStationsList(FireStation fireStation) {
        fireStation.setSort(sort);
        fireStationRepository.save(fireStation);
        return "redirect:/fireStations";
    }*/
}
