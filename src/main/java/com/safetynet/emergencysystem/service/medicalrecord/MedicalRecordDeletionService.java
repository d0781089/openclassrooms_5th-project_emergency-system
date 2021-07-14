package com.safetynet.emergencysystem.service.medicalrecord;

import com.safetynet.emergencysystem.dao.MedicalRecordRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class MedicalRecordDeletionService {

    @Autowired
    private MedicalRecordRepository medicalRecordRepository;

    public void deleteMedicalRecord(Long id) {

        medicalRecordRepository.deleteById(id);
    }
}
