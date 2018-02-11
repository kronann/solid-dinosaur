package solid;

import dinosaur.Dinosaur;
import dinosaur.TrainingExercice;
import park.Keeper;
import park.Park;
import utils.DisplayDinosaur;
import utils.ResourceLoader;

import java.io.IOException;

public class Main {
    static Park dinosaursPark;

    public static void main(String[] args) throws IOException {

        ResourceLoader resourceLoader = new ResourceLoader();
        dinosaursPark = resourceLoader.getDinosaursParkFromJson("dinosaurs-park.json");

        DisplayDinosaur displayDinosaur = new DisplayDinosaur();
        displayDinosaur.displayPretty(dinosaursPark.getDinosaurs());

        Keeper keeper = new Keeper();
        keeper.trainDinosaurs(dinosaursPark.getDinosaurs(), TrainingExercice.RUN);

    }

    static Dinosaur getMosa(){
        return dinosaursPark.getDinosaurs().get(0);
    }

    static Dinosaur getTricé(){
        return dinosaursPark.getDinosaurs().get(2);
    }
}
