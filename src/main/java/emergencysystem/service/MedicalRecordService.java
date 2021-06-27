package emergencysystem.service;

import emergencysystem.dao.MedicalRecordRepository;
import emergencysystem.model.MedicalRecord;
import emergencysystem.model.Person;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.time.LocalDate;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Optional;

@Service
public class MedicalRecordService {

    @Autowired
    private MedicalRecordRepository medicalRecordRepository;

    private static final Logger logger = LogManager.getLogger(MedicalRecordService.class);

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

    public Map<String, Integer> getNumberOfChildrenAndAdults(List<Person> persons) {

        logger.debug("[Persons list covered by the firestation] Retrieved covered persons: " + persons);

        Map<String, Integer> numberOfChildrenAndAdults = new HashMap<String, Integer>();
        String minimumBirthDateRequired = LocalDate.now().minusYears(18).toString();
        numberOfChildrenAndAdults.put("children", medicalRecordRepository
                .countByBirthDateAfter(minimumBirthDateRequired));
        numberOfChildrenAndAdults.put("adults", medicalRecordRepository
                .countByBirthDateBeforeOrBirthDateEquals(minimumBirthDateRequired,minimumBirthDateRequired));

        return numberOfChildrenAndAdults;
    }
}
