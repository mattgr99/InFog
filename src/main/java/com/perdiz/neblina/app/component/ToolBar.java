/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.perdiz.neblina.app.component;

import com.perdiz.neblina.app.controller.ToolBarController;
import javafx.geometry.Orientation;
import javafx.scene.control.Separator;
import javafx.scene.layout.HBox;
import javafx.scene.layout.Priority;
import org.kordamp.ikonli.javafx.FontIcon;
import org.kordamp.ikonli.themify.Themify;

/**
 *
 * @author alexander
 */
public class ToolBar extends ToolBarController {

    public ToolBar() {

        HBox container = new HBox();
        HBox.setHgrow(container, Priority.ALWAYS);

        container.getChildren().addAll(leftComponent(), centerComponent(), rightComponent());
        this.getItems().add(container);

    }

    private HBox leftComponent() {
        HBox component = new HBox(
                showLeftPanelBtn,
                new Separator(Orientation.VERTICAL),
                newBtn,
                openBtn,
                saveBtn,
                new Separator(Orientation.VERTICAL),
                playBtn,
                playRandomBtn
        );
        FontIcon icon = new FontIcon(Themify.LAYOUT_SIDEBAR_LEFT);
        icon.setIconSize(20);
        return component;
    }

    private HBox rightComponent() {
        HBox component = new HBox(showRightPanelBtn);
        return component;
    }

    private HBox centerComponent() {
        HBox component = new HBox();
        HBox.setHgrow(component, Priority.ALWAYS);
        return component;
    }
}
