package inzenjering_znanja.api.DTO;

public class RecommendDTO {
    // CPU constraints
    public int cpuClockMin;
    public int cpuClockMax;
    public int cpuCoresMin;
    public int cpuCoresMax;
    public int cpuThreadsMin;
    public int cpuThreadsMax;
    // GPU constraints
    public int gpuClockMin;
    public int gpuClockMax;
    public int gpuVRAMMin;
    public int gpuVRAMMax;
    public String gpuPciE;
    // RAM constraints
    public int ramClockMin;
    public int ramClockMax;
    public int ramSizeMin;
    public int ramSizeMax;
    public String ramSpeedType;
    // STORAGE constraints
    public int storageWriteSpeedMin;
    public int storageWriteSpeedMax;
    public int storageCapacityMin;
    public int storageCapacityMax;
    public String storageType;
    public int storageRPM;
    // COOLING constraints
    public int minimalThermalPerformance;
    // PSU constraints
    public int minPSUPower;
    public int maxPSUPower;
    // MOTHERBOARD constraints
    public int minNumOfRamSlotsMb;
    public int maxNumOfRamSlotsMb;
    public int minRamCapacityMb;
    public int maxRamCapacityMb;
    public String[] pciEMb;
}
