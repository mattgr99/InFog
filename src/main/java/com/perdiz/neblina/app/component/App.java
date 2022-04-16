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
import com.perdiz.neblina.app.controller.device.CableDeviceController;
import com.perdiz.neblina.app.iu.Device;
import com.perdiz.neblina.app.resource.Icon;
import com.perdiz.neblina.charts.BarChartTraffic;
import com.perdiz.neblina.charts.LineChartTraffic;
import com.perdiz.neblina.heuristics.DeviceTraffic;
import com.perdiz.neblina.heuristics.DeviceTrafficRandom;
import com.perdiz.neblina.heuristics.TrafficHeuristc;
import com.perdiz.neblina.heuristics.TrafficRandomHeuristc;
import com.perdiz.neblina.model.*;
import com.perdiz.neblina.util.Pkg;
import javafx.animation.Interpolator;
import javafx.animation.TranslateTransition;
import javafx.beans.InvalidationListener;
import javafx.beans.value.ChangeListener;
import javafx.beans.value.ObservableValue;
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
import javafx.scene.text.Text;
import javafx.stage.Modality;
import javafx.stage.Stage;
import javafx.util.Duration;
//import javafx.animation.transition.*;
import java.util.*;

/**
 * @author alexander
 */
public class App extends AppController {

    private final ToolBar toolBar = new ToolBar();
    private final RightSideBar rightSideBar = new RightSideBar();
    private final LeftSideBar leftSideBar = new LeftSideBar();
    public static WorkStation workStation = new WorkStation();
    protected Stage formStage;
    protected Scene formScene;
    protected TextField latencyField;
    protected TextField trafficField;
    protected TextField trafficRField1;
    protected TextField trafficRField2;
    protected TextField timeExeField;
    protected TextField timeDeviceField;
    //protected CableModel cable;

    public static ArrayList<CableModel> connects = new ArrayList<CableModel>();
    ArrayList<Integer> slots = new ArrayList<Integer>();
    public static Hashtable<String, DeviceTraffic> slotsTrf = new Hashtable<String, DeviceTraffic>();
    public static Hashtable<String, ArrayList<Integer>> serverTrf = new Hashtable<String, ArrayList<Integer>>();
    public static Hashtable<String, DeviceTrafficRandom> trfDevice = new Hashtable<String, DeviceTrafficRandom>();

    //CableDevice cb = new CableDevice();
    ImageView trimg = new ImageView("file:src/main/resources/image/email1.png");
    ArrayList<ImageView> imgTraffics = new ArrayList<ImageView>();
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
        //cable = new CableModel();
        this.initLeftSideBar();
        this.initToolBar();
        this.init();
    }

    private void init() {
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

        footer.addLog("${Ib}", "=> " + Pkg.NAME + "@" + Pkg.VERSION + " running.");

        // Examples of printing the logs
        footer.addLog("${_}", "");
        //footer.addLog("${_} ${Sb_} ${_}", "Agregar servidores para ver la funcionalidad de ", "Log Viewer", "en acción.");
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
            ArrayList<Integer> listr = new ArrayList<>();
            for (Map.Entry<String, DeviceTraffic> von : slotsTrf.entrySet()) {

                DeviceTraffic trafficDev = von.getValue();
                String nameS =  getnameServer(trafficDev.getNameSensor());
                //System.out.println(nameS +" +");
                if (serverTrf.get(nameS)  != null) {
                    ArrayList<Integer> listrServer = serverTrf.get(nameS);
                    listrServer.add(Integer.parseInt(trafficDev.getValTraffic()));
                    serverTrf.put(nameS,listrServer);
                    //System.out.println(serverTrf);
                }else{
                    ArrayList<Integer> listrServer = new ArrayList<>();
                    listrServer.add(Integer.parseInt(trafficDev.getValTraffic()));
                    serverTrf.put(nameS,listrServer);
                    //System.out.println(serverTrf);
                }


                //listr.add(Integer.parseInt(trafficDev.getValTraffic()));
                //pos = von.getKey();

            }

           /* int sizelist = listr.size() - 1;

            for (int i = 0; i < listr.size(); i++) {
                slots.add(listr.get(sizelist));
                sizelist--;
            }*/

            for (Map.Entry<String, ArrayList<Integer>> von : serverTrf.entrySet()) {
                ArrayList<Integer> rmvms = new ArrayList<Integer>();

                ServerModel model = trafficServer(von.getKey());
                //System.out.println(model.getName());
                TrafficHeuristc tf = new TrafficHeuristc((int) model.getVmachines(), von.getValue().size(), model.getName());
                for (int rv : model.getRamVM()) {
                    rmvms.add(rv);

                }
                //System.out.println(rmvms);
                tf.calcLoadServer(von.getValue(), rmvms);
                tf.energyServer();
                von.getValue().clear();
            }

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
                    DeviceTrafficRandom dvt = this.trfDevice.get(nameSensors.get(exe));
                    TrafficRandomHeuristc ner1 = new TrafficRandomHeuristc((int) model.getVmachines(), rmvms, dvt.getTime(), nameSensors.get(exe), finalDisTrf, dvt.getFirstInterval(), dvt.getSecondInterval());
                    trsend.add(ner1);
                }

                /*Enumeration<String> ekey = this.trfDevice.keys();
                ekey = this.trfDevice.keys();
                while(ekey.hasMoreElements()){
                    this.trfDevice.remove(ekey.nextElement());
                }*/

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
                            //footer.addLog("${_}", "\"*********** Process completed ***********");
                            cancel();


                            //footer.addLog("${_}", "*********** Process completed ***********");
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
            Device device = new ServerDevice(new ServerModel("CS" + number, "CloudServer" + number, Byte.parseByte("0")));
            //device.setOnConnectEvent(onConnectEvent(device));
            //device.setOnDeleteEvent(onDeleteDevice(device));
            workStation.getChildren().add(device);
            CableDeviceController.workStation = workStation;
            footer.addLog(addedFormat, "Added =>", "CloudServer" + number);

        });
        this.leftSideBar.setFogServerActionEvent((t) -> {
            byte number = workStation.getNumberOfFogServers();
            ServerModel serverModel = new ServerModel("FS" + number, "FogServer" + number, Byte.parseByte("1"));
            Device device = new ServerDevice(serverModel);
            //device.setOnConnectEvent(onConnectEvent(device));
            //device.setOnDeleteEvent(onDeleteDevice(device));
            workStation.getChildren().add(device);
            CableDeviceController.workStation = workStation;
            footer.addLog(addedFormat, "Added =>", "FogServer" + number);

        });
        this.leftSideBar.setProxyServerActionEvent((t) -> {
            byte number = workStation.getNumberOfProxyServers();
            ServerModel serverModel = new ServerModel("PS" + number, "ProxyServer" + number, Byte.parseByte("2"));
            Device device = new ServerDevice(serverModel);
            //device.setOnConnectEvent(onConnectEvent(device));
            //device.setOnDeleteEvent(onDeleteDevice(device));
            workStation.getChildren().add(device);
            CableDeviceController.workStation = workStation;
            footer.addLog(addedFormat, "Added =>", "ProxyServer" + number);

        });

        this.leftSideBar.setActuatorActionEvent((t) -> {
            byte number = workStation.getNumberOfActuators();
            Device device = new ActuatorDevice(new ActuatorModel("AT" + number, "Actuator" + number));
            //device.setOnConnectEvent(onConnectEvent(device));
            //device.setOnDeleteEvent(onDeleteDevice(device));
            workStation.getChildren().add(device);
            CableDeviceController.workStation = workStation;
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

    protected ServerModel trafficServer(String nameServer) {
        ServerModel model = null;
        for (Node device : workStation.getChildren()) {
            if (device instanceof ServerDevice) {
                model = (ServerModel) ((ServerDevice) device).getModel();
                if (model.getName().equals(nameServer)) {
                    break;
                }

            }
        }

        return model;
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

    protected String getnameServer(String nameSensor) {
        String name = "";
        for (Node device : workStation.getChildren()) {
            if (device instanceof CableDevice) {
                CableModel model = (CableModel) ((CableDevice) device).getModel();
                if ((model.getDev1Name().equals(nameSensor))) {
                    name = model.getDev2Name();
                    break;
                }

                if ((model.getDev2Name().equals(nameSensor))) {
                    name = model.getDev1Name();
                    break;
                }

            }
        }

        return name;
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

    // Reset form values if canceled
    protected EventHandler<MouseEvent> onCancelBtnClicked() {
        return event -> {
            //this.cbx.setValue(this.cable.getDestinyId());
            this.trafficField.setText("");
            this.formStage.close();
        };
    }

    protected EventHandler<MouseEvent> onCancelRandomTrfBtnClicked() {
        return event -> {
            trafficRField1.setText("");
            trafficRField2.setText("");
            timeDeviceField.setText("");
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
        cbx.valueProperty().addListener(onChangeTrafficField());

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


    }

    protected ChangeListener<String> onChangeTrafficField() {
        return (ChangeListener<String>) (ObservableValue<? extends String> observable, String oldValue, String newValue) -> {
            if (newValue != null) {

                if (slotsTrf.get(newValue) != null) {
                    DeviceTraffic dt = slotsTrf.get(newValue);
                    trafficField.setText(dt.getValTraffic());
                } else {
                    trafficField.setText("");
                }

            }
            //System.out.println("Cambio contenido");
        };
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
        cbx.valueProperty().addListener(onChangeTrafficRandomField());

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


    }

    protected ChangeListener<String> onChangeTrafficRandomField() {
        return (ChangeListener<String>) (ObservableValue<? extends String> observable, String oldValue, String newValue) -> {
            if (newValue != null) {

                if (trfDevice.get(newValue) != null) {
                    DeviceTrafficRandom dtr = trfDevice.get(newValue);
                    trafficRField1.setText("" + dtr.getFirstInterval());
                    trafficRField2.setText("" + dtr.getSecondInterval());
                    timeDeviceField.setText("" + dtr.getTime());
                } else {
                    trafficRField1.setText("");
                    trafficRField2.setText("");
                    timeDeviceField.setText("");
                }

            }
            //System.out.println("Cambio contenido");
        };
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
                DeviceTraffic trfInstance = new DeviceTraffic(trafficField.getText(), cbx.getValue());
                slotsTrf.put(cbx.getValue(), trfInstance);

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
                //slots.remove(Integer.parseInt(trafficField.getText()));
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
            DeviceTrafficRandom dvt = new DeviceTrafficRandom(Integer.parseInt(this.trafficRField1.getText()), Integer.parseInt(this.trafficRField2.getText()), Integer.parseInt(this.timeDeviceField.getText()));
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
            String addedFormat = "${Ib_} ${}";
            byte number = workStation.getNumberOfSensors();
            SensorModel sensorModel = new SensorModel("SN" + number, "Sensor" + number, 1);
            Device device = new SensorDevice(sensorModel, new ImageView(Icon.SN_CCTV.src));
            workStation.getChildren().add(device);
            footer.addLog(addedFormat, "Added =>", "Sensor" + number);
            CableDeviceController.workStation = workStation;
        };
    }

    protected EventHandler<MouseEvent> onAirPurifierBtnClicked() {
        return event -> {
            byte number = workStation.getNumberOfSensors();
            SensorModel sensorModel = new SensorModel("SN" + number, "Sensor" + number, 2);
            Device device = new SensorDevice(sensorModel, new ImageView(Icon.SN_PURIFIER.src));
            workStation.getChildren().add(device);
            String addedFormat = "${Ib_} ${}";
            footer.addLog(addedFormat, "Added =>", "Sensor" + number);
            CableDeviceController.workStation = workStation;
        };
    }

    protected EventHandler<MouseEvent> onMotionSensorBtnClicked() {
        return event -> {
            byte number = workStation.getNumberOfSensors();
            SensorModel sensorModel = new SensorModel("SN" + number, "Sensor" + number, 3);
            Device device = new SensorDevice(sensorModel, new ImageView(Icon.SN_MOTION.src));
            String addedFormat = "${Ib_} ${}";
            footer.addLog(addedFormat, "Added =>", "Sensor" + number);
            CableDeviceController.workStation = workStation;
            workStation.getChildren().add(device);
        };
    }


    protected EventHandler<MouseEvent> onLightSensorBtnClicked() {
        return event -> {
            byte number = workStation.getNumberOfSensors();
            SensorModel sensorModel = new SensorModel("SN" + number, "Sensor" + number, 4);
            Device device = new SensorDevice(sensorModel, new ImageView(Icon.SN_LIGHT_SENSOR.src));
            String addedFormat = "${Ib_} ${}";
            footer.addLog(addedFormat, "Added =>", "Sensor" + number);
            CableDeviceController.workStation = workStation;
            workStation.getChildren().add(device);
        };
    }


    protected EventHandler<MouseEvent> onFingerPrintBtnClicked() {
        return event -> {
            byte number = workStation.getNumberOfSensors();
            SensorModel sensorModel = new SensorModel("SN" + number, "Sensor" + number, 5);
            Device device = new SensorDevice(sensorModel, new ImageView(Icon.SN_FINGERPRINT.src));
            String addedFormat = "${Ib_} ${}";
            footer.addLog(addedFormat, "Added =>", "Sensor" + number);
            CableDeviceController.workStation = workStation;
            workStation.getChildren().add(device);
        };
    }

    protected EventHandler<MouseEvent> onCardiogramBtnClicked() {
        return event -> {
            byte number = workStation.getNumberOfSensors();
            SensorModel sensorModel = new SensorModel("SN" + number, "Sensor" + number, 6);
            Device device = new SensorDevice(sensorModel, new ImageView(Icon.SN_CARDIOGRAM.src));
            String addedFormat = "${Ib_} ${}";
            footer.addLog(addedFormat, "Added =>", "Sensor" + number);
            CableDeviceController.workStation = workStation;
            workStation.getChildren().add(device);
        };
    }

    protected EventHandler<MouseEvent> onPulseOximeterBtnClicked() {
        return event -> {
            byte number = workStation.getNumberOfSensors();
            SensorModel sensorModel = new SensorModel("SN" + number, "Sensor" + number, 7);
            Device device = new SensorDevice(sensorModel, new ImageView(Icon.SN_OXIMETER.src));
            String addedFormat = "${Ib_} ${}";
            footer.addLog(addedFormat, "Added =>", "Sensor" + number);
            CableDeviceController.workStation = workStation;
            workStation.getChildren().add(device);
        };
    }


    protected EventHandler<MouseEvent> onGlucometerBtnClicked() {
        return event -> {
            byte number = workStation.getNumberOfSensors();
            SensorModel sensorModel = new SensorModel("SN" + number, "Sensor" + number, 8);
            Device device = new SensorDevice(sensorModel, new ImageView(Icon.SN_GLUCOMETER.src));
            String addedFormat = "${Ib_} ${}";
            footer.addLog(addedFormat, "Added =>", "Sensor" + number);
            CableDeviceController.workStation = workStation;
            workStation.getChildren().add(device);
        };
    }

    protected EventHandler<MouseEvent> onSmartTemperatureBtnClicked() {
        return event -> {
            byte number = workStation.getNumberOfSensors();
            SensorModel sensorModel = new SensorModel("SN" + number, "Sensor" + number, 9);
            Device device = new SensorDevice(sensorModel, new ImageView(Icon.SN_TEMPERATURE.src));
            String addedFormat = "${Ib_} ${}";
            footer.addLog(addedFormat, "Added =>", "Sensor" + number);
            CableDeviceController.workStation = workStation;
            workStation.getChildren().add(device);
        };
    }

    protected EventHandler<MouseEvent> onbtnSmartwatchBtnClicked() {
        return event -> {
            byte number = workStation.getNumberOfSensors();
            SensorModel sensorModel = new SensorModel("SN" + number, "Sensor" + number, 10);
            Device device = new SensorDevice(sensorModel, new ImageView(Icon.SN_SMARTWATCH.src));
            String addedFormat = "${Ib_} ${}";
            footer.addLog(addedFormat, "Added =>", "Sensor" + number);
            CableDeviceController.workStation = workStation;
            workStation.getChildren().add(device);
        };
    }

    protected EventHandler<MouseEvent> onLightMeterBtnClicked() {
        return event -> {
            byte number = workStation.getNumberOfSensors();
            SensorModel sensorModel = new SensorModel("SN" + number, "Sensor" + number, 11);
            Device device = new SensorDevice(sensorModel, new ImageView(Icon.SN_lIGHT_METER.src));
            String addedFormat = "${Ib_} ${}";
            footer.addLog(addedFormat, "Added =>", "Sensor" + number);
            CableDeviceController.workStation = workStation;
            workStation.getChildren().add(device);
        };
    }

    protected EventHandler<MouseEvent> onDriverlessCarBtnClicked() {
        return event -> {
            byte number = workStation.getNumberOfSensors();
            SensorModel sensorModel = new SensorModel("SN" + number, "Sensor" + number, 12);
            Device device = new SensorDevice(sensorModel, new ImageView(Icon.SN_DIVERLESS.src));
            String addedFormat = "${Ib_} ${}";
            footer.addLog(addedFormat, "Added =>", "Sensor" + number);
            CableDeviceController.workStation = workStation;
            workStation.getChildren().add(device);
        };
    }

    protected EventHandler<MouseEvent> onChargingStationBtnClicked() {
        return event -> {
            byte number = workStation.getNumberOfSensors();
            SensorModel sensorModel = new SensorModel("SN" + number, "Sensor" + number, 13);
            Device device = new SensorDevice(sensorModel, new ImageView(Icon.SN_CHARGIN_STATION.src));
            String addedFormat = "${Ib_} ${}";
            footer.addLog(addedFormat, "Added =>", "Sensor" + number);
            CableDeviceController.workStation = workStation;
            workStation.getChildren().add(device);
        };
    }

}