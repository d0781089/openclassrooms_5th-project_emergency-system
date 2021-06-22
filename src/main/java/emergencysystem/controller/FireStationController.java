package emergencysystem.controller;

import emergencysystem.model.FireStation;
import emergencysystem.model.JsonData;
import emergencysystem.service.JsonService;
import emergencysystem.util.FireStationRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

import java.io.IOException;
import java.util.List;

@Controller
@RequestMapping("/fireStations")
public class FireStationController {

    private static final String sort = "all";

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
    }

}
