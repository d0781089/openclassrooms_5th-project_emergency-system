package emergencysystem.service;

import emergencysystem.model.FireStation;
import emergencysystem.model.MedicalRecord;
import emergencysystem.model.Person;
import emergencysystem.dao.PersonRepository;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.apache.tomcat.jni.Local;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.time.LocalDate;
import java.time.Period;
import java.util.*;
import java.util.stream.Collectors;

@Service
public class PersonService {

    @Autowired
    private PersonRepository personRepository;

    @Autowired
    private FireStationService fireStationService;

    @Autowired
    private MedicalRecordService medicalRecordService;

    private static final Logger logger = LogManager.getLogger(PersonService.class);

    public Person createPerson(Person person) {

        return personRepository.save(person);
    }

    public List<Person> createPersons(List<Person> persons) {

        return personRepository.saveAll(persons);
    }

    public Person getPersonById(Long id) {

        return personRepository.findById(id).get(); // 'personRepository.getById(id)': Error 500
    }

    public List<Map<String, String>> getByFirstNameAndLastName(String firstName, String lastName) {

        List<Person> persons = personRepository.getByFirstNameAndLastName(firstName, lastName);
        logger.debug("[PERSONINFO] Retrieved persons: " + persons);
        List<MedicalRecord> medicalRecords = medicalRecordService.getMedicalRecords();
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
                        person.put("age", String.valueOf(Period.between(m.getBirthDate().toLocalDate(), LocalDate.now()).getYears()));
                        person.put("medications", m.getMedications().toString());
                        person.put("allergies", m.getAllergies().toString());
                    });

            logger.debug("[PERSONINFO] Added person: " + person);
            result.add(person);
        });

        return result;
    }

    public Set<String> getEmailByCity(String city) {

        // Using Set not retrieving duplicates
        Set<String> emails = personRepository.getByCity(city).stream()
                .map(Person::getEmail)
                .collect(Collectors.toSet());

        return emails;
    }

    public List<Person> getPersons() {

        return personRepository.findAll();
    }

    public Person updatePerson(Person person) {
        Person personToUpdate;
        Optional<Person> optionalPerson = personRepository.findById(person.getId());

        if(optionalPerson.isPresent()) {
            personToUpdate = optionalPerson.get();
            /*personToUpdate.setFirstName(person.getFirstName()); // Todo: First and Last names must not change
            personToUpdate.setLastName(person.getLastName());*/
            personToUpdate.setAddress(person.getAddress());
            personToUpdate.setCity(person.getCity());
            personToUpdate.setZip(person.getZip());
            personToUpdate.setPhone(person.getPhone());
            personToUpdate.setEmail(person.getEmail());
            personRepository.save(personToUpdate);
        } else {
            return new Person();
        }
        return personToUpdate;
    }

    public String deletePersonById(Long id) {

        personRepository.deleteById(id);

        return "The person was DELETED successfully!";
    }

    public List<Person> getPersonsByAddress(String address) {

        logger.debug("[COVERED] Retrieved fire station address: " + address);

        return personRepository.getByAddress(address);
    }

    public Map<String, List<Map<String, String>>> getPersonsByStations(List<Integer> stations) {

        Map<String, List<Map<String, String>>> result = new TreeMap<>();
        List<Map<String, String>> residents = new ArrayList<>();

        stations.forEach(s -> {
            List<FireStation> fireStations = fireStationService.getFireStationByStation(s);

            Set<String> stationAddresses = fireStations.stream()
                    .map(FireStation::getAddress)
                    .collect(Collectors.toSet());

            stationAddresses.forEach( address -> {
                logger.debug("[FLOOD] Address: " + address);
                List<Person> persons = personRepository.getByAddress(address);
                logger.debug("[FLOOD] Persons: " + persons);
                List<MedicalRecord> medicalRecords = medicalRecordService.getByFirstNameAndLastName(persons);
                logger.debug("[FLOOD] Medical records: " + medicalRecords);

                medicalRecords.forEach(m -> {
                    Map<String, String> resident = new TreeMap<>();
                    resident.put("firstName", m.getFirstName());
                    resident.put("lastName", m.getLastName());
                    resident.put("phone", persons.stream()
                            .filter(p -> m.getFirstName().equals(p.getFirstName()))
                            .filter(p -> m.getLastName().equals(p.getLastName()))
                            .map(Person::getPhone)
                            .collect(Collectors.joining()));
                    resident.put("age", String.valueOf(Period.between(m.getBirthDate().toLocalDate(), LocalDate.now()).getYears()));
                    resident.put("medications", m.getMedications().toString());
                    resident.put("allergies", m.getAllergies().toString());
                    residents.add(resident);
                });

                result.put(address, residents);
            });
        });

        return result;
    }
}
