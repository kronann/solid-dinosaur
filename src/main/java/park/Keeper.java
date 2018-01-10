package park;

import dinosaur.Dinosaur;
import dinosaur.FoodEnum;
import dinosaur.Stegosaur;
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

    // OI
    public void feedDinosaur(FoodEnum foodEnum) {
        for (Dinosaur dinosaur : dinosaurs) {
            switch (foodEnum) {
                case MEAT:
                    dinosaur.eatMeat();
                    break;
                case GRASS:
                    if (dinosaur instanceof Triceratops) {
                        ((Triceratops)dinosaur).eatGrass();
                    }else if (dinosaur instanceof Stegosaur) {
                        ((Stegosaur) dinosaur).eatGrass();
                    }
                    break;
                default:
                    throw new UnsupportedOperationException("Can't eat anything");
            }
        }
    }
}
