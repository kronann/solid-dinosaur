package dinosaur;

import lombok.NoArgsConstructor;

@NoArgsConstructor
public class Stegosaur extends Dinosaur {

    public Stegosaur(String name, int age, int height, int weight) {
        super(name, age, height, weight);
    }

    @Override
    public String toString() {
        return "Stegosaur - " + super.toString();
    }

    public void eat() {
        System.out.println(getName() + " eatGrass");
    }

    @Override
    public void startTraining() {
        talk();
    }
}
