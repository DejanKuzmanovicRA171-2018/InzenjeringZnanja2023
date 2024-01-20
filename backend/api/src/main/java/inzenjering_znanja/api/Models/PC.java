package inzenjering_znanja.api.Models;

import com.fasterxml.jackson.annotation.JsonIgnore;

import lombok.Getter;
import lombok.Setter;
import ucm.gaia.jcolibri.cbrcore.Attribute;
import ucm.gaia.jcolibri.cbrcore.CaseComponent;

@Getter
@Setter
public class PC implements CaseComponent {
    // FIELDS
    private CPU cpu;
    private GPU gpu;
    private Motherboard motherboard;
    private RAM ram;
    private Storage storage;
    private PSU psu;
    private Cooling cooling;
    private Case pcCase;

    // CONSTRUCTORS
    public PC(CPU cpu, GPU gpu, Motherboard motherboard, RAM ram, Storage storage) {
        this.cpu = cpu;
        this.gpu = gpu;
        this.motherboard = motherboard;
        this.ram = ram;
        this.storage = storage;
    }

    @JsonIgnore
    @Override
    public Attribute getIdAttribute() {
        return new Attribute("id", this.getClass());
    }
}
