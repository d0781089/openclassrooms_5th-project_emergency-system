package emergencysystem.model;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class ResultByFireStation {

    private int childrenCount;
    private int adultsCount;
    private List<PersonWithAge> persons;

    public int getChildrenCount() {
        return childrenCount;
    }

    public void setChildrenCount(int childrenCount) {
        this.childrenCount = childrenCount;
    }

    public int getAdultsCount() {
        return adultsCount;
    }

    public void setAdultsCount(int adultsCount) {
        this.adultsCount = adultsCount;
    }

    public List<PersonWithAge> getPersons() {
        return persons;
    }

    public void setPersons(List<PersonWithAge> persons) {
        this.persons = persons;
    }
}
