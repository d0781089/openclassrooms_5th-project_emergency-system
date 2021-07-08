package emergencysystem.service;

import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class JsonService {

    @Autowired
    static PersonReadService personReadService;

    @Autowired
    static FireStationReadService fireStationReadService;

    @Autowired
    static MedicalRecordReadService medicalRecordReadService;

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
