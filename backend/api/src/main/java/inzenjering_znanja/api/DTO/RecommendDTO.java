package inzenjering_znanja.api.DTO;

public class RecommendDTO {
    // Motherboard Constraints
    public int ramSlots;
    public String pciE;

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
    // RAM constraints
    public int ramClockMin;
    public int ramClockMax;
    public int ramSizeMin;
    public int ramSizeMax;
    // STORAGE constraints
    public int storageWriteSpeedMin;
    public int storageWriteSpeedMax;
    public int storageCapacityMin;
    public int storageCapacityMax;
    public int storageRPM;
    // TODO: Case and PSU constraints
}
