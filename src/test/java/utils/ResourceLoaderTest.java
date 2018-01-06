package utils;

import dinosaur.Dinosaur;
import org.junit.Test;
import park.Park;

import static org.junit.Assert.assertTrue;

public class ResourceLoaderTest {

    @Test
    public void should_getDinosaurParkFromJson() throws Exception {
        String fileName = "dinosaurs-park.json";
        ResourceLoader resourceLoader = new ResourceLoader();
        Park park = resourceLoader.getDinosaurParkFromJson(fileName);

        assertTrue(park.getDinosaurs().size() > 1);
    }

    @Test
    public void should_getDinosaurFromJson() throws Exception {
        String fileName = "dinosaur.json";
        ResourceLoader resourceLoader = new ResourceLoader();
        Dinosaur dinosaur = resourceLoader.getDinosaurFromJson(fileName);

        assertTrue("Must be Mosa", dinosaur.getName().equals("Mosa"));
    }

}