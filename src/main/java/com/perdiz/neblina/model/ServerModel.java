/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.perdiz.neblina.model;

import java.util.ArrayList;

/**
 *
 * @author alexander
 */
public class ServerModel extends DeviceModel {

    private byte level;
    private double uplink;
    private double downlink;
    private double vmachines;
    private double ram;
    private double rate;
    private ArrayList<Integer> ramVM = new ArrayList<Integer>();;

    public ServerModel() {
    }

    public ServerModel(String id, String name, byte level) {
        super(id, name);
        this.level = level;
        //this.ramVM;
    }

    public ServerModel(String id, String name, byte level, double uplink, double downlink, double mips, double ram, double rate) {
        super(id, name);
        this.level = level;
        this.uplink = uplink;
        this.downlink = downlink;
        this.vmachines = mips;
        this.ram = ram;
        this.rate = rate;

    }

    public void addRamVM(int ramVM1){
        this.ramVM.add(ramVM1);
    }

    public ArrayList<Integer> getRamVM() {
        return ramVM;
    }

    public void setRamVM(ArrayList<Integer> ramVM) {
        this.ramVM = ramVM;
    }

    public double getRate() {
        return rate;
    }

    public void setRate(double rate) {
        this.rate = rate;
    }

    public byte getLevel() {
        return level;
    }

    public void setLevel(byte level) {
        this.level = level;
    }

    public double getUplink() {
        return uplink;
    }

    public void setUplink(double uplink) {
        this.uplink = uplink;
    }

    public double getDownlink() {
        return downlink;
    }

    public void setDownlink(double downlink) {
        this.downlink = downlink;
    }

    public double getVmachines() {
        return vmachines;
    }

    public void setVmachines(double mips) {
        this.vmachines = mips;
    }

    public double getRam() {
        return ram;
    }

    public void setRam(double ram) {
        this.ram = ram;
    }

}
