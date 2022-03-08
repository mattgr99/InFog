/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.perdiz.neblina.app.controller.device;


import com.perdiz.neblina.app.iu.Device;
import com.perdiz.neblina.model.CableModel;
import javafx.event.EventHandler;
import javafx.scene.control.TextField;
import javafx.scene.input.MouseEvent;
import javafx.scene.shape.Line;
import javafx.stage.WindowEvent;

import java.util.ArrayList;
import java.util.List;

/**
 *
 * @author alexander
 */
public class CableDeviceController extends Device {
    protected TextField nameField;
    protected TextField latencyField;
    protected CableModel cable;


    public CableDeviceController(CableModel cableModel) {
        super(cableModel);
        this.cable = cableModel;
    }

    @Override
    public EventHandler<WindowEvent> beforeCloseFormStage() {
        return event -> {
            this.nameField.setText(this.cable.getName());
            this.latencyField.setText(this.cable.getLatency());
        };
    }


    // Save new form values
    protected EventHandler onOkBtnClicked() {
        return event -> {
            this.cable.setName(this.nameField.getText());
            this.cable.setName(this.latencyField.getText());
          //  this.nameLbl.setText(this.cable.getName());
            this.formStage.close();
        };
    }

    // Reset form values if canceled
    protected EventHandler<MouseEvent> onCancelBtnClicked() {
        return event -> {
            this.nameField.setText(this.cable.getName());

            this.formStage.close();
        };
    }
}
