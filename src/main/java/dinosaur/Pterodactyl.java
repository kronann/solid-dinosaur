package dinosaur;

import api.FlyingDinosaur;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@NoArgsConstructor
public class Pterodactyl extends Dinosaur implements FlyingDinosaur {

    @Getter
    @Setter
    private int wingsWide;

    public Pterodactyl(String name, int age, int height, int weight, int wingsWide) {
        super(name, age, height, weight);
        this.wingsWide = wingsWide;
    }

    @Override
    public String toString() {
        return "Pterodactyl - " + super.toString() +
                ", wingsWide='" + wingsWide + '\'';
    }

    @Override
    public void eat() {
        System.out.println(getName() + " eatMeat");
    }

    @Override
    public void fly() {
        System.out.println(getName() + " fly");
    }


    @Override
    public void startTraining() {
        fly();
    }
}
