package inzenjering_znanja.api.Models;

public class CPU {
    // FIELDS
    private String name;
    private int clockSpeed;
    private int numOfCores;
    private int numOfThreads;

    // CONSTRUCTORS
    public CPU() {
        this.name = "";
        this.clockSpeed = 0;
        this.numOfCores = 0;
        this.numOfThreads = 0;
    }

    public CPU(String name, int clockSpeed, int numOfCores, int numOfThreads) {
        this.name = name;
        this.clockSpeed = clockSpeed;
        this.numOfCores = numOfCores;
        this.numOfThreads = numOfThreads;
    }

    // GET METHODS
    public String getName() {
        return this.name;
    }

    public int getClockSpeed() {
        return this.clockSpeed;
    }

    public int getNumOfCores() {
        return this.numOfCores;
    }

    public int getNumOfThreads() {
        return this.numOfThreads;
    }

    // SET METHODS
    public void setName(String name) {
        this.name = name;
    }

    public void setClockSpeed(int clockSpeed) {
        this.clockSpeed = clockSpeed;
    }

    public void setNumOfCores(int numOfCores) {
        this.numOfCores = numOfCores;
    }

    public void setNumOfThreads(int numOfThreads) {
        this.numOfThreads = numOfThreads;
    }
}
