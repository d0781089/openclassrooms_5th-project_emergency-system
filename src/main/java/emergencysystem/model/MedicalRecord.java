package emergencysystem.model;

import com.fasterxml.jackson.annotation.JsonIgnore;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.format.annotation.DateTimeFormat;

import javax.persistence.*;
import java.sql.Date;
import java.util.List;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Entity
public class MedicalRecord {

    @JsonIgnore
    @Id
    @GeneratedValue(strategy= GenerationType.AUTO)
    private Long id;

    private String firstName;
    private String lastName;

    @DateTimeFormat
    private Date birthDate;

    @ElementCollection
    private List<String> medications;

    @ElementCollection
    private List<String> allergies;
}
