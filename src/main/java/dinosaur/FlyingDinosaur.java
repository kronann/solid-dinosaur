package dinosaur;

import api.FlyingSkill;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@NoArgsConstructor
public class FlyingDinosaur extends Dinosaur implements FlyingSkill {

    @Getter
    @Setter
    private int wingsWide;

    public FlyingDinosaur(String name, int age, int height, int weight, int wingsWide) {
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
        System.out.println("Pterodactyl.eatMeat");
    }

    @Override
    public void fly() {
        System.out.println("Pterodactyl.fly");
    }


    @Override
    public void startTraining() {
        fly();
    }
}
