package emergencysystem.controller;

import emergencysystem.model.MedicalRecord;
import emergencysystem.service.MedicalRecordService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Map;

@RestController
public class MedicalRecordController {

    @Autowired
    private MedicalRecordService medicalRecordService;

    @GetMapping("/medicalRecords")
    public List<MedicalRecord> getMedicalRecords() {

        return medicalRecordService.getMedicalRecords();
    }

    @GetMapping("/medicalRecords/{id}")
    public MedicalRecord getMedicalRecordById(@PathVariable Long id) {

        return medicalRecordService.getMedicalRecordById(id);
    }

    @PostMapping("/createMedicalRecord")
    public MedicalRecord createMedicalRecord(@RequestBody MedicalRecord medicalRecord) {

        return medicalRecordService.createMedicalRecord(medicalRecord);
    }

    @PostMapping("/createMedicalRecords")
    public List<MedicalRecord> createMedicalRecord(@RequestBody List<MedicalRecord> medicalRecords) {

        return medicalRecordService.createMedicalRecords(medicalRecords);
    }

    @PutMapping("/updateMedicalRecord")
    public MedicalRecord updateMedicalRecord(@RequestBody MedicalRecord medicalRecord) {

        return medicalRecordService.updateMedicalRecord(medicalRecord);
    }

    @DeleteMapping("/medicalRecords/{id}")
    public String deleteMedicalRecordById(@PathVariable Long id) {

        return medicalRecordService.deleteMedicalRecord(id);
    }

    @GetMapping("/childAlert")
    public Map<String, List<Map<String, String>>> getChildrenByAddress(@RequestParam String address) {

        return medicalRecordService.getChildrenByAddress(address);
    }

    @GetMapping("/fire")
    public Map<Map<String, Integer>, List<Map<String, String>>> getPersonsByAddress(@RequestParam String address) {

        return medicalRecordService.getPersonsByAddress(address);
    }
}
