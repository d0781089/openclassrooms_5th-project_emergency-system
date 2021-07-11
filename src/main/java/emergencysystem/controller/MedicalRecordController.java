package emergencysystem.controller;

import emergencysystem.model.MedicalRecord;
import emergencysystem.service.MedicalRecordCreationService;
import emergencysystem.service.MedicalRecordDeletionService;
import emergencysystem.service.MedicalRecordReadService;
import emergencysystem.service.MedicalRecordUpdateService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.converter.json.MappingJacksonValue;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Map;

@RestController
public class MedicalRecordController {

    @Autowired
    private MedicalRecordCreationService medicalRecordCreationService;
    @Autowired
    private MedicalRecordReadService medicalRecordReadService;
    @Autowired
    private MedicalRecordUpdateService medicalRecordUpdateService;
    @Autowired
    private MedicalRecordDeletionService medicalRecordDeletionService;

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

        return medicalRecordCreationService.createMedicalRecord(medicalRecord);
    }

    @PostMapping("/createMedicalRecords")
    public List<MedicalRecord> createMedicalRecord(@RequestBody List<MedicalRecord> medicalRecords) {

        return medicalRecordCreationService.createMedicalRecords(medicalRecords);
    }

    @PutMapping("/updateMedicalRecord")
    public MedicalRecord updateMedicalRecord(@RequestBody MedicalRecord medicalRecord) {

        return medicalRecordUpdateService.updateMedicalRecord(medicalRecord);
    }

    @DeleteMapping("/medicalRecords/{id}")
    public void deleteMedicalRecordById(@PathVariable Long id) {

        medicalRecordDeletionService.deleteMedicalRecord(id);
    }

    @GetMapping("/childAlert")
    public MappingJacksonValue getChildrenByAddress(@RequestParam String address) {

        return medicalRecordReadService.getChildrenByAddress(address);
    }

    @GetMapping("/fire")
    public MappingJacksonValue getPersonsByAddress(@RequestParam String address) {

        return medicalRecordReadService.getPersonsByAddress(address);
    }
}
