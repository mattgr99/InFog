package com.perdiz.neblina.model;


public class CableSave {
    private String device1;
    private String device2;
    //private Line line;
    private String dev1Name;
    private String dev2Name;
    private String latency;

    public CableSave(String dev1, String dev2, String name1, String name2, String Lat){
        this.device1 = dev1;
        this.device2 = dev2;
        this.latency = Lat;
        this.dev1Name = name1;
        this.dev2Name = name2;
    }

    public String getDevice1() {
        return device1;
    }

    public void setDevice1(String device1) {
        this.device1 = device1;
    }

    public String getDevice2() {
        return device2;
    }

    public void setDevice2(String device2) {
        this.device2 = device2;
    }

    public String getDev1Name() {
        return dev1Name;
    }

    public void setDev1Name(String dev1Name) {
        this.dev1Name = dev1Name;
    }

    public String getDev2Name() {
        return dev2Name;
    }

    public void setDev2Name(String dev2Name) {
        this.dev2Name = dev2Name;
    }

    public String getLatency() {
        return latency;
    }

    public void setLatency(String latency) {
        this.latency = latency;
    }
}
