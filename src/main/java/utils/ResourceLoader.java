package utils;

import com.fasterxml.jackson.databind.ObjectMapper;
import dinosaur.Dinosaur;
import park.Park;

import java.io.IOException;

public class ResourceLoader {

    private ObjectMapper buildMapper() {
        ObjectMapper mapper = new ObjectMapper();
        mapper.enableDefaultTyping();
        return mapper;
    }

    public Park getDinosaurParkFromJson(String fileName) throws IOException {
        return buildMapper().readValue(this.getClass().getClassLoader().getResourceAsStream(fileName), Park.class);
    }

    public Dinosaur getDinosaurFromJson(String fileName) throws IOException {
        return buildMapper().readValue(this.getClass().getClassLoader().getResourceAsStream(fileName), Dinosaur.class);
    }
}
