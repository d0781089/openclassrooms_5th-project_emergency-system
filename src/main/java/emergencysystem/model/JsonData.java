package emergencysystem.model;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;

public class JsonData {

    private List<Person> persons;
    private List<FireStation> fireStations;
    private List<MedicalRecord> medicalRecords;

    public List<Person> getPersons() {
        return persons;
    }

    public void setPersons(List<Person> persons) {
        this.persons = persons;
    }

    public List<FireStation> getFireStations() {
        return fireStations;
    }

    public void setFireStations(List<FireStation> fireStations) {
        this.fireStations = fireStations;
    }

    public List<MedicalRecord> getMedicalRecords() {
        return medicalRecords;
    }

    public void setMedicalRecords(List<MedicalRecord> medicalRecords) {
        this.medicalRecords = medicalRecords;
    }
}
