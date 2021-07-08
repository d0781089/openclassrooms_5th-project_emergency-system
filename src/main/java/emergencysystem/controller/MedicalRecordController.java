package emergencysystem.controller;

import emergencysystem.model.MedicalRecord;
import emergencysystem.service.MedicalRecordReadService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Map;

@RestController
public class MedicalRecordController {

    @Autowired
    private MedicalRecordReadService medicalRecordReadService;

    @GetMapping("/medicalRecords")
    public List<MedicalRecord> getMedicalRecords() {

        return medicalRecordReadService.getMedicalRecords();
    }

    @GetMapping("/medicalRecords/{id}")
    public MedicalRecord getMedicalRecordById(@PathVariable Long id) {

        return medicalRecordReadService.getMedicalRecordById(id);
    }

    @PostMapping("/createMedicalRecord")
    public MedicalRecord createMedicalRecord(@RequestBody MedicalRecord medicalRecord) {

        return medicalRecordReadService.createMedicalRecord(medicalRecord);
    }

    @PostMapping("/createMedicalRecords")
    public List<MedicalRecord> createMedicalRecord(@RequestBody List<MedicalRecord> medicalRecords) {

        return medicalRecordReadService.createMedicalRecords(medicalRecords);
    }

    @PutMapping("/updateMedicalRecord")
    public MedicalRecord updateMedicalRecord(@RequestBody MedicalRecord medicalRecord) {

        return medicalRecordReadService.updateMedicalRecord(medicalRecord);
    }

    @DeleteMapping("/medicalRecords/{id}")
    public String deleteMedicalRecordById(@PathVariable Long id) {

        return medicalRecordReadService.deleteMedicalRecord(id);
    }

    @GetMapping("/childAlert")
    public Map<String, List<Map<String, String>>> getChildrenByAddress(@RequestParam String address) {

        return medicalRecordReadService.getChildrenByAddress(address);
    }

    @GetMapping("/fire")
    public Map<Map<String, Integer>, List<Map<String, String>>> getPersonsByAddress(@RequestParam String address) {

        return medicalRecordReadService.getPersonsByAddress(address);
    }
}
