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
import com.perdiz.neblina.model.DeviceModel;
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
public class CableDevice  extends Line/*extends CableDeviceController*/ {


    //protected CableModel device;
    /*private Device device1;
    private Device device2;
    private Line line;
    private String dev1Name;
    private String dev2Name;
    private String latency;*/
    private CableModel model;
    private  Color colorConnection;



    /*public CableDevice(Device device, Device deviceEnd, String dev1, String dev2, String latency) {

        this.device1=device;
        this.device2=deviceEnd;
        this.dev1Name = dev1;
        this.dev2Name = dev2;
        this.latency = latency;
        init();

    }*/

    public CableDevice() {

    }

    public CableDevice(CableModel cableModel) {
        this.model = cableModel;
        init();

    }

    public CableDevice(CableModel cableModel, Color color) {
        this.model = cableModel;
        this.colorConnection = color;
        init();

    }



    private void init() {


        setStroke(this.colorConnection);
        setStrokeWidth(3);
        setSmooth(false);
        //line.getStrokeDashArray().addAll(25.0,10.0);

// SVG dentro de javafx
        //Bind the starting point coordinate of the line with the center coordinate of node device
        startXProperty().bind(this.model.getDevice1().layoutXProperty().add(this.model.getDevice1().widthProperty().divide(2)));
        startYProperty().bind(this.model.getDevice1().layoutYProperty().add(this.model.getDevice1().heightProperty().divide(2)));

        //Bind the end coordinates of the line with the center coordinates of node deviceEnd
        endXProperty().bind(this.model.getDevice2().layoutXProperty().add(this.model.getDevice2().widthProperty().divide(2)));
        endYProperty().bind(this.model.getDevice2().layoutYProperty().add(this.model.getDevice2().heightProperty().divide(2)));
        this.model.setLine(this);

        /*setStartX(cable.getSourceX());
        setStartY(cable.getSourceY());
        setEndX(cable.getDestinyX());
        setEndY(cable.getDestinyY());
        setStroke(Color.RED);
        setStrokeWidth(3);*/

    }

    public CableModel getModel() {
        return model;
    }

    public void setModel(CableModel model) {
        this.model = model;
    }
}
