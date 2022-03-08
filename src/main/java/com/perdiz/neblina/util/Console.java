/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.perdiz.neblina.util;

/**
 *
 * @author alexander
 */
public class Console {

    private final String DANGER = "\033[91m";
    private final String WARNING = "\033[93m";
    private final String SUCCESS = "\033[92m";
    private final String INFO = "\033[96m";
    private final String NORMAL = "\033[0m";
    private final String BOLD = "\033[1m";

    public Console() {
    }

    public void title(String title) {

        int length = (35 - (title.length() / 2));
        String line = "";

        for (byte i = 0; i < length; i++) {
            line += "-";
        }

        System.out.print(BOLD + line + "< " + NORMAL);
        System.out.print(INFO + title + NORMAL);
        System.out.println(BOLD + " >" + line + NORMAL);
    }

    public void danger(String string) {
        System.out.println(DANGER + string + NORMAL);
    }

    public void warning(String string) {
        System.out.println(WARNING + string + NORMAL);
    }

    public void success(String string) {
        System.out.println(SUCCESS + string + NORMAL);
    }

    public void info(String string) {
        System.out.println(INFO + string + NORMAL);
    }

    public String getDanger() {
        return DANGER;
    }

    public String getWarning() {
        return WARNING;
    }

    public String getSuccess() {
        return SUCCESS;
    }

    public String getInfo() {
        return INFO;
    }

    public String getNormal() {
        return NORMAL;
    }

    public String getBold() {
        return BOLD;
    }

}
