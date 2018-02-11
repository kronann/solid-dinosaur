package dinosaur;

import api.SwimmingDinosaur;
import lombok.NoArgsConstructor;

@NoArgsConstructor
public class Mosasaure extends Dinosaur implements SwimmingDinosaur {

    public Mosasaure(String name, int age, int height, int weight) {
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
