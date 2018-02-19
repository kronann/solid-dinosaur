package utils;

import dinosaur.Dinosaur;
import dinosaur.GossipDinosaur;
import org.junit.Test;
import park.Park;

import java.io.ByteArrayOutputStream;
import java.io.PrintStream;

import static org.junit.Assert.assertTrue;


public class DisplayDinosaurTest {

    @Test
    public void should_display_dinosaur() throws Exception {

        Dinosaur velociraptor = new GossipDinosaur("Vélo", 15, 20, 20);

        try (ByteArrayOutputStream outContent = new ByteArrayOutputStream()) {
            System.setOut(new PrintStream(outContent));

            DisplayDinosaur displayDinosaur = new DisplayDinosaur();
            displayDinosaur.displayRaw(velociraptor);

            assertTrue(outContent.toString().contains("Vélo"));
        }
    }

    @Test
    public void should_display_dinosaurs() throws Exception {
        Park park = new JSONLoader().loadPark();

        try (ByteArrayOutputStream outContent = new ByteArrayOutputStream()) {
            System.setOut(new PrintStream(outContent));

            DisplayDinosaur displayDinosaur = new DisplayDinosaur();
            displayDinosaur.displayPretty(park.getDinosaurs());

            assertTrue(outContent.toString().contains("Mosa"));
            assertTrue(outContent.toString().contains("Ptéro"));
        }
    }
}