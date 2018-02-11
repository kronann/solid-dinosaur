package utils;

import api.ResourceLoader;
import com.fasterxml.jackson.databind.ObjectMapper;
import dinosaur.Dinosaur;
import org.springframework.stereotype.Component;
import park.Park;

import java.io.IOException;
import java.io.InputStream;

@Component
public class FileLoader implements ResourceLoader {

    private static final String PARK_JSON = "dinosaurs-park.json";
    private static final String DINOSAUR_JSON = "dinosaur-rella.json";

    private ObjectMapper buildMapper() {
        ObjectMapper mapper = new ObjectMapper();
        mapper.enableDefaultTyping();
        return mapper;
    }

    @Override
    public Park loadPark() throws IOException {
        try (InputStream inputStream = this.getClass().getClassLoader().getResourceAsStream(PARK_JSON)) {
            return buildMapper().readValue(inputStream, Park.class);
        }
    }

    @Override
    public Dinosaur loadDinosaur() throws IOException {
        try (InputStream inputStream = this.getClass().getClassLoader().getResourceAsStream(DINOSAUR_JSON)) {
            return buildMapper().readValue(inputStream, Dinosaur.class);
        }
    }
}
