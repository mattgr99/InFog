/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.perdiz.neblina.model;

import java.util.ArrayList;
import java.util.List;

/**
 *
 * @author alexander
 */
public class ActuatorModel extends DeviceModel {

    private String type;


    //public static List<CableModel> cables = new ArrayList<>();

    public ActuatorModel() {
    }



    public ActuatorModel(String id, String name) {

        super(id, name);

    }

    public ActuatorModel(String id, String type, String name) {
        super(id, name);
        this.type = type;
    }

    public String getType() {
        return type;
    }

    public void setType(String type) {
        this.type = type;
    }

}
