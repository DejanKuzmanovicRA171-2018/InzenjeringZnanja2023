package inzenjering_znanja.api.Models;

import com.fasterxml.jackson.annotation.JsonIgnore;

import lombok.Getter;
import lombok.Setter;
import ucm.gaia.jcolibri.cbrcore.Attribute;
import ucm.gaia.jcolibri.cbrcore.CaseComponent;

@Getter
@Setter
public class GPU implements CaseComponent {
    // FIELDS
    private String name;
    private int gpuClockSpeed;
    private int gpuNumOfCores;
    private int gpuVRAM;

    // CONSTRUCTORS
    public GPU() {
        this.gpuClockSpeed = 0;
        this.gpuNumOfCores = 0;
        this.gpuVRAM = 0;
    }

    public GPU(String name, int gpuClockSpeed, int gpuNumOfCores, int gpuVRAM) {
        this.name = name;
        this.gpuClockSpeed = gpuClockSpeed;
        this.gpuNumOfCores = gpuNumOfCores;
        this.gpuVRAM = gpuVRAM;
    }

    @JsonIgnore
    @Override
    public Attribute getIdAttribute() {
        return new Attribute("id", this.getClass());
    }
}
