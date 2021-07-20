package com.safetynet.emergencysystem.controller;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.safetynet.emergencysystem.model.FireStation;
import com.safetynet.emergencysystem.service.firestation.FireStationCreationService;
import com.safetynet.emergencysystem.service.firestation.FireStationDeletionService;
import com.safetynet.emergencysystem.service.firestation.FireStationReadService;
import com.safetynet.emergencysystem.service.firestation.FireStationUpdateService;
import org.junit.jupiter.api.Test;
import org.mockito.Mockito;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.MvcResult;

import java.util.ArrayList;
import java.util.List;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.delete;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.content;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@SpringBootTest
@AutoConfigureMockMvc
public class FireStationControllerTest {

    @Autowired
    private MockMvc mockMvc;
    @Autowired
    private ObjectMapper objectMapper;

    @MockBean
    private FireStationCreationService fireStationCreationService;
    @MockBean
    private FireStationReadService fireStationReadService;
    @MockBean
    private FireStationUpdateService fireStationUpdateService;
    @MockBean
    private FireStationDeletionService fireStationDeletionService;

    @Test
    public void shouldGetFireStations() throws Exception {

        FireStation fireStation = new FireStation();
        fireStation.setId(1L);
        fireStation.setStation(5);
        fireStation.setAddress("29, Privet Drive");

        List<FireStation> fireStations = new ArrayList<>();
        fireStations.add(fireStation);

        Mockito.when(fireStationReadService.getFireStations()).thenReturn(fireStations);

        MvcResult mvcResult = mockMvc.perform(get("/fireStations")).andExpect(status().isOk()).andReturn();

        String actualResponse = mvcResult.getResponse().getContentAsString();
        String expectedResponse = objectMapper.writeValueAsString(fireStations);

        assertEquals(actualResponse, expectedResponse);
    }

    @Test
    public void shouldGetFireStationById() throws Exception {

        FireStation fireStation = new FireStation();
        fireStation.setId(1L);
        fireStation.setStation(5);
        fireStation.setAddress("29, Privet Drive");

        Mockito.when(fireStationReadService.getFireStationById(1L)).thenReturn(fireStation);

        MvcResult mvcResult = mockMvc.perform(get("/fireStations/1")).andExpect(status().isOk()).andReturn();

        String actualResponse = mvcResult.getResponse().getContentAsString();
        String expectedResponse = objectMapper.writeValueAsString(fireStation);

        assertEquals(actualResponse, expectedResponse);
    }

    @Test
    public void shouldCreateFireStation() throws Exception {

        FireStation fireStation = new FireStation();
        fireStation.setStation(5);
        fireStation.setAddress("29, Privet Drive");

        FireStation fireStationSaved = new FireStation();
        fireStationSaved.setId(1L);
        fireStationSaved.setStation(5);
        fireStationSaved.setAddress("29, Privet Drive");

        Mockito.when(fireStationCreationService.createFireStation(fireStation)).thenReturn(fireStationSaved);

        mockMvc.perform(post("/createFireStation")
                .contentType("application/json")
                .content(objectMapper.writeValueAsString(fireStation))
        ).andExpect(status().isOk())
                .andExpect(content().string(objectMapper.writeValueAsString(fireStationSaved))).andReturn();
    }

    @Test
    public void shouldCreateFireStations() throws Exception {

        FireStation fireStation = new FireStation();
        fireStation.setStation(5);
        fireStation.setAddress("29, Privet Drive");

        List<FireStation> fireStations = new ArrayList<>();
        fireStations.add(fireStation);

        FireStation fireStationSaved = new FireStation();
        fireStation.setId(1L);
        fireStation.setStation(5);
        fireStation.setAddress("29, Privet Drive");

        List<FireStation> fireStationsSaved = new ArrayList<>();
        fireStationsSaved.add(fireStationSaved);

        Mockito.when(fireStationCreationService.createFireStations(fireStations)).thenReturn(fireStationsSaved);

        mockMvc.perform(post("/createFireStations")
                .contentType("application/json")
                .content(objectMapper.writeValueAsString(fireStations))).andExpect(status().isOk())
                .andExpect(content().string(objectMapper.writeValueAsString(fireStationsSaved))).andReturn();
    }

    @Test
    public void shouldUpdateFireStation() throws Exception {

        FireStation fireStation = new FireStation();
        fireStation.setId(1L);
        fireStation.setStation(5);
        fireStation.setAddress("29, Privet Drive");

        FireStation fireStationUpdated = new FireStation();
        fireStationUpdated.setId(1L);
        fireStationUpdated.setStation(5);
        fireStationUpdated.setAddress("19, Privet Drive");

        Mockito.when(fireStationUpdateService.updateFireStation(fireStation)).thenReturn(fireStationUpdated);

        mockMvc.perform(put("/updateFireStation")
                .contentType("application/json")
                .content(objectMapper.writeValueAsString(fireStation)))
                .andExpect(status().isOk())
                .andExpect(content().string(objectMapper.writeValueAsString(fireStationUpdated)));
    }

    @Test
    public void shouldDeleteFireStation() throws Exception {

        Mockito.doNothing().when(fireStationDeletionService).deleteFireStation(1L);

        mockMvc.perform(delete("/fireStations/1")).andExpect(status().isOk());

        Mockito.verify(fireStationDeletionService, Mockito.times(1)).deleteFireStation(1L);
    }
}
