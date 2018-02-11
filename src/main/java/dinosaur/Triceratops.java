package dinosaur;

import lombok.NoArgsConstructor;

@NoArgsConstructor
public class Triceratops extends Dinosaur {

    public Triceratops(String name, int age, int height, int weight) {
        super(name, age, height, weight);
    }

    @Override
    public String toString() {
        return "Triceratops - " + super.toString();
    }

    public void eat() {
        System.out.println("Triceratops.eatGrass");
    }

    @Override
    public void startTraining() {
        talk();
    }
}
