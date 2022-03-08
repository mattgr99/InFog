/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.perdiz.neblina.model;

import javafx.scene.image.ImageView;

/**
 *
 * @author alexander
 */
public class SensorModel extends DeviceModel {

    private String type;
    private String distributionType;
    private double mean;
    private double stdDev;
    private double min;
    private double max;
    private double value;
    public int nSensor;
   // public ImageView icon;

    public SensorModel() {
    }

    public SensorModel(String id, String name, int sen /*ImageView icon1*/) {
        super(id, name);
        //this.icon = icon1;
        this.nSensor=sen;
    }

    public SensorModel(String id, String type, String distributionType, double mean, double stdDev, double min, double max, double value, String name ) {
        super(id, name);
        this.type = type;
        this.distributionType = distributionType;
        this.mean = mean;
        this.stdDev = stdDev;
        this.min = min;
        this.max = max;
        this.value = value;
    }

    public String getType() {
        return type;
    }

    public void setType(String type) {
        this.type = type;
    }

    public String getDistributionType() {
        return distributionType;
    }

    public void setDistributionType(String distributionType) {
        this.distributionType = distributionType;
    }

    public double getMean() {
        return mean;
    }

    public void setMean(double mean) {
        this.mean = mean;
    }

    public double getStdDev() {
        return stdDev;
    }

    public void setStdDev(double stdDev) {
        this.stdDev = stdDev;
    }

    public double getMin() {
        return min;
    }

    public void setMin(double min) {
        this.min = min;
    }

    public double getMax() {
        return max;
    }

    public void setMax(double max) {
        this.max = max;
    }

    public double getValue() {
        return value;
    }

    public void setValue(double value) {
        this.value = value;
    }

}
