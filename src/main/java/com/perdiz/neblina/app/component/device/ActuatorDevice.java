/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.perdiz.neblina.app.component.device;

import com.perdiz.neblina.app.controller.device.ActuatorDeviceController;
import com.perdiz.neblina.model.ActuatorModel;
import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.HBox;
import javafx.scene.text.Text;

/**
 * @author alexander
 */
public class ActuatorDevice extends ActuatorDeviceController {

    public ActuatorDevice(ActuatorModel device) {
        super(device);
        this.init();
    }

    private void init() {

        GridPane gridPane = new GridPane();
        gridPane.setPadding(new Insets(10));
        gridPane.setVgap(10);
        gridPane.setHgap(5);
        gridPane.setAlignment(Pos.CENTER);

        //Arranging all the nodes in the grid
        gridPane.add(new Text("Name        "), 0, 0);
        gridPane.add(nameField, 1, 0);
        gridPane.add(new Text("Type"), 0, 1);
        gridPane.add(typeField, 1, 1);

        Button okBtn = new Button("ok");
        okBtn.getStyleClass().add("okbtn");
        okBtn.setOnMouseClicked(this.onOkBtnClicked());

        Button cancelBtn = new Button("Cancel");
        cancelBtn.getStyleClass().add("cancelbtn");
        cancelBtn.setOnMouseClicked(this.onCancelBtnClicked());

        HBox btnContainer = new HBox(cancelBtn, okBtn);
        btnContainer.setSpacing(10);
        btnContainer.setAlignment(Pos.CENTER_RIGHT);
        gridPane.add(btnContainer, 1, 3);
        this.formScene = new Scene(gridPane);
    }
}
