package emergencysystem.service;

import emergencysystem.dao.MedicalRecordRepository;
import emergencysystem.model.MedicalRecord;
import emergencysystem.model.Person;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.sql.Date;
import java.time.LocalDate;
import java.time.Period;
import java.util.*;
import java.util.stream.Collectors;

@Service
public class MedicalRecordReadService {

    @Autowired
    private MedicalRecordRepository medicalRecordRepository;

    @Autowired
    private PersonReadService personReadService;

    @Autowired
    private FireStationReadService fireStationReadService;

    private static final Logger logger = LogManager.getLogger(MedicalRecordReadService.class);

    public List<MedicalRecord> getMedicalRecords() {

        return medicalRecordRepository.findAll();
    }

    public MedicalRecord getMedicalRecordById(Long id) {

        return medicalRecordRepository.findById(id).get();
    }

    public List<MedicalRecord> getByFirstNameAndLastName(List<Person> persons) {

        List<String> firstNames = persons.stream()
                .map(Person::getFirstName)
                .collect(Collectors.toList());

        List<String> lastNames = persons.stream()
                .map(Person::getLastName)
                .collect(Collectors.toList());

        return medicalRecordRepository.getByFirstNameInAndLastNameIn(firstNames, lastNames);
    }

    public Map<String, Integer> getCountOfChildrenAndAdults(List<Person> persons) {

        logger.debug("[COVERED] Retrieve persons: " + persons);

        Date minimumBirthDateRequired = Date.valueOf(LocalDate.now().minusYears(18));
        logger.debug("[COVERED] Get minimum birth date: " + minimumBirthDateRequired);

        Map<String, Integer> countOfChildrenAndAdults = new HashMap<String, Integer>();

        List<MedicalRecord> children = medicalRecordRepository.getByBirthDateGreaterThan(minimumBirthDateRequired);
        logger.debug("[COVERED] Retrieve children medical records: " + children);

        List<MedicalRecord> adults = medicalRecordRepository.getByBirthDateLessThanEqual(minimumBirthDateRequired);
        logger.debug("[COVERED] Retrieve adults medical records: " + adults);

        Set<String> firstNames = persons.stream()
                .map(Person::getFirstName)
                .collect(Collectors.toSet());

        Set<String> lastNames = persons.stream()
                .map(Person::getLastName)
                .collect(Collectors.toSet());

        List<MedicalRecord> childrenByFirstNameAndLastName =
                children.stream().filter(medicalRecord -> firstNames.contains(medicalRecord.getFirstName()))
                        .filter(s -> lastNames.contains(s.getLastName()))
                        .collect(Collectors.toList());
        logger.debug("[COVERED] Get children by first and last name: " + childrenByFirstNameAndLastName);

        List<MedicalRecord> adultsByFirstNameAndLastName =
                adults.stream().filter(medicalRecord -> firstNames.contains(medicalRecord.getFirstName()))
                        .filter(s -> lastNames.contains(s.getLastName()))
                        .collect(Collectors.toList());
        logger.debug("[COVERED] Get children by first and last name: " + adultsByFirstNameAndLastName);

        countOfChildrenAndAdults.put("children", childrenByFirstNameAndLastName.size());
        countOfChildrenAndAdults.put("adults", adultsByFirstNameAndLastName.size());

        return countOfChildrenAndAdults;
    }

    public Map<String, List<Map<String, String>>> getChildrenByAddress(String address) {

        //Todo: Return the person objet properties and values, hide the non-requested

        Date minimumBirthDate = Date.valueOf(LocalDate.now().minusYears(18));

        List<Person> persons = personReadService.getPersonsByAddress(address);
        logger.debug("[CHILDALERT] Retrieve persons: " + persons);

        List<MedicalRecord> childrenMedicalRecords = medicalRecordRepository.getByBirthDateGreaterThan(minimumBirthDate);
        logger.debug("[CHILDALERT] Retrieve children medical records: " + childrenMedicalRecords);

        List<MedicalRecord> adultsMedicalRecords= medicalRecordRepository.getByBirthDateLessThanEqual(minimumBirthDate);
        logger.debug("[CHILDALERT] Retrieve adults medical records: " + adultsMedicalRecords);

        Set<String> firstNames = persons.stream()
                .map(Person::getFirstName)
                .collect(Collectors.toSet());

        Set<String> lastNames = persons.stream()
                .map(Person::getLastName)
                .collect(Collectors.toSet());

        List<MedicalRecord> childrenByFirstNameAndLastName =
                childrenMedicalRecords.stream().filter(medicalRecord -> firstNames.contains(medicalRecord.getFirstName())
                        && lastNames.contains(medicalRecord.getLastName()))
                        .collect(Collectors.toList());
        logger.debug("[CHILD-ALERT] Get children medical records by first and last name: " + childrenByFirstNameAndLastName);

        List<MedicalRecord> adultsByFirstNameAndLastName =
                adultsMedicalRecords.stream().filter(medicalRecord -> firstNames.contains(medicalRecord.getFirstName())
                        && lastNames.contains(medicalRecord.getLastName()))
                        .collect(Collectors.toList());
        logger.debug("[CHILDALERT] Get adults medical records by first and last name: " + adultsByFirstNameAndLastName);

        List<Map<String, String>> children = new ArrayList<>();
        List<Map<String, String>> adults = new ArrayList<>();

        for(MedicalRecord m : childrenByFirstNameAndLastName) {
            Map<String, String> child = new TreeMap<>();
            child.put("lastName", m.getLastName());
            child.put("firstName", m.getFirstName());
            child.put("age", String.valueOf(Period.between(m.getBirthDate().toLocalDate(), LocalDate.now()).getYears()));

            logger.debug("[CHILDALERT] Add child: " + child);
            children.add(child);
        }

        logger.debug("[CHILDALERT] Get children: " + children);

        for(MedicalRecord m : adultsByFirstNameAndLastName) {
            Map<String, String> adult = new TreeMap<>();
            adult.put("lastName", m.getLastName());
            adult.put("firstName", m.getFirstName());
            adult.put("age", String.valueOf(Period.between(m.getBirthDate().toLocalDate(), LocalDate.now()).getYears()));

            logger.debug("[CHILD-ALERT] Add adult: " + adult);
            adults.add(adult);
        }

        Map<String, List<Map<String, String>>> resultList = new TreeMap<>();

        logger.debug("[CHILD-ALERT] Adults data: " + children);
        resultList.put("children", children);

        logger.debug("[CHILDALERT] Get adults: " + adults);
        resultList.put("adults", adults);

        return resultList;
    }

    public Map<Map<String, Integer>, List<Map<String, String>>>  getPersonsByAddress(String address) {

        // Todo: Return the station number

        int station = fireStationReadService.getFireStationByAddress(address).getStation();

        List<Person> personsByAddress = personReadService.getPersonsByAddress(address);
        List<MedicalRecord> medicalRecords = medicalRecordRepository.findAll();

        Set<String> firstNames = personsByAddress.stream()
                .map(Person::getFirstName)
                .collect(Collectors.toSet());

        Set<String> lastNames = personsByAddress.stream()
                .map(Person::getLastName)
                .collect(Collectors.toSet());

        List<MedicalRecord> medicalRecordsByFirstNameAndLastName = medicalRecords.stream()
                .filter(medicalRecord -> firstNames.contains(medicalRecord.getFirstName())
                && lastNames.contains(medicalRecord.getLastName()))
                .collect(Collectors.toList());

        List<Map<String, String>> persons = new ArrayList<>();

        medicalRecordsByFirstNameAndLastName.forEach(medicalRecord -> {
            Map<String, String> person = new TreeMap<String, String>();

            person.put("lastName", medicalRecord.getLastName());
            person.put("firstName", medicalRecord.getFirstName());
            person.put("phone", personsByAddress.stream()
                    .filter(p -> medicalRecord.getFirstName().equals(p.getFirstName()))
                    .filter(p -> medicalRecord.getLastName().equals(p.getLastName()))
                    .map(Person::getPhone)
                    .collect(Collectors.joining()));

            personsByAddress.stream()
                    .map(Person::getPhone)
                    .forEach(phone -> logger.debug("[FIREALERT] " + medicalRecord.getFirstName() + "'s phone: " + phone));

            person.put("age", String.valueOf(Period.between(medicalRecord.getBirthDate().toLocalDate()
                    , LocalDate.now()).getYears()));
            person.put("allergies", medicalRecord.getAllergies().toString());
            person.put("medications", medicalRecord.getMedications().toString());
            persons.add(person);
        });

        Map<String, Integer> stationDisplay = new TreeMap<>();
        stationDisplay.put("station", station);

        Map<Map<String, Integer>, List<Map<String, String>>> result
                = new HashMap<Map<String, Integer>, List<Map<String, String>>>();
        result.put(stationDisplay, persons);

        return result;
    }
}
