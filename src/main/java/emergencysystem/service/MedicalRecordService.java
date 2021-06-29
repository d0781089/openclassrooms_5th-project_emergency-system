package emergencysystem.service;

import emergencysystem.dao.MedicalRecordRepository;
import emergencysystem.model.MedicalRecord;
import emergencysystem.model.Person;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.time.LocalDate;
import java.util.*;
import java.util.stream.Collectors;

@Service
public class MedicalRecordService {

    @Autowired
    private MedicalRecordRepository medicalRecordRepository;

    @Autowired
    private PersonService personService;

    private static final Logger logger = LogManager.getLogger(MedicalRecordService.class);
    private String minimumBirthDateRequired = LocalDate.now().minusYears(18).toString();

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
            /*medicalRecordToUpdate.setFirstName(medicalRecord.getFirstName()); // Todo: First/Last names must not change
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

        List<MedicalRecord> childrenList = medicalRecordRepository.getByBirthDateAfter(minimumBirthDateRequired);
        logger.debug("[Persons list covered by the firestation] Children medical records: " + childrenList);

        List<MedicalRecord> adultsList = medicalRecordRepository
                .getByBirthDateBeforeOrBirthDateEquals(minimumBirthDateRequired, minimumBirthDateRequired);
        logger.debug("[Persons list covered by the firestation] Adults medical records: " + adultsList);

        Set<String> medicalRecordFirstNames = persons.stream()
                .map(Person::getFirstName)
                .collect(Collectors.toSet());

        Set<String> medicalRecordLastNames = persons.stream()
                .map(Person::getLastName)
                .collect(Collectors.toSet());

        List<MedicalRecord> childrenListFiltered =
                childrenList.stream().filter(s -> medicalRecordFirstNames.contains(s.getFirstName()))
                        .filter(s -> medicalRecordLastNames.contains(s.getLastName()))
                        .collect(Collectors.toList());

        logger.debug("[Persons list covered by the firestation] Filtered children list: " + childrenListFiltered);

        List<MedicalRecord> adultsListFiltered =
                adultsList.stream().filter(s -> medicalRecordFirstNames.contains(s.getFirstName()))
                        .filter(s -> medicalRecordLastNames.contains(s.getLastName()))
                        .collect(Collectors.toList());

        logger.debug("[Persons list covered by the firestation] Filtered adults list: " + adultsListFiltered);

        numberOfChildrenAndAdults.put("children", childrenListFiltered.size());
        numberOfChildrenAndAdults.put("adults", adultsListFiltered.size());

        return numberOfChildrenAndAdults;
    }

    /*public Map<Map<String, List<Person>>, Map<String, List<Person>>> getChildrenByAddress(String address) {

        List<Person> personsCovered = personService.getPersonsByAddress(address);

        logger.debug("[Children list covered by the address] Retrieved covered persons: " + personsCovered);

        List<Person> childrenCovered = medicalRecordRepository.getByBirthDateBeforeOrBirthDateEquals(personsCovered);
        List<Person> adultsCovered = medicalRecordRepository.getByBirthDateAfter(personsCovered);
    }*/
}
