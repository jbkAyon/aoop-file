package com.example.go;

import javafx.animation.FadeTransition;
import javafx.concurrent.Task;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Label;
import javafx.scene.control.ProgressBar;
import javafx.stage.Stage;
import javafx.util.Duration;

import java.net.URL;
import java.util.ResourceBundle;

public class HelloController implements Initializable {

    @FXML private ProgressBar progressbar;
    @FXML private Label labelprogressbar;

    private Task<Void> task;

    @Override
    public void initialize(URL url, ResourceBundle rb) {
        // Background Task
        task = new Task<>() {
            @Override
            protected Void call() throws Exception {
                for (int i = 0; i <= 100; i++) {
                    updateProgress(i, 100);
                    updateMessage(String.valueOf(i));
                    Thread.sleep(50);
                }
                return null;
            }
        };

        // Bind UI
        progressbar.progressProperty().bind(task.progressProperty());
        task.messageProperty().addListener((obs, oldVal, newVal) ->
                labelprogressbar.setText("Loading " + newVal + "%")
        );

        // Done -> go to login
        task.setOnSucceeded(e -> goNext());

        // Start Task
        Thread t = new Thread(task);
        t.setDaemon(true);
        t.start();
    }

    private void goNext() {
        try {
            URL fxml = getClass().getResource("/com/example/go/Login.fxml");
            if (fxml == null) {
                System.err.println("❌ FXML not found: resources/com/example/go/Login.fxml");
                return;
            }

            FXMLLoader loader = new FXMLLoader(fxml);
            Parent root = loader.load();

            Stage stage = (Stage) progressbar.getScene().getWindow();
            Scene nextScene = new Scene(root);

            // Ensure scene change happens on FX thread
            javafx.application.Platform.runLater(() -> {
                // Smooth fade-in
                FadeTransition fade = new FadeTransition(Duration.millis(300), root);
                fade.setFromValue(0.0);
                fade.setToValue(1.0);

                stage.setScene(nextScene);
                stage.setTitle("Login");
                fade.play();
            });
        } catch (Exception ex) {
            ex.printStackTrace();
        }
    }
}