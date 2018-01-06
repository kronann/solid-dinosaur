package utils;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import dinosaur.Dinosaur;

import java.util.List;

public class DisplayDinosaur {

    public void display(List<Dinosaur> dinosaurs) {
        System.out.print("Dinos : ");
        formatDisplay(dinosaurs);
    }

    public void display(Dinosaur dinosaur) {
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
