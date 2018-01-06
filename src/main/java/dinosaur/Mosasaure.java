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
}
