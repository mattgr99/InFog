/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.perdiz.neblina.app.resource;

/**
 * @author alexander
 */
public enum Icon {

    /**
     * Cloud server dark icon
     */
    CS_DARK("/CloudServer.png"),

    /**
     * Cloud server light icon
     */
    CS_LIGHT("/CloudServerLight.png"),

    /**
     * Fog server dark icon
     */
    FS_DARK("/FogServer.png"),

    /**
     * Fog server light icon
     */
    FS_LIGHT("/FogServerLight.png"),

    /**
     * Actuator dark icon
     */
    AC_DARK("/Actuator.png"),

    /**
     * Actuator server light icon
     */
    AC_LIGHT("/ActuatorLight.png"),

    /**
     * Sensor dark icon
     */
    SN_DARK("/Sensor.png"),

    /**
     * Sensor server light icon
     */
    SN_LIGHT("/SensorLight.png"),

    SN_CARDIOGRAM("/Cardiogram.png"),
    SN_OXIMETER("/pulse-oximeter.png"),
    SN_PURIFIER("/air-purifier.png"),
    SN_GLUCOMETER("/glucometer.png"),
    SN_TEMPERATURE("/smart-temperature.png"),
    SN_SMARTWATCH("/smartwatch.png"),
    SN_CCTV("/cctv.png"),
    SN_FINGERPRINT("/fingerprint.png"),
    SN_CHARGIN_STATION("/charging-station.png"),
    SN_LIGHT_SENSOR("/light-sensor.png"),
    SN_lIGHT_METER("/light-meter.png"),
    SN_MOTION("/motion-sensor.png"),
    SN_DIVERLESS("/driverless-car.png"),

    /**
     * Traffic light icon
     */
    TF_LIGHT("/TrafficLight.png"),

    /**
     * Traffic Shuffle light icon
     */
    TF_SHUFFLE_LIGHT("/TrafficShuffleLight.png"),

    /**
     * Chart bar light icon
     */
    CHART_BAR_LIGHT("/ChartBarLight.png"),

    /**
     * Chart line light icon
     */
    CHART_LINE_LIGHT("/ChartLineLight.png"),

    /**
     * Chart multiline light icon
     */
    CHART_MULTILINE_LIGHT("/ChartMultilineLight.png");

    /*************************************************************
     *                        CONSTRUCTOR                        *
     *************************************************************/

    public final String src;

    Icon(String src) {
        this.src = "file:src/main/resources/image" + src;
    }
}
