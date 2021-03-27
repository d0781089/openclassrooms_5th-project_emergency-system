package emergencysystem.service;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.JsonNode;
import emergencysystem.model.Person;
import emergencysystem.util.PersonRepository;
import org.junit.jupiter.api.Test;

import java.io.IOException;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;

class JsonServiceTest {

    private String jsonTestString = "{\"firstName\":\"Harry\"," +
            "\"lastName\":\"POTTER\"," +
            "\"address\":\"4, Privet Drive\"," +
            "\"city\":\"Little Whinging\"," +
            "\"zip\":\"21944\"," +
            "\"phone\":\"+44 20 7234 3456\"," +
            "\"email\":\"hpotter@mail.co.uk\"}";

    @Test
    void parse() throws JsonProcessingException {
        JsonNode jsonNode = JsonService.parse(jsonTestString);

        assertEquals(jsonNode.get("firstName").asText(), "Harry");
    }

    @Test
    void fromJson() throws JsonProcessingException {
        JsonNode jsonNode = JsonService.parse(jsonTestString);
        Person person = JsonService.fromJson(jsonNode, Person.class);

        assertEquals(person.getFirstName(), "Harry");
    }

    @Test
    void toJson() {
        Person person = new Person();
        person.setFirstName("Harry");
        person.setLastName("POTTER");
        person.setAddress("4, Privet Drive");
        person.setCity("Little Whinging");
        person.setZip("21944");
        person.setPhone("442072343456");
        person.setEmail("hpotter@mail.co.uk");
        JsonNode jsonNode = JsonService.toJson(person);

        assertEquals(jsonNode.get("firstName").asText(), "Harry");
    }

    @Test
    void stringify() throws JsonProcessingException {
        Person person = new Person();
        person.setFirstName("Harry");
        person.setLastName("POTTER");
        person.setAddress("4, Privet Drive");
        person.setCity("Little Whinging");
        person.setZip("21944");
        person.setPhone("+44 20 7234 3456");
        person.setEmail("hpotter@mail.co.uk");
        JsonNode jsonNode = JsonService.toJson(person);

        assertEquals(JsonService.stringify(jsonNode, false), jsonTestString);
    }

    @Test
    void loadJsonData() throws IOException {
        JsonService.loadJsonData(Person);
    }
}