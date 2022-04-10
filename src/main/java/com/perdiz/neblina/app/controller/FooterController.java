package com.perdiz.neblina.app.controller;


import com.jfoenix.controls.JFXButton;
import javafx.scene.control.ScrollPane;
import javafx.scene.layout.HBox;
import javafx.scene.layout.VBox;
import javafx.scene.paint.Color;
import javafx.scene.text.Font;
import javafx.scene.text.FontWeight;
import javafx.scene.text.Text;
import javafx.scene.text.TextFlow;

import java.util.regex.Pattern;

/**
 * @author alexander
 */
public class FooterController extends VBox {

    protected final Integer HEIGHT = 205;
    protected String contentName = null;

    protected JFXButton outputBtn = new JFXButton("output");
    protected ScrollPane scrollPane = new ScrollPane();
    protected TextFlow logViewer = new TextFlow();

    public FooterController() {
        initEvents();
    }

    private void initEvents() {
        outputBtn.setOnMouseClicked(e -> {
            if (contentName == null || contentName != "output") {
                contentName = "output";
                scrollPane.setContent(logViewer);
                getChildren().add(0, scrollPane);
            } else {
                contentName = null;
                getChildren().remove(0);

            }
        });
    }


    /**
     * Add new message to log viewer.
     * <pre>{@code
     * // Example
     * addLog(
     *      "${_} ${b_} ${S_} ${I_} ${W_} ${E_} ${Sb}.",
     *      "Normal", "Bold", "Success", "Info", "Warning", "Error", "Bold success."
     * );
     * }</pre>
     *
     * @param format format to print the message
     * @param args   arguments declared in the format
     */
    public void addLog(String format, String... args) {


        Pattern pattern = Pattern.compile("\\$(\\{.*?})");
        Object[] rejexList = pattern.matcher(format).results().map(mr -> mr.group()).toArray();

        for (int index = 0; index < rejexList.length; index++) {
            String options = rejexList[index].toString();

            Text text = new Text(args[index] + (options.contains("_") ? " " : ""));
            text.setFill(Color.WHITE);

            if (options.contains("S")) text.setFill(Color.LIGHTGREEN);
            if (options.contains("I")) text.setFill(Color.CYAN);
            if (options.contains("W")) text.setFill(Color.ORANGE);
            if (options.contains("E")) text.setFill(Color.RED);
            if (options.contains("b")) text.setFont(Font.font("Verdana", FontWeight.EXTRA_BOLD, 13));

            logViewer.getChildren().add(text);
        }
        logViewer.getChildren().add(new Text(System.lineSeparator()));
        scrollPane.setVvalue(1.0);
    }

}
