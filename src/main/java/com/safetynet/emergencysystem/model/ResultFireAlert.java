package com.safetynet.emergencysystem.model;

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
