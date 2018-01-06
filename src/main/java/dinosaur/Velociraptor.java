package dinosaur;

import lombok.NoArgsConstructor;

@NoArgsConstructor
public class Velociraptor extends Dinosaur {

    public Velociraptor(String name, int age, int height, int weight) {
        super(name, age, height, weight);
    }

    @Override
    public String toString() {
        return "Velociraptor - " + super.toString();
    }
}
