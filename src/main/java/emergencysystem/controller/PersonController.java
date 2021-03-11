package emergencysystem.controller;

import emergencysystem.model.Person;
import emergencysystem.util.PersonRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

import java.util.List;

@Controller
@RequestMapping("/persons")
public class PersonController {

    private static final String sort = "all";

    private PersonRepository personRepository;

    @Autowired
    public PersonController(PersonRepository personRepository) {
        this.personRepository = personRepository;
    }

    @RequestMapping(method= RequestMethod.GET)
    public String userPersons(Model model) {

        List<Person> persons = personRepository.findBySort(sort);
        if (persons != null) {
            model.addAttribute("persons", persons);
        }
        return "persons";
    }

    @RequestMapping(method=RequestMethod.POST)
    public String addToReadingList(Person person) {
        person.setUser(sort);
        personRepository.save(person);
        return "redirect:/persons";
    }

}
