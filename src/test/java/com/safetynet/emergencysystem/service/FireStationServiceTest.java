package com.safetynet.emergencysystem.service;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.safetynet.emergencysystem.model.FireStation;
import com.safetynet.emergencysystem.service.firestation.FireStationReadService;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.web.servlet.MockMvc;

import java.util.ArrayList;
import java.util.List;

import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.delete;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@SpringBootTest
@AutoConfigureMockMvc
public class FireStationServiceTest {

    @Autowired
    private MockMvc mockMvc;
    @Autowired
    private ObjectMapper objectMapper;

    @Autowired
    private FireStationReadService fireStationReadService;

    @Test
    public void shouldGetFireStations() throws Exception {

        mockMvc.perform(get("/fireStations")).andExpect(status().isOk());
    }

    @Test
    public void shouldGetFireStationById() throws Exception {

        mockMvc.perform(get("/fireStations/1")).andExpect(status().isOk());
    }

    @Test
    public void shouldGetPersonsByFireStation() throws Exception {

        mockMvc.perform(get("/firestation").param("stationNumber", "3")).andExpect(status().isOk());
    }

    @Test
    public void shouldCreateFireStation() throws Exception {

        FireStation fireStation = new FireStation();
        fireStation.setStation(5);
        fireStation.setAddress("29, Privet Drive");

        mockMvc.perform(post("/createFireStation")
                .contentType("application/json")
                .content(objectMapper.writeValueAsString(fireStation))
        ).andExpect(status().isOk());
    }

    @Test
    public void shouldCreateFireStations() throws Exception {

        FireStation fireStation = new FireStation();
        fireStation.setStation(5);
        fireStation.setAddress("29, Privet Drive");

        List<FireStation> fireStations = new ArrayList<>();
        fireStations.add(fireStation);

        mockMvc.perform(post("/createFireStations")
                .contentType("application/json")
                .content(objectMapper.writeValueAsString(fireStations))).andExpect(status().isOk());
    }

    @Test
    public void shouldUpdateFireStation() throws Exception {

        FireStation fireStation = new FireStation();
        fireStation.setId(1L);
        fireStation.setStation(5);
        fireStation.setAddress("29, Privet Drive");

        mockMvc.perform(put("/updateFireStation")
                .contentType("application/json")
                .content(objectMapper.writeValueAsString(fireStation)))
                .andExpect(status().isOk());
    }

    @Test
    public void shouldDeleteFireStation() throws Exception {

        mockMvc.perform(delete("/fireStations/1")).andExpect(status().isOk());
    }
}
