package dinosaur;

import lombok.NoArgsConstructor;

@NoArgsConstructor
public class Mosasaure extends Dinosaur {

    public Mosasaure(String name, int age, int height, int weight) {
        super(name, age, height, weight);
    }

    @Override
    public String toString() {
        return "Mosasaure - " + super.toString();
    }

    // LI
    @Override
    public void fly() {
        System.out.println("I can't fly :'(");
    }

    @Override
    public void swim() {
        System.out.println("Mosasaure.swim");
    }

}
