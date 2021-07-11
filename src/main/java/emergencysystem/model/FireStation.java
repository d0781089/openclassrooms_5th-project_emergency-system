package emergencysystem.model;

import com.fasterxml.jackson.annotation.JsonIgnore;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Entity
public class FireStation {

    //@JsonIgnore
    @Id
    @GeneratedValue(strategy= GenerationType.AUTO)
    private Long id;

    private String address;
    private int station;
}
