package emergencysystem.model;

import com.fasterxml.jackson.annotation.JsonFilter;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@JsonFilter("personWithAgeFilter")
@Data
@AllArgsConstructor
@NoArgsConstructor
public class PersonWithAge extends Person {

    private int age;
}
