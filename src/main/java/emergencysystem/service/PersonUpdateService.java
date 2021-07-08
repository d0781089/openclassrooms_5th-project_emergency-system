package emergencysystem.service;

import emergencysystem.dao.PersonRepository;
import emergencysystem.model.Person;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Optional;

@Service
public class PersonUpdateService {

    @Autowired
    private PersonRepository personRepository;

    public Person updatePerson(Person person) {

        Person personUpdated;
        Optional<Person> optionalPerson = personRepository.findById(person.getId());

        if(optionalPerson.isPresent()) {
            personUpdated = optionalPerson.get();
            personUpdated.setAddress(person.getAddress());
            personUpdated.setCity(person.getCity());
            personUpdated.setZip(person.getZip());
            personUpdated.setPhone(person.getPhone());
            personUpdated.setEmail(person.getEmail());

            personRepository.save(personUpdated);
        } else {
            return new Person();
        }
        return personUpdated;
    }
}
