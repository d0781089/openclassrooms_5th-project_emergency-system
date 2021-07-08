package emergencysystem.service;

import com.fasterxml.jackson.databind.ser.FilterProvider;
import com.fasterxml.jackson.databind.ser.impl.SimpleBeanPropertyFilter;
import com.fasterxml.jackson.databind.ser.impl.SimpleFilterProvider;
import emergencysystem.model.*;
import emergencysystem.dao.PersonRepository;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.hibernate.engine.spi.Mapping;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.converter.json.MappingJacksonValue;
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
    private FireStationReadService fireStationReadService;

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

    public MappingJacksonValue getPersonsByFirstNameAndLastName(String firstName, String lastName) {

        List<Person> persons = personRepository.getByFirstNameAndLastName(firstName, lastName);
        logger.debug("[PERSONINFO] Retrieve persons: " + persons);

        List<MedicalRecord> medicalRecords = medicalRecordReadService.getMedicalRecords();
        logger.debug("[PERSONINFO] Retrieve medical records: " + medicalRecords);

        List<PersonWithMedicalRecords> personsFiltered = new ArrayList<>();

        persons.forEach( p -> {
            PersonWithMedicalRecords person = new PersonWithMedicalRecords();

            person.setFirstName(p.getFirstName());
            person.setLastName(p.getLastName());
            person.setAddress(p.getAddress());
            person.setEmail(p.getEmail());
            medicalRecords.stream()
                    .filter(m -> firstName.equals(m.getFirstName()))
                    .filter(m -> lastName.equals(m.getLastName()))
                    .forEach(m -> {
                        person.setAge(Period.between(m.getBirthDate().toLocalDate()
                                , LocalDate.now()).getYears());
                        person.setMedications(m.getMedications());
                        person.setAllergies(m.getAllergies());
                    });

            logger.debug("[PERSONINFO] Add person: " + person);
            personsFiltered.add(person);
        });

        SimpleBeanPropertyFilter personInfoFilter = SimpleBeanPropertyFilter.filterOutAllExcept(
                "firstName", "lastName", "address", "age", "email", "medications", "allergies");
        FilterProvider filterList = new SimpleFilterProvider().addFilter("personWithMedicalRecordsFilter", personInfoFilter);
        MappingJacksonValue result  = new MappingJacksonValue(personsFiltered);
        result.setFilters(filterList);

        return result;
    }

    public Set<String> getEmailsByCity(String city) {

        Set<String> emails = personRepository.getByCity(city).stream()
                .map(Person::getEmail)
                .collect(Collectors.toSet());

        return emails;
    }

    public MappingJacksonValue getPersonsByStations(List<Integer> stations) {

        Map<String, List<PersonWithMedicalRecords>> personsByAddressResult = new HashMap<>();

        stations.forEach(station -> {
            List<FireStation> fireStations = fireStationReadService.getFireStationByStation(station);

            Set<String> addressesByStation = fireStations.stream()
                    .map(FireStation::getAddress)
                    .collect(Collectors.toSet());

            addressesByStation.forEach( address -> {
                List<PersonWithMedicalRecords> personsByAddress = new ArrayList<>();

                logger.debug("[FLOOD] Get address: " + address);

                List<Person> persons = personRepository.getByAddress(address);
                logger.debug("[FLOOD] Get persons: " + persons);

                List<MedicalRecord> medicalRecords = medicalRecordReadService.getByFirstNameAndLastName(persons);
                logger.debug("[FLOOD] Get medical records: " + medicalRecords);

                medicalRecords.forEach(medicalRecord -> {
                    PersonWithMedicalRecords person = new PersonWithMedicalRecords();

                    person.setFirstName(medicalRecord.getFirstName());
                    person.setLastName(medicalRecord.getLastName());
                    person.setPhone(persons.stream()
                            .filter(p -> medicalRecord.getFirstName().equals(p.getFirstName()))
                            .filter(p -> medicalRecord.getLastName().equals(p.getLastName()))
                            .map(Person::getPhone)
                            .collect(Collectors.joining()));
                    person.setAge(Period.between(medicalRecord.getBirthDate().toLocalDate()
                            , LocalDate.now()).getYears());
                    person.setMedications(medicalRecord.getMedications());
                    person.setAllergies(medicalRecord.getAllergies());

                    personsByAddress.add(person);
                });

                personsByAddressResult.put(address, personsByAddress);
            });
        });

        SimpleBeanPropertyFilter personFloodFilter = SimpleBeanPropertyFilter.filterOutAllExcept(
                "firstName", "lastName", "phone", "age", "medications", "allergies");
        FilterProvider filterList = new SimpleFilterProvider().addFilter("personWithMedicalRecordsFilter", personFloodFilter);
        MappingJacksonValue result = new MappingJacksonValue(personsByAddressResult);
        result.setFilters(filterList);

        return result;
    }
}
