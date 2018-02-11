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
    public void feedDinosaur(List<Dinosaur> dinosaurs) {
        System.out.println("Keeper.feedDinosaur");

        for (Dinosaur dinosaur : dinosaurs) {
            if (dinosaur instanceof Triceratops) {
                ((Triceratops) dinosaur).eatGrass();
            } else if (dinosaur instanceof Stegosaur) {
                ((Stegosaur) dinosaur).eatGrass();
            } else {
                dinosaur.eatMeat();
            }
        }
    }
}
