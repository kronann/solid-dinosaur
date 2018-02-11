package solid;

import dinosaur.Dinosaur;
import dinosaur.FoodType;
import park.Keeper;
import park.Park;
import utils.DisplayDinosaur;
import utils.ResourceLoader;

import java.io.IOException;

public class Main {

    public static void main(String[] args) throws IOException {

        ResourceLoader resourceLoader = new ResourceLoader();
        Park dinosaursPark = resourceLoader.getDinosaursParkFromJson("dinosaurs-park.json");
        Dinosaur dinosaur = resourceLoader.getDinosaurFromJson("dinosaur.json");
        Dinosaur dinosaurFromIS = resourceLoader.readFromInputStream("dinosaur-rella.json");

        DisplayDinosaur displayDinosaur = new DisplayDinosaur();
        displayDinosaur.displayRaw(dinosaursPark.getDinosaurs());
        displayDinosaur.displayPretty(dinosaursPark.getDinosaurs());

        displayDinosaur.displayRaw(dinosaur);

        displayDinosaur.displayPretty(dinosaurFromIS);
        dinosaurFromIS.run();
        System.out.println();

        Keeper keeper = new Keeper();
        keeper.feedDinosaur(dinosaursPark.getDinosaurs());
    }

}
