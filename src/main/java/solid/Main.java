package solid;

import dinosaur.Dinosaur;
import park.Park;
import utils.DisplayDinosaur;
import utils.ResourceLoader;

import java.io.IOException;
import java.util.stream.Collectors;

public class Main {

    public static void main(String[] args) throws IOException {

        ResourceLoader resourceLoader = new ResourceLoader();
        Park dinosaurPark = resourceLoader.getDinosaurParkFromJson("dinosaurs-park.json");
        Dinosaur dinosaur = resourceLoader.getDinosaurFromJson("dinosaur.json");

        DisplayDinosaur displayDinosaur = new DisplayDinosaur();
        displayDinosaur.display(dinosaurPark.getDinosaurs());

        displayDinosaur.display(dinosaurPark.getDinosaurs().stream()
                .filter(d -> d.getAge() < 25)
                .collect(Collectors.toList()));

        displayDinosaur.display(dinosaur);
    }

}
