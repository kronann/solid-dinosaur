package dinosaur;

import api.SwimmingSkill;
import lombok.NoArgsConstructor;

@NoArgsConstructor
public class SwimmingDinosaur extends Dinosaur implements SwimmingSkill {

    public SwimmingDinosaur(String name, int age, int height, int weight) {
        super(name, age, height, weight);
    }

    @Override
    public String toString() {
        return "Mosasaure - " + super.toString();
    }

    @Override
    public void eat() {
        System.out.println("Mosasaure.eatMeat");
    }


    @Override
    public void swim() {
        System.out.println("Mosasaure.swim");
    }

    @Override
    public void startTraining() {
        swim();
    }
}
