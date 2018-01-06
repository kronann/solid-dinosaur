package park;

import dinosaur.Dinosaur;
import lombok.NoArgsConstructor;

import java.util.List;

@NoArgsConstructor
public class Park {

    private List<Dinosaur> dinosaurs;

    public List<Dinosaur> getDinosaurs() {
        return dinosaurs;
    }

    public void setDinosaurs(List<Dinosaur> dinosaurs) {
        this.dinosaurs = dinosaurs;
    }
}
