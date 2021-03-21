package emergencysystem.controller;

import emergencysystem.model.FireStation;
import emergencysystem.util.FireStationRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

import java.util.List;

@Controller
@RequestMapping("/firestations")
public class FireStationController {

    private static final String sort = "all";

    private FireStationRepository fireStationRepository;

    @Autowired
    public FireStationController(FireStationRepository fireStationRepository) {
        this.fireStationRepository = fireStationRepository;
    }

    @RequestMapping(method= RequestMethod.GET)
    public String sortFireStations(Model model) {

        List<FireStation> fireStations = fireStationRepository.findBySort(sort);
        if (fireStations != null) {
            model.addAttribute("firestations", fireStations);
        }
        return "firestations";
    }

    @RequestMapping(method=RequestMethod.POST)
    public String addToFireStationsList(FireStation fireStation) {
        fireStation.setSort(sort);
        fireStationRepository.save(fireStation);
        return "redirect:/firestations";
    }

}
