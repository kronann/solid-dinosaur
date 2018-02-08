package park;

import dinosaur.Dinosaur;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.ArrayList;
import java.util.List;

@NoArgsConstructor
@Data
public class Park {
    private List<Dinosaur> dinosaurs = new ArrayList<>();
}
