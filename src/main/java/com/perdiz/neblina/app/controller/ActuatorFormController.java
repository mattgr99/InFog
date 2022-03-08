/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.perdiz.neblina.app.controller;

import javafx.scene.control.TextField;
import javafx.scene.layout.VBox;

/**
 *
 * @author alexander
 */
public class ActuatorFormController extends VBox {

    protected TextField nameField;
    protected TextField typeField;

    public ActuatorFormController() {
        this.nameField = new TextField();
        this.typeField = new TextField();
    }

}
