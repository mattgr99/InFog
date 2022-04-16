package com.perdiz.neblina.heuristics;

public class DeviceTraffic {
    private String valTraffic;
    private String nameSensor;


    public DeviceTraffic(String traffic, String name){
        this.valTraffic = traffic;
        this.nameSensor = name;

    }

    public String getValTraffic() {
        return valTraffic;
    }

    public void setValTraffic(String valTraffic) {
        this.valTraffic = valTraffic;
    }

    public String getNameSensor() { return nameSensor; }
}
