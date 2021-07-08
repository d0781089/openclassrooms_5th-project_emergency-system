package emergencysystem.model;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class JsonData {

    List<Person> persons;
    List<FireStation> fireStations;
    List<MedicalRecord> medicalRecords;
}
