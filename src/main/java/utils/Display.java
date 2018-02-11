package utils;

import dinosaur.Dinosaur;

import java.util.List;

public interface Display {
    void displayPretty(List<Dinosaur> dinosaurs);

    void displayRaw(List<Dinosaur> dinosaurs);
}
