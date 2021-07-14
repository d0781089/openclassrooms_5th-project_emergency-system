package com.safetynet.emergencysystem.service.medicalrecord;

import com.safetynet.emergencysystem.dao.MedicalRecordRepository;
import com.safetynet.emergencysystem.model.MedicalRecord;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Optional;

@Service
public class MedicalRecordUpdateService {

    @Autowired
    private MedicalRecordRepository medicalRecordRepository;

    public MedicalRecord updateMedicalRecord(MedicalRecord medicalRecord) {

        MedicalRecord medicalRecordUpdated;
        Optional<MedicalRecord> medicalRecordOptional = medicalRecordRepository.findById(medicalRecord.getId());

        if(medicalRecordOptional.isPresent()) {
            medicalRecordUpdated = medicalRecordOptional.get();
            medicalRecordUpdated.setBirthDate(medicalRecord.getBirthDate());
            medicalRecordUpdated.setMedications(medicalRecord.getMedications());
            medicalRecordUpdated.setAllergies(medicalRecord.getAllergies());
            medicalRecordRepository.save(medicalRecordUpdated);
        } else {
            return new MedicalRecord();
        }
        return medicalRecordUpdated;
    }
}
