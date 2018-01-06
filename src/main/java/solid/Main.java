package solid;

import dinosaur.*;
import park.Park;
import utils.DisplayDinosaur;

import java.io.IOException;
import java.util.Arrays;

public class Main {

    public static void main(String[] args) {

        Dinosaur mosasaure = new Mosasaure("Mosa", 25, 20, 20);
        Dinosaur pterodactyl = new Pterodactyl("Ptéro", 20, 20, 20, 6);
        Dinosaur triceratops = new Triceratops("Tricé", 50, 20, 20);
        Dinosaur velociraptor = new Velociraptor("Velo", 15, 20, 20);

        Park park = new Park();
        park.setDinosaurs(Arrays.asList(mosasaure, pterodactyl, triceratops, velociraptor));

        try {
            DisplayDinosaur displayDinosaur = new DisplayDinosaur();

            displayDinosaur.displayFromJson();
            displayDinosaur.displayFromJsonPretty();
            displayDinosaur.display(park.getDinosaurs());

        } catch (IOException e) {
            e.printStackTrace();
        }
    }

}
