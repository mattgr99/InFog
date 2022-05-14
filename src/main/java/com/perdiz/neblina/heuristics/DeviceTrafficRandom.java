package com.perdiz.neblina.heuristics;

public class DeviceTrafficRandom {
    private int time;
    private int firstInterval;
    private int secondInterval;
    private String nameSensor;

    public DeviceTrafficRandom(int traffic1, int traffic2, int timeDev){
        this.time = timeDev;
        this.firstInterval = traffic1;
        this.secondInterval = traffic2;
    }

    public int getTime() {
        return time;
    }

    public void setTime(int time) {
        this.time = time;
    }

    public int getFirstInterval() {
        return firstInterval;
    }

    public void setFirstInterval(int firstInterval) {
        this.firstInterval = firstInterval;
    }

    public int getSecondInterval() {
        return secondInterval;
    }

    public void setSecondInterval(int secondInterval) {
        this.secondInterval = secondInterval;
    }

    public String getNameSensor() {
        return nameSensor;
    }

    public void setNameSensor(String nameSensor) {
        this.nameSensor = nameSensor;
    }
}
