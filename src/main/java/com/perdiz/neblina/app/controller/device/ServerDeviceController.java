/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.perdiz.neblina.app.controller.device;

import com.perdiz.neblina.app.iu.Device;
import com.perdiz.neblina.app.iu.NumberField;
import com.perdiz.neblina.app.resource.Icon;
import com.perdiz.neblina.model.ServerModel;

import javafx.beans.value.ChangeListener;
import javafx.beans.value.ObservableValue;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;

import javafx.event.EventHandler;
import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.Scene;

import javafx.scene.control.*;
import javafx.scene.input.InputMethodEvent;

import javafx.scene.input.MouseEvent;
import javafx.scene.layout.GridPane;
import javafx.scene.text.Text;
import javafx.stage.Modality;
import javafx.stage.Stage;
import javafx.stage.WindowEvent;

import java.util.ArrayList;

/**
 * @author alexander
 */
public class ServerDeviceController extends Device {

    protected ServerModel model;
    protected boolean isOpen;
    protected Stage formStage1;
    protected Scene formScene1;

    protected TextField nameField;
    protected NumberField uplinkField;
    protected NumberField downlinkField;
    protected NumberField vmField;
    protected NumberField ramField;
    protected NumberField rateField;
    //ComboBox<String> cbx;
    protected ArrayList<ComboBox<String>> listVM = new ArrayList<ComboBox<String>>();;
    protected boolean vmL = false;
    protected ArrayList<Integer> ramModel = new ArrayList<Integer>();
    protected ArrayList<Integer> ramVMs = new ArrayList<Integer>();
    protected Text ramVMText = new Text("RAM VM (Mb)");
    protected Double ramCompare = -1.0;
    protected  ToggleGroup group = new ToggleGroup();
    protected RadioButton rb1;
    protected RadioButton rb2 ;
    protected RadioButton rb3 ;
    protected RadioButton rb4 ;
    protected RadioButton rb5 ;
    protected RadioButton rb6 ;
    protected RadioButton rb7 ;
    protected RadioButton rb8 ;
    protected RadioButton rb9 ;
    protected RadioButton rb10;
    protected ComboBox<Integer> mipsCbx;
    ObservableList<Integer> itemsMips = FXCollections.observableArrayList();
    protected final Slider rateSlider = new Slider(/*512, 8192, 65536*/);


    public ServerDeviceController(ServerModel serverModel) {

        super(serverModel, serverModel.getLevel() );

        this.model = serverModel;
        // this.listVM = new ArrayList<TextField>();
        /*for (int i = 0; i < this.model.getRamVM().size(); i++){
            TextField ramVM = new TextField(this.model.getRamVM().get(i).toString());
            ramVM.setPrefWidth(80);
            this.listVM.add(ramVM);
        }*/
        this.init();
    }

    public ServerDeviceController(ServerModel serverModel, boolean inicial) {
        super(serverModel, serverModel.getLevel());
        this.model = serverModel;
        this.isOpen = inicial;
        Tooltip.install(
                getIcon(),
                new Tooltip(((int)this.model.getVmachines())+ " VMs\n" + this.model.getRamVM())
        );
        // this.listVM = new ArrayList<TextField>();
        /*for (int i = 0; i < this.model.getRamVM().size(); i++){
            TextField ramVM = new TextField(this.model.getRamVM().get(i).toString());
            ramVM.setPrefWidth(80);
            this.listVM.add(ramVM);
        }*/
        this.init();
    }

    private void init() {
        rateSlider.setShowTickLabels(true);
        rateSlider.setShowTickMarks(true);
        rateSlider.setMin(512);
        rateSlider.setMax(65536);
        rateSlider.setMajorTickUnit(512);
        rateSlider.setMinorTickCount(5);
        rateSlider.setBlockIncrement(512);
        itemsMips.add(512);
        itemsMips.add(1024);
        itemsMips.add(2048);
        itemsMips.add(4096);
        itemsMips.add(8192);
        itemsMips.add(16384);
        itemsMips.add(32768);
        itemsMips.add(65536);
        mipsCbx = new ComboBox<Integer>(itemsMips);

        int optionRAM = (int) model.getRam();
        this.nameField = new TextField(model.getName());
        this.uplinkField = new NumberField(model.getUplink());
        this.downlinkField = new NumberField(model.getDownlink());
        this.vmField = new NumberField(model.getVmachines());
        //this.ramField = new NumberField(model.getRam());
        mipsCbx.setValue((int)model.getRate());

        this.rb1 = new RadioButton("2");
        rb1.setToggleGroup(group);
        this.rb2 = new RadioButton("4");
        rb2.setToggleGroup(group);
        this.rb3 = new RadioButton("8");
        rb3.setToggleGroup(group);
        this.rb4 = new RadioButton("16");
        rb4.setToggleGroup(group);
        this.rb5 = new RadioButton("32");
        rb5.setToggleGroup(group);
        this.rb6 = new RadioButton("64");
        rb6.setToggleGroup(group);
        this.rb7 = new RadioButton("128");
        rb7.setToggleGroup(group);
        this.rb8 = new RadioButton("256");
        rb8.setToggleGroup(group);
        this.rb9 = new RadioButton("512");
        rb9.setToggleGroup(group);
        this.rb10 = new RadioButton("1024");
        rb10.setToggleGroup(group);

        switch (optionRAM){
            case 2:
                this.rb1.setSelected(true);
                break;
            case 4:
                this.rb2.setSelected(true);
                break;
            case 8:
                this.rb3.setSelected(true);
                break;
            case 16:
                this.rb4.setSelected(true);
                break;
            case 32:
                this.rb5.setSelected(true);
                break;
            case 64:
                this.rb6.setSelected(true);
                break;
            case 128:
                this.rb7.setSelected(true);
                break;
            case 256:
                this.rb8.setSelected(true);
                break;
            case 512:
                this.rb9.setSelected(true);
                break;
            case 1024:
                this.rb10.setSelected(true);
                break;
            default:
                this.rb1.setSelected(true);
                break;
        }


        //System.out.println(selectRAM.getText());

        for (int i = 0; i < this.listVM.size(); i++){
            this.listVM.get(i).setValue(this.model.getRamVM().get(i).toString());
        }
        this.ramCompare = (double) this.model.getRamVM().size();
        this.ramVMs=this.model.getRamVM();
    }

    // Reset form values if closed
    @Override
    public EventHandler<WindowEvent> beforeCloseFormStage() {
        return event -> {
            this.nameField.setText(this.model.getName());
            this.uplinkField.setValue(this.model.getUplink());
            this.downlinkField.setValue(this.model.getDownlink());
            this.vmField.setValue(this.model.getVmachines());

            int optionRAM = (int) model.getRam();
            switch (optionRAM){
                case 2:
                    this.rb1.setSelected(true);
                    break;
                case 4:
                    this.rb2.setSelected(true);
                    break;
                case 8:
                    this.rb3.setSelected(true);
                    break;
                case 16:
                    this.rb4.setSelected(true);
                    break;
                case 32:
                    this.rb5.setSelected(true);
                    break;
                case 64:
                    this.rb6.setSelected(true);
                    break;
                case 128:
                    this.rb7.setSelected(true);
                    break;
                case 256:
                    this.rb8.setSelected(true);
                    break;
                case 512:
                    this.rb9.setSelected(true);
                    break;
                case 1024:
                    this.rb10.setSelected(true);
                    break;
                default:
                    this.rb1.setSelected(true);
                    break;
            }
            mipsCbx.setValue((int)model.getRate());
            for (int i = 0; i< this.listVM.size(); i++){
                this.listVM.get(i).setValue(this.model.getRamVM().get(i).toString());
            }
        };
    }

    // Save new form values
    protected EventHandler onOkBtnClicked() {
        return event -> {
            int sumRam =0;
            for (int i = 0; i< this.model.getRamVM().size(); i++){
                sumRam += this.model.getRamVM().get(i);
            }

            RadioButton selectRAM = (RadioButton) this.group.getSelectedToggle();

            if(sumRam<= Integer.parseInt(selectRAM.getText()) *1000){
                this.model.setName(this.nameField.getText());
                this.model.setUplink(this.uplinkField.getValue());
                this.model.setDownlink(this.downlinkField.getValue());
                this.model.setVmachines(this.vmField.getValue());
                this.model.setRam(Integer.parseInt(selectRAM.getText()));
                this.model.setRate(this.mipsCbx.getValue());

                this.nameLbl.setText(this.model.getName());
                this.vmL=false;
                Tooltip.install(
                        getIcon(),
                        new Tooltip(((int)this.model.getVmachines())+ " VMs\n" + this.model.getRamVM())
                );
                this.formStage.close();
            }else{
                this.ramCompare=-1.0;
                Alert alert = new Alert(Alert.AlertType.WARNING);
                alert.setHeaderText(null);
                alert.setTitle("RAM Virtual Machines");
                alert.setContentText("RAM in virtual machines is greater than Server" );
                alert.showAndWait();

            }



        };
    }

    // Reset form values if canceled
    protected EventHandler<MouseEvent> onCancelBtnClicked() {
        return event -> {
            this.nameField.setText(this.model.getName());
            this.uplinkField.setValue(this.model.getUplink());
            this.downlinkField.setValue(this.model.getDownlink());
            this.vmField.setValue(this.model.getVmachines());
            //this.ramField.setValue(this.model.getRam());
            int optionRAM = (int) model.getRam();
            switch (optionRAM){
                case 2:
                    this.rb1.setSelected(true);
                    break;
                case 4:
                    this.rb2.setSelected(true);
                    break;
                case 8:
                    this.rb3.setSelected(true);
                    break;
                case 16:
                    this.rb4.setSelected(true);
                    break;
                case 32:
                    this.rb5.setSelected(true);
                    break;
                case 64:
                    this.rb6.setSelected(true);
                    break;
                case 128:
                    this.rb7.setSelected(true);
                    break;
                case 256:
                    this.rb8.setSelected(true);
                    break;
                case 512:
                    this.rb9.setSelected(true);
                    break;
                case 1024:
                    this.rb10.setSelected(true);
                    break;
                default:
                    this.rb1.setSelected(true);
                    break;
            }
            this.mipsCbx.setValue((int)model.getRate());
            //System.out.println("777777777777777777777777");
            for (int i=0; i < this.listVM.size(); i++){
                this.listVM.get(i).setValue(this.model.getRamVM().get(i).toString());
              //  System.out.println(this.model.getRamVM().get(i).toString());
            }
            this.vmL=true;
           // this.model.etRamVM()
            this.formStage.close();
        };
    }

    protected EventHandler onVMBtnClicked() {
        return event -> {

            //Arranging all the nodes in the grid

            if (vmField.getValue() <= 5 && vmField.getValue()!= 0){

                GridPane gridPane = new GridPane();
                gridPane.setPadding(new Insets(10));
                gridPane.setVgap(12);
                gridPane.setHgap(6);
                gridPane.setAlignment(Pos.CENTER);

                gridPane.add(ramVMText, 0, 0);

                ramVMText.setVisible(true);
               /* for (int item : this.model.getRamVM()){
                    System.out.println(item);
                }*/
                if (this.vmField.getValue() != this.ramCompare){
                    this.listVM.clear();
                    for (int i=0; i < vmField.getValue(); i++){
                        TextField ramVM = new TextField();
                        ObservableList<String> items = FXCollections.observableArrayList();
                        items.add("128");
                        items.add("256");
                        items.add("512");
                        items.add("1024");
                        ComboBox<String> cbx = new ComboBox<>(items);
                        cbx.setPrefWidth(80);
                        this.listVM.add(cbx);
                        Text numCore = new Text("#" + (i+1) + " of Core");
                        gridPane.add(numCore, i, 1);
                        gridPane.add(cbx, i, 2);
                    }

                }else{

                        for (int i=0; i < this.listVM.size(); i++){
                            this.listVM.get(i).setValue(this.model.getRamVM().get(i).toString());

                            //System.out.println(this.model.getRamVM().get(i).toString());
                            Text numCore = new Text("#" + (i+1) + " of Core");
                            gridPane.add(numCore, i, 1);
                            gridPane.add(this.listVM.get(i), i, 2);
                        }
                    }


                this.ramCompare= vmField.getValue();

                this.model.setRamVM(this.ramModel);

                Button cancelBtn = new Button("Cancel");
                cancelBtn.getStyleClass().add("cancelbtn");
                cancelBtn.setAlignment(Pos.CENTER);
                //cancelBtn.setPrefWidth(25);
                cancelBtn.setOnMouseClicked(this.onCancelVMBtnClicked());
                gridPane.add(cancelBtn, 0, 3);


                Button okBtn = new Button("ok");
                okBtn.getStyleClass().add("okbtn");
                okBtn.setAlignment(Pos.CENTER);
                //okBtn.setPrefWidth(25);
                okBtn.setOnMouseClicked(this.onOkVMBtnClicked());
                gridPane.add(okBtn, 1, 3);


                this.formScene1 = new Scene(gridPane);

                final Stage primaryStage = (Stage) this.getScene().getWindow();
                this.formStage1 = new Stage();
                // formStage.setOnCloseRequest(beforeCloseFormStage());
                this.formScene1.getStylesheets().addAll("file:src/main/resources/style/FormStyle.css");
                this.formStage1.setScene(formScene1);
                this.formStage1.initModality(Modality.WINDOW_MODAL);
                this.formStage1.initOwner(primaryStage);
                this.formStage1.setResizable(false);
                this.formStage1.setTitle("Virtual Machine " );
                this.formStage1.showAndWait();
                this.formStage1.close();


            }else{
                Alert alert = new Alert(Alert.AlertType.WARNING);
                alert.setHeaderText(null);
                alert.setTitle("Virtual Machines");

                if (vmField.getValue() == 0){

                    alert.setContentText("You should enter a number of virtual machines");

                }else{

                    alert.setContentText("You can enter until 5 virtual machines");

                }
                alert.showAndWait();

            }
        };
    }

    protected EventHandler onOkVMBtnClicked() {
        return event -> {
            try {
                this.ramModel.clear();
                int totalRam=0;
                for (ComboBox ramVM : this.listVM){
                    int ramfield=Integer.parseInt((String) ramVM.getValue());
                    this.ramModel.add(ramfield);
                    totalRam += ramfield;
                }
                RadioButton selectRAM = (RadioButton) this.group.getSelectedToggle();
                //System.out.println(selectRAM.getText());
                if (totalRam <= (Integer.parseInt(selectRAM.getText())*1000)){
                    this.model.setRamVM(this.ramModel);
                    this.ramModel= this.model.getRamVM();
                    this.formStage1.close();
                }else{
                    Alert alert = new Alert(Alert.AlertType.ERROR);
                    alert.setHeaderText(null);
                    alert.setTitle("RAM Virtual Machines");
                    alert.setContentText("The sum of RAM in the virtual machines is: "+ totalRam+ " Mb\n "+ "RAM of server is: "+ (Integer.parseInt(selectRAM.getText())*1000) + " Mb");
                    alert.showAndWait();
                    onOkVMBtnClicked();
                }
            }catch ( IndexOutOfBoundsException ix){
                System.out.println("");
            }

        };
    }

    protected EventHandler<MouseEvent> onCancelVMBtnClicked() {
        return event -> {
            this.formStage1.close();
        };
    }

    /*protected ChangeListener<Integer> onChangeVmField() {
        return (ChangeListener<Integer>)(ObservableValue<? extends Integer> observable, Integer oldValue, Integer newValue) -> {
            if(newValue != null ) {
                System.out.println(newValue);
            }
            //System.out.println("Cambio contenido");
        };
    }*/

    public ServerModel getModel() {
        return this.model;
    }
}
