package com.safetynet.emergencysystem.service;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.JsonNode;
import com.safetynet.emergencysystem.model.JsonData;
import com.safetynet.emergencysystem.model.Person;
import org.junit.jupiter.api.Test;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Paths;

import static org.junit.jupiter.api.Assertions.*;

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

    /*
    @Test
    void shouldParseJson() throws JsonProcessingException {

        JsonNode jsonNode = jsonParseService.parse(testJsonString);

        assertEquals(jsonNode.get("firstName").asText(), "Harry");
    }

    @Test
    void shouldStoreJsonInModel() throws JsonProcessingException {

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
    void shouldReadFromJsonFile() throws IOException {

        String file = "src/main/resources/data.json";
        String json = new String(Files.readAllBytes(Paths.get(file)));
        JsonNode jsonNode = jsonParseService.parse(json);

        JsonData jsonData = jsonReadService.read(jsonNode, JsonData.class);

        assertEquals(jsonData.getPersons().get(0).getFirstName(), "John");
    }
    */
}
