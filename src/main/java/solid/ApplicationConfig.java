package solid;

import api.ResourceLoader;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import park.ParkManager;
import utils.FileLoader;

import java.io.IOException;

@Configuration
public class ApplicationConfig {

    @Bean
    ParkManager createParkManager(ResourceLoader resourceLoader) throws IOException {
        return new ParkManager(resourceLoader);
    }

    @Bean
    ResourceLoader createResourceLoader() {
        return new FileLoader();
    }

}
