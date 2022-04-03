/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.perdiz.neblina.util;

/**
 * @author alexander
 */
public class Console {

    private static final String DANGER = "\033[91m";
    private static final String WARNING = "\033[93m";
    private static final String SUCCESS = "\033[92m";
    private static final String INFO = "\033[96m";
    private static final String NORMAL = "\033[0m";
    private static final String BOLD = "\033[1m";

    public Console() {
    }

    public static String title(String title) {

        int length = (35 - (title.length() / 2));
        String line = "";

        for (byte i = 0; i < length; i++) {
            line += "-";
        }

        return bold(line + "< ") + info(title) + bold(" >" + line);

    }

    public static String danger(String string) {
        return DANGER + string + NORMAL;
    }

    public static String warning(String string) {
        return WARNING + string + NORMAL;
    }

    public static String success(String string) {
        return SUCCESS + string + NORMAL;
    }

    public static String info(String string) {
        return INFO + string + NORMAL;
    }

    public static String bold(String string) {
        return BOLD + string + NORMAL;
    }


}
