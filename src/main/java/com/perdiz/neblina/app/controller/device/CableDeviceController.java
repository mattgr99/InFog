/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.perdiz.neblina.app.controller.device;


import com.perdiz.neblina.app.component.App;
import com.perdiz.neblina.app.component.WorkStation;
import com.perdiz.neblina.app.component.device.ActuatorDevice;
import com.perdiz.neblina.app.component.device.CableDevice;
import com.perdiz.neblina.app.component.device.SensorDevice;
import com.perdiz.neblina.app.component.device.ServerDevice;
import com.perdiz.neblina.app.iu.Device;
import com.perdiz.neblina.model.*;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.EventHandler;
import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.Node;
import javafx.scene.Scene;
import javafx.scene.control.Alert;
import javafx.scene.control.Button;
import javafx.scene.control.ComboBox;
import javafx.scene.control.TextField;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.HBox;
import javafx.scene.paint.Color;
import javafx.scene.shape.Line;
import javafx.scene.text.Text;
import javafx.stage.Modality;
import javafx.stage.Stage;
import javafx.stage.WindowEvent;

import java.util.ArrayList;
import java.util.List;

/**
 *
 * @author alexander
 */
public class CableDeviceController extends Device {
    protected TextField nameField;
    protected TextField latencyField;
    protected CableModel cable;
    public static WorkStation workStation = new WorkStation();
    protected ComboBox<String> cbx;
    private Device deviceStart;
    private Device deviceEnd;
    private Device deviceVal;

    String nameDev1;
    String nameDev2;
    public ArrayList<CableModel> connectsRep = new ArrayList<>();

    public CableDeviceController(CableModel cableModel) {
        //super(cableModel);
        this.cable = cableModel;
    }

    public CableDeviceController(DeviceModel device1) {
        this.latencyField = new TextField("");
        this.device = device1;
    }

    public CableDeviceController(DeviceModel device1, boolean valDelete) {
        this.device = device1;
        addDestiny(FXCollections.observableArrayList(), valDelete);
    }

    public void activityFormCable(){
        ObservableList<String> items = FXCollections.observableArrayList();

        addDestiny(items, true);
        removeElementList(items);

        GridPane gridPane = new GridPane();
        gridPane.setPadding(new Insets(10));
        gridPane.setVgap(10);
        gridPane.setHgap(5);
        gridPane.setAlignment(Pos.CENTER);

        //Arranging all the nodes in the grid
        gridPane.add(new Text("Destination        "), 0, 0);
        gridPane.add(cbx = new ComboBox<>(items), 1, 0);
        gridPane.add(new Text("Latency (ms)"), 0, 1);
        gridPane.add(latencyField, 1, 1);

        Button okBtn = new Button("ok");
        okBtn.getStyleClass().add("okbtn");
        okBtn.setOnMouseClicked(this.onOkBtnClicked());

        Button cancelBtn = new Button("Cancel");
        cancelBtn.getStyleClass().add("cancelbtn");
        //cancelBtn.setOnMouseClicked(this.onCancelBtnClicked());

        HBox btnContainer = new HBox(cancelBtn, okBtn);
        btnContainer.setSpacing(10);
        btnContainer.setAlignment(Pos.CENTER_RIGHT);
        gridPane.add(btnContainer, 1, 3);
        this.formScene = new Scene(gridPane);

        //final Stage primaryStage = (Stage) this.getScene().getWindow();
        formStage = new Stage();
        // formStage.setOnCloseRequest(beforeCloseFormStage());
        formScene.getStylesheets().addAll("file:src/main/resources/style/FormStyle.css");
        formStage.setScene(formScene);
        formStage.initModality(Modality.WINDOW_MODAL);
        //formStage.initOwner(primaryStage);
        formStage.setResizable(false);
        formStage.setTitle("Connect " + device.getClass().getSimpleName());
        formStage.showAndWait();
        formStage.close();
        items.clear();
    }

    public void deleteDevice(){

        App.connects.forEach((item)->{
           // System.out.println(item.getDevice1());
            //System.out.println(item.getDevice2());
            if ((item.getDevice1()==deviceStart) || (item.getDevice2()==deviceStart)){
                App.workStation.getChildren().remove(item.getLine());
                //this.cb = item;
                connectsRep.add(item);

            }

        });
        App.workStation.getChildren().remove(deviceStart);

        connectsRep.forEach((t1)->{
            App.connects.remove(t1);
        });
    }

    @Override
    public EventHandler<WindowEvent> beforeCloseFormStage() {
        return event -> {
           // this.nameField.setText(this.cable.getName());
            this.latencyField.setText(this.cable.getLatency());
        };
    }

    public void addDestiny(ObservableList<String> items, boolean flag){

        for (Node node : workStation.getChildren()){
            if (node instanceof ServerDevice) {
                ServerModel model = (ServerModel) ((ServerDevice) node).getModel();
                items.add(model.getName());
                if (model.getName().equals(device.getName())){
                    deviceStart = (Device) node;
                    if (!flag){
                        //System.out.println(deviceStart);
                        break;
                    }

                }

            } else if (node instanceof SensorDevice) {
                SensorModel model = (SensorModel) ((SensorDevice) node).getModel();
                items.add(model.getName());
                if (model.getName().equals(device.getName())){
                    deviceStart = (Device) node;
                    if (!flag){
                        //System.out.println(deviceStart);
                        break;
                    }
                }
            } else if (node instanceof ActuatorDevice) {
                ActuatorModel model = (ActuatorModel) ((ActuatorDevice) node).getModel();
                items.add(model.getName());
                if (model.getName().equals(device.getName())){
                    deviceStart = (Device) node;
                    if (!flag){
                        //System.out.println(deviceStart);
                        break;
                    }
                }
            }
        }
       // workStation.getChildren().forEach((node) -> {
            //System.out.println(node);

     //   });

    }

    public void removeElementList(ObservableList<String> items){
        if (device instanceof ServerModel) {
            //cable.setName(model.getName());
            items.remove(device.getName());
        } else if (device instanceof SensorModel) {
            items.remove(device.getName());
            //cable.setName(model.getName());
        } else if (device instanceof ActuatorModel) {
            items.remove(device.getName());
            //cable.setName(model.getName());
        }
    }

    private String selectNameDevice(Device deviceN){
        String nameDev= "";
        if (deviceN instanceof ServerDevice) {
            ServerModel model = (ServerModel) ((ServerDevice) deviceN).getModel();
            nameDev = model.getName();

        } else if (deviceN instanceof SensorDevice) {
            SensorModel model = (SensorModel) ((SensorDevice) deviceN).getModel();
            nameDev = model.getName();
        } else if (deviceN instanceof ActuatorDevice) {
            ActuatorModel model = (ActuatorModel) ((ActuatorDevice) deviceN).getModel();
            nameDev = model.getName();
        }

        return nameDev;

    }


    // Save new form values
    protected EventHandler onOkBtnClicked() {
        return event -> {
            //Device devC = deviceStart;
            //Device deviceEnd;
            String valor;
            // this.cable.setDestinyId(this.cbx.getValue());
            //this.cable.setLatency(this.latencyField.getText());
            //System.out.println(this.cbx.getValue());
            valor = cbx.getValue();

            if (Integer.parseInt(this.latencyField.getText()) <= 100){
                workStation.getChildren().forEach((node) -> {
                    if (node instanceof ServerDevice) {
                        ServerModel model = (ServerModel) ((ServerDevice) node).getModel();
                        if (model.getName().equals(valor)){
                            deviceEnd = (Device) node;
                        }

                    } else if (node instanceof SensorDevice) {
                        SensorModel model = (SensorModel) ((SensorDevice) node).getModel();
                        if (model.getName().equals(valor)){
                            deviceEnd = (Device) node;
                        }
                    } else if (node instanceof ActuatorDevice) {
                        ActuatorModel model = (ActuatorModel) ((ActuatorDevice) node).getModel();
                        if (model.getName().equals(valor)){
                            deviceEnd = (Device) node;
                        }
                    }
                });


                //Create lin

                nameDev1= selectNameDevice(deviceStart);
                nameDev2= selectNameDevice(deviceEnd);
                byte number = App.workStation.getNumberOfConnections();
                CableModel cableModel = new CableModel("CC" + number,"Cable" + number ,deviceStart,deviceEnd, nameDev1, nameDev2, this.latencyField.getText());
                CableDevice cableDevice = new CableDevice(cableModel);
                App.connects.add(cableModel);
                App.workStation.getChildren().add(cableDevice);


                this.latencyField.setText("");
                this.formStage.close();
            }else{
                Alert alert = new Alert(Alert.AlertType.ERROR);
                alert.setHeaderText(null);
                alert.setTitle("Connect");
                alert.setContentText("The latency is higher than 100 ms ");
                alert.showAndWait();
                this.latencyField.setText("");
                onOkBtnClicked();
            }
            //this.formStage.close();
        };
    }

    // Reset form values if canceled
    protected EventHandler<MouseEvent> onCancelBtnClicked() {
        return event -> {
            this.nameField.setText(this.cable.getName());

            this.formStage.close();
        };
    }

}
