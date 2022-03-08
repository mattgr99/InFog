/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.perdiz.neblina.app.component.device;

import com.perdiz.neblina.app.controller.AppController;
import com.perdiz.neblina.app.controller.device.CableDeviceController;
import com.perdiz.neblina.app.iu.Device;
import com.perdiz.neblina.model.CableModel;
import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.HBox;
import javafx.scene.paint.Color;
import javafx.scene.shape.Line;
import javafx.scene.text.Text;
import javafx.stage.Modality;
import javafx.stage.Stage;

/**
 *
 * @author alexander
 */
public class CableDevice  /*extends CableDeviceController*/ {


    protected CableModel device;
    private Device device1;
    private Device device2;
    private Line line;
    private String dev1Name;
    private String dev2Name;
    private String latency;



    public CableDevice(Device device, Device deviceEnd, Line connection, String dev1, String dev2, String latency) {

        this.device1=device;
        this.device2=deviceEnd;
        this.line=connection;
        this.dev1Name = dev1;
        this.dev2Name = dev2;
        this.latency = latency;
        init();

    }

    public CableDevice() {

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

    public Line getLine() {
        return line;
    }

    public void setLine(Line line) {
        this.line = line;
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

    private void init() {

        /*setStartX(cable.getSourceX());
        setStartY(cable.getSourceY());
        setEndX(cable.getDestinyX());
        setEndY(cable.getDestinyY());
        setStroke(Color.RED);
        setStrokeWidth(3);*/

    }

}
