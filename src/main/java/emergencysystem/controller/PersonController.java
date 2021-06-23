package emergencysystem.controller;

import emergencysystem.model.Person;
import emergencysystem.service.PersonService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
//@Controller
//@RequestMapping("/persons")
public class PersonController {

    @Autowired
    private PersonService personService;

    @PostMapping("/createPerson")
    public Person createPerson(@RequestBody Person person) {

        return personService.createPerson(person);
    }

    @PostMapping("/createPersons")
    public List<Person> createPersons(@RequestBody List<Person> persons) {

        return personService.createPersons(persons);
    }

    @GetMapping("/persons/{id}")
    public Person getPersonById(@PathVariable Long id) {

        return personService.getPersonById(id);
    }

    @GetMapping("/persons")
    public List<Person> getPersons() {

        return personService.getPersons();
    }

    @PutMapping("/updatePerson")
    public Person updatePerson(@RequestBody Person person) {

        return personService.updatePerson(person);
    }

    @DeleteMapping("/deletePerson")
    public String deletePersonById(@PathVariable Long id) {

        return personService.deletePersonById(id);
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
