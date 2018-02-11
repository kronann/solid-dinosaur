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

    @Override
    public void fly() {
        System.out.println("I can't fly :'(");
    }

    @Override
    public void swim() {
        System.out.println("I can't swim :'(");

    }

    public void eatGrass() {
        System.out.println(getName() + " eatGrass");
    }
}
