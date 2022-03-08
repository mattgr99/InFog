/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.perdiz.neblina.app.component;

import com.perdiz.neblina.app.controller.ActuatorFormController;
import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.control.TextField;
import javafx.scene.layout.GridPane;
import javafx.scene.text.Text;

/**
 *
 * @author alexander
 */
public class ActuatorForm extends ActuatorFormController {

    public ActuatorForm() {
        this.init();
    }

    private void init() {

        GridPane gridPane = new GridPane();
        gridPane.setPadding(new Insets(5));
        gridPane.setVgap(5);
        gridPane.setHgap(5);
        gridPane.setAlignment(Pos.CENTER);

        //Arranging all the nodes in the grid 
        gridPane.add(new Text("Name        "), 0, 0);
        gridPane.add(nameField, 1, 0);
        gridPane.add(new Text("Type"), 0, 1);
        gridPane.add(typeField, 1, 1);

        this.getChildren().addAll(gridPane);
    }

    public void clear() {
        this.nameField.setText("");
        this.typeField.setText("");
    }

}
