/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.perdiz.neblina.app.component;

import com.jfoenix.controls.JFXButton;
import com.perdiz.neblina.app.component.device.ActuatorDevice;
import com.perdiz.neblina.app.component.device.CableDevice;
import com.perdiz.neblina.app.component.device.SensorDevice;
import com.perdiz.neblina.app.component.device.ServerDevice;
import com.perdiz.neblina.app.controller.AppController;
import com.perdiz.neblina.app.iu.Device;
import com.perdiz.neblina.app.resource.Icon;
import com.perdiz.neblina.charts.BarChartTraffic;
import com.perdiz.neblina.charts.LineChartTraffic;
import com.perdiz.neblina.heuristics.DeviceTraffic;
import com.perdiz.neblina.heuristics.TrafficHeuristc;
import com.perdiz.neblina.heuristics.TrafficRandomHeuristc;
import com.perdiz.neblina.model.ActuatorModel;
import com.perdiz.neblina.model.CableModel;
import com.perdiz.neblina.model.SensorModel;
import com.perdiz.neblina.model.ServerModel;
import com.perdiz.neblina.util.Pkg;
import javafx.animation.Interpolator;
import javafx.animation.TranslateTransition;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.EventHandler;
import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.Node;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.scene.image.ImageView;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.HBox;
import javafx.scene.layout.Priority;
import javafx.scene.layout.VBox;
import javafx.scene.paint.Color;
import javafx.scene.shape.Line;
import javafx.scene.text.Text;
import javafx.stage.Modality;
import javafx.stage.Stage;
import javafx.util.Duration;

import java.util.*;

/**
 * @author alexander
 */
public class App extends AppController {

    private final ToolBar toolBar = new ToolBar();
    private final RightSideBar rightSideBar = new RightSideBar();
    private final LeftSideBar leftSideBar = new LeftSideBar();
    private final WorkStation workStation = new WorkStation();

    protected Stage formStage;
    protected Scene formScene;
    protected TextField latencyField;
    protected TextField trafficField;
    protected TextField trafficRField1;
    protected TextField trafficRField2;
    protected TextField timeExeField;
    protected TextField timeDeviceField;
    protected CableModel cable;
    protected Device deviceEnd;
    ArrayList<CableDevice> connects = new ArrayList<CableDevice>();
    ArrayList<CableDevice> connectsRep = new ArrayList<CableDevice>();
    ArrayList<Integer> slots = new ArrayList<Integer>();
    Hashtable<String, DeviceTraffic> trfDevice = new Hashtable<String, DeviceTraffic>();

    CableDevice cb = new CableDevice();
    ImageView trimg = new ImageView("file:src/main/resources/image/email1.png");
    ArrayList<ImageView> imgTraffics = new ArrayList<ImageView>();
    String nameDev1;
    String nameDev2;
    String traffic;
    int timeTraffic;
    Boolean isSetTime = false;

    //protected
    protected ComboBox<String> cbx;


    //Sensor buttons
    JFXButton btnCardiogram = new JFXButton("", new ImageView(Icon.SN_CARDIOGRAM.src));
    JFXButton btnPulseOximeter = new JFXButton("", new ImageView(Icon.SN_OXIMETER.src));
    JFXButton btnAirPurifier = new JFXButton("", new ImageView(Icon.SN_PURIFIER.src));
    JFXButton btnGlucometer = new JFXButton("", new ImageView(Icon.SN_GLUCOMETER.src));
    JFXButton btnSmartTemperature = new JFXButton("", new ImageView(Icon.SN_TEMPERATURE.src));
    JFXButton btnSmartwatch = new JFXButton("", new ImageView(Icon.SN_SMARTWATCH.src));
    JFXButton btnCameraHome = new JFXButton("", new ImageView(Icon.SN_CCTV.src));
    JFXButton btnFingerPrint = new JFXButton("", new ImageView(Icon.SN_FINGERPRINT.src));

    JFXButton btnChargingStation = new JFXButton("", new ImageView(Icon.SN_CHARGIN_STATION.src));
    JFXButton btnLightSensor = new JFXButton("", new ImageView(Icon.SN_LIGHT_SENSOR.src));
    JFXButton btnLightMeter = new JFXButton("", new ImageView(Icon.SN_lIGHT_METER.src));
    JFXButton btnMotionSensor = new JFXButton("", new ImageView(Icon.SN_MOTION.src));
    JFXButton btnDriverlessCar = new JFXButton("", new ImageView(Icon.SN_DIVERLESS.src));


    public App() {
        cable = new CableModel();
        this.initLeftSideBar();
        this.initToolBar();
        this.init();
    }

    private void init() {
        //items.clear();

        VBox container = new VBox();
        ScrollPane workStationScroll = new ScrollPane(workStation);
        VBox.setVgrow(workStationScroll, Priority.ALWAYS);
        workStationScroll.setFitToHeight(true);
        workStationScroll.setFitToWidth(true);

        container.getChildren().addAll(workStationScroll, footer);

        this.latencyField = new TextField("");
        this.trafficField = new TextField("");
        this.timeExeField = new TextField("");
        this.trafficRField1 = new TextField("");
        this.trafficRField2 = new TextField("");
        this.timeDeviceField = new TextField("");
        this.setTop(toolBar);
        this.setLeft(leftSideBar);
        this.setCenter(container);
        this.setRight(rightSideBar);

        footer.addLog("${Ib}", "=> " + Pkg.NAME + "@" + Pkg.VERSION + " running. aaa");

        // Examples of printing the logs
        String lorem = "Lorem Ipsum is simply dummy text of the printing and typesetting industry. Lorem Ipsum has been the industry's standard dummy text ever since the 1500s, when an unknown printer took a galley of type and scrambled it to make a type specimen book. It has survived not only five centuries, but also the leap into electronic typesetting, remaining essentially unchanged. It was popularised in the 1960s with the release of Letraset sheets containing Lorem Ipsum passages, and more recently with desktop publishing software like Aldus PageMaker including versions of Lorem Ipsum.";
        footer.addLog("${b_} ${}", "NORMAL:", lorem);
        footer.addLog("${Sb_} ${S}", "EXITO:", lorem);
        footer.addLog("${Ib_} ${I}", "INFO:", lorem);
        footer.addLog("${Wb_} ${W}", "WARNING:", lorem);
        footer.addLog("${Eb_} ${E}", "ERROR:", lorem);


    }

    private void initToolBar() {

        this.toolBar.setOnShowLeftBarActionEvent((t) -> {
            this.leftSideBar.show();
        });

        this.toolBar.setOnNewActionEvent((t) -> {
            String clearWorkStation = workStation.canClearWorkStation();
            switch (clearWorkStation) {
                case "Undefined":
                    workStation.restartInitialsValues();
                    break;
                case "SaveFirst":
                    workStation.saveDocument();
                    workStation.restartInitialsValues();
                    break;
                case "DontSave":
                    workStation.restartInitialsValues();
                    break;
            }

        });

        this.toolBar.setOnOpenActionEvent((t) -> {
            workStation.openDocument();
        });

        this.toolBar.setOnSaveActionEvent((t) -> {
            workStation.saveDocument();
        });

        this.toolBar.setOnPlayActionEvent((t) -> {
            //Work time server
            ArrayList<Integer> rmvms = new ArrayList<Integer>();
            ServerModel model = trafficServer();
            TrafficHeuristc tf = new TrafficHeuristc((int) model.getVmachines(), slots.size());
            for (int rv : model.getRamVM()) {
                rmvms.add(rv);
            }

            tf.calcLoadServer(slots, rmvms);
            tf.energyServer();
            slots.clear();
        });


        this.toolBar.setOnPlayRandomActionEvent((t) -> {

            launchFormRandomPlay();

            if (isSetTime) {
                TrafficRandomHeuristc.vms_on.clear();
                TrafficRandomHeuristc.slots.clear();
                TrafficRandomHeuristc.energyServer.clear();
                this.isSetTime = false;
                int stopTime = this.timeTraffic;
                Timer timer = new Timer();
                ServerModel model = trafficServer();
                ArrayList<Integer> rmvms = new ArrayList<Integer>();
                //ArrayList<Integer> timtr = new ArrayList<Integer>();
                ArrayList<String> nameSensors = new ArrayList<String>();
                ArrayList<TrafficRandomHeuristc> trsend = new ArrayList<TrafficRandomHeuristc>();
                Hashtable<Integer, ArrayList> finalDisTrf = new Hashtable<>();
                int nsen = trafficSensor();
                nameSensors = nameSensor();

                /*for(int rv1=0; rv1<nsen; rv1++){
                    timtr.add((int)(Math.random()*(10-1+1)+1));
                }*/

                for (int rv : model.getRamVM()) {
                    rmvms.add(rv);
                }

                for (int tf1 = 0; tf1 < rmvms.size(); tf1++) {
                    ArrayList<Integer> baltr = new ArrayList<>();
                    baltr.add(0);
                    finalDisTrf.put(tf1, baltr);
                }


                for (int exe = 0; exe < nsen; exe++) {
                    if (this.trfDevice.get(nameSensors.get(exe)) == null) {
                        continue;
                    }
                    DeviceTraffic dvt = this.trfDevice.get(nameSensors.get(exe));
                    TrafficRandomHeuristc ner1 = new TrafficRandomHeuristc((int) model.getVmachines(), rmvms, dvt.getTime(), nameSensors.get(exe), finalDisTrf, dvt.getFirstInterval(), dvt.getSecondInterval());
                    trsend.add(ner1);
                }

                Enumeration<String> ekey = this.trfDevice.keys();
                ekey = this.trfDevice.keys();
                while (ekey.hasMoreElements()) {
                    this.trfDevice.remove(ekey.nextElement());
                }

                ArrayList<String> finalNameSensors = nameSensors;

                //Hashtable<Integer, ArrayList> finalDisTrf1 = finalDisTrf;
                TimerTask tarea = new TimerTask() {
                    private int count = 0;
                    boolean startP = true;

                    @Override
                    public void run() {

                        if (startP) {
                            for (TrafficRandomHeuristc sensor : trsend) {
                                sensor.start();
                            }
                            startP = false;
                        }

                        if (count > 0) {
                            if ((count % 5) == 0) {
                                //System.out.println((count%5) );
                                for (TrafficRandomHeuristc sensor : trsend) {
                                    //sensor.setFnPr(true);
                                    sensor.suspend();
                                }
                                TrafficRandomHeuristc.resetServer(rmvms);
                                //trsend.clear();
                            /*for(int exe=0; exe<nsen; exe++){
                                TrafficRandomHeuristc ner1 = new TrafficRandomHeuristc((int)model.getVmachines(), rmvms, timtr.get(exe), finalNameSensors.get(exe));
                                trsend.add(ner1);
                            }*/

                                for (TrafficRandomHeuristc sensor : trsend) {
                                    sensor.resume();
                                    //sensor.setFnPr(false);
                                }

                            }
                        }


                        if (count == stopTime) {


                            for (TrafficRandomHeuristc sensor : trsend) {
                                sensor.setFnPr(true);
                                sensor.stop();
                            }
                            //System.out.println("Terminado "+ TrafficRandomHeuristc.alt11);
                            System.out.println("");
                            cancel();

                            System.out.println("*********** Process completed ***********");


                        }
                        count++;


                    }
                };
                timer.schedule(tarea, 0, 1000);

            }
        });

        this.toolBar.setOnshowRightBarActionEvent((t) -> {
            this.rightSideBar.show();
        });
    }

    private void initLeftSideBar() {
        String addedFormat = "${Ib_} ${}";
        this.leftSideBar.setCloudServerActionEvent((t) -> {
            byte number = workStation.getNumberOfCloudServers();

            Device device = new ServerDevice(new ServerModel("CS" + number, "CS" + number, Byte.parseByte("0")));
            device.setOnConnectEvent(onConnectEvent(device));
            device.setOnDeleteEvent(onDeleteDevice(device));
            workStation.getChildren().add(device);
            footer.addLog(addedFormat, "Added =>", "CS" + number);
        });
        this.leftSideBar.setFogServerActionEvent((t) -> {
            byte number = workStation.getNumberOfFogServers();
            Device device = new ServerDevice(new ServerModel("FS" + number, "FS" + number, Byte.parseByte("1")));
            device.setOnConnectEvent(onConnectEvent(device));
            device.setOnDeleteEvent(onDeleteDevice(device));
            workStation.getChildren().add(device);
            footer.addLog(addedFormat, "Added =>", "FS" + number);
        });
        this.leftSideBar.setActuatorActionEvent((t) -> {
            byte number = workStation.getNumberOfActuators();
            Device device = new ActuatorDevice(new ActuatorModel("AT" + number, "Actuator" + number));
            device.setOnConnectEvent(onConnectEvent(device));
            device.setOnDeleteEvent(onDeleteDevice(device));
            workStation.getChildren().add(device);
            footer.addLog(addedFormat, "Added =>", "Actuator" + number);
        });
        this.leftSideBar.setSensorActionEvent((t) -> {
            launchFormSensors();
        });

        this.leftSideBar.setTrafficActionEvent((t) -> {
            launchFormTraffic();

        });


        this.leftSideBar.setTrafficRanActionEvent((t) -> {
            launchFormRandomTraffic();

        });

        this.leftSideBar.setChartBarBtnEvent((t) -> {

            BarChartTraffic chartTraffic = new BarChartTraffic(TrafficRandomHeuristc.energyServer);
            try {
                chartTraffic.start(new Stage());
            } catch (Exception e) {
                e.printStackTrace();
            }

            // TrafficRandomHeuristc.energyServer.clear();

        });

        this.leftSideBar.setChartLineVmBtnEvent((t) -> {

            LineChartTraffic lineTraffic = new LineChartTraffic(TrafficRandomHeuristc.vms_on, "Time(sec)", "# VM - ON", "Virtual Machines turned on", "VM", true);
            try {
                lineTraffic.start(new Stage());
            } catch (Exception e) {
                e.printStackTrace();
            }

            // TrafficRandomHeuristc.vms_on.clear();

        });

        this.leftSideBar.setChartLineTrBtnEvent((t) -> {

            LineChartTraffic lineTraffic = new LineChartTraffic(TrafficRandomHeuristc.slots, "Slot Time (sec)", "# OF ARRIVAL PER SLOT", "Trace of I/O arrival data", "Slot", false);
            try {
                lineTraffic.start(new Stage());
            } catch (Exception e) {
                e.printStackTrace();
            }

            //TrafficRandomHeuristc.slots.clear();
        });

        workStation.setOnMouseClicked((t) -> {
            imgTraffics.forEach((item) -> {
                workStation.getChildren().remove(item);
            });
            imgTraffics.clear();

        });
    }




    /*-----------------------------------------------------------------------
     -                                  Events                              -
     ----------------------------------------------------------------------*/


    // Event to delete a device
    protected EventHandler onDeleteDevice(Device device) {
        return (t) -> {
            /* */

            connects.forEach((item) -> {

                if ((item.getDevice1() == device) || (item.getDevice2() == device)) {
                    workStation.getChildren().remove(item.getLine());
                    this.cb = item;
                    connectsRep.add(item);

                }

            });
            workStation.getChildren().remove(device);

            connectsRep.forEach((t1) -> {
                connects.remove(t1);
            });


        };
    }

    // Event to connect two devices
    protected EventHandler onConnectEvent(Device device) {

        return (t) -> {
            launchFormStage(device);
        };
    }

    private String selectNameDevice(Device deviceN) {
        String nameDev = "";
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

    protected ServerModel trafficServer() {
        ServerModel model = null;
        for (Node device : workStation.getChildren()) {
            if (device instanceof ServerDevice) {
                model = (ServerModel) ((ServerDevice) device).getModel();
                break;
            }
        }

        return model;
    }

    protected int trafficSensor() {
        String nameSen = null;
        int sen = 0;
        for (Node device : workStation.getChildren()) {
            if (device instanceof SensorDevice) {
                sen++;
            }
        }

        return sen;
    }


    protected ArrayList<String> nameSensor() {

        ArrayList<String> namesS = new ArrayList<String>();
        String nameSen = null;

        for (Node device : workStation.getChildren()) {
            if (device instanceof SensorDevice) {
                nameSen = (String) ((SensorDevice) device).getModel().getName();
                namesS.add(nameSen);
            }
        }

        return namesS;
    }

    //ventana modal

    protected void launchFormStage(Device device) {
        ObservableList<String> items = FXCollections.observableArrayList();

        addDestiny(items);
        removeElementList(items, device);

        GridPane gridPane = new GridPane();
        gridPane.setPadding(new Insets(10));
        gridPane.setVgap(10);
        gridPane.setHgap(5);
        gridPane.setAlignment(Pos.CENTER);

        //Arranging all the nodes in the grid
        gridPane.add(new Text("Destiny        "), 0, 0);
        gridPane.add(cbx = new ComboBox<>(items), 1, 0);
        gridPane.add(new Text("Latency"), 0, 1);
        gridPane.add(latencyField, 1, 1);

        Button okBtn = new Button("ok");
        okBtn.getStyleClass().add("okbtn");
        okBtn.setOnMouseClicked(this.onOkBtnClicked(device));

        Button cancelBtn = new Button("Cancel");
        cancelBtn.getStyleClass().add("cancelbtn");
        cancelBtn.setOnMouseClicked(this.onCancelBtnClicked());

        HBox btnContainer = new HBox(cancelBtn, okBtn);
        btnContainer.setSpacing(10);
        btnContainer.setAlignment(Pos.CENTER_RIGHT);
        gridPane.add(btnContainer, 1, 3);
        this.formScene = new Scene(gridPane);

        final Stage primaryStage = (Stage) this.getScene().getWindow();
        formStage = new Stage();
        // formStage.setOnCloseRequest(beforeCloseFormStage());
        formScene.getStylesheets().addAll("file:src/main/resources/style/FormStyle.css");
        formStage.setScene(formScene);
        formStage.initModality(Modality.WINDOW_MODAL);
        formStage.initOwner(primaryStage);
        formStage.setResizable(false);
        formStage.setTitle("Connect " + device.getClass().getSimpleName());
        formStage.showAndWait();
        formStage.close();
        items.clear();
    }

    protected EventHandler onOkBtnClicked(Device device) {

        return event -> {
            //Device deviceEnd;
            String valor;
            this.cable.setDestinyId(this.cbx.getValue());
            this.cable.setLatency(this.latencyField.getText());
            //System.out.println(this.cbx.getValue());
            valor = cbx.getValue();

            workStation.getChildren().forEach((node) -> {
                if (node instanceof ServerDevice) {
                    ServerModel model = (ServerModel) ((ServerDevice) node).getModel();
                    if (model.getName().equals(valor)) {
                        deviceEnd = (Device) node;
                    }

                } else if (node instanceof SensorDevice) {
                    SensorModel model = (SensorModel) ((SensorDevice) node).getModel();
                    if (model.getName().equals(valor)) {
                        deviceEnd = (Device) node;
                    }
                } else if (node instanceof ActuatorDevice) {
                    ActuatorModel model = (ActuatorModel) ((ActuatorDevice) node).getModel();
                    if (model.getName().equals(valor)) {
                        deviceEnd = (Device) node;
                    }
                }
            });


            //Create line
            Line line = new Line();
            line.setStroke(Color.RED);
            line.setStrokeWidth(3);


            //Bind the starting point coordinate of the line with the center coordinate of node device
            line.startXProperty().bind(device.layoutXProperty().add(device.widthProperty().divide(2)));
            line.startYProperty().bind(device.layoutYProperty().add(device.heightProperty().divide(2)));

            //Bind the end coordinates of the line with the center coordinates of node deviceEnd
            line.endXProperty().bind(deviceEnd.layoutXProperty().add(deviceEnd.widthProperty().divide(2)));
            line.endYProperty().bind(deviceEnd.layoutYProperty().add(deviceEnd.heightProperty().divide(2)));

            nameDev1 = selectNameDevice(device);
            nameDev2 = selectNameDevice(deviceEnd);

            connects.add(new CableDevice(device, deviceEnd, line, nameDev1, nameDev2, this.latencyField.getText()));
            workStation.getChildren().add(line);

            this.formStage.close();
        };
    }

    // Reset form values if canceled
    protected EventHandler<MouseEvent> onCancelBtnClicked() {
        return event -> {
            this.cbx.setValue(this.cable.getDestinyId());
            this.latencyField.setText(this.cable.getLatency());
            this.formStage.close();
        };
    }

    protected EventHandler<MouseEvent> onCancelRandomTrfBtnClicked() {
        return event -> {
            //this.cbx.setValue(this.cable.getDestinyId());
            //this.latencyField.setText(this.cable.getLatency());
            this.formStage.close();
        };
    }

    protected EventHandler<MouseEvent> onCancelRandomBtnClicked() {
        return event -> {
            this.timeExeField.setText("");
            this.formStage.close();
        };
    }

    protected void launchFormTraffic() {


        ObservableList<String> items = FXCollections.observableArrayList();
        addDestiny(items);
        //addTraffic(items);
        //items.stream().distinct();

        GridPane gridPane = new GridPane();
        gridPane.setPadding(new Insets(10));
        gridPane.setVgap(10);
        gridPane.setHgap(5);
        gridPane.setAlignment(Pos.CENTER);

        //Arranging all the nodes in the grid
        gridPane.add(new Text("Device        "), 0, 0);
        gridPane.add(cbx = new ComboBox<>(items), 1, 0);
        gridPane.add(new Text("# Traffic"), 0, 1);
        gridPane.add(trafficField, 1, 1);

        Button okBtn = new Button("ok");
        okBtn.getStyleClass().add("okbtn");
        okBtn.setOnMouseClicked(this.onOkBtnTrafficClicked());

        Button cancelBtn = new Button("Cancel");
        cancelBtn.getStyleClass().add("cancelbtn");
        cancelBtn.setOnMouseClicked(this.onCancelBtnClicked());

        HBox btnContainer = new HBox(cancelBtn, okBtn);
        btnContainer.setSpacing(10);
        btnContainer.setAlignment(Pos.CENTER_RIGHT);
        gridPane.add(btnContainer, 1, 3);
        this.formScene = new Scene(gridPane);

        final Stage primaryStage = (Stage) this.getScene().getWindow();
        formStage = new Stage();
        // formStage.setOnCloseRequest(beforeCloseFormStage());
        formScene.getStylesheets().addAll("file:src/main/resources/style/FormStyle.css");
        formStage.setScene(formScene);
        formStage.initModality(Modality.WINDOW_MODAL);
        formStage.initOwner(primaryStage);
        formStage.setResizable(false);
        formStage.setTitle("Traffic Devices ");
        formStage.showAndWait();
        formStage.close();
        items.clear();
        //


    }

    protected void launchFormRandomTraffic() {


        ObservableList<String> items = FXCollections.observableArrayList();
        addDestiny(items);
        //addTraffic(items);
        //items.stream().distinct();

        GridPane gridPane = new GridPane();
        gridPane.setPadding(new Insets(10));
        gridPane.setVgap(10);
        gridPane.setHgap(5);
        gridPane.setAlignment(Pos.CENTER);
        //trafficRField1.setPrefWidth(30);
        //trafficRField2.setPrefWidth(120);
        //trafficRField1.setMaxWidth(40);
        //trafficRField2.setMaxWidth(40);
        //Arranging all the nodes in the grid
        gridPane.add(new Text("Device        "), 0, 0);
        gridPane.add(cbx = new ComboBox<>(items), 1, 0);
        gridPane.add(new Text("# Set Traffic"), 0, 1);

        gridPane.add(trafficRField1, 1, 1);
        gridPane.add(new Text("to"), 2, 1);
        gridPane.add(trafficRField2, 3, 1);

        gridPane.add(new Text("# Set Time (sec)"), 0, 2);
        gridPane.add(timeDeviceField, 1, 2);
        gridPane.add(new Text(""), 0, 3);

        Button okBtn = new Button("ok");
        okBtn.getStyleClass().add("okbtn");
        okBtn.setOnMouseClicked(this.onOkBtnTrafficRandomClicked());

        Button cancelBtn = new Button("Cancel");
        cancelBtn.getStyleClass().add("cancelbtn");
        cancelBtn.setOnMouseClicked(this.onCancelRandomTrfBtnClicked());

        HBox btnContainer = new HBox(cancelBtn, okBtn);
        btnContainer.setSpacing(10);
        btnContainer.setAlignment(Pos.CENTER_RIGHT);
        gridPane.add(btnContainer, 1, 3);
        this.formScene = new Scene(gridPane);

        final Stage primaryStage = (Stage) this.getScene().getWindow();
        formStage = new Stage();
        // formStage.setOnCloseRequest(beforeCloseFormStage());
        formScene.getStylesheets().addAll("file:src/main/resources/style/FormStyle.css");
        formStage.setScene(formScene);
        formStage.initModality(Modality.WINDOW_MODAL);
        formStage.initOwner(primaryStage);
        formStage.setResizable(false);
        formStage.setTitle("Traffic Devices Random");
        formStage.showAndWait();
        formStage.close();
        items.clear();
        //


    }

    protected void launchFormRandomPlay() {

        GridPane gridPane = new GridPane();
        gridPane.setPadding(new Insets(10));
        gridPane.setVgap(10);
        gridPane.setHgap(5);
        gridPane.setAlignment(Pos.CENTER);

        //Arranging all the nodes in the grid
        gridPane.add(new Text("# Set Time Execution (min)"), 0, 1);
        gridPane.add(timeExeField, 1, 1);

        Button okBtn = new Button("ok");
        okBtn.getStyleClass().add("okbtn");
        okBtn.setOnMouseClicked(this.onOkBtnRandomPlayClicked());

        Button cancelBtn = new Button("Cancel");
        cancelBtn.getStyleClass().add("cancelbtn");
        cancelBtn.setOnMouseClicked(this.onCancelRandomBtnClicked());

        HBox btnContainer = new HBox(cancelBtn, okBtn);
        btnContainer.setSpacing(10);
        btnContainer.setAlignment(Pos.CENTER_RIGHT);
        gridPane.add(btnContainer, 1, 3);
        this.formScene = new Scene(gridPane);

        final Stage primaryStage = (Stage) this.getScene().getWindow();
        formStage = new Stage();
        // formStage.setOnCloseRequest(beforeCloseFormStage());
        formScene.getStylesheets().addAll("file:src/main/resources/style/FormStyle.css");
        formStage.setScene(formScene);
        formStage.initModality(Modality.WINDOW_MODAL);
        formStage.initOwner(primaryStage);
        formStage.setResizable(false);
        formStage.setTitle("Traffic Random Devices ");
        formStage.showAndWait();
        formStage.close();

        //


    }

    private void animation1(ImageView img, Device deviceStart, Device deviceEnd) {
        TranslateTransition tt = new TranslateTransition(Duration.millis(2000));
        tt.setNode(img);
        tt.setByX(deviceEnd.getLayoutX() - deviceStart.getLayoutX());
        tt.setByY(deviceEnd.getLayoutY() - deviceStart.getLayoutY());
        tt.setInterpolator(Interpolator.LINEAR);
        tt.setCycleCount(2);
        tt.setAutoReverse(true);
        tt.play();
    }


    public void addDestiny(ObservableList<String> items) {

        workStation.getChildren().forEach((node) -> {
            if (node instanceof ServerDevice) {
                ServerModel model = (ServerModel) ((ServerDevice) node).getModel();
                items.add(model.getName());

            } else if (node instanceof SensorDevice) {
                SensorModel model = (SensorModel) ((SensorDevice) node).getModel();
                items.add(model.getName());
            } else if (node instanceof ActuatorDevice) {
                ActuatorModel model = (ActuatorModel) ((ActuatorDevice) node).getModel();
                items.add(model.getName());
            }
        });

    }


    protected EventHandler onOkBtnTrafficClicked() {
        //final int[] i = {1};
        //System.out.println("Node" + i[0]);
        return event -> {
            slots.add(Integer.parseInt(trafficField.getText()));
            String valDevice;
            valDevice = cbx.getValue();
            ArrayList<Device> mList = new ArrayList<>();
            connects.forEach((item) -> {
                mList.add(item.getDevice1());
                mList.add(item.getDevice2());
            });


            Device deviceTraf = devInstance(valDevice);
            int countA = Collections.frequency(mList, deviceTraf);
            if (countA != 0) {
                connects.forEach((itDev) -> {
                    if (itDev.getDevice1() == deviceTraf) {

                        this.traffic = this.trafficField.getText();
                        //System.out.printf("%s\n",this.traffic);
                        ImageView rect = new ImageView("file:src/main/resources/image/email1.png");
                        imgTraffics.add(rect);
                        rect.setX(itDev.getDevice1().getLayoutX());
                        rect.setY(itDev.getDevice1().getLayoutY());

                        this.trimg = rect;
                        workStation.getChildren().add(rect);

                        /***********/
                        animation1(rect, itDev.getDevice1(), itDev.getDevice2());


                    }

                    if (itDev.getDevice2() == deviceTraf) {
                        this.traffic = this.trafficField.getText();
                        //System.out.printf("%s\n",this.traffic);
                        ImageView rect = new ImageView("file:src/main/resources/image/email1.png");
                        imgTraffics.add(rect);
                        rect.setX(itDev.getDevice2().getLayoutX());
                        rect.setY(itDev.getDevice2().getLayoutY());

                        this.trimg = rect;

                        workStation.getChildren().add(rect);

                        /***********/
                        animation1(rect, itDev.getDevice2(), itDev.getDevice1());
                    }

                });

            } else {
                System.out.println("Device is not in traffic");
                slots.remove(Integer.parseInt(trafficField.getText()));
            }
            trafficField.setText("");
            this.formStage.close();
        };
    }

    protected EventHandler onOkBtnTrafficRandomClicked() {
        //final int[] i = {1};
        //System.out.println("Node" + i[0]);
        return event -> {
            //slots.add(Integer.parseInt(trafficField.getText()));
            DeviceTraffic dvt = new DeviceTraffic(Integer.parseInt(this.trafficRField1.getText()), Integer.parseInt(this.trafficRField2.getText()), Integer.parseInt(this.timeDeviceField.getText()));
            String valDevice;
            valDevice = cbx.getValue();
            this.trfDevice.put(valDevice, dvt);

            //System.out.println(trfDevice);
            ArrayList<Device> mList = new ArrayList<>();
            connects.forEach((item) -> {
                mList.add(item.getDevice1());
                mList.add(item.getDevice2());
            });


            Device deviceTraf = devInstance(valDevice);
            int countA = Collections.frequency(mList, deviceTraf);
            if (countA != 0) {
                connects.forEach((itDev) -> {
                    if (itDev.getDevice1() == deviceTraf) {

                        this.traffic = this.trafficField.getText();
                        //System.out.printf("%s\n",this.traffic);
                        ImageView rect = new ImageView("file:src/main/resources/image/digital.png");
                        imgTraffics.add(rect);
                        rect.setX(itDev.getDevice1().getLayoutX());
                        rect.setY(itDev.getDevice1().getLayoutY());

                        this.trimg = rect;
                        workStation.getChildren().add(rect);

                        /***********/
                        animation1(rect, itDev.getDevice1(), itDev.getDevice2());


                    }

                    if (itDev.getDevice2() == deviceTraf) {
                        this.traffic = this.trafficField.getText();
                        //System.out.printf("%s\n",this.traffic);
                        ImageView rect = new ImageView("file:src/main/resources/image/digital.png");
                        imgTraffics.add(rect);
                        rect.setX(itDev.getDevice2().getLayoutX());
                        rect.setY(itDev.getDevice2().getLayoutY());

                        this.trimg = rect;

                        workStation.getChildren().add(rect);

                        /***********/
                        animation1(rect, itDev.getDevice2(), itDev.getDevice1());
                    }

                });

            } else {
                System.out.println("Device is not in traffic");
                this.trfDevice.remove(valDevice);
            }
            trafficRField1.setText("");
            trafficRField2.setText("");
            timeDeviceField.setText("");
            this.formStage.close();
        };
    }

    protected EventHandler onOkBtnRandomPlayClicked() {
        return event -> {
            this.timeTraffic = (Integer.parseInt(timeExeField.getText())) * 60;
            this.isSetTime = true;
            this.formStage.close();
        };

    }

    public Device devInstance(String nameDev) {
        Device devTr = null;
        for (Node node : workStation.getChildren()) {
            if (node instanceof ServerDevice) {
                ServerModel model = (ServerModel) ((ServerDevice) node).getModel();
                if (model.getName().equals(nameDev)) {
                    devTr = (Device) node;
                    break;
                }

            } else if (node instanceof SensorDevice) {
                SensorModel model = (SensorModel) ((SensorDevice) node).getModel();
                if (model.getName().equals(nameDev)) {
                    devTr = (Device) node;
                    break;
                }
            } else if (node instanceof ActuatorDevice) {
                ActuatorModel model = (ActuatorModel) ((ActuatorDevice) node).getModel();
                if (model.getName().equals(nameDev)) {
                    devTr = (Device) node;
                    break;
                }
            }
        }

        return devTr;
    }

    public void removeElementList(ObservableList<String> items, Device device) {
        if (device instanceof ServerDevice) {
            ServerModel model = (ServerModel) ((ServerDevice) device).getModel();
            //cable.setName(model.getName());
            items.remove(model.getName());
        } else if (device instanceof SensorDevice) {
            SensorModel model = (SensorModel) ((SensorDevice) device).getModel();
            items.remove(model.getName());
            //cable.setName(model.getName());
        } else if (device instanceof ActuatorDevice) {
            ActuatorModel model = (ActuatorModel) ((ActuatorDevice) device).getModel();
            items.remove(model.getName());
            //cable.setName(model.getName());
        }
    }

    /*
     *  Menu Options Sensor Devices
     */

    protected void launchFormSensors() {

        GridPane gridPane = new GridPane();
        gridPane.setPadding(new Insets(10));
        gridPane.setVgap(10);
        gridPane.setHgap(5);
        gridPane.setAlignment(Pos.CENTER);

        //Arranging all the nodes in the grid
        gridPane.add(new Text("Home        "), 0, 0);
        gridPane.add(btnAirPurifier, 0, 1);
        btnAirPurifier.setTooltip(new Tooltip("Air Purifier"));
        gridPane.add(btnCameraHome, 1, 1);
        btnCameraHome.setTooltip(new Tooltip("Camera Home"));
        gridPane.add(btnMotionSensor, 2, 1);
        btnMotionSensor.setTooltip(new Tooltip("Motion Sensor"));
        gridPane.add(btnLightSensor, 3, 1);
        btnLightSensor.setTooltip(new Tooltip("Light Sensor"));


        gridPane.add(new Text("City        "), 0, 2);
        gridPane.add(btnFingerPrint, 0, 3);
        btnFingerPrint.setTooltip(new Tooltip("Finger Print Scan"));


        gridPane.add(new Text("Health        "), 0, 4);
        gridPane.add(btnCardiogram, 0, 5);
        btnCardiogram.setTooltip(new Tooltip("ECG Monitor"));
        gridPane.add(btnPulseOximeter, 1, 5);
        btnPulseOximeter.setTooltip(new Tooltip("Pulse Oximeter"));
        gridPane.add(btnGlucometer, 2, 5);
        btnGlucometer.setTooltip(new Tooltip("Glucometer"));
        gridPane.add(btnSmartTemperature, 3, 5);
        btnSmartTemperature.setTooltip(new Tooltip("Smart Temperature"));
        gridPane.add(btnSmartwatch, 4, 5);
        btnSmartwatch.setTooltip(new Tooltip("Smartwatch"));

        gridPane.add(new Text("Agriculture        "), 0, 6);
        gridPane.add(btnLightMeter, 0, 7);
        btnLightMeter.setTooltip(new Tooltip("Light Meter"));

        gridPane.add(new Text("Transport        "), 0, 9);
        gridPane.add(btnDriverlessCar, 0, 10);
        btnDriverlessCar.setTooltip(new Tooltip("DriverlessCar"));
        gridPane.add(btnChargingStation, 1, 10);
        btnChargingStation.setTooltip(new Tooltip("Charging Station"));

        //buttons event
        btnAirPurifier.setOnMouseClicked(this.onAirPurifierBtnClicked());
        btnCameraHome.setOnMouseClicked(this.onCameraHomeBtnClicked());
        btnMotionSensor.setOnMouseClicked(this.onMotionSensorBtnClicked());
        btnLightSensor.setOnMouseClicked(this.onLightSensorBtnClicked());
        btnFingerPrint.setOnMouseClicked(this.onFingerPrintBtnClicked());
        btnCardiogram.setOnMouseClicked(this.onCardiogramBtnClicked());
        btnPulseOximeter.setOnMouseClicked(this.onPulseOximeterBtnClicked());
        btnGlucometer.setOnMouseClicked(this.onGlucometerBtnClicked());
        btnSmartTemperature.setOnMouseClicked(this.onSmartTemperatureBtnClicked());
        btnSmartwatch.setOnMouseClicked(this.onbtnSmartwatchBtnClicked());
        btnLightMeter.setOnMouseClicked(this.onLightMeterBtnClicked());
        btnDriverlessCar.setOnMouseClicked(this.onDriverlessCarBtnClicked());
        btnChargingStation.setOnMouseClicked(this.onChargingStationBtnClicked());


        this.formScene = new Scene(gridPane);

        final Stage primaryStage = (Stage) this.getScene().getWindow();
        formStage = new Stage();
        // formStage.setOnCloseRequest(beforeCloseFormStage());
        formScene.getStylesheets().addAll("file:src/main/resources/style/FormStyle.css");
        formStage.setScene(formScene);
        formStage.initModality(Modality.WINDOW_MODAL);
        formStage.initOwner(primaryStage);
        formStage.setResizable(false);
        formStage.setTitle("Sensor Devices");
        formStage.showAndWait();
        formStage.close();

    }


    /**
     * EVENTS BUTTONS, CREATE SENSOR DEVICES
     *
     * @return
     */


    protected EventHandler<MouseEvent> onCameraHomeBtnClicked() {
        return event -> {
            byte number = workStation.getNumberOfSensors();
            SensorModel sensorModel = new SensorModel("SN" + number, "Sensor" + number, 1);
            Device device = new SensorDevice(sensorModel, new ImageView(Icon.SN_CCTV.src));
            device.setOnConnectEvent(onConnectEvent(device));
            device.setOnDeleteEvent(onDeleteDevice(device));
            workStation.getChildren().add(device);
        };
    }

    protected EventHandler<MouseEvent> onAirPurifierBtnClicked() {
        return event -> {
            byte number = workStation.getNumberOfSensors();
            SensorModel sensorModel = new SensorModel("SN" + number, "Sensor" + number, 2);
            Device device = new SensorDevice(sensorModel, new ImageView(Icon.SN_PURIFIER.src));
            device.setOnConnectEvent(onConnectEvent(device));
            device.setOnDeleteEvent(onDeleteDevice(device));
            workStation.getChildren().add(device);
        };
    }

    protected EventHandler<MouseEvent> onMotionSensorBtnClicked() {
        return event -> {
            byte number = workStation.getNumberOfSensors();
            SensorModel sensorModel = new SensorModel("SN" + number, "Sensor" + number, 3);
            Device device = new SensorDevice(sensorModel, new ImageView(Icon.SN_MOTION.src));
            device.setOnConnectEvent(onConnectEvent(device));
            device.setOnDeleteEvent(onDeleteDevice(device));
            workStation.getChildren().add(device);
        };
    }


    protected EventHandler<MouseEvent> onLightSensorBtnClicked() {
        return event -> {
            byte number = workStation.getNumberOfSensors();
            SensorModel sensorModel = new SensorModel("SN" + number, "Sensor" + number, 4);
            Device device = new SensorDevice(sensorModel, new ImageView(Icon.SN_LIGHT_SENSOR.src));
            device.setOnConnectEvent(onConnectEvent(device));
            device.setOnDeleteEvent(onDeleteDevice(device));
            workStation.getChildren().add(device);
        };
    }


    protected EventHandler<MouseEvent> onFingerPrintBtnClicked() {
        return event -> {
            byte number = workStation.getNumberOfSensors();
            SensorModel sensorModel = new SensorModel("SN" + number, "Sensor" + number, 5);
            Device device = new SensorDevice(sensorModel, new ImageView(Icon.SN_FINGERPRINT.src));
            device.setOnConnectEvent(onConnectEvent(device));
            device.setOnDeleteEvent(onDeleteDevice(device));
            workStation.getChildren().add(device);
        };
    }

    protected EventHandler<MouseEvent> onCardiogramBtnClicked() {
        return event -> {
            byte number = workStation.getNumberOfSensors();
            SensorModel sensorModel = new SensorModel("SN" + number, "Sensor" + number, 6);
            Device device = new SensorDevice(sensorModel, new ImageView(Icon.SN_CARDIOGRAM.src));
            device.setOnConnectEvent(onConnectEvent(device));
            device.setOnDeleteEvent(onDeleteDevice(device));
            workStation.getChildren().add(device);
        };
    }

    protected EventHandler<MouseEvent> onPulseOximeterBtnClicked() {
        return event -> {
            byte number = workStation.getNumberOfSensors();
            SensorModel sensorModel = new SensorModel("SN" + number, "Sensor" + number, 7);
            Device device = new SensorDevice(sensorModel, new ImageView(Icon.SN_OXIMETER.src));
            device.setOnConnectEvent(onConnectEvent(device));
            device.setOnDeleteEvent(onDeleteDevice(device));
            workStation.getChildren().add(device);
        };
    }


    protected EventHandler<MouseEvent> onGlucometerBtnClicked() {
        return event -> {
            byte number = workStation.getNumberOfSensors();
            SensorModel sensorModel = new SensorModel("SN" + number, "Sensor" + number, 8);
            Device device = new SensorDevice(sensorModel, new ImageView(Icon.SN_GLUCOMETER.src));
            device.setOnConnectEvent(onConnectEvent(device));
            device.setOnDeleteEvent(onDeleteDevice(device));
            workStation.getChildren().add(device);
        };
    }

    protected EventHandler<MouseEvent> onSmartTemperatureBtnClicked() {
        return event -> {
            byte number = workStation.getNumberOfSensors();
            SensorModel sensorModel = new SensorModel("SN" + number, "Sensor" + number, 9);
            Device device = new SensorDevice(sensorModel, new ImageView(Icon.SN_TEMPERATURE.src));
            device.setOnConnectEvent(onConnectEvent(device));
            device.setOnDeleteEvent(onDeleteDevice(device));
            workStation.getChildren().add(device);
        };
    }

    protected EventHandler<MouseEvent> onbtnSmartwatchBtnClicked() {
        return event -> {
            byte number = workStation.getNumberOfSensors();
            SensorModel sensorModel = new SensorModel("SN" + number, "Sensor" + number, 10);
            Device device = new SensorDevice(sensorModel, new ImageView(Icon.SN_SMARTWATCH.src));
            device.setOnConnectEvent(onConnectEvent(device));
            device.setOnDeleteEvent(onDeleteDevice(device));
            workStation.getChildren().add(device);
        };
    }

    protected EventHandler<MouseEvent> onLightMeterBtnClicked() {
        return event -> {
            byte number = workStation.getNumberOfSensors();
            SensorModel sensorModel = new SensorModel("SN" + number, "Sensor" + number, 11);
            Device device = new SensorDevice(sensorModel, new ImageView(Icon.SN_lIGHT_METER.src));
            device.setOnConnectEvent(onConnectEvent(device));
            device.setOnDeleteEvent(onDeleteDevice(device));
            workStation.getChildren().add(device);
        };
    }

    protected EventHandler<MouseEvent> onDriverlessCarBtnClicked() {
        return event -> {
            byte number = workStation.getNumberOfSensors();
            SensorModel sensorModel = new SensorModel("SN" + number, "Sensor" + number, 12);
            Device device = new SensorDevice(sensorModel, new ImageView(Icon.SN_DIVERLESS.src));
            device.setOnConnectEvent(onConnectEvent(device));
            device.setOnDeleteEvent(onDeleteDevice(device));
            workStation.getChildren().add(device);
        };
    }

    protected EventHandler<MouseEvent> onChargingStationBtnClicked() {
        return event -> {
            byte number = workStation.getNumberOfSensors();
            SensorModel sensorModel = new SensorModel("SN" + number, "Sensor" + number, 13);
            Device device = new SensorDevice(sensorModel, new ImageView(Icon.SN_CHARGIN_STATION.src));
            device.setOnConnectEvent(onConnectEvent(device));
            device.setOnDeleteEvent(onDeleteDevice(device));
            workStation.getChildren().add(device);
        };
    }

}
