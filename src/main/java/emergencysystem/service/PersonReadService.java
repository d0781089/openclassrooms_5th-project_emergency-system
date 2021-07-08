package emergencysystem.service;

import emergencysystem.model.FireStation;
import emergencysystem.model.MedicalRecord;
import emergencysystem.model.Person;
import emergencysystem.dao.PersonRepository;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.time.LocalDate;
import java.time.Period;
import java.util.*;
import java.util.stream.Collectors;

@Service
public class PersonReadService {

    @Autowired
    private PersonRepository personRepository;

    @Autowired
    private FireStationService fireStationService;

    @Autowired
    private MedicalRecordReadService medicalRecordReadService;

    private static final Logger logger = LogManager.getLogger(PersonReadService.class);

    public List<Person> getPersons() {

        return personRepository.findAll();
    }

    public Person getPersonById(Long id) {

        return personRepository.findById(id).get();
    }

    public List<Person> getPersonsByAddress(String address) {

        logger.debug("[COVERED] Retrieve fire station address: " + address);

        return personRepository.getByAddress(address);
    }

    public List<Map<String, String>> getPersonsByFirstNameAndLastName(String firstName, String lastName) {

        List<Person> persons = personRepository.getByFirstNameAndLastName(firstName, lastName);
        logger.debug("[PERSONINFO] Retrieve persons: " + persons);

        List<MedicalRecord> medicalRecords = medicalRecordReadService.getMedicalRecords();
        logger.debug("[PERSONINFO] Retrieve medical records: " + medicalRecords);

        List<Map<String, String>> result = new ArrayList<>();

        persons.forEach( p -> {
            Map<String, String> person = new TreeMap<>();

            person.put("firstName", p.getFirstName());
            person.put("lastName", p.getLastName());
            person.put("address", p.getAddress());
            person.put("email", p.getEmail());
            medicalRecords.stream()
                    .filter(m -> firstName.equals(m.getFirstName()))
                    .filter(m -> lastName.equals(m.getLastName()))
                    .forEach(m -> {
                        person.put("age", String.valueOf(Period.between(m.getBirthDate().toLocalDate()
                                , LocalDate.now()).getYears()));
                        person.put("medications", m.getMedications().toString());
                        person.put("allergies", m.getAllergies().toString());
                    });

            logger.debug("[PERSONINFO] Add person: " + person);
            result.add(person);
        });

        return result;
    }

    public Set<String> getEmailsByCity(String city) {

        Set<String> emails = personRepository.getByCity(city).stream()
                .map(Person::getEmail)
                .collect(Collectors.toSet());

        return emails;
    }

    public Map<String, List<Map<String, String>>> getPersonsByStations(List<Integer> stations) {

        Map<String, List<Map<String, String>>> result = new TreeMap<>();
        List<Map<String, String>> personsByAddress = new ArrayList<>();

        stations.forEach(station -> {
            List<FireStation> fireStations = fireStationService.getFireStationByStation(station);

            Set<String> addressesByStation = fireStations.stream()
                    .map(FireStation::getAddress)
                    .collect(Collectors.toSet());

            addressesByStation.forEach( address -> {
                logger.debug("[FLOOD] Get address: " + address);

                List<Person> persons = personRepository.getByAddress(address);
                logger.debug("[FLOOD] Get persons: " + persons);

                List<MedicalRecord> medicalRecords = medicalRecordReadService.getByFirstNameAndLastName(persons);
                logger.debug("[FLOOD] Get medical records: " + medicalRecords);

                medicalRecords.forEach(medicalRecord -> {
                    Map<String, String> person = new TreeMap<>();

                    person.put("firstName", medicalRecord.getFirstName());
                    person.put("lastName", medicalRecord.getLastName());
                    person.put("phone", persons.stream()
                            .filter(p -> medicalRecord.getFirstName().equals(p.getFirstName()))
                            .filter(p -> medicalRecord.getLastName().equals(p.getLastName()))
                            .map(Person::getPhone)
                            .collect(Collectors.joining()));
                    person.put("age", String.valueOf(Period.between(medicalRecord.getBirthDate().toLocalDate()
                            , LocalDate.now()).getYears()));
                    person.put("medications", medicalRecord.getMedications().toString());
                    person.put("allergies", medicalRecord.getAllergies().toString());

                    personsByAddress.add(person);
                });

                result.put(address, personsByAddress);
            });
        });

        return result;
    }
}
