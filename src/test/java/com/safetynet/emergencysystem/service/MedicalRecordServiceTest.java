package com.safetynet.emergencysystem.service;

import com.fasterxml.jackson.databind.ObjectMapper;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.web.servlet.MockMvc;

import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@SpringBootTest
@AutoConfigureMockMvc
public class MedicalRecordServiceTest {

    @Autowired
    private MockMvc mockMvc;
    @Autowired
    private ObjectMapper objectMapper;

    @Autowired
    private MedicalRecordReadService medicalRecordReadService;

    @Test
    public void shouldGetMedicalRecords() throws Exception {

        mockMvc.perform(get("/medicalRecords")).andExpect(status().isOk());
    }

    @Test
    public void shouldGetChildrenByAddress() throws Exception {

        mockMvc.perform(get("/childAlert").param("address", "1509 Culver St"))
                .andExpect(status().isOk());
    }

    @Test
    public void shouldGetPersonsByAddress() throws Exception {

        mockMvc.perform(get("/fire").param("address", "1509 Culver St"))
                .andExpect(status().isOk());
    }
}
