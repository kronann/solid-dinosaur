package utils;

import com.fasterxml.jackson.databind.ObjectMapper;
import dinosaur.Dinosaur;
import lombok.Data;
import park.Park;

import java.io.IOException;
import java.io.InputStream;
import java.util.List;
import java.util.function.Predicate;

@Data
public class DisplayDinosaur {
    private Park park;
    private static final String DINOSAURS_FILE_JSON = "dinosaurs-park.json";
    private ObjectMapper mapper = new ObjectMapper();

    public DisplayDinosaur() {
        mapper.enableDefaultTyping();
    }

    public void display(List<Dinosaur> dinosaurs) {
        dinosaurs.forEach(this::display);
    }

    public void display(List<Dinosaur> dinosaurs, Predicate<Dinosaur> predicate) {
        dinosaurs.stream().filter(predicate).forEach(this::display);
    }

    public void display(Dinosaur dinosaur) {
        System.out.println("Lonesome dinosaur... " + dinosaur);
    }

    public void displayFromJson() throws IOException {
        System.out.println("displayFromJson -> ");

        park = mapper.readValue(this.getClass().getClassLoader().getResourceAsStream(DINOSAURS_FILE_JSON), Park.class);
        String json = mapper.writeValueAsString(park);
        System.out.println(json);
    }

    public void displayFromJsonPretty() throws IOException {
        System.out.println("displayFromJsonPretty -> ");

        park = mapper.readValue(this.getClass().getClassLoader().getResourceAsStream(DINOSAURS_FILE_JSON), Park.class);
        String jsonPretty = mapper.writerWithDefaultPrettyPrinter().writeValueAsString(park);
        System.out.println(jsonPretty);
    }

    public Dinosaur readFromInputStream(String file) throws IOException {
        InputStream inputStream = this.getClass().getClassLoader().getResourceAsStream(file);

        Dinosaur dinosaur = mapper.readValue(inputStream, Dinosaur.class);
        park.addDinosaur(dinosaur);
        String dino = mapper.writeValueAsString(dinosaur);
        System.out.println("From input stream - " + dino);

        return dinosaur;
    }
}
