package org.elettrodomestici.brunasso_vittorio_elettrodomestici;

import java.io.IOException;
import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.stage.Stage;

public class Main extends Application {

    public static void main(String[] args) {
        launch(args);
    }

    public void start(Stage primaryStage) throws IOException {
        FXMLLoader customerLoader = new FXMLLoader(this.getClass().getResource("Login_Interface.fxml"));
        Parent customerRoot = customerLoader.load();
        Scene customerScene = new Scene(customerRoot);
        primaryStage.setScene(customerScene);
        primaryStage.setMaximized(true);
        primaryStage.show();
    }
}

