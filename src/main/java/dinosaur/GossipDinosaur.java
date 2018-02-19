package dinosaur;

import lombok.NoArgsConstructor;

@NoArgsConstructor
public class GossipDinosaur extends Dinosaur {

    public GossipDinosaur(String name, int age, int height, int weight) {
        super(name, age, height, weight);
    }

    @Override
    public String toString() {
        return "GossipDinosaur - " + super.toString();
    }

    public void eat() {
        System.out.println("Stegosaur.eatGrass");
    }

    @Override
    public void startTraining() {
        talk();
    }
}
