package com.example.go;

import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.stage.Stage;

import java.io.IOException;

public class HelloApplication extends Application {
    @Override
    public void start(Stage stage) throws IOException {
        // Ensure database is initialized before any user actions
        com.example.go.Db.initialize();
        FXMLLoader fxmlLoader = new FXMLLoader(HelloApplication.class.getResource("/com/example/go/hello-view.fxml"));
        Scene scene = new Scene(fxmlLoader.load());
        stage.setTitle("StartingBar");
        stage.setScene(scene);
        stage.sizeToScene();
        stage.show();
    }
}