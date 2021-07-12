package emergencysystem.service;

import emergencysystem.controller.PersonController;
import org.junit.jupiter.api.Test;
import org.mockito.Mockito;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.test.web.servlet.MockMvc;

import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@WebMvcTest(PersonController.class)
public class PersonControllerTest {

    @Autowired
    private MockMvc mockMvc;

    @MockBean
    private PersonReadService personReadService;
    @MockBean
    private PersonUpdateService personUpdateService;
    @MockBean
    private PersonDeletionService personDeletionService;
    @MockBean
    private PersonCreationService personCreationService;
    @MockBean
    private FireStationCreationService fireStationCreationService;
    @MockBean
    private MedicalRecordCreationService medicalRecordCreationService;
    @MockBean
    private PersonController personController;

    @Test
    public void shouldInitialize() throws Exception {


        Mockito.doNothing().when(personController).initializeData("data.json");

        mockMvc.perform(get("/init").param("fileName", "data.json")).andExpect(status().isOk());

        Mockito.verify(personController, Mockito.times(1)).initializeData("data.json");
    }
}
