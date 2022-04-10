/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.perdiz.neblina.app.component;

import com.perdiz.neblina.app.controller.FooterController;
import com.perdiz.neblina.app.controller.RightSideBarController;
import com.perdiz.neblina.util.Pkg;
import javafx.scene.control.Label;
import javafx.scene.control.ScrollPane;
import javafx.scene.layout.HBox;
import javafx.scene.layout.Priority;
import javafx.scene.layout.VBox;
import javafx.scene.paint.Color;

/**
 * @author alexander
 */
public class Footer extends FooterController {


    public Footer() {

        this.init();
    }

    private void init() {
        HBox actions = new HBox();


        logViewer.getStyleClass().add("footer-content");
        logViewer.setPrefHeight(HEIGHT - 5);
        scrollPane.setMinHeight(HEIGHT);
        scrollPane.setFitToWidth(true);


        outputBtn.getStyleClass().add("footer-btn");
        actions.getChildren().add(outputBtn);


        getStyleClass().add("footer-container");
        getChildren().addAll(actions);
    }


}
