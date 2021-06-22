package emergencysystem.service;

import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.*;
import emergencysystem.model.JsonData;
import emergencysystem.model.Person;
import emergencysystem.util.PersonRepository;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.List;

public class JsonService {

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

    public static JsonData getData(JsonData jsonData) throws IOException {
        String file = "src/main/resources/data.json";
        String json = new String(Files.readAllBytes(Paths.get(file)));
        JsonNode jsonNode = JsonService.parse(json);
        jsonData = JsonService.fromJson(jsonNode, JsonData.class);
        return jsonData;
    }
}
