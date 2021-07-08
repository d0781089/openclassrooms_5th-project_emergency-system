package emergencysystem.service;

import emergencysystem.dao.MedicalRecordRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class MedicalRecordDeletionService {

    @Autowired
    private MedicalRecordRepository medicalRecordRepository;

    public String deleteMedicalRecord(Long id) {

        medicalRecordRepository.deleteById(id);

        return "The medical record was deleted successfully.";
    }
}
