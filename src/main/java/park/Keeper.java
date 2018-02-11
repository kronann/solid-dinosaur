package park;

import dinosaur.Dinosaur;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;

@NoArgsConstructor
@Data
public class Keeper {
    // O
    public void feedDinosaur(List<Dinosaur> dinosaurs) {
        System.out.println("Keeper.feedDinosaur");
        for (Dinosaur dinosaur : dinosaurs) {
            dinosaur.eat();
        }
    }

    public void trainDinosaurs(List<Dinosaur> dinosaurs) {
        System.out.println("Keeper.trainDinosaurs - ");
        for (Dinosaur dinosaur : dinosaurs) {
            dinosaur.startTraining();
        }
    }
}
