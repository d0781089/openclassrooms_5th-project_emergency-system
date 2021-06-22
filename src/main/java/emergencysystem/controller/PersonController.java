package emergencysystem.controller;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.JsonNode;
import emergencysystem.model.JsonData;
import emergencysystem.model.Person;
import emergencysystem.service.JsonService;
import emergencysystem.util.PersonRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.List;

@Controller
@RequestMapping("/persons")
public class PersonController {

    private static final String sort = "all";

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
        JsonData jsonData = new JsonData();
        jsonData = JsonService.getData(jsonData);
        jsonData.setPersons(personRepository.findAll());
        JsonNode jsonNode = JsonService.toJson(jsonData);
        System.out.println(JsonService.stringify(jsonNode, true));

        return "redirect:/persons";
    }

}
