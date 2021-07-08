package emergencysystem.model;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class JsonData {

    private List<Person> persons;
    private List<FireStation> fireStations;
    private List<MedicalRecord> medicalRecords;
}
