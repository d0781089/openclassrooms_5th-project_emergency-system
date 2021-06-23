package emergencysystem.service;

import emergencysystem.model.Person;
import emergencysystem.util.PersonRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class PersonService {

    @Autowired
    private PersonRepository personRepository;

    public Person createPerson(Person person) {

        return personRepository.save(person);
    }

    public List<Person> createPersons(List<Person> persons) {

        return personRepository.saveAll(persons);
    }

    public Person getPersonById(Long id) {

        return personRepository.getById(id); // '.orElse(null)' may be deprecated
    }

    public List<Person> getPersons() {

        return personRepository.findAll();
    }

    public Person updatePerson(Person person) {
        Person personToUpdate;
        Optional<Person> optionalPerson=personRepository.findById(person.getId());
        if(optionalPerson.isPresent()) {
            personToUpdate=optionalPerson.get();
            personToUpdate.setFirstName(person.getFirstName());
            personToUpdate.setLastName(person.getLastName());
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

        return "The user of DELETED successfully!";
    }
}
