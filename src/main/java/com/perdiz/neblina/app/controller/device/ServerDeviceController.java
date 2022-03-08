/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.perdiz.neblina.app.controller.device;

import com.jfoenix.controls.JFXButton;
import com.perdiz.neblina.app.iu.Device;
import com.perdiz.neblina.app.iu.NumberField;
import com.perdiz.neblina.app.resource.ImageResource;
import com.perdiz.neblina.app.resource.Resource;
import com.perdiz.neblina.model.ServerModel;
import javafx.beans.value.ChangeListener;
import javafx.beans.value.ObservableValue;
import javafx.event.EventHandler;
import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.Scene;
import javafx.scene.control.Alert;
import javafx.scene.control.Button;
import javafx.scene.control.ComboBox;
import javafx.scene.control.TextField;
import javafx.scene.input.InputMethodEvent;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.HBox;
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
    protected Stage formStage1;
    protected Scene formScene1;

    protected TextField nameField;
    protected NumberField uplinkField;
    protected NumberField downlinkField;
    protected NumberField vmField;
    protected NumberField ramField;
    protected NumberField rateField;

    protected ArrayList<TextField> listVM = new ArrayList<TextField>();;
    protected boolean vmL = false;
    protected ArrayList<Integer> ramModel = new ArrayList<Integer>();
    protected ArrayList<Integer> ramVMs = new ArrayList<Integer>();
    protected Text ramVMText = new Text("RAM VM (Mb)");
    protected Double ramCompare = -1.0;

    public ServerDeviceController(ServerModel serverModel) {
        super(serverModel, (serverModel.getLevel() == 0) ? new ImageResource(Resource.CLOUDSERVER) : new ImageResource(Resource.FOGSERVER));
        this.model = serverModel;
        // this.listVM = new ArrayList<TextField>();
        /*for (int i = 0; i < this.model.getRamVM().size(); i++){
            TextField ramVM = new TextField(this.model.getRamVM().get(i).toString());
            ramVM.setPrefWidth(80);
            this.listVM.add(ramVM);
        }*/
        this.init();
    }

    private void init() {
        this.nameField = new TextField(model.getName());
        this.uplinkField = new NumberField(model.getUplink());
        this.downlinkField = new NumberField(model.getDownlink());
        this.vmField = new NumberField(model.getVmachines());
        this.ramField = new NumberField(model.getRam());
        this.rateField = new NumberField(model.getRate());
        for (int i = 0; i < this.listVM.size(); i++){
            this.listVM.get(i).setText(this.model.getRamVM().get(i).toString());
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
            this.ramField.setValue(this.model.getRam());
            this.rateField.setValue(this.model.getRate());
            for (int i = 0; i< this.listVM.size(); i++){
                this.listVM.get(i).setText(this.model.getRamVM().get(i).toString());
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

            if(sumRam<=this.ramField.getValue()*1000){
                this.model.setName(this.nameField.getText());
                this.model.setUplink(this.uplinkField.getValue());
                this.model.setDownlink(this.downlinkField.getValue());
                this.model.setVmachines(this.vmField.getValue());
                this.model.setRam(this.ramField.getValue());
                this.model.setRate(this.rateField.getValue());
                this.nameLbl.setText(this.model.getName());
                this.vmL=false;
                this.formStage.close();
            }else{
                this.ramCompare=-1.0;
                Alert alert = new Alert(Alert.AlertType.WARNING);
                alert.setHeaderText(null);
                alert.setTitle("RAM Virtual Machines");
                alert.setContentText("RAM in virtual machines is greater than Server");
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
            this.ramField.setValue(this.model.getRam());
            this.rateField.setValue(this.model.getRate());
            //System.out.println("777777777777777777777777");
            for (int i=0; i < this.listVM.size(); i++){
                this.listVM.get(i).setText(this.model.getRamVM().get(i).toString());
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
                        ramVM.setPrefWidth(80);
                        this.listVM.add(ramVM);
                        gridPane.add(ramVM, i, 1);
                    }

                }else{

                        for (int i=0; i < this.listVM.size(); i++){
                            this.listVM.get(i).setText(this.model.getRamVM().get(i).toString());

                            //System.out.println(this.model.getRamVM().get(i).toString());
                            gridPane.add(this.listVM.get(i), i, 1);
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
            this.ramModel.clear();
            int totalRam=0;
            for (TextField ramVM : this.listVM){
                int ramfield=Integer.parseInt(ramVM.getText());
                this.ramModel.add(ramfield);
                totalRam += ramfield;
            }
            if (totalRam <= (this.ramField.getValue()*1000)){
                this.model.setRamVM(this.ramModel);
                this.ramModel= this.model.getRamVM();
                this.formStage1.close();
            }else{
                Alert alert = new Alert(Alert.AlertType.WARNING);
                alert.setHeaderText(null);
                alert.setTitle("RAM Virtual Machines");
                alert.setContentText("The sum of RAM in the virtual machines is greater than RAM of Server");
                alert.showAndWait();
                onOkVMBtnClicked();
            }

        };
    }

    protected EventHandler<MouseEvent> onCancelVMBtnClicked() {
        return event -> {
            this.formStage1.close();
        };
    }

    /*protected ChangeListener<Boolean> onChangeVmField() {
        return (ChangeListener<Boolean>)(ObservableValue<? extends Boolean> observable, Boolean oldValue, Boolean newValue) -> {
            if(!newValue) {
                System.out.println("Focusing out from textfield");
            }
            //System.out.println("Cambio contenido");
        };
    }*/

    public ServerModel getModel() {
        return this.model;
    }
}
