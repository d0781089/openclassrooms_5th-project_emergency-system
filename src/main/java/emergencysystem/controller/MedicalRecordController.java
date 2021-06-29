package emergencysystem.controller;

import emergencysystem.model.MedicalRecord;
import emergencysystem.model.Person;
import emergencysystem.service.MedicalRecordService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Map;

@RestController
//@Controller
//@RequestMapping("/medicalRecords")
public class MedicalRecordController {

    @Autowired
    private MedicalRecordService medicalRecordService;

    @PostMapping("/createMedicalRecord")
    public MedicalRecord createMedicalRecord(@RequestBody MedicalRecord medicalRecord) {

        return medicalRecordService.createMedicalRecord(medicalRecord);
    }

    @PostMapping("/createMedicalRecords")
    public List<MedicalRecord> createMedicalRecord(@RequestBody List<MedicalRecord> medicalRecords) {

        return medicalRecordService.createMedicalRecords(medicalRecords);
    }

    @GetMapping("/medicalRecords/{id}")
    public MedicalRecord getMedicalRecordById(@PathVariable Long id) {

        return medicalRecordService.getMedicalRecordById(id);
    }

    @GetMapping("/medicalRecords")
    public List<MedicalRecord> getMedicalRecords() {

        return medicalRecordService.getMedicalRecords();
    }

    @PutMapping("/updateMedicalRecord")
    public MedicalRecord updateMedicalRecord(@RequestBody MedicalRecord medicalRecord) {

        return medicalRecordService.updateMedicalRecord(medicalRecord);
    }

    @DeleteMapping("/medicalRecords/{id}")
    public String deleteMedicalRecord(@PathVariable Long id) {

        return medicalRecordService.deleteMedicalRecord(id);
    }

    @GetMapping("/childAlert")
    public Map<String, Map<String, Map<Integer, Map<String, String>>>> getChildrenByAddress(@RequestParam String address) {

        return medicalRecordService.getChildrenByAddress(address);
    }

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
