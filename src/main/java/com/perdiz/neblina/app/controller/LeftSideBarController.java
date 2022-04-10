/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.perdiz.neblina.app.controller;

import com.jfoenix.controls.JFXButton;

import com.perdiz.neblina.app.resource.Icon;
import javafx.event.EventHandler;
import javafx.scene.control.Tooltip;
import javafx.scene.image.ImageView;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.VBox;

/**
 * @author alexander
 */
public class LeftSideBarController extends VBox {


    protected JFXButton cloudServerBtn = new JFXButton("", new ImageView(Icon.CS_LIGHT.src));
    protected JFXButton fogServerBtn = new JFXButton("", new ImageView(Icon.FS_LIGHT.src));
    protected JFXButton proxyServerBtn = new JFXButton("", new ImageView(Icon.FS_LIGHT.src));
    protected JFXButton actuatorBtn = new JFXButton("", new ImageView(Icon.AC_LIGHT.src));
    protected JFXButton sensorBtn = new JFXButton("", new ImageView(Icon.SN_LIGHT.src));
    protected JFXButton trafficBtn = new JFXButton("", new ImageView(Icon.TF_LIGHT.src));
    protected JFXButton trafficRanBtn = new JFXButton("", new ImageView(Icon.TF_SHUFFLE_LIGHT.src));
    protected JFXButton chartBarBtn = new JFXButton("", new ImageView(Icon.CHART_BAR_LIGHT.src));
    protected JFXButton chartLineVmBtn = new JFXButton("", new ImageView(Icon.CHART_LINE_LIGHT.src));
    protected JFXButton chartLineTrBtn = new JFXButton("", new ImageView(Icon.CHART_MULTILINE_LIGHT.src));

    public LeftSideBarController() {
        this.cloudServerBtn.setTooltip(new Tooltip("Cloud Server"));
        this.fogServerBtn.setTooltip(new Tooltip("Fog Server"));
        this.proxyServerBtn.setTooltip(new Tooltip("Proxy Server"));
        this.sensorBtn.setTooltip(new Tooltip("Sensor"));
        this.actuatorBtn.setTooltip(new Tooltip("Actuator"));
        this.trafficBtn.setTooltip(new Tooltip("Traffic"));
        this.trafficRanBtn.setTooltip(new Tooltip("Traffic Random"));
        this.chartBarBtn.setTooltip(new Tooltip("Energy Consumption Server"));
        this.chartLineVmBtn.setTooltip(new Tooltip("VM - ON"));
        this.chartLineTrBtn.setTooltip(new Tooltip("Arrival Per Slot"));

    }

    public void setCloudServerActionEvent(EventHandler<MouseEvent> cloudServerActionEvent) {
        this.cloudServerBtn.setOnMouseClicked(cloudServerActionEvent);
    }

    public void setFogServerActionEvent(EventHandler<MouseEvent> fogServerActionEvent) {
        this.fogServerBtn.setOnMouseClicked(fogServerActionEvent);
    }

    public void setProxyServerActionEvent(EventHandler<MouseEvent> proxyServerActionEvent) {
        this.proxyServerBtn.setOnMouseClicked(proxyServerActionEvent);
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

    public void setTrafficRanActionEvent(EventHandler<MouseEvent> sensorActionEvent) {
        this.trafficRanBtn.setOnMouseClicked(sensorActionEvent);
    }

    public void setChartBarBtnEvent(EventHandler<MouseEvent> sensorActionEvent) {
        this.chartBarBtn.setOnMouseClicked(sensorActionEvent);
    }

    public void setChartLineVmBtnEvent(EventHandler<MouseEvent> chartActionEvent) {
        this.chartLineVmBtn.setOnMouseClicked(chartActionEvent);
    }

    public void setChartLineTrBtnEvent(EventHandler<MouseEvent> chartActionEvent) {
        this.chartLineTrBtn.setOnMouseClicked(chartActionEvent);
    }
}
