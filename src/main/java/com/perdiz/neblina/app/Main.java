package com.perdiz.neblina.app;

import com.perdiz.neblina.app.component.App;
import com.perdiz.neblina.util.Console;
import javafx.application.Application;
import javafx.scene.Scene;
import javafx.scene.image.Image;
import javafx.stage.Stage;

public class Main extends Application {

    @Override
    public void start(Stage stage) {
        App app = new App();
        Scene scene = new Scene(app, 900, 600);
        scene.getStylesheets().addAll("file:src/main/resources/style/AppStyle.css");
        stage.setScene(scene);
        stage.getIcons().add(new Image("file:src/main/resources/image/icon.png"));
        stage.setTitle("Neblina");
        stage.show();
        Console console = new Console();
        console.title("Neblina");
        System.out.println("Neblina is running with " +
                console.getInfo() + "java: " + System.getProperty("java.version") + console.getNormal() +
                " and " +
                console.getInfo() + "javaFX: " + System.getProperty("javafx.version") + console.getNormal() + "."
        );

    }

    public static void main(String[] args) {
        launch();
    }

}
