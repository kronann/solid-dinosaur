package park;

import api.ResourceLoader;
import lombok.Data;

import java.io.IOException;

@Data
public class ParkManager {

    private Park dinosaursPark;

    public ParkManager(ResourceLoader resourceLoader) throws IOException {
        dinosaursPark = resourceLoader.loadPark();
    }
}
