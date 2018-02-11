package utils;

import api.ResourceLoader;
import com.fasterxml.jackson.databind.ObjectMapper;
import dinosaur.Dinosaur;
import org.springframework.stereotype.Component;
import park.Park;

import java.io.IOException;

// S - OK
@Component
public class JSONLoader implements ResourceLoader {

    private static final String PARK_JSON = "dinosaurs-park.json";
    private static final String DINOSAUR_JSON = "dinosaur-rella.json";

    private ObjectMapper buildMapper() {
        ObjectMapper mapper = new ObjectMapper();
        mapper.enableDefaultTyping();
        return mapper;
    }

    @Override
    public Park loadPark() throws IOException {
        return buildMapper().readValue(this.getClass().getClassLoader().getResourceAsStream(PARK_JSON), Park.class);
    }

    @Override
    public Dinosaur loadDinosaur() throws IOException {
        return buildMapper().readValue(this.getClass().getClassLoader().getResourceAsStream(DINOSAUR_JSON), Dinosaur.class);
    }
}
