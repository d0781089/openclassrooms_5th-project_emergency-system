package emergencysystem.service;

import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.*;
import emergencysystem.model.Person;
import emergencysystem.util.FireStationRepository;
import emergencysystem.util.MedicalRecordRepository;
import emergencysystem.util.PersonRepository;

import java.io.File;
import java.io.IOException;
import java.util.List;

public class JsonService {

    private static ObjectMapper objectMapper = getDefaultObjectMapper();

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

    public static <classTargeted> void loadJsonData(Class<classTargeted> classTargeted) throws IOException {
        objectMapper.readValue(new File("src/main/resources/data.json"), classTargeted);
    }
}
