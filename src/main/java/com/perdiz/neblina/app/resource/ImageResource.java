/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.perdiz.neblina.app.resource;

import com.perdiz.neblina.util.Console;
import java.io.File;
import java.net.MalformedURLException;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;

/**
 *
 * @author alexander
 */
public class ImageResource extends ImageView {

    public ImageResource(Resource resource) {

        switch (resource) {
            case ACTUATOR:
                setImage(loadImage("Actuator"));
                break;
            case ACTUATORLIGHT:
                setImage(loadImage("ActuatorLight"));
                break;
            case ALARMCLOCK:
                setImage(loadImage("AlarmClock"));
                break;
            case CARDIOGRAM:
                setImage(loadImage("Cardiogram"));
                break;
            case CLOUDSERVER:
                setImage(loadImage("CloudServer"));
                break;
            case CLOUDSERVERLIGHT:
                setImage(loadImage("CloudServerLight"));
                break;
            case FOGSERVER:
                setImage(loadImage("FogServer"));
                break;
            case FOGSERVERLIGHT:
                setImage(loadImage("FogServerLight"));
                break;
            case MACBOOK:
                setImage(loadImage("Macbook"));
                break;
            case MOVEICON:
                setImage(loadImage("MoveIcon"));
                break;
            case SENSOR:
                setImage(loadImage("Sensor"));
                break;
            case SENSORLIGHT:
                setImage(loadImage("SensorLight"));
                break;

        }

    }

    private Image loadImage(String image) {
        Image localImage = null;
        try {
            File file = new File("src/main/resources/image/" + image + ".png");
            if (file.exists()) {
                localImage = new Image(file.toURI().toURL().toString());
            } else {
                new Console().danger("The image \"" + image + ".png\" does not exist or was not exported from \"" + image + ".svg\"");
                System.exit(0);
            }
        } catch (MalformedURLException e) {
            new Console().danger("The image " + image + ".png does not exist or was not exported from " + image + ".svg");
            System.exit(0);
        }
        return localImage;
    }

}
