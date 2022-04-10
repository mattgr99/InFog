/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.perdiz.neblina.app.controller.device;

import com.perdiz.neblina.app.iu.Device;
import com.perdiz.neblina.app.iu.NumberField;
import com.perdiz.neblina.app.resource.Icon;
import com.perdiz.neblina.model.SensorModel;
import javafx.beans.value.ChangeListener;
import javafx.beans.value.ObservableValue;
import javafx.collections.FXCollections;
import javafx.event.EventHandler;
import javafx.scene.control.Spinner;
import javafx.scene.control.TextField;
import javafx.scene.image.ImageView;
import javafx.scene.input.MouseEvent;
import javafx.stage.WindowEvent;

/**
 * @author alexander
 */
public class SensorDeviceController extends Device {

    protected SensorModel model;

    protected TextField nameField;
    protected TextField typeField;
    protected Spinner<String> distTypeSpinner; //normal, Unifirm, deterministic
    protected NumberField meanField;
    protected NumberField stdDevField;
    protected NumberField minField;
    protected NumberField maxField;
    protected NumberField valueField;

    public SensorDeviceController(SensorModel sensorModel) {
        super(sensorModel, new ImageView(Icon.SN_DARK.src));
        this.model = sensorModel;

        init();
    }

    public SensorDeviceController(SensorModel sensorModel, ImageView icon) {
        super(sensorModel, icon);
        this.model = sensorModel;

        init();
    }


    private void init() {
        this.nameField = new TextField(model.getName());
        this.typeField = new TextField(model.getType());
        this.distTypeSpinner = new Spinner<>(
                FXCollections.observableArrayList("Normal", "Uniform", "Deterministic"));
        this.meanField = new NumberField(model.getMean());
        this.stdDevField = new NumberField(model.getStdDev());
        this.minField = new NumberField(model.getMin());
        this.maxField = new NumberField(model.getMax());
        this.valueField = new NumberField(model.getValue());
    }



    protected ChangeListener<String> distTypeOnChange() {

        return (ChangeListener<String>) (ObservableValue<? extends String> obs, String oldValue, String newValue) -> {
            switch (newValue) {
                case "Normal":
                    this.meanField.setDisable(false);
                    this.stdDevField.setDisable(false);
                    this.minField.setDisable(true);
                    this.maxField.setDisable(true);
                    this.valueField.setDisable(true);
                    break;
                case "Uniform":

                    this.meanField.setDisable(true);
                    this.stdDevField.setDisable(true);
                    this.minField.setDisable(false);
                    this.maxField.setDisable(false);
                    this.valueField.setDisable(true);
                    break;
                case "Deterministic":
                    this.meanField.setDisable(true);
                    this.stdDevField.setDisable(true);
                    this.minField.setDisable(true);
                    this.maxField.setDisable(true);
                    this.valueField.setDisable(false);
                    break;

            }
        };

    }


    // Reset form values if closed
    @Override
    public EventHandler<WindowEvent> beforeCloseFormStage() {
        return event -> {
            this.nameField.setText(this.model.getName());
            this.typeField.setText(this.model.getType());
            //this.model.setDistributionType(this.distributi.getValue());
            this.meanField.setValue(this.model.getMean());
            this.stdDevField.setValue(this.model.getStdDev());
            this.minField.setValue(this.model.getMin());
            this.maxField.setValue(this.model.getMax());
            this.valueField.setValue(this.model.getValue());
        };
    }

    // Save new form values
    protected EventHandler onOkBtnClicked() {
        return event -> {

            this.model.setName(this.nameField.getText());
            this.model.setType(this.typeField.getText());
            //this.model.setDistributionType(this.distributi.getValue());
            this.model.setMean(this.meanField.getValue());
            this.model.setStdDev(this.stdDevField.getValue());
            this.model.setMin(this.minField.getValue());
            this.model.setMax(this.maxField.getValue());
            this.model.setValue(this.valueField.getValue());
            this.nameLbl.setText(this.model.getName());
            this.formStage.close();
        };
    }

    // Reset form values if canceled
    protected EventHandler<MouseEvent> onCancelBtnClicked() {
        return event -> {
            this.nameField.setText(this.model.getName());
            this.typeField.setText(this.model.getType());
            //this.model.setDistributionType(this.distributi.getValue());
            this.meanField.setValue(this.model.getMean());
            this.stdDevField.setValue(this.model.getStdDev());
            this.minField.setValue(this.model.getMin());
            this.maxField.setValue(this.model.getMax());
            this.valueField.setValue(this.model.getValue());

            this.formStage.close();
        };
    }

    public SensorModel getModel() {
        return this.model;
    }
}
