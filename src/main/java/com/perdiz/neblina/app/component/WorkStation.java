/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.perdiz.neblina.app.component;

import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;
import com.perdiz.neblina.app.component.device.ActuatorDevice;
import com.perdiz.neblina.app.component.device.SensorDevice;
import com.perdiz.neblina.app.component.device.ServerDevice;
import com.perdiz.neblina.app.controller.WorkStationController;
import com.perdiz.neblina.app.resource.ImageResource;
import com.perdiz.neblina.app.resource.Resource;
import com.perdiz.neblina.model.ActuatorModel;
import com.perdiz.neblina.model.SensorModel;
import com.perdiz.neblina.model.ServerModel;

import java.io.BufferedWriter;
import java.io.FileNotFoundException;
import java.io.FileWriter;
import java.io.IOException;
import java.lang.reflect.Type;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Scanner;
import java.util.logging.Level;
import java.util.logging.Logger;

import javafx.scene.control.Alert;
import javafx.scene.control.ButtonType;
import javafx.scene.image.ImageView;
import javafx.stage.FileChooser;
import javafx.stage.Stage;

/**
 * @author alexander
 */
public class WorkStation extends WorkStationController {

    public WorkStation() {
        getStyleClass().add("WorkStation");
        init();

    }

    private void init() {
    }

    /*-------------------------------------------------------------------------
    -                                 Save file                               -
    -------------------------------------------------------------------------*/
    protected Map<String, Object> getDevicesData() {
        Map<String, Object> map = new HashMap<>();
        List<ServerModel> servers = new ArrayList<>();
        List<SensorModel> sensors = new ArrayList<>();
        List<ActuatorModel> actuators = new ArrayList<>();


        getChildren().forEach((node) -> {
            if (node instanceof ServerDevice) {
                ServerModel model = (ServerModel) ((ServerDevice) node).getModel();
                servers.add(model);
            } else if (node instanceof SensorDevice) {
                SensorModel model = (SensorModel) ((SensorDevice) node).getModel();
                sensors.add(model);
            } else if (node instanceof ActuatorDevice) {
                ActuatorModel model = (ActuatorModel) ((ActuatorDevice) node).getModel();
                actuators.add(model);
            }
        });

        map.put("servers", servers);
        map.put("sensors", sensors);
        map.put("actuators", actuators);
        return map;
    }

    protected Map<String, Object> getLinksData() {
        Map<String, Object> map = new HashMap<>();
        return map;
    }

    protected Map<String, Object> getInitialsValues() {
        Map<String, Object> map = new HashMap<>();
        map.put("cloudServers", numberOfCloudServers);
        map.put("fogServers", numberOfFogServers);
        map.put("sensors", numberOfSensors);
        map.put("actuators", numberOfActuators);
        return map;
    }

    protected Map<String, Object> getDocumentToSave() {
        Map<String, Object> map = new HashMap<>();
        map.put("devices", getDevicesData());
        map.put("links", getLinksData());
        map.put("initialsValues", getInitialsValues());
        return map;
    }

    public void saveDocument() {
        if (selectedFile == null) {
            final Stage stage = (Stage) this.getScene().getWindow();
            FileChooser fileChooser = new FileChooser();
            fileChooser.setTitle("Save file");

            fileChooser.getExtensionFilters().add(
                    new FileChooser.ExtensionFilter("Neblina File (*.nbl)", "*.nbl")
            );
            selectedFile = fileChooser.showSaveDialog(stage);
        }

        if (selectedFile != null) {
            BufferedWriter bw;
            try {
                boolean hasExtension = selectedFile.toString().contains(".nbl");
                bw = new BufferedWriter(new FileWriter(selectedFile + (hasExtension ? "" : ".nbl")));
                bw.write(new Gson().toJson(getDocumentToSave()));
                bw.close();
            } catch (IOException ex) {
                Logger.getLogger(App.class.getName()).log(Level.SEVERE, null, ex);
            }
        }

    }

    /*-------------------------------------------------------------------------
    -                                 Open file                               -
    -------------------------------------------------------------------------*/
    protected Map<String, Object> getDocumentRead() {
        String document = "";
        Type deviceType = new TypeToken<Map<String, Object>>() {
        }.getType();
        final Stage stage = (Stage) this.getScene().getWindow();
        FileChooser fileChooser = new FileChooser();
        fileChooser.setTitle("Open file");

        fileChooser.getExtensionFilters().add(
                new FileChooser.ExtensionFilter("Neblina File (*.nbl)", "*.nbl")
        );

        selectedFile = fileChooser.showOpenDialog(stage);
        if (selectedFile != null) {
            Scanner scanner;

            try {
                scanner = new Scanner(selectedFile);
                while (scanner.hasNextLine()) {
                    document += scanner.nextLine();
                }
                scanner.close();
            } catch (FileNotFoundException ex) {
                System.err.println("Mensaje: " + ex.getMessage());
            }
        }
        return new Gson().fromJson(document, deviceType);
    }

    protected void renderDataRead() {

        Type defaultType = new TypeToken<Map<String, Object>>() {
        }.getType();

        Map<String, Object> map = getDocumentRead();
        if (map != null) {

            for (Map.Entry element : map.entrySet()) {
                switch ((String) element.getKey()) {

                    case "initialsValues":

                        Map<String, Object> initialsValues = new Gson().fromJson(element.getValue().toString(), defaultType);

                        for (Map.Entry value : initialsValues.entrySet()) {
                            switch ((String) value.getKey()) {
                                case "sensors":
                                    numberOfSensors = (byte) (int) Double.parseDouble(value.getValue().toString());
                                    break;

                                case "actuators":
                                    numberOfActuators = (byte) (int) Double.parseDouble(value.getValue().toString());
                                    break;
                                case "cloudServers":
                                    numberOfCloudServers = (byte) (int) Double.parseDouble(value.getValue().toString());
                                    break;
                                case "fogServers":
                                    numberOfFogServers = (byte) (int) Double.parseDouble(value.getValue().toString());
                                    break;
                            }
                        }
                        break;
                    case "devices":

                        Map<String, Object> devices = new Gson().fromJson(element.getValue().toString(), defaultType);
                        for (Map.Entry device : devices.entrySet()) {
                            switch ((String) device.getKey()) {
                                case "servers":
                                    Type serverType = new TypeToken<List<ServerModel>>() {
                                    }.getType();
                                    List<ServerModel> servers = new Gson().fromJson(device.getValue().toString(), serverType);
                                    servers.forEach((server) -> {
                                        getChildren().add(new ServerDevice(server));
                                    });
                                    break;

                                case "sensors":
                                    Type sensorType = new TypeToken<List<SensorModel>>() {
                                    }.getType();

                                    List<SensorModel> sensors = new Gson().fromJson(device.getValue().toString(), sensorType);
                                    sensors.forEach((sensor) -> {
                                        switch (sensor.nSensor){
                                            case 1:
                                                ImageView icon1= new ImageView("file:src/main/resources/image/cctv.png");
                                                getChildren().add(new SensorDevice(sensor, icon1));
                                                break;
                                            case 2:
                                                ImageView icon2= new ImageView("file:src/main/resources/image/air-purifier.png");
                                                getChildren().add(new SensorDevice(sensor, icon2));
                                                break;
                                            case 3:
                                                ImageView icon3= new ImageView("file:src/main/resources/image/motion-sensor.png");
                                                getChildren().add(new SensorDevice(sensor, icon3));
                                                break;
                                            case 4:
                                                ImageView icon4= new ImageView("file:src/main/resources/image/light-sensor.png");
                                                getChildren().add(new SensorDevice(sensor, icon4));
                                                break;
                                            case 5:
                                                ImageView icon5= new ImageView("file:src/main/resources/image/fingerprint.png");
                                                getChildren().add(new SensorDevice(sensor, icon5));
                                                break;
                                            case 6:
                                                ImageView icon6= new ImageResource(Resource.CARDIOGRAM);
                                                getChildren().add(new SensorDevice(sensor, icon6));
                                                break;
                                            case 7:
                                                ImageView icon7= new ImageView("file:src/main/resources/image/pulse-oximeter.png");
                                                getChildren().add(new SensorDevice(sensor, icon7));
                                                break;
                                            case 8:
                                                ImageView icon8= new ImageView("file:src/main/resources/image/glucometer.png");
                                                getChildren().add(new SensorDevice(sensor, icon8));
                                                break;
                                            case 9:
                                                ImageView icon9= new ImageView("file:src/main/resources/image/smart-temperature.png");
                                                getChildren().add(new SensorDevice(sensor, icon9));
                                                break;
                                            case 10:
                                                ImageView icon10= new ImageView("file:src/main/resources/image/smartwatch.png");
                                                getChildren().add(new SensorDevice(sensor, icon10));
                                                break;
                                            case 11:
                                                ImageView icon11= new ImageView("file:src/main/resources/image/light-meter.png");
                                                getChildren().add(new SensorDevice(sensor, icon11));
                                                break;
                                            case 12:
                                                ImageView icon12= new ImageView("file:src/main/resources/image/driverless-car.png");
                                                getChildren().add(new SensorDevice(sensor, icon12));
                                                break;
                                            case 13:
                                                ImageView icon13= new ImageView("ffile:src/main/resources/image/charging-station.png");
                                                getChildren().add(new SensorDevice(sensor, icon13));
                                                break;

                                        }

                                    });
                                    break;

                                case "actuators":
                                    Type actuatorType = new TypeToken<List<ActuatorModel>>() {
                                    }.getType();
                                    List<ActuatorModel> actuators = new Gson().fromJson(device.getValue().toString(), actuatorType);
                                    actuators.forEach((actuator) -> {
                                        getChildren().add(new ActuatorDevice(actuator));
                                    });
                                    break;
                            }
                        }

                        break;
                    case "links":

                        break;

                }
            }
        }
    }

    public void openDocument() {
        String clearWorkStation = canClearWorkStation();
        switch (clearWorkStation) {
            case "Undefined":
                renderDataRead();
                break;
            case "SaveFirst":
                saveDocument();
                restartInitialsValues();
                renderDataRead();
                break;
            case "DontSave":
                restartInitialsValues();
                renderDataRead();
                break;
        }

    }

    /*-------------------------------------------------------------------------
    -                            Clear WorkStation                            -
    -------------------------------------------------------------------------*/
    public String canClearWorkStation() {
        String result = "Undefined";
        String document = (selectedFile != null) ? selectedFile.getName() : "New document";
        if (getChildren().size() > 0) {
            Alert alert = new Alert(Alert.AlertType.CONFIRMATION);
            ButtonType buttonType = new ButtonType("Don't save");
            alert.getButtonTypes().add(buttonType);
            alert.setHeaderText(null);
            alert.setContentText("Save changes to document \"" + document + "\" before closing?");
            alert.showAndWait();
            switch (alert.getResult().getText()) {
                case "OK":
                    result = "SaveFirst";
                    break;
                case "Don't save":
                    result = "DontSave";
                    break;
                default:
                    result = alert.getResult().getText();
                    break;
            }
        }
        return result;
    }
}
