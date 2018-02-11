package park;

import dinosaur.Dinosaur;
import dinosaur.TrainingExercice;
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

    public void trainDinosaurs(List<Dinosaur> dinosaurs, TrainingExercice trainingExercice) {
        for (Dinosaur dinosaur : dinosaurs) {
            switch (trainingExercice) {
                case RUN:
                    dinosaur.run();
                    break;
                case SWIM:
                    dinosaur.swim();
                    break;
                case FLY:
                    dinosaur.fly();
                    break;
                default:
                    dinosaur.talk();
                    break;
            }
        }
    }

}
