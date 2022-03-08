/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.perdiz.neblina.app.component;

import com.perdiz.neblina.app.controller.RightSideBarController;
import javafx.scene.control.Label;
import javafx.scene.layout.HBox;

/**
 *
 * @author alexander
 */
public class RightSideBar extends RightSideBarController {

    private boolean isActive = false;

    public RightSideBar() {
    }

    private void init() {
        HBox toolbar = new HBox(new Label("Sidebar"));
        toolbar.setStyle("-fx-background-color: '#0097fc'");
        toolbar.setMinWidth(300);
        this.getChildren().addAll(toolbar);
    }

    public void show() {
        if (this.isActive) {
            this.getChildren().clear();
            this.isActive = !this.isActive;
        } else {
            this.init();
            this.isActive = !this.isActive;
        }
    }

}
