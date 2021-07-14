package com.safetynet.emergencysystem.controller;

import com.safetynet.emergencysystem.model.*;
import com.safetynet.emergencysystem.service.*;
import com.safetynet.emergencysystem.service.firestation.FireStationCreationService;
import com.safetynet.emergencysystem.service.medicalrecord.MedicalRecordCreationService;
import com.safetynet.emergencysystem.service.person.PersonCreationService;
import com.safetynet.emergencysystem.service.person.PersonDeletionService;
import com.safetynet.emergencysystem.service.person.PersonReadService;
import com.safetynet.emergencysystem.service.person.PersonUpdateService;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.converter.json.MappingJacksonValue;
import org.springframework.web.bind.annotation.*;

import java.io.IOException;
import java.util.List;
import java.util.Set;

@RestController
public class PersonController {

    @Autowired
    private PersonCreationService personCreationService;
    @Autowired
    private PersonReadService personReadService;
    @Autowired
    private PersonUpdateService personUpdateService;
    @Autowired
    private PersonDeletionService personDeletionService;

    @Autowired
    private FireStationCreationService fireStationCreationService;

    @Autowired
    private MedicalRecordCreationService medicalRecordCreationService;

    private JsonReadService jsonReadService;
    private JsonParseService jsonParseService;

    private static final Logger logger = LogManager.getLogger(PersonReadService.class);

    @GetMapping("/persons")
    public List<Person> getPersons() throws IOException {

        return personReadService.getPersons();
    }

    @GetMapping("/persons/{id}")
    public Person getPersonById(@PathVariable Long id) {

        return personReadService.getPersonById(id);
    }

    @PostMapping("/createPerson")
    public Person createPerson(@RequestBody Person person) {

        return personCreationService.createPerson(person);
    }

    @PostMapping("/createPersons")
    public List<Person> createPersons(@RequestBody List<Person> persons) {

        return personCreationService.createPersons(persons);
    }

    @PutMapping("/updatePerson")
    public Person updatePerson(@RequestBody Person person) {

        return personUpdateService.updatePerson(person);
    }

    @DeleteMapping("/persons/{id}")
    public void deletePersonById(@PathVariable Long id) {

        personDeletionService.deletePersonById(id);
    }

    @GetMapping("/flood/stations")
    public MappingJacksonValue getPersonsByStations(@RequestParam List<Integer> stations) {

        return personReadService.getPersonsByStations(stations);
    }

    @GetMapping("/personInfo")
    public MappingJacksonValue getPersonsByFirstNameAndLastName(@RequestParam String firstName, String lastName) {

        logger.debug("[PERSONINFO] " + firstName + " " + lastName);

        return personReadService.getPersonsByFirstNameAndLastName(firstName, lastName);
    }

    @GetMapping("/communityEmail")
    public Set<String> getEmailsByCity(@RequestParam String city) {

        return personReadService.getEmailsByCity(city);
    }

    @GetMapping("/init")
    public void initializeData(@RequestParam String fileName) throws IOException {

        personReadService.initializeData(fileName);
    }
}
