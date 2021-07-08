package emergencysystem.model;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class ResultFlood {

    Map<String, List<PersonWithMedicalRecords>> persons = new HashMap<>();
}
