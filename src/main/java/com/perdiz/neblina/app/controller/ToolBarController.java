/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.perdiz.neblina.app.controller;

import javafx.event.EventHandler;
import javafx.scene.control.Button;
import javafx.scene.control.ToolBar;
import javafx.scene.input.MouseEvent;
import org.kordamp.ikonli.fontawesome.FontAwesome;
import org.kordamp.ikonli.javafx.FontIcon;
import org.kordamp.ikonli.themify.Themify;

/**
 *
 * @author alexander
 */
public class ToolBarController extends ToolBar {

    protected Button showRightPanelBtn;
    protected Button showLeftPanelBtn;

    protected Button newBtn;
    protected Button saveBtn;
    protected Button openBtn;

    protected Button playBtn;

    public ToolBarController() {
        showRightPanelBtn = new Button("", new FontIcon(Themify.LAYOUT_SIDEBAR_RIGHT));
        showLeftPanelBtn = new Button("", new FontIcon(Themify.LAYOUT_SIDEBAR_LEFT));
        newBtn = new Button("", new FontIcon(Themify.PENCIL_ALT));
        saveBtn = new Button("", new FontIcon(FontAwesome.SAVE));
        openBtn = new Button("", new FontIcon(FontAwesome.FOLDER_OPEN_O));
        playBtn = new Button("", new FontIcon(FontAwesome.PLAY));

    }

    public void setOnshowRightBarActionEvent(EventHandler<MouseEvent> showRightPanelAction) {
        this.showRightPanelBtn.setOnMouseClicked(showRightPanelAction);

    }

    public void setOnShowLeftBarActionEvent(EventHandler<MouseEvent> showLeftPanelAction) {
        this.showLeftPanelBtn.setOnMouseClicked(showLeftPanelAction);
    }

    public void setOnNewActionEvent(EventHandler<MouseEvent> newActionEvent) {
        this.newBtn.setOnMouseClicked(newActionEvent);
    }

    public void setOnOpenActionEvent(EventHandler<MouseEvent> openActionEvent) {
        this.openBtn.setOnMouseClicked(openActionEvent);
    }

    public void setOnSaveActionEvent(EventHandler<MouseEvent> saveAction) {
        this.saveBtn.setOnMouseClicked(saveAction);
    }

    public void setOnPlayActionEvent(EventHandler<MouseEvent> playAction) {
        this.playBtn.setOnMouseClicked(playAction);

    }

}
