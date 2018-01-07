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

    @Override
    public void fly() {
        System.out.println("I can't fly :'(");
    }
}
