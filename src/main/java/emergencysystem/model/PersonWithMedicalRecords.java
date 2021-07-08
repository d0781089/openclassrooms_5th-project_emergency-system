package emergencysystem.model;

import com.fasterxml.jackson.annotation.JsonFilter;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.persistence.ElementCollection;
import java.util.List;

@JsonFilter("personWithMedicalRecordsFilter")
@Data
@AllArgsConstructor
@NoArgsConstructor
public class PersonWithMedicalRecords extends Person {

    private int age;

    @ElementCollection
    private List<String> medications;

    @ElementCollection
    private List<String> allergies;
}
