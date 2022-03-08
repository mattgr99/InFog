/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.perdiz.neblina.app.component.device;

import com.perdiz.neblina.app.controller.device.SensorDeviceController;
import com.perdiz.neblina.model.SensorModel;
import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.image.ImageView;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.HBox;
import javafx.scene.text.Text;

/**
 * @author alexander
 */
public class SensorDevice extends SensorDeviceController {


    public SensorDevice(SensorModel device) {
        super(device);
        init();
    }

    public SensorDevice(SensorModel device, ImageView icon) {
        super(device, icon);
        init();
    }

    private void init() {

        GridPane gridPane = new GridPane();
        gridPane.setPadding(new Insets(10));
        gridPane.setVgap(10);
        gridPane.setHgap(5);
        gridPane.setAlignment(Pos.CENTER);

        //Arranging all the nodes in the grid
        gridPane.add(new Text("Name"), 0, 0);
        gridPane.add(nameField, 1, 0);
        gridPane.add(new Text("Type"), 0, 1);
        gridPane.add(typeField, 1, 1);
        gridPane.add(new Text("Distribution\nType"), 0, 2);
        gridPane.add(distTypeSpinner, 1, 2);
        gridPane.add(new Text("Mean"), 0, 3);
        gridPane.add(meanField, 1, 3);
        gridPane.add(new Text("stdDev"), 0, 4);
        gridPane.add(stdDevField, 1, 4);
        gridPane.add(new Text("Min"), 0, 5);
        gridPane.add(minField, 1, 5);
        gridPane.add(new Text("Max"), 0, 6);
        gridPane.add(maxField, 1, 6);
        gridPane.add(new Text("Value"), 0, 7);
        gridPane.add(valueField, 1, 7);
        Button okBtn = new Button("ok");
        okBtn.getStyleClass().add("okbtn");
        okBtn.setOnMouseClicked(this.onOkBtnClicked());

        Button cancelBtn = new Button("Cancel");
        cancelBtn.getStyleClass().add("cancelbtn");
        cancelBtn.setOnMouseClicked(this.onCancelBtnClicked());

        HBox btnContainer = new HBox(cancelBtn, okBtn);
        btnContainer.setSpacing(10);
        btnContainer.setAlignment(Pos.CENTER_RIGHT);
        gridPane.add(btnContainer, 1, 8);


        distTypeSpinner.getEditor().textProperty().addListener(distTypeOnChange());


        this.formScene = new Scene(gridPane);
    }
}
