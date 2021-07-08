package emergencysystem.model;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.ArrayList;
import java.util.List;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class ResultChildAlert {

    private List<PersonWithAge> children = new ArrayList<>();
    private List<PersonWithAge> adults = new ArrayList<>();
}
