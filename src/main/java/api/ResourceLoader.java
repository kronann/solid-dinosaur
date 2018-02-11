package api;

import dinosaur.Dinosaur;
import park.Park;

import java.io.IOException;

public interface ResourceLoader {
    Park loadPark() throws IOException;

    Dinosaur loadDinosaur() throws IOException;
}
