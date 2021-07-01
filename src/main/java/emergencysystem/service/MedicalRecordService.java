package emergencysystem.service;

import emergencysystem.dao.MedicalRecordRepository;
import emergencysystem.model.MedicalRecord;
import emergencysystem.model.Person;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.apache.tomcat.jni.Local;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.thymeleaf.standard.expression.Each;

import java.sql.Date;
import java.time.LocalDate;
import java.time.Period;
import java.time.format.DateTimeFormatter;
import java.time.temporal.TemporalAmount;
import java.util.*;
import java.util.stream.Collectors;

@Service
public class MedicalRecordService {

    @Autowired
    private MedicalRecordRepository medicalRecordRepository;

    @Autowired
    private PersonService personService;

    @Autowired
    private FireStationService fireStationService;

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

        logger.debug("[COVERED] Retrieved covered persons: " + persons);

        //DateTimeFormatter formatter = DateTimeFormatter.ofPattern("dd/MM/yyyy");
        Date minimumBirthDateRequired = Date.valueOf(LocalDate.now().minusYears(18)); //.format(formatter));
        logger.debug("[COVERED] Minimum birth date required: " + minimumBirthDateRequired);

        Map<String, Integer> numberOfChildrenAndAdults = new HashMap<String, Integer>();

        List<MedicalRecord> childrenList = medicalRecordRepository.getByBirthDateGreaterThan(minimumBirthDateRequired);
        logger.debug("[COVERED] Children medical records: " + childrenList);

        List<MedicalRecord> adultsList = medicalRecordRepository
                .getByBirthDateLessThanEqual(minimumBirthDateRequired);
        logger.debug("[COVERED] Adults medical records: " + adultsList);

        Set<String> firstNamesSet = persons.stream()
                .map(Person::getFirstName)
                .collect(Collectors.toSet());

        Set<String> lastNameSet = persons.stream()
                .map(Person::getLastName)
                .collect(Collectors.toSet());

        List<MedicalRecord> childrenListFiltered =
                childrenList.stream().filter(s -> firstNamesSet.contains(s.getFirstName()))
                        .filter(s -> lastNameSet.contains(s.getLastName()))
                        .collect(Collectors.toList());

        logger.debug("[COVERED] Filtered children list: " + childrenListFiltered);

        List<MedicalRecord> adultsListFiltered =
                adultsList.stream().filter(s -> firstNamesSet.contains(s.getFirstName()))
                        .filter(s -> lastNameSet.contains(s.getLastName()))
                        .collect(Collectors.toList());

        logger.debug("[COVERED] Filtered adults list: " + adultsListFiltered);

        numberOfChildrenAndAdults.put("children", childrenListFiltered.size());
        numberOfChildrenAndAdults.put("adults", adultsListFiltered.size());

        return numberOfChildrenAndAdults;
    }

    public Map<String, Map<String, Map<Integer, Map<String, String>>>> getChildrenByAddress(String address) {

        //DateTimeFormatter formatter = DateTimeFormatter.ofPattern("dd/MM/yyyy");
        Date minimumBirthDateRequired = Date.valueOf(LocalDate.now().minusYears(18)); //.format(formatter));
        List<Person> persons = personService.getPersonsByAddress(address);

        logger.debug("[CHILD-ALERT] Retrieved covered persons: " + persons);

        List<MedicalRecord> childrenList = medicalRecordRepository.getByBirthDateGreaterThan(minimumBirthDateRequired);
        logger.debug("[CHILD-ALERT] Children medical records: " + childrenList);

        List<MedicalRecord> adultsList = medicalRecordRepository
                .getByBirthDateLessThanEqual(minimumBirthDateRequired);
        logger.debug("[CHILD-ALERT] Adults medical records: " + adultsList);

        Set<String> firstNamesSet = persons.stream()
                .map(Person::getFirstName)
                .collect(Collectors.toSet());

        Set<String> lastNameSet = persons.stream()
                .map(Person::getLastName)
                .collect(Collectors.toSet());

        List<MedicalRecord> childrenListFiltered =
                childrenList.stream().filter(s -> firstNamesSet.contains(s.getFirstName()))
                        .filter(s -> lastNameSet.contains(s.getLastName()))
                        .collect(Collectors.toList());

        logger.debug("[CHILD-ALERT] Adults medical records: " + childrenListFiltered);

        List<MedicalRecord> adultsListFiltered =
                adultsList.stream().filter(s -> firstNamesSet.contains(s.getFirstName()))
                        .filter(s -> lastNameSet.contains(s.getLastName()))
                        .collect(Collectors.toList());

        logger.debug("[CHILD-ALERT] Adults medical records: " + adultsListFiltered);

        Map<Integer, Map<String, String>> childrenResult = new TreeMap<>();
        Map<Integer, Map<String, String>> adultsResult = new TreeMap<>();

        for(MedicalRecord m : childrenListFiltered) {
            Map<String, String> childData = new TreeMap<>();
            childData.put("Nom", m.getLastName());
            childData.put("Prénom", m.getFirstName());
            childData.put("Âge", String.valueOf(Period.between(m.getBirthDate().toLocalDate(), LocalDate.now()).getYears()));
            childrenResult.put(childrenResult.size() + 1, childData);
        }

        logger.debug("[CHILD-ALERT] Children data: " + childrenResult);

        for(MedicalRecord m : adultsListFiltered) {
            Map<String, String> adultData = new TreeMap<>();
            adultData.clear();
            adultData.put("Nom", m.getLastName());
            adultData.put("Prénom", m.getFirstName());
            adultData.put("Âge", String.valueOf(Period.between(m.getBirthDate().toLocalDate(), LocalDate.now()).getYears()));
            logger.debug("[CHILD-ALERT] Single adult data: " + adultData);
            adultsResult.put(adultsResult.size() + 1, adultData);
        }

        logger.debug("[CHILD-ALERT] Adults data: " + adultsResult);

        Map<String, Map<Integer, Map<String, String>>> resultList = new TreeMap<>();
        resultList.put("Enfant(s)", childrenResult);
        resultList.put("Reste du foyer", adultsResult);

        /*Map<String, Map<String, List<MedicalRecord>>> users = new TreeMap<>();
        users.put("adults", resultList);*/

        Map<String, Map<String, Map<Integer, Map<String, String>>>> result =
                new HashMap<String, Map<String, Map<Integer, Map<String, String>>>>();
        result.put("Liste des enfants couverent par l'adresse : " + address + " comportant "
                + childrenListFiltered.size() + " enfant(s) et " + adultsListFiltered.size()
                + " adulte(s)", resultList);

        return result;
    }

    public Map<String, Map<Integer, Map<String, String>>> getResidentsByAddress(String address) {

        int station = fireStationService.getFireStationByAddress(address).getStation();

        List<Person> personsCovered = personService.getPersonsByAddress(address);

        List<MedicalRecord> medicalRecords = medicalRecordRepository.findAll();

        Set<String> firstNameFilter = personsCovered.stream()
                .map(Person::getFirstName)
                .collect(Collectors.toSet());

        Set<String> lastNameFilter = personsCovered.stream()
                .map(Person::getLastName)
                .collect(Collectors.toSet());

        List<MedicalRecord> medicalRecordsFiltered = medicalRecords.stream()
                .filter(m -> firstNameFilter.contains(m.getFirstName()))
                .filter(m -> lastNameFilter.contains(m.getLastName()))
                .collect(Collectors.toList());

        Map<Integer, Map<String, String>> residents = new TreeMap<Integer, Map<String, String>>();

        for(MedicalRecord m : medicalRecordsFiltered) {
            Map<String, String> resident = new TreeMap<String, String>();
            resident.put("Nom", m.getLastName());
            resident.put("Prénom", m.getFirstName());
            resident.put("Téléphone", personsCovered.stream()
                    .filter(p -> m.getFirstName().equals(p.getFirstName()))
                    .filter(p -> m.getLastName().equals(p.getLastName()))
                    .map(Person::getPhone)
                    .collect(Collectors.joining()));
            personsCovered.stream()
                    .map(Person::getPhone)
                    .forEach(p -> logger.debug("[FIREALERT] Medical record: " + m.getFirstName() + " | Person: " + p));
            resident.put("Âge", String.valueOf(Period.between(m.getBirthDate().toLocalDate(), LocalDate.now()).getYears()));
            resident.put("Allergie(s)", m.getAllergies().toString());
            resident.put("Médicament(s)", m.getMedications().toString());
            residents.put(residents.size() + 1, resident);
        }

        Map<String, Map<Integer, Map<String, String>>> result = new HashMap<String, Map<Integer, Map<String, String>>>();
        result.put("Liste des habitants par l'adresse " + address + " comportant " + residents.size() + " habitant(s) :", residents);

        return result;
    }
}
