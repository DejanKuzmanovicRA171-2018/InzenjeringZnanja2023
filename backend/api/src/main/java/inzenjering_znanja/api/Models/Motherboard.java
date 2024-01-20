package inzenjering_znanja.api.Models;

import com.fasterxml.jackson.annotation.JsonIgnore;

import lombok.Getter;
import lombok.Setter;
import ucm.gaia.jcolibri.cbrcore.Attribute;
import ucm.gaia.jcolibri.cbrcore.CaseComponent;

@Getter
@Setter
public class Motherboard implements CaseComponent {
    // FIELDS
    String name;
    private int numOfRAMSlotsM;
    private int ramCapacityM;
    // private String ramSpeedType;

    // CONSTRUCTORS
    public Motherboard() {
        this.numOfRAMSlotsM = 0;
        this.ramCapacityM = 0;
        // this.ramSpeedType = "DDR1";
    }

    public Motherboard(String name, int numOfRAMSlotsM, int ramCapacityM /* ,String hasRAMSpeedType */) {
        this.name = name;
        this.numOfRAMSlotsM = numOfRAMSlotsM;
        this.ramCapacityM = ramCapacityM;
        // this.ramSpeedType = hasRAMSpeedType;
    }

    @JsonIgnore
    @Override
    public Attribute getIdAttribute() {
        return new Attribute("id", this.getClass());
    }
}
