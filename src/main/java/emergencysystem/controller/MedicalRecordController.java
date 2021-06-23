package emergencysystem.controller;

import emergencysystem.model.JsonData;
import emergencysystem.model.MedicalRecord;
import emergencysystem.service.JsonService;
import emergencysystem.dao.MedicalRecordRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import java.io.IOException;
import java.util.List;

@RestController
//@Controller
//@RequestMapping("/medicalRecords")
public class MedicalRecordController {

    /*private static final String sort = "all";

    JsonData jsonData = new JsonData();
    private MedicalRecordRepository medicalRecordRepository;

    @Autowired
    public MedicalRecordController(MedicalRecordRepository medicalRecordRepository) {
        this.medicalRecordRepository = medicalRecordRepository;
    }

    @RequestMapping(method= RequestMethod.GET)
    public String sortMedicalRecords(Model model) throws IOException {
        List<MedicalRecord> medicalRecords = medicalRecordRepository.findBySort(sort);
        medicalRecords.addAll(JsonService.getData(jsonData).getMedicalRecords());

        if (medicalRecords != null) {
            model.addAttribute("medicalRecords", medicalRecords);
        }
        return "medicalRecords";
    }

    @RequestMapping(method=RequestMethod.POST)
    public String addToMedicalRecordsList(MedicalRecord medicalRecord) {
        medicalRecord.setSort(sort);
        medicalRecordRepository.save(medicalRecord);
        return "redirect:/medicalRecords";
    }*/
}
