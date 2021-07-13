package emergencysystem.model;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.ArrayList;
import java.util.List;

public class ResultFireAlert {

    private int station;
    private List<PersonWithMedicalRecords> persons;

    public int getStation() {
        return station;
    }

    public void setStation(int station) {
        this.station = station;
    }

    public List<PersonWithMedicalRecords> getPersons() {
        return persons;
    }

    public void setPersons(List<PersonWithMedicalRecords> persons) {
        this.persons = persons;
    }
}
