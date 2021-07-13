package emergencysystem.service;

import com.fasterxml.jackson.databind.ObjectMapper;
import emergencysystem.model.Person;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.Mockito;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.MvcResult;

import java.util.ArrayList;
import java.util.List;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.delete;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.content;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@SpringBootTest
@AutoConfigureMockMvc
public class PersonServiceTest {

    @Autowired
    private MockMvc mockMvc;
    @Autowired
    private ObjectMapper objectMapper;

    @Autowired
    private PersonReadService personReadService;

    @BeforeEach
    public void shouldInitialize() throws Exception {

        mockMvc.perform(get("/init")
                .param("fileName", "data.json"))
                .andExpect(status().isOk());
    }

    @Test
    public void shouldGetPersons() throws Exception {

        mockMvc.perform(get("/persons")).andExpect(status().isOk());
    }

    @Test
    public void shouldGetPersonByFirstNameAndLastName() throws Exception {

        mockMvc.perform(get("/personInfo")
                .param("firstName", "John")
                .param("lastName", "Boyd")).andExpect(status().isOk());
    }

    @Test
    public void shouldCreatePerson() throws Exception {

        Person person = new Person();
        person.setFirstName("Harry");
        person.setLastName("POTTER");
        person.setAddress("4, Privet Drive");
        person.setCity("Little Whinging");
        person.setZip("21944");
        person.setPhone("+44 20 7234 3456");
        person.setEmail("hpotter@mail.co.uk");

        mockMvc.perform(post("/createPerson")
                .contentType("application/json")
                .content(objectMapper.writeValueAsString(person)))
                .andExpect(status().isOk());
    }

    @Test
    public void shouldCreatePersons() throws Exception {

        Person person = new Person();
        person.setFirstName("Harry");
        person.setLastName("POTTER");
        person.setAddress("4, Privet Drive");
        person.setCity("Little Whinging");
        person.setZip("21944");
        person.setPhone("+44 20 7234 3456");
        person.setEmail("hpotter@mail.co.uk");

        List<Person> persons = new ArrayList<>();
        persons.add(person);

        mockMvc.perform(post("/createPersons")
                .contentType("application/json")
                .content(objectMapper.writeValueAsString(persons)))
                .andExpect(status().isOk());
    }

    @Test
    public void shouldUpdatePerson() throws Exception {

        Person person = new Person();
        person.setId(1L);
        person.setFirstName("Harry");
        person.setLastName("POTTER");
        person.setAddress("4, Privet Drive");
        person.setCity("Little Whinging");
        person.setZip("21944");
        person.setPhone("+44 20 7234 3456");
        person.setEmail("hpotter@mail.co.uk");

        mockMvc.perform(put("/updatePerson")
                .contentType("application/json")
                .content(objectMapper.writeValueAsString(person)))
                .andExpect(status().isOk());
    }
}
