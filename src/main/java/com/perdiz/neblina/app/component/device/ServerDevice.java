/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.perdiz.neblina.app.component.device;

import com.perdiz.neblina.app.controller.device.ServerDeviceController;
import com.perdiz.neblina.app.iu.NumberField;
import com.perdiz.neblina.model.ServerModel;
import javafx.beans.value.ChangeListener;
import javafx.beans.value.ObservableValue;
import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.Scene;
import javafx.scene.control.Alert;
import javafx.scene.control.Button;
import javafx.scene.control.TextField;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.HBox;
import javafx.scene.layout.VBox;
import javafx.scene.text.Text;

import javax.swing.*;

/**
 * @author alexander
 */
public class ServerDevice extends ServerDeviceController {

    public ServerDevice(ServerModel server) {
        super(server);
        ramVMText.setVisible(false);
        init();
    }

    private void init() {

        GridPane gridPane = new GridPane();
        gridPane.setPadding(new Insets(10));
        gridPane.setVgap(10);
        gridPane.setHgap(5);
        gridPane.setAlignment(Pos.CENTER);

        //Button vm
        Button vmBtn = new Button("Generate VM");
        vmBtn.getStyleClass().add("vmbtn");
        //vmBtn.setOnMouseClicked(this.onCancelBtnClicked());

        HBox btnVmContainer = new HBox(vmBtn);
        btnVmContainer.setSpacing(10);
        btnVmContainer.setAlignment(Pos.CENTER_RIGHT);

        //Arranging all the nodes in the grid
        gridPane.add(new Text("Name        "), 0, 0);
        gridPane.add(nameField, 1, 0);
        gridPane.add(new Text("Uplink Bw"), 0, 1);
        gridPane.add(uplinkField, 1, 1);
        gridPane.add(new Text("Downlink Bw"), 0, 2);
        gridPane.add(downlinkField, 1, 2);
        gridPane.add(new Text("RAM (Gb)"), 0, 3);

        HBox radioContainer1 = new HBox(rb1, rb2, rb3);
        HBox radioContainer2 = new HBox(rb4, rb5, rb6);
        HBox radioContainer3 = new HBox(rb7, rb8, rb9);
        HBox radioContainer4 = new HBox(rb10);

        radioContainer1.setSpacing(5);
        radioContainer1.setAlignment(Pos.CENTER_LEFT);
        radioContainer2.setSpacing(5);
        radioContainer2.setAlignment(Pos.CENTER_LEFT);
        radioContainer3.setSpacing(5);
        radioContainer3.setAlignment(Pos.CENTER_LEFT);
        radioContainer4.setSpacing(5);
        radioContainer4.setAlignment(Pos.CENTER_LEFT);
        GridPane gridPaneR = new GridPane();
        gridPaneR.setPadding(new Insets(10));
        gridPaneR.setVgap(10);
        gridPaneR.setHgap(5);
        gridPaneR.setAlignment(Pos.CENTER);
        gridPaneR.add(radioContainer1, 0, 0);
        gridPaneR.add(radioContainer2, 0, 1);
        gridPaneR.add(radioContainer3, 0, 2);
        gridPaneR.add(radioContainer4, 0, 3);

        gridPane.add(gridPaneR, 1, 3);
        gridPane.add(new Text("# of Core"), 0, 4);
        gridPane.add(vmField, 1, 4);
        gridPane.add(btnVmContainer, 1, 5);
        gridPane.add(new Text("CPU (MIPS)"), 0, 6);
        gridPane.add(mipsCbx, 1, 6);

        Button okBtn = new Button("ok");
        okBtn.getStyleClass().add("okbtn");
        okBtn.setOnMouseClicked(this.onOkBtnClicked());
        //vmField.setOnKeyPressed();

        Button cancelBtn = new Button("Cancel");
        cancelBtn.getStyleClass().add("cancelbtn");
        cancelBtn.setOnMouseClicked(this.onCancelBtnClicked());


        HBox btnContainer = new HBox(cancelBtn, okBtn);
        btnContainer.setSpacing(10);
        btnContainer.setAlignment(Pos.CENTER_RIGHT);
        gridPane.add(btnContainer, 1, 8);
        //mipsCbx.valueProperty().addListener(onChangeVmField());
        vmBtn.setOnMouseClicked(this.onVMBtnClicked());
        /**/
        this.formScene = new Scene(gridPane);
    }

}
