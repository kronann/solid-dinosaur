package dinosaur;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonSubTypes;
import com.fasterxml.jackson.annotation.JsonTypeInfo;
import lombok.Data;

@JsonIgnoreProperties(ignoreUnknown = true)
@JsonTypeInfo(use = JsonTypeInfo.Id.NAME, include = JsonTypeInfo.As.PROPERTY)
@JsonSubTypes({
        @JsonSubTypes.Type(value = Mosasaure.class, name = "Mosasaure"),
        @JsonSubTypes.Type(value = Pterodactyl.class, name = "Pterodactyl"),
        @JsonSubTypes.Type(value = Triceratops.class, name = "Triceratops"),
        @JsonSubTypes.Type(value = Velociraptor.class, name = "Velociraptor"),
}
)
@Data
public abstract class Dinosaur {
    private String name;
    private int age;
    private int height; // meter
    private int weight; // tons

    protected Dinosaur() {
    }

    protected Dinosaur(String name, int age, int height, int weight) {
        this.name = name;
        this.age = age;
        this.height = height;
        this.weight = weight;
    }

    @Override
    public String toString() {
        return
                "name='" + name + '\'' +
                        ", age='" + age + '\'' +
                        ", height='" + height + '\'' +
                        ", weight='" + weight + '\'';
    }
}
