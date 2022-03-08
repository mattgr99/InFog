/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.perdiz.neblina.app.controller.device;

import com.perdiz.neblina.app.iu.Device;
import com.perdiz.neblina.app.resource.ImageResource;
import com.perdiz.neblina.app.resource.Resource;
import com.perdiz.neblina.model.ActuatorModel;
import com.perdiz.neblina.model.ServerModel;
import javafx.event.EventHandler;
import javafx.scene.control.TextField;
import javafx.scene.input.MouseEvent;
import javafx.stage.WindowEvent;

/**
 * @author alexander
 */
public class ActuatorDeviceController extends Device {

    protected ActuatorModel model;

    protected TextField nameField;
    protected TextField typeField;


    public ActuatorDeviceController(ActuatorModel actuatorModel) {
        super(actuatorModel, new ImageResource(Resource.ACTUATOR));
        this.model = actuatorModel;
        this.init();
    }

    private void init() {
        this.nameField = new TextField(this.model.getName());
        this.typeField = new TextField(this.model.getType());
    }

    // Reset form values if closed
    @Override
    public EventHandler<WindowEvent> beforeCloseFormStage() {
        return event -> {
            this.nameField.setText(this.model.getName());
            this.typeField.setText(this.model.getType());
        };
    }

    // Save new form values
    protected EventHandler onOkBtnClicked() {
        return event -> {
            this.model.setName(this.nameField.getText());
            this.model.setType(this.typeField.getText());
            this.nameLbl.setText(this.model.getName());
            this.formStage.close();
        };
    }

    // Reset form values if canceled
    protected EventHandler<MouseEvent> onCancelBtnClicked() {
        return event -> {
            this.nameField.setText(this.model.getName());
            this.typeField.setText(this.model.getType());
            this.formStage.close();
        };
    }

    public ActuatorModel getModel() {
        return this.model;
    }
}
