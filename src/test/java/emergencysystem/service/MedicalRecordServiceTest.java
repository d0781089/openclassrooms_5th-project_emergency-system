package emergencysystem.service;

import com.fasterxml.jackson.databind.ObjectMapper;
import emergencysystem.controller.MedicalRecordController;
import emergencysystem.model.MedicalRecord;
import org.junit.jupiter.api.Test;
import org.mockito.Mockito;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.MvcResult;

import java.sql.Date;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.delete;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.content;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@WebMvcTest(MedicalRecordController.class)
public class MedicalRecordServiceTest {

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
    public void shouldCreateMedicalRecord() throws Exception {

        MedicalRecord medicalRecord = new MedicalRecord();
        medicalRecord.setFirstName("Harry");
        medicalRecord.setLastName("POTTER");
        medicalRecord.setBirthDate(Date.valueOf(LocalDate.now().minusYears(12)));
        List<String> medicationsAndAllergies = new ArrayList<>();
        medicalRecord.setMedications(medicationsAndAllergies);
        medicalRecord.setAllergies(medicationsAndAllergies);

        MedicalRecord medicalRecordSaved = new MedicalRecord();
        medicalRecordSaved.setId(1L);
        medicalRecordSaved.setFirstName("Harry");
        medicalRecordSaved.setLastName("POTTER");
        medicalRecordSaved.setBirthDate(Date.valueOf(LocalDate.now().minusYears(12)));
        medicalRecordSaved.setMedications(medicationsAndAllergies);
        medicalRecordSaved.setAllergies(medicationsAndAllergies);

        Mockito.when(medicalRecordCreationService.createMedicalRecord(medicalRecord)).thenReturn(medicalRecordSaved);

        mockMvc.perform(post("/createMedicalRecord")
                .contentType("application/json")
                .content(objectMapper.writeValueAsString(medicalRecord))
        ).andExpect(status().isOk())
                .andExpect(content().string(objectMapper.writeValueAsString(medicalRecordSaved))).andReturn();
    }

    @Test
    public void shouldCreateMedicalRecords() throws Exception {

        MedicalRecord medicalRecord = new MedicalRecord();
        medicalRecord.setFirstName("Harry");
        medicalRecord.setLastName("POTTER");
        medicalRecord.setBirthDate(Date.valueOf(LocalDate.now().minusYears(12)));
        List<String> medicationsAndAllergies = new ArrayList<>();
        medicalRecord.setMedications(medicationsAndAllergies);
        medicalRecord.setAllergies(medicationsAndAllergies);

        List<MedicalRecord> medicalRecords = new ArrayList<>();
        medicalRecords.add(medicalRecord);

        MedicalRecord medicalRecordSaved = new MedicalRecord();
        medicalRecordSaved.setId(1L);
        medicalRecordSaved.setFirstName("Harry");
        medicalRecordSaved.setLastName("POTTER");
        medicalRecordSaved.setBirthDate(Date.valueOf(LocalDate.now().minusYears(12)));
        medicalRecordSaved.setMedications(medicationsAndAllergies);
        medicalRecordSaved.setAllergies(medicationsAndAllergies);

        List<MedicalRecord> medicalRecordsSaved = new ArrayList<>();
        medicalRecordsSaved.add(medicalRecordSaved);

        Mockito.when(medicalRecordCreationService.createMedicalRecords(medicalRecords)).thenReturn(medicalRecordsSaved);

        mockMvc.perform(post("/createMedicalRecords")
                .contentType("application/json")
                .content(objectMapper.writeValueAsString(medicalRecords))
        ).andExpect(status().isOk())
                .andExpect(content().string(objectMapper.writeValueAsString(medicalRecordsSaved))).andReturn();
    }

    @Test
    public void shouldUpdateMedicalRecord() throws Exception {

        MedicalRecord medicalRecord = new MedicalRecord();
        medicalRecord.setId(1L);
        medicalRecord.setFirstName("Harry");
        medicalRecord.setLastName("POTTER");
        medicalRecord.setBirthDate(Date.valueOf(LocalDate.now().minusYears(12)));
        List<String> medicationsAndAllergies = new ArrayList<>();
        medicalRecord.setMedications(medicationsAndAllergies);
        medicalRecord.setAllergies(medicationsAndAllergies);

        MedicalRecord medicalRecordUpdated = new MedicalRecord();
        medicalRecordUpdated.setId(1L);
        medicalRecordUpdated.setFirstName("Harry");
        medicalRecordUpdated.setLastName("POTTER");
        medicalRecordUpdated.setBirthDate(Date.valueOf(LocalDate.now().minusYears(18)));
        medicalRecordUpdated.setMedications(medicationsAndAllergies);
        medicalRecordUpdated.setAllergies(medicationsAndAllergies);

        Mockito.when(medicalRecordUpdateService.updateMedicalRecord(medicalRecord)).thenReturn(medicalRecordUpdated);

        mockMvc.perform(put("/updateMedicalRecord")
                .contentType("application/json")
                .content(objectMapper.writeValueAsString(medicalRecord)))
                .andExpect(status().isOk())
                .andExpect(content().string(objectMapper.writeValueAsString(medicalRecordUpdated)));
    }

    @Test
    public void shouldDeleteMedicalRecord() throws Exception {

        Mockito.doNothing().when(medicalRecordDeletionService).deleteMedicalRecord(1L);

        mockMvc.perform(delete("/medicalRecords/1")).andExpect(status().isOk());

        Mockito.verify(medicalRecordDeletionService, Mockito.times(1)).deleteMedicalRecord(1L);
    }
}
