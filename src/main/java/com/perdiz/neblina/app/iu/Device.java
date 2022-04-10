/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.perdiz.neblina.app.iu;

import com.jfoenix.controls.JFXButton;
import com.perdiz.neblina.app.controller.device.CableDeviceController;
import com.perdiz.neblina.app.resource.Icon;
import com.perdiz.neblina.model.DeviceModel;

import java.awt.MouseInfo;
import java.awt.Point;
import java.util.UUID;

import com.perdiz.neblina.model.ServerModel;
import com.perdiz.neblina.util.Console;
import javafx.event.EventHandler;
import javafx.geometry.Pos;
import javafx.scene.Cursor;
import javafx.scene.Scene;
import javafx.scene.control.Label;
import javafx.scene.image.ImageView;
import javafx.scene.input.MouseButton;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.AnchorPane;
import javafx.scene.layout.Pane;
import javafx.scene.layout.VBox;
import javafx.stage.*;
import org.kordamp.ikonli.javafx.FontIcon;
import org.kordamp.ikonli.materialdesign.MaterialDesign;

/**
 * @author alexander
 */
public abstract class Device extends VBox {

    protected Stage formStage;
    protected Scene formScene;
    protected Scene formSceneCable;
    protected Label nameLbl;

    private double positionX;
    private double positionY;

    protected DeviceModel device;
    private /*final*/ ImageView icon;

    private JFXButton connectMenuItem;
    private JFXButton deleteMenuItem;
    private JFXButton editMenuItem;
    private Popup popup;




    public Device(DeviceModel device, ImageView icon) {

        this.device = device;
        this.icon = icon;
        setLayoutX(device.getX());
        setLayoutY(device.getY());
        setAlignment(Pos.CENTER);
        //provide a universally unique identifier for this object
        //setId(UUID.randomUUID().toString());
        init();
    }

    public Device(DeviceModel device, Byte level) {

        this.device = device;
        if (level == 0) {
            this.icon = new ImageView("file:src/main/resources/image/cloud1Server.png");
        }else if (level == 1){
            this.icon = new ImageView(Icon.CS_DARK.src);
        } else if (level== 2){
            this.icon = new ImageView("file:src/main/resources/image/proxyserver.png");
        }

        setLayoutX(device.getX());
        setLayoutY(device.getY());
        setAlignment(Pos.CENTER);
        //provide a universally unique identifier for this object
        //setId(UUID.randomUUID().toString());
        init();
    }

    public Device(DeviceModel device){
        this.device = device;

    }

    public Device(){


    }



    private void init() {
        nameLbl = new Label(device.getName());
        popup = new Popup();

        popup.getContent().addAll(popupContent());



        this.getChildren().addAll(icon, nameLbl);

        //when mouse button is pressed, save the initial position of screen or launch form stage
        //setOnMouseEntered();

        setOnMousePressed((MouseEvent event) -> {
            if (event.getButton() == MouseButton.PRIMARY) {
                if (event.getClickCount() == 1) {
                    setCursor(Cursor.MOVE);
                    positionX = event.getScreenX() - getLayoutX();
                    positionY = event.getScreenY() - getLayoutY();
                }

                if (event.getClickCount() == 2) {
                    this.launchFormStage();
                }

            } else if (event.getButton() == MouseButton.SECONDARY) {
                Point p = MouseInfo.getPointerInfo().getLocation();
                popup.show(icon, p.getX(), p.getY());
            }

        });

        //when screen is dragged, translate it accordingly
        setOnMouseDragged((MouseEvent event) -> {
            if (event.getButton() == MouseButton.PRIMARY) {
                if ((event.getScreenX() - positionX) >= 0 && (event.getScreenY() - positionY) >= 0) {
                    setLayoutX(event.getScreenX() - positionX);
                    setLayoutY(event.getScreenY() - positionY);
                }
            }
        });

        // when the screen drag ends, move the device
        setOnMouseReleased((t) -> {
            setCursor(Cursor.DEFAULT);
            device.setX(getLayoutX());
            device.setY(getLayoutY());
        });
    }



    private VBox popupContent() {


        final FontIcon cableIcon = new FontIcon(MaterialDesign.MDI_ACCESS_POINT);
        cableIcon.setIconSize(20);
        connectMenuItem = new JFXButton("Connect", cableIcon);
        connectMenuItem.setMinWidth(150);
        connectMenuItem.setAlignment(Pos.CENTER_LEFT);
        connectMenuItem.getStyleClass().add("MenuItem");
        connectMenuItem.setOnMouseClicked(event -> this.launchFormStage1());

        final FontIcon editIcon = new FontIcon(MaterialDesign.MDI_PENCIL);
        editIcon.setIconSize(20);
        editMenuItem = new JFXButton("Edit", editIcon);
        editMenuItem.setMinWidth(150);
        editMenuItem.setAlignment(Pos.CENTER_LEFT);
        editMenuItem.getStyleClass().add("MenuItem");
        editMenuItem.setOnMouseClicked(event -> this.launchFormStage());

        final FontIcon deleteIcon = new FontIcon(MaterialDesign.MDI_DELETE);
        deleteIcon.setIconSize(20);
        deleteMenuItem = new JFXButton("Delete", deleteIcon);
        deleteMenuItem.setMinWidth(150);
        deleteMenuItem.setAlignment(Pos.CENTER_LEFT);
        deleteMenuItem.getStyleClass().add("MenuItem");
        deleteMenuItem.setOnMouseClicked(event -> this.deleteDeviceAction());

        final VBox content = new VBox(
                connectMenuItem,
                editMenuItem,
                deleteMenuItem
        );

        content.setOnMouseExited((t) -> {
            popup.hide();
        });
        content.getStyleClass().add("MenuContect");
        return content;
    }

    protected String getFormTitle() {
        String formTitle = this.device.getClass().getSimpleName().toLowerCase();
        switch (formTitle) {
            case "servermodel":
                ServerModel serverModel = (ServerModel) this.device;
                if (serverModel.getLevel() == 0) {
                    formTitle = "Cloud Server - " + this.device.getName();
                }
                if (serverModel.getLevel() == 1) {
                    formTitle = "Fog Server - " + this.device.getName();
                }
                break;
            case "sensormodel":
                formTitle = "Sensor - " + this.device.getName();
                break;
            case "actuatormodel":
                formTitle = "Actuator - " + this.device.getName();
                break;
        }
        return formTitle;
    }

    protected void launchFormStage() {

        if (this.formScene != null) {
            final Stage primaryStage = (Stage) this.getScene().getWindow();
            formStage = new Stage();
            formStage.setOnCloseRequest(beforeCloseFormStage());
            formScene.getStylesheets().addAll("file:src/main/resources/style/FormStyle.css");
            formStage.setScene(formScene);
            formStage.initModality(Modality.WINDOW_MODAL);
            formStage.initOwner(primaryStage);
            formStage.setResizable(false);
            formStage.setTitle(getFormTitle());
            formStage.showAndWait();
            formStage.close();
        } else {
            new Console().danger("The form is not available");
        }

    }

    protected void launchFormStage1() {

        CableDeviceController cdc= new CableDeviceController(device);
        cdc.activityFormCable();

    }

    protected void deleteDeviceAction() {

        CableDeviceController deleteDev= new CableDeviceController(device, false);
        deleteDev.deleteDevice();

    }

    public ImageView getIcon() { return icon; }

    public void setDevice(DeviceModel device) {
        this.device = device;
    }

    public void setOnConnectEvent(EventHandler event) {
        connectMenuItem.setOnMouseClicked(event);
    }

    protected void setOnEditEvent(EventHandler event) {
        editMenuItem.setOnMouseClicked(event);
    }

    public void setOnDeleteEvent(EventHandler event) {
        deleteMenuItem.setOnMouseClicked(event);
    }

    public abstract EventHandler<WindowEvent> beforeCloseFormStage();
}
