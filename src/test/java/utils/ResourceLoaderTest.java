package utils;

import api.ResourceLoader;
import dinosaur.Dinosaur;
import org.junit.Test;
import park.Park;

import static org.junit.Assert.assertTrue;


public class ResourceLoaderTest {

    private ResourceLoader resourceLoader;

    @Test
    public void should_getDinosaurParkFromJson() throws Exception {
        resourceLoader = new JSONLoader();
        Park park = resourceLoader.loadPark();

        assertTrue(park.getDinosaurs().size() > 1);
    }

    @Test
    public void should_getDinosaurFromFile() throws Exception {
        resourceLoader = new FileLoader();
        Dinosaur dinosaur = resourceLoader.loadDinosaur();

        assertTrue("Must be Mosa", dinosaur.getName().equals("Mosa rella"));
    }

}