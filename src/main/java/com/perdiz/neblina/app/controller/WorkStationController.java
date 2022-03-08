/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.perdiz.neblina.app.controller;

import java.io.File;
import javafx.scene.layout.AnchorPane;

/**
 *
 * @author alexander
 */
public class WorkStationController extends AnchorPane {

    protected File selectedFile;
    protected byte numberOfCloudServers = 0;
    protected byte numberOfFogServers = 0;
    protected byte numberOfSensors = 0;
    protected byte numberOfActuators = 0;

    public WorkStationController() {
    }

    public byte getNumberOfCloudServers() {
        return ++numberOfCloudServers;
    }

    public byte getNumberOfFogServers() {
        return ++numberOfFogServers;
    }

    public byte getNumberOfSensors() {
        return ++numberOfSensors;
    }

    public byte getNumberOfActuators() {
        return ++numberOfActuators;
    }

    public void restartInitialsValues() {
        getChildren().clear();
        selectedFile = null;
        numberOfCloudServers = 0;
        numberOfFogServers = 0;
        numberOfSensors = 0;
        numberOfActuators = 0;
    }

}
