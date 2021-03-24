package emergencysystem.controller;

import emergencysystem.model.MedicalRecord;
import emergencysystem.util.MedicalRecordRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

import java.util.List;

@Controller
@RequestMapping("/medicalrecords")
public class MedicalRecordController {

    private static final String sort = "all";

    private MedicalRecordRepository medicalRecordRepository;

    @Autowired
    public MedicalRecordController(MedicalRecordRepository medicalRecordRepository) {
        this.medicalRecordRepository = medicalRecordRepository;
    }

    @RequestMapping(method= RequestMethod.GET)
    public String sortMedicalRecords(Model model) {

        List<MedicalRecord> medicalRecords = medicalRecordRepository.findBySort(sort);
        if (medicalRecords != null) {
            model.addAttribute("medicalrecords", medicalRecords);
        }
        return "medicalrecords";
    }

    @RequestMapping(method=RequestMethod.POST)
    public String addToMedicalRecordsList(MedicalRecord medicalRecord) {
        medicalRecord.setSort(sort);
        medicalRecordRepository.save(medicalRecord);
        return "redirect:/medicalrecords";
    }
}
