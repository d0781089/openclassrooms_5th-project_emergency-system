package emergencysystem.service;

import emergencysystem.dao.PersonRepository;
import emergencysystem.model.Person;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class PersonCreationService {

    @Autowired
    private PersonRepository personRepository;

    public Person createPerson(Person person) {

        return personRepository.save(person);
    }

    public List<Person> createPersons(List<Person> persons) {

        return personRepository.saveAll(persons);
    }
}
