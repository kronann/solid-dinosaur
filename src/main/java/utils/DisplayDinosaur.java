package utils;

import com.fasterxml.jackson.databind.ObjectMapper;
import dinosaur.Dinosaur;
import park.Park;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.util.List;
import java.util.function.Predicate;

public class DisplayDinosaur {
    private static final String DINOSAURS_FILE_JSON = "dinosaurs.json";
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

    public void displayFromJson() throws IOException {
        Park park = mapper.readValue(this.getClass().getClassLoader().getResourceAsStream(DINOSAURS_FILE_JSON), Park.class);
        String json = mapper.writeValueAsString(park);
        System.out.println(json);
    }

    public void displayFromJsonPretty() throws IOException {
        Park park = mapper.readValue(this.getClass().getClassLoader().getResourceAsStream("dinosaurs.json"), Park.class);
        String jsonPretty = mapper.writerWithDefaultPrettyPrinter().writeValueAsString(park);
        System.out.println(jsonPretty);
    }

    public void display(Dinosaur dinosaur) {
        System.out.println("Lonesome dinosaur... " + dinosaur);
    }

    private String readFromInputStream(InputStream inputStream) throws IOException {
        StringBuilder resultStringBuilder = new StringBuilder();

        try (BufferedReader br = new BufferedReader(new InputStreamReader(inputStream))) {
            String line;
            while ((line = br.readLine()) != null) {
                resultStringBuilder.append(line).append("\n");
            }
        }
        return resultStringBuilder.toString();
    }
}
