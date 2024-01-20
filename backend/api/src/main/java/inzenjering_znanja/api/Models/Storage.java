package inzenjering_znanja.api.Models;

import com.fasterxml.jackson.annotation.JsonIgnore;

import lombok.Getter;
import lombok.Setter;
import ucm.gaia.jcolibri.cbrcore.Attribute;
import ucm.gaia.jcolibri.cbrcore.CaseComponent;

@Getter
@Setter
public class Storage implements CaseComponent {
    // FIELDS
    private String name;
    private int writeSpeed;
    private int storageCapacity;
    private int rpm;

    // CONSTRUCTORS
    public Storage() {
        this.writeSpeed = 0;
        this.storageCapacity = 0;
        this.rpm = 0;
    }

    public Storage(String name, int writeSpeed, int storageCapacity, int rpm) {
        this.name = name;
        this.writeSpeed = writeSpeed;
        this.storageCapacity = storageCapacity;
        this.rpm = rpm;
    }

    @JsonIgnore
    @Override
    public Attribute getIdAttribute() {
        return new Attribute("id", this.getClass());
    }
}
