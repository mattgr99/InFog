/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.perdiz.neblina.app.iu;

import java.util.regex.Pattern;

import javafx.scene.control.Spinner;

/**
 * @author alexander
 */
public class NumberField extends Spinner<Double> {


    public NumberField() {
        super(0, Integer.MAX_VALUE, 0, 0.01);
        this.init();
    }

    public NumberField(double value) {
        super(0, Integer.MAX_VALUE, 0, 0.01);
        this.init();
        this.setValue(value);
    }

    private void init() {
        this.setEditable(true);

        this.getEditor().textProperty().addListener((obs, oldValue, newValue) -> {
            String decimalPattern = "([0-9]*)\\.([0-9]*)";
            this.setStyle(!Pattern.matches(decimalPattern, newValue)
                    ? "-fx-background-color:red"
                    : "-fx-background-color:'#2196F3'"
            );
        });
    }

    public void setValue(double value) {
        this.getValueFactory().setValue(value);
    }

    //public Double getValue() {
    //    return this.getValueFactory().getValue();
    //}

    public void clear() {
        this.getValueFactory().setValue(0.0);
    }

}
