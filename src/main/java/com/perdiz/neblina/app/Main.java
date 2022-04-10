package com.perdiz.neblina.app;

import com.perdiz.neblina.app.component.App;
import com.perdiz.neblina.app.resource.CssColor;
import com.perdiz.neblina.util.Console;
import com.perdiz.neblina.util.Pkg;
import javafx.application.Application;
import javafx.scene.Scene;
import javafx.scene.image.Image;
import javafx.stage.Stage;

public class Main extends Application {

    @Override
    public void start(Stage stage) {
        App app = new App();
        app.setStyle(String.join(";", CssColor.TUNDORA));

        Scene scene = new Scene(app, 900, 600);
        scene.getStylesheets().addAll("file:src/main/resources/style/AppStyle.css");
        stage.getIcons().add(Pkg.LOGO);
        stage.setTitle(Pkg.NAME);
        stage.setScene(scene);
        stage.show();

        System.out.println(Console.title("Started"));
        System.out.printf("%s\t%s@%s\n", Console.success("App:"), Pkg.NAME, Pkg.VERSION);

    }

    public static void main(String[] args) {
        launch();
    }

}
