package solid;

import dinosaur.Dinosaur;
import dinosaur.FoodEnum;
import park.Keeper;
import park.Park;
import utils.DisplayDinosaur;

import java.io.IOException;

public class Main {

    public static void main(String[] args) throws IOException {
        DisplayDinosaur dinosaurs = new DisplayDinosaur();

        dinosaurs.displayFromJson();
        dinosaurs.displayFromJsonPretty();

        Keeper keeper = new Keeper();
        Park park = dinosaurs.getPark();
        keeper.feedDinosaur(park.getDinosaurs(), FoodEnum.GRASS);
        System.out.println();

        dinosaurs.readFromInputStream("dinosaur-rella.json");
        System.out.println();

        Dinosaur dino = dinosaurs.getPark().getDinosaurs().get(0);
        dinosaurs.display(dino);
        dino.run();

//        dino.eatMeat();
//        dino.fly();
    }

}
