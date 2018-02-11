package utils;

import com.fasterxml.jackson.databind.ObjectMapper;
import dinosaur.Dinosaur;
import park.Park;

import java.io.IOException;
import java.io.InputStream;

// S - OK
public final class ResourceLoader {

    private ObjectMapper buildMapper() {
        ObjectMapper mapper = new ObjectMapper();
        mapper.enableDefaultTyping();
        return mapper;
    }

    public Park getDinosaursParkFromJson(String fileName) throws IOException {
        return buildMapper().readValue(this.getClass().getClassLoader().getResourceAsStream(fileName), Park.class);
    }

    public Dinosaur getDinosaurFromJson(String fileName) throws IOException {
        return buildMapper().readValue(this.getClass().getClassLoader().getResourceAsStream(fileName), Dinosaur.class);
    }

    public Dinosaur readFromInputStream(String file) throws IOException {
        try (InputStream inputStream = this.getClass().getClassLoader().getResourceAsStream(file)) {
            return buildMapper().readValue(inputStream, Dinosaur.class);
        }
    }

}
