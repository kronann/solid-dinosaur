package utils;

import dinosaur.*;
import org.junit.Test;
import park.Park;

import java.io.ByteArrayOutputStream;
import java.io.PrintStream;
import java.util.Arrays;

import static org.junit.Assert.assertTrue;


public class DisplayDinosaurTest {

    @Test
    public void should_display_dinosaur() throws Exception {

        Dinosaur velociraptor = new Stegosaur("Vélo", 15, 20, 20);

        try (ByteArrayOutputStream outContent = new ByteArrayOutputStream()) {
            System.setOut(new PrintStream(outContent));

            DisplayDinosaur displayDinosaur = new DisplayDinosaur();
            displayDinosaur.displayRaw(velociraptor);

            assertTrue(outContent.toString().contains("Vélo"));
        }
    }

    @Test
    public void should_display_dinosaurs() throws Exception {
        Park park = buildPark();

        try (ByteArrayOutputStream outContent = new ByteArrayOutputStream()) {
            System.setOut(new PrintStream(outContent));

            DisplayDinosaur displayDinosaur = new DisplayDinosaur();
            displayDinosaur.displayPretty(park.getDinosaurs());

            assertTrue(outContent.toString().contains("Mosa"));
            assertTrue(outContent.toString().contains("Vélo"));
        }
    }


    private Park buildPark() {
        Dinosaur mosasaure = new Mosasaure("Mosa", 25, 20, 20);
        Dinosaur pterodactyl = new Pterodactyl("Ptéro", 20, 3, 1, 5);
        Dinosaur triceratops = new Triceratops("Tricé", 50, 5, 15);
        Dinosaur velociraptor = new Stegosaur("Vélo", 15, 2, 1);

        Park park = new Park();
        park.setDinosaurs(Arrays.asList(mosasaure, pterodactyl, triceratops, velociraptor));

        return park;
    }

}