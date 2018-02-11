package dinosaur;

import exception.FlyableException;
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

    // LI
    @Override
    public void fly() {
        try {
            throw new FlyableException("I can't fly :'(");
        } catch (FlyableException e) {
            e.printStackTrace();
        }
    }

    @Override
    public void swim() {
        System.out.println("I can't swim :'(  but....");
        run();
    }

    public void eatGrass() {
        System.out.println(getName() + " eatGrass");
    }
}
