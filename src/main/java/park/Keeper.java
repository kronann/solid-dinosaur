package park;

import dinosaur.Dinosaur;
import dinosaur.FoodEnum;
import dinosaur.Stegosaur;
import dinosaur.Triceratops;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.ArrayList;
import java.util.List;

@NoArgsConstructor
@Data
public class Keeper {
    // OI
    public void feedDinosaur(List<Dinosaur> dinosaurs, FoodEnum foodEnum) {
        for (Dinosaur dinosaur : dinosaurs) {
            switch (foodEnum) {
                case MEAT:
                    dinosaur.eatMeat();
                    break;
                case GRASS:
                    if (dinosaur instanceof Triceratops) {
                        ((Triceratops) dinosaur).eatGrass();
                    } else if (dinosaur instanceof Stegosaur) {
                        ((Stegosaur) dinosaur).eatGrass();
                    }
                    break;
                default:
                    throw new UnsupportedOperationException("Can't eat anything");
            }
        }
    }
}
