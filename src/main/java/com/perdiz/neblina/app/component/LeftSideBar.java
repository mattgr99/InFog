/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.perdiz.neblina.app.component;

import com.perdiz.neblina.app.controller.LeftSideBarController;
import javafx.scene.layout.VBox;

/**
 *
 * @author alexander
 */
public class LeftSideBar extends LeftSideBarController {

    private boolean isActive = false;

    public LeftSideBar() {
        getStyleClass().add("LeftSideBar");
        init();
    }

    private void init() {
        this.getChildren().add(new VBox(
                cloudServerBtn,
                proxyServerBtn,
                fogServerBtn,
                actuatorBtn,
                sensorBtn,
                trafficBtn,
                trafficRanBtn,
                chartBarBtn,
                chartLineVmBtn,
                chartLineTrBtn
        ));

    }

    public void show() {
        if (!this.isActive) {
            this.getChildren().clear();
            this.isActive = !this.isActive;
        } else {
            this.init();
            this.isActive = !this.isActive;
        }
    }
}
