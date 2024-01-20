package inzenjering_znanja.api.Models;

import com.fasterxml.jackson.annotation.JsonIgnore;

import lombok.Getter;
import lombok.Setter;
import ucm.gaia.jcolibri.cbrcore.Attribute;
import ucm.gaia.jcolibri.cbrcore.CaseComponent;

@Getter
@Setter
public class RAM implements CaseComponent {
    // FIELDS
    private String name;
    private int ramClockSpeed;
    private int sizeOfRAM;
    // private String speedOfRAM;

    // CONSTRUCTORS
    public RAM() {
        this.ramClockSpeed = 0;
        this.sizeOfRAM = 0;
        // this.speedOfRAM = "DDR1";
    }

    public RAM(String name, int ramClockSpeed, int sizeOfRAM /* , String speedOfRAM */) {
        this.name = name;
        this.ramClockSpeed = ramClockSpeed;
        this.sizeOfRAM = sizeOfRAM;
        // this.speedOfRAM = speedOfRAM;
    }

    @JsonIgnore
    @Override
    public Attribute getIdAttribute() {
        return new Attribute("id", this.getClass());
    }
}
