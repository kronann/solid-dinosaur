package dinosaur;

import api.DinosaurActivity;
import api.DinosaurStuff;
import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonSubTypes;
import com.fasterxml.jackson.annotation.JsonTypeInfo;
import lombok.Data;

@JsonIgnoreProperties(ignoreUnknown = true)
@JsonTypeInfo(use = JsonTypeInfo.Id.NAME, include = JsonTypeInfo.As.PROPERTY)
@JsonSubTypes({
        @JsonSubTypes.Type(value = SwimmingDinosaur.class, name = "SwimmingDinosaur"),
        @JsonSubTypes.Type(value = FlyingDinosaur.class, name = "FlyingDinosaur"),
        @JsonSubTypes.Type(value = GossipDinosaur.class, name = "GossipDinosaur"),
        @JsonSubTypes.Type(value = GossipDinosaur.class, name = "GossipDinosaur"),
}
)
@Data
public abstract class Dinosaur implements DinosaurStuff, DinosaurActivity {
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
    public void run() {
        System.out.println(getName() + " run");
    }

    @Override
    public void talk() {
        System.out.println(getName() + " talk");
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
