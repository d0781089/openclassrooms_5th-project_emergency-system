package com.safetynet.emergencysystem.service.person;

import com.safetynet.emergencysystem.dao.PersonRepository;
import com.safetynet.emergencysystem.model.Person;
import com.safetynet.emergencysystem.service.firestation.FireStationReadService;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class PersonCreationService {

    @Autowired
    private PersonRepository personRepository;

    private static final Logger logger = LogManager.getLogger(FireStationReadService.class);

    public Person createPerson(Person person) {

        logger.debug("Create person: " + person);

        return personRepository.save(person);
    }

    public List<Person> createPersons(List<Person> persons) {

        logger.debug("Create persons: " + persons);

        return personRepository.saveAll(persons);
    }
}
