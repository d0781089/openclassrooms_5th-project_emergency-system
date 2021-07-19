package com.safetynet.emergencysystem.service;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.node.ObjectNode;
import com.safetynet.emergencysystem.model.JsonData;
import com.safetynet.emergencysystem.model.Person;
import org.junit.jupiter.api.Test;
import org.springframework.boot.test.context.SpringBootTest;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Paths;

import static org.junit.jupiter.api.Assertions.assertEquals;

@SpringBootTest
class JsonReadServiceTest {

    private JsonReadService jsonReadService;
    private JsonParseService jsonParseService;

    private String testJsonString = "{\"firstName\":\"Harry\"," +
            "\"lastName\":\"POTTER\"," +
            "\"address\":\"4, Privet Drive\"," +
            "\"city\":\"Little Whinging\"," +
            "\"zip\":\"21944\"," +
            "\"phone\":\"+44 20 7234 3456\"," +
            "\"email\":\"hpotter@mail.co.uk\"}";

    private String testJsonIndentedString = "{\r\n"
            + "  \"firstName\" : \"Harry\",\r\n"
                        + "  \"lastName\" : \"POTTER\",\r\n"
                        + "  \"address\" : \"4, Privet Drive\",\r\n"
                        + "  \"city\" : \"Little Whinging\",\r\n"
                        + "  \"zip\" : \"21944\",\r\n"
                        + "  \"phone\" : \"+44 20 7234 3456\",\r\n"
                        + "  \"email\" : \"hpotter@mail.co.uk\"\r\n"
            + "}";

    @Test
    void shouldParseJson() throws IOException {

        JsonNode jsonNode = jsonParseService.parse(testJsonString);

        assertEquals(jsonNode.get("firstName").asText(), "Harry");
    }

    @Test
    void shouldStoreJsonInModel() throws IOException {

        JsonNode jsonNode = jsonParseService.parse(testJsonString);
        Person person = jsonReadService.read(jsonNode, Person.class);

        assertEquals(person.getFirstName(), "Harry");
    }

    @Test
    void shouldParseToJson() {

        Person person = new Person();

        person.setFirstName("Harry");
        person.setLastName("POTTER");
        person.setAddress("4, Privet Drive");
        person.setCity("Little Whinging");
        person.setZip("21944");
        person.setPhone("442072343456");
        person.setEmail("hpotter@mail.co.uk");

        JsonNode jsonNode = jsonParseService.toJson(person);

        assertEquals(jsonNode.get("firstName").asText(), "Harry");
    }

    @Test
    void shouldStringify() throws JsonProcessingException {

        Person person = new Person();

        person.setFirstName("Harry");
        person.setLastName("POTTER");
        person.setAddress("4, Privet Drive");
        person.setCity("Little Whinging");
        person.setZip("21944");
        person.setPhone("+44 20 7234 3456");
        person.setEmail("hpotter@mail.co.uk");

        JsonNode jsonNode = jsonParseService.toJson(person);

        assertEquals(jsonReadService.stringify(jsonNode, false), testJsonString);
    }

    @Test
    void shouldStringifyWithIndentation() throws JsonProcessingException {

        Person person = new Person();

        person.setFirstName("Harry");
        person.setLastName("POTTER");
        person.setAddress("4, Privet Drive");
        person.setCity("Little Whinging");
        person.setZip("21944");
        person.setPhone("+44 20 7234 3456");
        person.setEmail("hpotter@mail.co.uk");

        JsonNode jsonNode = jsonParseService.toJson(person);

        String expected = jsonReadService.stringify(jsonNode, true);

        assertEquals(expected, testJsonIndentedString);
    }
}
