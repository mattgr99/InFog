/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.perdiz.neblina.model;

import javafx.scene.shape.Line;

/**
 *
 * @author alexander
 */
public class DeviceModel {

    protected String id;
    protected double x;
    protected double y;
    protected String name;

    public DeviceModel() {
    }

    public DeviceModel(String id, String name) {
        this.id = id;
        this.name = name;
    }

    public double getX() {
        return x;
    }

    public void setX(double x) {
        this.x = x;
    }

    public double getY() {
        return y;
    }

    public void setY(double y) {
        this.y = y;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

}
