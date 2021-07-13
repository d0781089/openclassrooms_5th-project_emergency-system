package emergencysystem.model;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.ArrayList;
import java.util.List;

public class ResultChildAlert {

    private List<PersonWithAge> children;
    private List<PersonWithAge> adults;

    public List<PersonWithAge> getChildren() {
        return children;
    }

    public void setChildren(List<PersonWithAge> children) {
        this.children = children;
    }

    public List<PersonWithAge> getAdults() {
        return adults;
    }

    public void setAdults(List<PersonWithAge> adults) {
        this.adults = adults;
    }
}
