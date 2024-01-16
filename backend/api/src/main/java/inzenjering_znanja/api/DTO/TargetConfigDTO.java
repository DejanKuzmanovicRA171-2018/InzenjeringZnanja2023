package inzenjering_znanja.api.DTO;

public class TargetConfigDTO {
    // CPU constraints
    public int cpuClock;
    public int cpuCores;
    public int cpuThreads;
    // GPU constraints
    public int gpuClock;
    public int gpuVRAM;
    // RAM constraints
    public int ramClock;
    public int ramSize;
    // STORAGE constraints
    public int storageWriteSpeed;
    public int storageCapacity;
    public int storageRPM;
    // COOLING constraints
    public int thermalPerformance;
    // PSU constraints
    public int psuPower;
    // MOTHERBOARD constraints
    public int numOfRamSlotsMb;
    public int CapacityMb;
}
