/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.perdiz.neblina.app.controller;

import com.jfoenix.controls.JFXButton;
import com.perdiz.neblina.app.resource.ImageResource;
import com.perdiz.neblina.app.resource.Resource;
import javafx.event.EventHandler;
import javafx.scene.control.Tooltip;
import javafx.scene.image.ImageView;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.VBox;

/**
 *
 * @author alexander
 */
public class LeftSideBarController extends VBox {

    protected JFXButton cloudServerBtn = new JFXButton("", new ImageResource(Resource.CLOUDSERVERLIGHT));
    protected JFXButton fogServerBtn = new JFXButton("", new ImageResource(Resource.FOGSERVERLIGHT));
    protected JFXButton actuatorBtn = new JFXButton("", new ImageResource(Resource.ACTUATORLIGHT));
    protected JFXButton sensorBtn = new JFXButton("", new ImageResource(Resource.SENSORLIGHT));
    protected JFXButton trafficBtn = new JFXButton("", new ImageView("file:src/main/resources/image/email1.png"));

    public LeftSideBarController() {
        this.cloudServerBtn.setTooltip(new Tooltip("Cloud Server"));
        this.fogServerBtn.setTooltip(new Tooltip("Fog Server"));
        this.sensorBtn.setTooltip(new Tooltip("Sensor"));
        this.actuatorBtn.setTooltip(new Tooltip("Actuator"));
        this.trafficBtn.setTooltip(new Tooltip("Traffic"));

    }

    public void setCloudServerActionEvent(EventHandler<MouseEvent> cloudServerActionEvent) {
        this.cloudServerBtn.setOnMouseClicked(cloudServerActionEvent);
    }

    public void setFogServerActionEvent(EventHandler<MouseEvent> fogServerActionEvent) {
        this.fogServerBtn.setOnMouseClicked(fogServerActionEvent);
    }

    public void setActuatorActionEvent(EventHandler<MouseEvent> macbookActionEvent) {
        this.actuatorBtn.setOnMouseClicked(macbookActionEvent);
    }

    public void setSensorActionEvent(EventHandler<MouseEvent> sensorActionEvent) {
        this.sensorBtn.setOnMouseClicked(sensorActionEvent);
    }

    public void setTrafficActionEvent(EventHandler<MouseEvent> sensorActionEvent) {
        this.trafficBtn.setOnMouseClicked(sensorActionEvent);
    }
}
