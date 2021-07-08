package emergencysystem.service;

import com.fasterxml.jackson.databind.ser.FilterProvider;
import com.fasterxml.jackson.databind.ser.impl.SimpleBeanPropertyFilter;
import com.fasterxml.jackson.databind.ser.impl.SimpleFilterProvider;
import emergencysystem.dao.MedicalRecordRepository;
import emergencysystem.model.*;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.converter.json.MappingJacksonValue;
import org.springframework.stereotype.Service;

import java.sql.Date;
import java.time.LocalDate;
import java.time.Period;
import java.util.*;
import java.util.stream.Collectors;

@Service
public class MedicalRecordReadService {

    @Autowired
    private PersonReadService personReadService;

    @Autowired
    private FireStationReadService fireStationReadService;

    @Autowired
    private MedicalRecordRepository medicalRecordRepository;

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

    public MappingJacksonValue getChildrenByAddress(String address) {

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

        List<PersonWithAge> children = new ArrayList<>();
        List<PersonWithAge> adults = new ArrayList<>();

        for(MedicalRecord m : childrenByFirstNameAndLastName) {
            PersonWithAge child = new PersonWithAge();

            child.setFirstName(m.getFirstName());
            child.setLastName(m.getLastName());
            child.setAge(Period.between(m.getBirthDate().toLocalDate(), LocalDate.now()).getYears());

            logger.debug("[CHILDALERT] Add child: " + child);
            children.add(child);
        }

        logger.debug("[CHILDALERT] Get children: " + children);

        for(MedicalRecord m : adultsByFirstNameAndLastName) {
            PersonWithAge adult = new PersonWithAge();

            adult.setFirstName(m.getFirstName());
            adult.setLastName(m.getLastName());
            adult.setAge(Period.between(m.getBirthDate().toLocalDate(), LocalDate.now()).getYears());

            logger.debug("[CHILD-ALERT] Add adult: " + adult);
            adults.add(adult);
        }

        ResultChildAlert resultChildAlert = new ResultChildAlert();
        resultChildAlert.setChildren(children);
        resultChildAlert.setAdults(adults);

        SimpleBeanPropertyFilter personFilter = SimpleBeanPropertyFilter.filterOutAllExcept(
                "firstName", "lastName", "age");
        FilterProvider filters = new SimpleFilterProvider().addFilter("personWithAge", personFilter);
        MappingJacksonValue result = new MappingJacksonValue(resultChildAlert);
        result.setFilters(filters);

        return result;
    }

    public MappingJacksonValue getPersonsByAddress(String address) {

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

        List<PersonWithMedicalRecords> persons = new ArrayList<>();

        medicalRecordsByFirstNameAndLastName.forEach(medicalRecord -> {
            PersonWithMedicalRecords person = new PersonWithMedicalRecords();

            person.setFirstName(medicalRecord.getFirstName());
            person.setLastName(medicalRecord.getLastName());
            person.setPhone(personsByAddress.stream()
                    .filter(p -> medicalRecord.getFirstName().equals(p.getFirstName()))
                    .filter(p -> medicalRecord.getLastName().equals(p.getLastName()))
                    .map(Person::getPhone)
                    .collect(Collectors.joining()));
            person.setAge(Period.between(medicalRecord.getBirthDate().toLocalDate()
                    , LocalDate.now()).getYears());
            person.setMedications(medicalRecord.getMedications());
            person.setAllergies(medicalRecord.getAllergies());
            persons.add(person);
        });

        ResultFire resultFire = new ResultFire();
        resultFire.setStation(station);
        resultFire.setPersons(persons);

        SimpleBeanPropertyFilter personFilter = SimpleBeanPropertyFilter.filterOutAllExcept(
                "firstName", "lastName", "phone", "age", "medications", "allergies");
        FilterProvider filterList = new SimpleFilterProvider().addFilter("personWithMedicalRecordsFilter", personFilter);
        MappingJacksonValue result = new MappingJacksonValue(resultFire);
        result.setFilters(filterList);

        return result;
    }
}
