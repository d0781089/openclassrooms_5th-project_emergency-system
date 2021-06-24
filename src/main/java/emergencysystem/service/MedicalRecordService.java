package emergencysystem.service;

import emergencysystem.dao.MedicalRecordRepository;
import emergencysystem.model.MedicalRecord;
import emergencysystem.model.Person;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class MedicalRecordService {

    @Autowired
    private MedicalRecordRepository medicalRecordRepository;

    public MedicalRecord createMedicalRecord(MedicalRecord medicalRecord) {

        return medicalRecordRepository.save(medicalRecord);
    }

    public List<MedicalRecord> createMedicalRecords(List<MedicalRecord> medicalRecords) {

        return medicalRecordRepository.saveAll(medicalRecords);
    }

    public MedicalRecord getMedicalRecordById(Long id) {

        return medicalRecordRepository.findById(id).get();
    }

    public List<MedicalRecord> getMedicalRecords() {

        return medicalRecordRepository.findAll();
    }

    public MedicalRecord updateMedicalRecord(MedicalRecord medicalRecord) {

        MedicalRecord medicalRecordToUpdate;
        Optional<MedicalRecord> medicalRecordOptional = medicalRecordRepository.findById(medicalRecord.getId());

        if(medicalRecordOptional.isPresent()) {
            medicalRecordToUpdate = medicalRecordOptional.get();
            /*medicalRecordToUpdate.setFirstName(medicalRecord.getFirstName()); // Todo: First and Last names must not change
            medicalRecordToUpdate.setLastName(medicalRecord.getLastName());*/
            medicalRecordToUpdate.setBirthDate(medicalRecord.getBirthDate());
            medicalRecordToUpdate.setMedications(medicalRecord.getMedications());
            medicalRecordToUpdate.setAllergies(medicalRecord.getAllergies());
            medicalRecordRepository.save(medicalRecordToUpdate);
        } else {
            return new MedicalRecord();
        }
        return medicalRecordToUpdate;
    }

    public String deleteMedicalRecord(Long id) {

        medicalRecordRepository.deleteById(id);

        return "The medical record was DELETED successfully!";
    }
}
