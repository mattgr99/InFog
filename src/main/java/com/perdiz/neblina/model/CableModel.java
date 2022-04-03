/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.perdiz.neblina.model;

import com.perdiz.neblina.app.component.device.CableDevice;
import com.perdiz.neblina.app.iu.Device;
import javafx.scene.shape.Line;

/**
 *
 * @author alexander
 */
public class CableModel extends DeviceModel{

    private Device device1;
    private Device device2;
    private Line line;
    private String dev1Name;
    private String dev2Name;
    private String latency;
    /*protected double sourceX;
    protected double sourceY;
    protected double destinyX;
    protected double destinyY;*/

    public CableModel(String id, String name, Device sourceId, Device destinyId, String name1, String name2,String latency ) {

        super(id, name);

//        String dev = device1.toString();//getClass().getName();
        this.device1 = sourceId;
        this.device2 = destinyId;
        this.latency = latency;
        this.dev1Name = name1;
        this.dev2Name = name2;

    }
    public CableModel() {

    }

    public CableModel(Device sourceId, Device destinyId, String latency, String name1, String name2 /*double sourceX, double sourceY, double destinyX, double destinyY*/) {

        this.device1 = sourceId;
        this.device2 = destinyId;
        this.latency = latency;
        this.dev1Name = name1;
        this.dev2Name = name2;
        /*this.sourceX = sourceX;
        this.sourceY = sourceY;
        this.destinyX = destinyX;
        this.destinyY = destinyY;*/
        //CableModel cm = new CableModel("d", "");
    }



    public String getLatency() {
        return latency;
    }

    public void setLatency(String latency) {
        this.latency = latency;
    }

    public Device getDevice1() {
        return device1;
    }

    public void setDevice1(Device device1) {
        this.device1 = device1;
    }

    public Device getDevice2() {
        return device2;
    }

    public void setDevice2(Device device2) {
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

    public Line getLine() {
        return line;
    }

    public void setLine(Line line) {
        this.line = line;
    }

    /*public double getSourceX() {
        return sourceX;
    }

    public double getSourceY() {
        return sourceY;
    }

    public double getDestinyX() {
        return destinyX;
    }

    public double getDestinyY() {
        return destinyY;
    }

    public void setSource(double sourceX, double sourceY) {
        this.sourceX = sourceX;
        this.sourceY = sourceY;
    }

    public void setDestiny(double destinyX, double destinyY) {
        this.destinyX = destinyX;
        this.destinyY = destinyY;

    }

    public void setPoints(double sourceX, double sourceY, double destinyX, double destinyY) {
        this.destinyX = destinyX;
        this.destinyY = destinyY;
        this.sourceX = sourceX;
        this.sourceY = sourceY;
    }*/

}
