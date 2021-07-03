package emergencysystem.controller;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.node.ObjectNode;
import emergencysystem.model.FireStation;
import emergencysystem.model.JsonData;
import emergencysystem.model.MedicalRecord;
import emergencysystem.model.Person;
import emergencysystem.service.FireStationService;
import emergencysystem.service.JsonService;
import emergencysystem.service.MedicalRecordService;
import emergencysystem.service.PersonService;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.Set;

@RestController
//@Controller
//@RequestMapping("/persons")
public class PersonController {

    @Autowired
    private PersonService personService;

    @Autowired
    private FireStationService fireStationService;

    @Autowired
    private MedicalRecordService medicalRecordService;

    private static final Logger logger = LogManager.getLogger(PersonService.class);

    @PostMapping("/createPerson")
    public Person createPerson(@RequestBody Person person) {

        return personService.createPerson(person);
    }

    @PostMapping("/createPersons")
    public List<Person> createPersons(@RequestBody List<Person> persons) {

        return personService.createPersons(persons);
    }

    @GetMapping("/init")
    public String initializeData() throws IOException {

        JsonData jsonData = new JsonData();
        String file = "src/main/resources/data.json";
        String json = new String(Files.readAllBytes(Paths.get(file)));
        JsonNode jsonNode = JsonService.parse(json);
        //jsonData = fromJson(jsonNode, JsonData.class);
        List<Person> persons = new ArrayList<>();
        List<FireStation> fireStations = new ArrayList<>();
        List<MedicalRecord> medicalRecords = new ArrayList<>();

        jsonNode.get("persons").forEach(p -> {
            Person person = new Person();
            try {
                person = JsonService.fromJson(p, Person.class);
            } catch (JsonProcessingException e) {
                e.printStackTrace();
            }
            persons.add(person);
        });

        jsonNode.get("fireStations").forEach(f -> {
            FireStation fireStation = new FireStation();
            try {
                fireStation = JsonService.fromJson(f, FireStation.class);
            } catch (JsonProcessingException e) {
                e.printStackTrace();
            }
            fireStations.add(fireStation);
        });

        jsonNode.get("medicalRecords").forEach(m -> {
            ObjectNode o = (ObjectNode) m;
            DateTimeFormatter formatter = DateTimeFormatter.ofPattern("MM/dd/yyyy");
            o.put("birthDate", String.valueOf(LocalDate.parse(m.get("birthDate").asText(), formatter)));
            MedicalRecord medicalRecord = new MedicalRecord();
            try {
                medicalRecord = JsonService.fromJson(o, MedicalRecord.class);
            } catch (JsonProcessingException e) {
                e.printStackTrace();
            }
            medicalRecords.add(medicalRecord);
        });

        jsonData.setPersons(persons);
        jsonData.setFireStations(fireStations);
        jsonData.setMedicalRecords(medicalRecords);

        personService.createPersons(jsonData.getPersons());
        fireStationService.createFireStations(jsonData.getFireStations());
        medicalRecordService.createMedicalRecords(jsonData.getMedicalRecords());

        return "The \"data.json\" file was successfully initialized!";
    }

    @GetMapping("/persons/{id}")
    public Person getPersonById(@PathVariable Long id) {

        return personService.getPersonById(id);
    }

    @GetMapping("/persons")
    public List<Person> getPersons() throws IOException {

        return personService.getPersons();
    }

    @PutMapping("/updatePerson")
    public Person updatePerson(@RequestBody Person person) {

        return personService.updatePerson(person);
    }

    @DeleteMapping("/persons/{id}")
    public String deletePersonById(@PathVariable Long id) {

        return personService.deletePersonById(id);
    }

    @GetMapping("/flood/stations")
    public Map<String, List<Map<String, String>>> getPersonsByStations(@RequestParam List<Integer> stations) {

        return personService.getPersonsByStations(stations);
    }

    @GetMapping("/personInfo")
    public List<Map<String, String>> getByFirstNameAndLastName(@RequestParam String firstName, String lastName) {

        logger.debug("[PERSONINFO] " + firstName + " " + lastName);

        return personService.getByFirstNameAndLastName(firstName, lastName);
    }

    @GetMapping("/communityEmail")
    public Set<String> getEmailByCity(@RequestParam String city) {

        return personService.getEmailByCity(city);
    }

    /*private static final String sort = "all";
    JsonData jsonData = new JsonData();
    private PersonRepository personRepository;

    //@Autowired
    public PersonController(PersonRepository personRepository) throws IOException {
        this.personRepository = personRepository;
    }

    @RequestMapping(method= RequestMethod.GET)
    public String sortPersons(Model model) throws IOException {
        // Without rewrite
        //personRepository.saveAll(JsonService.getData(jsonData).getPersons());
        List<Person> persons = personRepository.findBySort(sort);
        persons.addAll(JsonService.getData(jsonData).getPersons());

        // With rewrite
        //List<Person> persons = JsonService.getData(jsonData).getPersons();
        if (persons != null) {
            model.addAttribute("persons", persons);
        }
        return "persons";
    }

    @RequestMapping(method=RequestMethod.POST)
    public String addToPersonsList(Person person) throws IOException {
        // Without rewrite
        person.setSort(sort);
        personRepository.save(person);

        // With rewrite
        //JsonData jsonData = new JsonData();
        //jsonData = JsonService.getData(jsonData);
        //jsonData.setPersons(personRepository.findAll());
        //JsonNode jsonNode = JsonService.toJson(jsonData);
        //System.out.println(JsonService.stringify(jsonNode, true));

        return "redirect:/persons";
    }

    @PutMapping("/persons/{id}")
    Person updatePerson(@RequestBody Person newPerson, @PathVariable Long id) {

        return personRepository.findById(id)
                .map(person -> {
                    person.setFirstName(newPerson.getFirstName());
                    person.setLastName(newPerson.getLastName());
                    person.setAddress(newPerson.getAddress());
                    person.setCity(newPerson.getCity());
                    person.setZip(newPerson.getZip());
                    person.setPhone(newPerson.getPhone());
                    person.setEmail(newPerson.getEmail());
                    return personRepository.save(person);
                })
                .orElseGet(() -> {
                    newPerson.setId(id);
                    return personRepository.save(newPerson);
                });
    }

    @DeleteMapping("/persons/{id}")
    void deletePerson(@PathVariable Long id) {
        personRepository.deleteById(id);
    }*/
}
