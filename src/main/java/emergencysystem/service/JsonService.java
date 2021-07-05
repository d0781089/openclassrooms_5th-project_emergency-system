package emergencysystem.service;

import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.*;
import com.fasterxml.jackson.databind.node.ObjectNode;
import emergencysystem.dao.PersonRepository;
import emergencysystem.model.FireStation;
import emergencysystem.model.JsonData;
import emergencysystem.model.MedicalRecord;
import emergencysystem.model.Person;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.web.bind.annotation.RestController;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.List;

@RestController
public class JsonService {

    @Autowired
    static PersonService personService;

    @Autowired
    static FireStationService fireStationService;

    @Autowired
    static MedicalRecordService medicalRecordService;

    public static ObjectMapper objectMapper = getDefaultObjectMapper();

    private static ObjectMapper getDefaultObjectMapper() {
        ObjectMapper defaultObjectMapper = new ObjectMapper();
        defaultObjectMapper.configure(DeserializationFeature.FAIL_ON_IGNORED_PROPERTIES, false);
        defaultObjectMapper.setSerializationInclusion(JsonInclude.Include.NON_NULL);

        return defaultObjectMapper;
    }

    public static JsonNode parse(String src) throws JsonProcessingException {

        return objectMapper.readTree(src);
    }

    public static <classTargeted> classTargeted fromJson(JsonNode jsonNode, Class<classTargeted> classTargeted)
            throws JsonProcessingException {

        return objectMapper.treeToValue(jsonNode, classTargeted);
    }

    public static JsonNode toJson(Object objectTargeted) {

        return objectMapper.valueToTree(objectTargeted);
    }

    public static String stringify(JsonNode jsonNode, boolean indented) throws JsonProcessingException {

        ObjectWriter objectWriter = objectMapper.writer();

        if (indented) {
            objectWriter = objectWriter.with(SerializationFeature.INDENT_OUTPUT);
        }

        return objectWriter.writeValueAsString(jsonNode);
    }
}
