/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.perdiz.neblina.app.component;

import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;
import com.perdiz.neblina.app.component.device.ActuatorDevice;
import com.perdiz.neblina.app.component.device.CableDevice;
import com.perdiz.neblina.app.component.device.SensorDevice;
import com.perdiz.neblina.app.component.device.ServerDevice;
import com.perdiz.neblina.app.controller.WorkStationController;
import com.perdiz.neblina.app.controller.device.CableDeviceController;
import com.perdiz.neblina.app.iu.Device;
import com.perdiz.neblina.app.resource.ImageResource;
import com.perdiz.neblina.app.resource.Resource;
import com.perdiz.neblina.model.*;

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
import java.util.concurrent.atomic.AtomicReference;
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
    private DeviceModel model2;
    private DeviceModel modelS;
    private DeviceModel modelS1;
    private Device deviceE;
    private Device deviceE1;

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
        //List<CableModel> cables = new ArrayList<>();
//mmodelo conexion

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
        //System.out.println(cables);
        map.put("servers", servers);
        map.put("sensors", sensors);
        map.put("actuators", actuators);
        //map.put("cables", cables);
        return map;
    }

    public Map<String, Object> getLinksData() {
        Map<String, Object> map = new HashMap<>();
        List<CableSave> cables = new ArrayList<>();
        getChildren().forEach((node) -> {
            if (node instanceof CableDevice) {
                CableModel model = (CableModel) ((CableDevice) node).getModel();
                addDeviceModel(model, cables);
                //System.out.println(model.getDevice1().toString()+ ", " + model.getDevice1().toString());

            }
        });

        //System.out.println(cables);
        map.put("cables", cables);
        return map;
    }

    private void addDeviceModel(CableModel model, List<CableSave> cables) {
        //AtomicReference<DeviceModel> dev1 = null;
        //AtomicReference<DeviceModel> dev2 = null;
        AtomicReference<String> idD1 = new AtomicReference<>("");
        AtomicReference<String> idD2 = new AtomicReference<>("");

        getChildren().forEach((node) -> {
            if (node instanceof ServerDevice) {

                //System.out.println("Node: "+node);

                if ((model.getDevice1().toString()).equals(node.toString())){

                    this.modelS = (ServerModel) ((ServerDevice) node).getModel();
                    idD1.set(this.modelS.getId());

                }else if ((model.getDevice2().toString()).equals(node.toString())){
                    this.model2 = (ServerModel) ((ServerDevice) node).getModel();
                    idD2.set(this.model2.getId());
                    // dev2.set((ServerModel) ((ServerDevice) node).getModel());
                }


            } else if (node instanceof SensorDevice) {
                if ((model.getDevice1().toString()).equals(node.toString())){

                    this.modelS = (SensorModel) ((SensorDevice) node).getModel();
                    idD1.set(this.modelS.getId());

                }else if ((model.getDevice2().toString()).equals(node.toString())){

                    this.model2 = (SensorModel) ((SensorDevice) node).getModel();
                    idD2.set(this.model2.getId());
                }


            } else if (node instanceof ActuatorDevice) {
                if ((model.getDevice1().toString()).equals(node.toString())){

                    this.modelS = (ActuatorModel) ((ActuatorDevice) node).getModel();
                    idD1.set(this.modelS.getId());

                }else if ((model.getDevice2().toString()).equals(node.toString())){

                    this.model2 = (ActuatorModel) ((ActuatorDevice) node).getModel();
                    idD2.set(this.model2.getId());
                }

            }

        });

        /*System.out.println(idD1.get());
        System.out.println(idD2.get());*/

        CableSave cableSave = new CableSave(idD1.get(), idD2.get(), model.getDev1Name(), model.getDev2Name(), model.getLatency());
        cables.add(cableSave);
    }

    protected Map<String, Object> getInitialsValues() {
        Map<String, Object> map = new HashMap<>();
        map.put("cloudServers", numberOfCloudServers);
        map.put("fogServers", numberOfFogServers);
        map.put("sensors", numberOfSensors);
        map.put("actuators", numberOfActuators);
       // map.put("cables", numberOfConnections);
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
                                        CableDeviceController.workStation = this;
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
                                                CableDeviceController.workStation = this;
                                                break;
                                            case 2:
                                                ImageView icon2= new ImageView("file:src/main/resources/image/air-purifier.png");
                                                getChildren().add(new SensorDevice(sensor, icon2));
                                                CableDeviceController.workStation = this;
                                                break;
                                            case 3:
                                                ImageView icon3= new ImageView("file:src/main/resources/image/motion-sensor.png");
                                                getChildren().add(new SensorDevice(sensor, icon3));
                                                CableDeviceController.workStation = this;
                                                break;
                                            case 4:
                                                ImageView icon4= new ImageView("file:src/main/resources/image/light-sensor.png");
                                                getChildren().add(new SensorDevice(sensor, icon4));
                                                CableDeviceController.workStation = this;
                                                break;
                                            case 5:
                                                ImageView icon5= new ImageView("file:src/main/resources/image/fingerprint.png");
                                                getChildren().add(new SensorDevice(sensor, icon5));
                                                CableDeviceController.workStation = this;
                                                break;
                                            case 6:
                                                ImageView icon6= new ImageResource(Resource.CARDIOGRAM);
                                                getChildren().add(new SensorDevice(sensor, icon6));
                                                CableDeviceController.workStation = this;
                                                break;
                                            case 7:
                                                ImageView icon7= new ImageView("file:src/main/resources/image/pulse-oximeter.png");
                                                getChildren().add(new SensorDevice(sensor, icon7));
                                                CableDeviceController.workStation = this;
                                                break;
                                            case 8:
                                                ImageView icon8= new ImageView("file:src/main/resources/image/glucometer.png");
                                                getChildren().add(new SensorDevice(sensor, icon8));
                                                CableDeviceController.workStation = this;
                                                break;
                                            case 9:
                                                ImageView icon9= new ImageView("file:src/main/resources/image/smart-temperature.png");
                                                getChildren().add(new SensorDevice(sensor, icon9));
                                                CableDeviceController.workStation = this;
                                                break;
                                            case 10:
                                                ImageView icon10= new ImageView("file:src/main/resources/image/smartwatch.png");
                                                getChildren().add(new SensorDevice(sensor, icon10));
                                                CableDeviceController.workStation = this;
                                                break;
                                            case 11:
                                                ImageView icon11= new ImageView("file:src/main/resources/image/light-meter.png");
                                                getChildren().add(new SensorDevice(sensor, icon11));
                                                CableDeviceController.workStation = this;
                                                break;
                                            case 12:
                                                ImageView icon12= new ImageView("file:src/main/resources/image/driverless-car.png");
                                                getChildren().add(new SensorDevice(sensor, icon12));
                                                CableDeviceController.workStation = this;
                                                break;
                                            case 13:
                                                ImageView icon13= new ImageView("ffile:src/main/resources/image/charging-station.png");
                                                getChildren().add(new SensorDevice(sensor, icon13));
                                                CableDeviceController.workStation = this;
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
                                        //System.out.println(actuator);
                                        CableDeviceController.workStation = this;
                                    });
                                    break;
                            }
                        }

                        break;
                    case "links":
                        Map<String, Object> connects = new Gson().fromJson(element.getValue().toString(), defaultType);
                        for (Map.Entry device : connects.entrySet()) {
                            switch ((String) device.getKey()) {
                                case "cables":
                                    Type cableType = new TypeToken<List<CableSave>>() {
                                    }.getType();
                                    List<CableSave> cables = new Gson().fromJson(device.getValue().toString(), cableType);
                                    //System.out.println(cable.getDevice1());
                                    //getChildren().add(new CableDevice(cable));
                                    //App.connects.add(cable);
                                    cables.forEach((cable) -> {
                                        generateConnection(cable);
                                        //CableDeviceController.workStation = this;
                                    });
                                    break;
                            }
                        }
                        break;

                }
            }
        }
    }

    public void generateConnection(CableSave cableSave){

        getChildren().forEach((node) -> {


            if (node instanceof ServerDevice) {

                this.modelS1 = (ServerModel) ((ServerDevice) node).getModel();
                //System.out.println("Node: "+modelS1.getId());
                if ((cableSave.getDevice1()).equals(this.modelS1.getId())){
                        this.deviceE = (ServerDevice) node;


                }else if ((cableSave.getDevice2()).equals(this.modelS1.getId())){
                    this.deviceE1 = (ServerDevice) node;
                    // dev2.set((ServerModel) ((ServerDevice) node).getModel());
                }


            } else if (node instanceof SensorDevice) {
                this.modelS1 = (SensorModel) ((SensorDevice) node).getModel();
               // System.out.println("Node: "+modelS1.getId());
                if ((cableSave.getDevice1()).equals(this.modelS1.getId())){
                    this.deviceE = (SensorDevice) node;


                }else if ((cableSave.getDevice2()).equals(this.modelS1.getId())){
                    this.deviceE1 = (SensorDevice) node;
                    // dev2.set((ServerModel) ((ServerDevice) node).getModel());
                }


            } else if (node instanceof ActuatorDevice) {

                this.modelS1 = (ActuatorModel) ((ActuatorDevice) node).getModel();
                //System.out.println("Node: "+modelS1.getId());

                if ((cableSave.getDevice1()).equals(this.modelS1.getId())){
                    this.deviceE = (ActuatorDevice) node;


                }else if ((cableSave.getDevice2()).equals(this.modelS1.getId())){
                    this.deviceE1 = (ActuatorDevice) node;
                    // dev2.set((ServerModel) ((ServerDevice) node).getModel());
                }

            }


        });

        /*System.out.println("Device 1 : " + cableSave.getDevice1());
        System.out.println("Device 2 : " + cableSave.getDevice2());*/
        /*System.out.println(this.deviceE);
        System.out.println(this.deviceE1);*/
        byte number = App.workStation.getNumberOfConnections();
        CableModel cableModel = new CableModel("CC" + number,"Cable" + number ,this.deviceE,this.deviceE1, cableSave.getDev1Name(), cableSave.getDev2Name(), cableSave.getLatency());
        CableDevice cableDevice = new CableDevice(cableModel);
        App.connects.add(cableModel);
        getChildren().add(cableDevice);


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
