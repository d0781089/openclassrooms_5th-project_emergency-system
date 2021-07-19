package com.safetynet.emergencysystem.service;

import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.*;
import com.safetynet.emergencysystem.model.JsonData;
import com.safetynet.emergencysystem.model.PersonWithAge;
import com.safetynet.emergencysystem.service.firestation.FireStationReadService;
import com.safetynet.emergencysystem.service.medicalrecord.MedicalRecordReadService;
import com.safetynet.emergencysystem.service.person.PersonReadService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class JsonReadService {

    @Autowired
    static PersonReadService personReadService;

    @Autowired
    static FireStationReadService fireStationReadService;

    @Autowired
    static MedicalRecordReadService medicalRecordReadService;

    private static ObjectMapper getDefaultObjectMapper() {
        ObjectMapper defaultObjectMapper = new ObjectMapper();
        defaultObjectMapper.configure(DeserializationFeature.FAIL_ON_IGNORED_PROPERTIES, false);
        defaultObjectMapper.setSerializationInclusion(JsonInclude.Include.NON_NULL);

        return defaultObjectMapper;
    }

    public static ObjectMapper objectMapper = getDefaultObjectMapper();

    public static <classTargeted> classTargeted read(JsonNode jsonNode, Class<classTargeted> classTargeted)
            throws JsonProcessingException {

        return objectMapper.treeToValue(jsonNode, classTargeted);
    }

    public static String stringify(JsonNode jsonNode, boolean indented) throws JsonProcessingException {

        JsonData jsonData = new JsonData();
        ObjectWriter objectWriter = objectMapper.writer();

        if (indented) {
            objectWriter = objectWriter.with(SerializationFeature.INDENT_OUTPUT);
        }

        return objectWriter.writeValueAsString(jsonNode);
    }
}
