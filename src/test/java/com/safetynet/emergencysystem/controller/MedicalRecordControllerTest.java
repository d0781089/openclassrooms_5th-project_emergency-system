package com.safetynet.emergencysystem.controller;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.safetynet.emergencysystem.model.MedicalRecord;
import com.safetynet.emergencysystem.model.ResultChildAlert;
import com.safetynet.emergencysystem.service.MedicalRecordCreationService;
import com.safetynet.emergencysystem.service.MedicalRecordDeletionService;
import com.safetynet.emergencysystem.service.MedicalRecordReadService;
import com.safetynet.emergencysystem.service.MedicalRecordUpdateService;
import org.junit.jupiter.api.Test;
import org.mockito.Mockito;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.converter.json.MappingJacksonValue;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.MvcResult;

import java.sql.Date;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.delete;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@WebMvcTest(MedicalRecordController.class)
public class MedicalRecordControllerTest {

    @Autowired
    private MockMvc mockMvc;
    @Autowired
    private ObjectMapper objectMapper;

    @MockBean
    private MedicalRecordCreationService medicalRecordCreationService;
    @MockBean
    private MedicalRecordReadService medicalRecordReadService;
    @MockBean
    private MedicalRecordUpdateService medicalRecordUpdateService;
    @MockBean
    private MedicalRecordDeletionService medicalRecordDeletionService;

    @Test
    public void shouldGetMedicalRecords() throws Exception {

        MedicalRecord medicalRecord = new MedicalRecord();
        medicalRecord.setId(1L);
        medicalRecord.setFirstName("Harry");
        medicalRecord.setLastName("POTTER");
        medicalRecord.setBirthDate(Date.valueOf(LocalDate.now().minusYears(12)));
        List<String> medicationsAndAllergies = new ArrayList<>();
        medicalRecord.setMedications(medicationsAndAllergies);
        medicalRecord.setAllergies(medicationsAndAllergies);

        List<MedicalRecord> medicalRecords = new ArrayList<>();
        medicalRecords.add(medicalRecord);

        Mockito.when(medicalRecordReadService.getMedicalRecords()).thenReturn(medicalRecords);

        MvcResult mvcResult = mockMvc.perform(get("/medicalRecords")).andExpect(status().isOk()).andReturn();

        String actualResponse = mvcResult.getResponse().getContentAsString();
        String expectedResponse = objectMapper.writeValueAsString(medicalRecords);

        assertEquals(actualResponse, expectedResponse);
    }

    @Test
    public void shouldGetMedicalRecordById() throws Exception {

        MedicalRecord medicalRecord = new MedicalRecord();
        medicalRecord.setId(1L);
        medicalRecord.setFirstName("Harry");
        medicalRecord.setLastName("POTTER");
        medicalRecord.setBirthDate(Date.valueOf(LocalDate.now().minusYears(12)));
        List<String> medicationsAndAllergies = new ArrayList<>();
        medicalRecord.setMedications(medicationsAndAllergies);
        medicalRecord.setAllergies(medicationsAndAllergies);

        Mockito.when(medicalRecordReadService.getMedicalRecordById(1L)).thenReturn(medicalRecord);

        MvcResult mvcResult = mockMvc.perform(get("/medicalRecords/1")).andExpect(status().isOk()).andReturn();

        String actualResponse = mvcResult.getResponse().getContentAsString();
        String expectedResponse = objectMapper.writeValueAsString(medicalRecord);

        assertEquals(actualResponse, expectedResponse);
    }

    @Test
    public void shouldGetChildrenByAddress() throws Exception {

        ResultChildAlert resultChildAlert = new ResultChildAlert();
        MappingJacksonValue result = new MappingJacksonValue(resultChildAlert);

        Mockito.when(medicalRecordReadService.getChildrenByAddress("1509 Culver St")).thenReturn(result);

        MvcResult mvcResult = mockMvc.perform(get("/childAlert").param("address", "1509 Culver St"))
                .andExpect(status().isOk()).andReturn();

        String expectedResponse = objectMapper.writeValueAsString(result);
        String actualResponse = "{\"value\":" + mvcResult.getResponse().getContentAsString() + ",\"serializationView\":null,\"filters\":null}";

        assertEquals(expectedResponse, actualResponse);
    }

    @Test
    public void shouldGetPersonsByAddress() throws Exception {

        ResultChildAlert resultChildAlert = new ResultChildAlert();
        MappingJacksonValue result = new MappingJacksonValue(resultChildAlert);

        Mockito.when(medicalRecordReadService.getPersonsByAddress("1509 Culver St")).thenReturn(result);

        MvcResult mvcResult = mockMvc.perform(get("/fire").param("address", "1509 Culver St"))
                .andExpect(status().isOk()).andReturn();

        String expectedResponse = objectMapper.writeValueAsString(result);
        String actualResponse = "{\"value\":" + mvcResult.getResponse().getContentAsString() + ",\"serializationView\":null,\"filters\":null}";

        assertEquals(expectedResponse, actualResponse);
    }

    @Test
    public void shouldDeleteMedicalRecord() throws Exception {

        Mockito.doNothing().when(medicalRecordDeletionService).deleteMedicalRecord(1L);

        mockMvc.perform(delete("/medicalRecords/1")).andExpect(status().isOk());

        Mockito.verify(medicalRecordDeletionService, Mockito.times(1)).deleteMedicalRecord(1L);
    }
}
