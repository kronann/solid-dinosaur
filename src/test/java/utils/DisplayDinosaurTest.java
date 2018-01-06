package utils;

import dinosaur.*;
import org.junit.Test;
import park.Park;

import java.io.ByteArrayOutputStream;
import java.io.PrintStream;
import java.util.Arrays;

import static org.junit.Assert.*;


public class DisplayDinosaurTest {

    @Test
    public void should_display_dinosaur() throws Exception {

        Dinosaur velociraptor = new Velociraptor("Velo", 15, 20, 20);

        try (ByteArrayOutputStream outContent = new ByteArrayOutputStream()) {
            System.setOut(new PrintStream(outContent));

            DisplayDinosaur displayDinosaur = new DisplayDinosaur();
            displayDinosaur.display(velociraptor);

            assertEquals("Lonesome dinosaur... " + velociraptor.toString(), outContent.toString().trim());
        }
    }

    @Test
    public void should_displayWithPredicate_dinosaur() throws Exception {

        Park park = buildPark();

        try (ByteArrayOutputStream outContent = new ByteArrayOutputStream()) {
            System.setOut(new PrintStream(outContent));

            DisplayDinosaur displayDinosaur = new DisplayDinosaur();
            displayDinosaur.display(park.getDinosaurs(), dinosaur -> dinosaur.getWeight() > 1);

            assertFalse(outContent.toString().contains("Velo"));
            assertFalse(outContent.toString().contains("Ptéro"));
            assertTrue(outContent.toString().contains("Mosa"));
        }
    }

    @Test
    public void should_displayFromJson_dinosaur() throws Exception {

        try (ByteArrayOutputStream outContent = new ByteArrayOutputStream()) {
            System.setOut(new PrintStream(outContent));

            DisplayDinosaur displayDinosaur = new DisplayDinosaur();
            displayDinosaur.displayFromJsonPretty();

            assertTrue(outContent.toString().contains("Mosasaure"));
        }

    }

    @Test
    public void should_displayFromJsonPretty_dinosaur() throws Exception {

        try (ByteArrayOutputStream outContent = new ByteArrayOutputStream()) {
            System.setOut(new PrintStream(outContent));

            DisplayDinosaur displayDinosaur = new DisplayDinosaur();
            displayDinosaur.displayFromJson();

            assertTrue(outContent.toString().contains("Triceratops"));
        }
    }


    private Park buildPark() {
        Dinosaur mosasaure = new Mosasaure("Mosa", 25, 20, 20);
        Dinosaur pterodactyl = new Pterodactyl("Ptéro", 20, 3, 1, 5);
        Dinosaur triceratops = new Triceratops("Tricé", 50, 5, 15);
        Dinosaur velociraptor = new Velociraptor("Velo", 15, 2, 1);

        Park park = new Park();
        park.setDinosaurs(Arrays.asList(mosasaure, pterodactyl, triceratops, velociraptor));

        return park;
    }

}