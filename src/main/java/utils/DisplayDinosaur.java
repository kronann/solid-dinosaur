package utils;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import dinosaur.Dinosaur;

import java.util.List;

// S - OK
public class DisplayDinosaur implements Display {

    @Override
    public void displayPretty(List<Dinosaur> dinosaurs) {
        System.out.print("Dinos : ");
        formatDisplay(dinosaurs);
    }

    @Override
    public void displayRaw(List<Dinosaur> dinosaurs) {
        System.out.print("Dinos : ");
        dinosaurs.forEach(System.out::println);
    }

    public void displayRaw(Dinosaur dinosaur) {
        System.out.print("Lonesome Dino : " + dinosaur);
        System.out.println();
    }

    public void displayPretty(Dinosaur dinosaur) {
        System.out.print("Lonesome Dino : ");
        formatDisplay(dinosaur);
    }

    private void formatDisplay(Object object) {
        ObjectMapper mapper = new ObjectMapper();
        try {
            System.out.println(mapper.writerWithDefaultPrettyPrinter().writeValueAsString(object));
            System.out.println();
        } catch (JsonProcessingException e) {
            e.printStackTrace();
        }
    }
}
