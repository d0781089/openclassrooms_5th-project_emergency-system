package emergencysystem.service;

import com.fasterxml.jackson.databind.ObjectMapper;
import emergencysystem.model.MedicalRecord;
import emergencysystem.model.ResultChildAlert;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.Mockito;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.converter.json.MappingJacksonValue;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.MvcResult;

import java.sql.Date;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.delete;
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
