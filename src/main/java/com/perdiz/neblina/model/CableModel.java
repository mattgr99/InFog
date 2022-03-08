/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.perdiz.neblina.model;

/**
 *
 * @author alexander
 */
public class CableModel extends DeviceModel{

    private String sourceId;
    private String destinyId;
    private String latency;
    /*protected double sourceX;
    protected double sourceY;
    protected double destinyX;
    protected double destinyY;*/

    public CableModel(String id, String name) {
        super(id, name);
    }
    public CableModel() {

    }

    public CableModel(String sourceId, String destinyId, String latency /*double sourceX, double sourceY, double destinyX, double destinyY*/) {

        this.sourceId = sourceId;
        this.destinyId = destinyId;
        this.latency = latency;
        /*this.sourceX = sourceX;
        this.sourceY = sourceY;
        this.destinyX = destinyX;
        this.destinyY = destinyY;*/
    }



    public String getLatency() {
        return latency;
    }

    public void setLatency(String latency) {
        this.latency = latency;
    }

    public String getSourceId() {
        return sourceId;
    }

    public void setSourceId(String sourceId) {
        this.sourceId = sourceId;
    }

    public String getDestinyId() {
        return destinyId;
    }

    public void setDestinyId(String destinyId) {
        this.destinyId = destinyId;
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
