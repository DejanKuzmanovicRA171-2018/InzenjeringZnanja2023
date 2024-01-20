package inzenjering_znanja.api.Models;

import lombok.Getter;
import lombok.Setter;
import ucm.gaia.jcolibri.cbrcore.Attribute;
import ucm.gaia.jcolibri.cbrcore.CaseComponent;
import com.fasterxml.jackson.annotation.JsonIgnore;

@Getter
@Setter
public class CPU implements CaseComponent {
    // FIELDS
    private String name;
    private int cpuClockSpeed;
    private int cpuNumOfCores;
    private int cpuNumOfThreads;

    // CONSTRUCTORS
    public CPU() {
        this.cpuClockSpeed = 0;
        this.cpuNumOfCores = 0;
        this.cpuNumOfThreads = 0;
    }

    public CPU(String name, int cpuClockSpeed, int cpuNumOfCores, int cpuNumOfThreads) {
        this.name = name;
        this.cpuClockSpeed = cpuClockSpeed;
        this.cpuNumOfCores = cpuNumOfCores;
        this.cpuNumOfThreads = cpuNumOfThreads;
    }

    @JsonIgnore
    @Override
    public Attribute getIdAttribute() {
        return new Attribute("id", this.getClass());
    }
}
