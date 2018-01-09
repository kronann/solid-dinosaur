package park;

import dinosaur.Dinosaur;
import dinosaur.Triceratops;
import lombok.NoArgsConstructor;

import java.util.ArrayList;
import java.util.List;

@NoArgsConstructor
public class Keeper {

    private List<Dinosaur> dinosaurs = new ArrayList<>();

    public List<Dinosaur> getDinosaurs() {
        return dinosaurs;
    }

    public void setDinosaurs(List<Dinosaur> dinosaurs) {
        this.dinosaurs = dinosaurs;
    }

    public void feedDinosaur(){
        for (Dinosaur dinosaur : dinosaurs){
            if (dinosaur instanceof Triceratops) {
                 // ?
            }
        }
    }
}
