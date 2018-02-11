package solid;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import park.Keeper;
import park.Park;
import park.ParkManager;
import utils.DisplayDinosaur;

@SpringBootApplication
public class Main implements CommandLineRunner {

    @Autowired
    ParkManager parkManager;

    public static void main(String[] args) {
        SpringApplication.run(Main.class, args);
    }

    @Override
    public void run(String... args) {
        Park dinosaursPark = parkManager.getDinosaursPark();

        DisplayDinosaur displayDinosaur = new DisplayDinosaur();
        displayDinosaur.displayPretty(dinosaursPark.getDinosaurs());

        Keeper keeper = new Keeper();
        keeper.trainDinosaurs(dinosaursPark.getDinosaurs());
    }
}
