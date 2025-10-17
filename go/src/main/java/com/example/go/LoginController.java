package com.example.go;


import javafx.fxml.FXML;
import javafx.scene.control.*;
import javafx.event.ActionEvent;
import javafx.scene.Scene;
import javafx.stage.Stage;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;

import java.io.IOException;

public class LoginController {

    @FXML private TextField emailField;
    @FXML private PasswordField passField;
    @FXML private Label errorLabel;

    @FXML
    private void handleLogin(ActionEvent event) {
        String email = emailField.getText();
        String pass = passField.getText();

        if (email.isEmpty() || pass.isEmpty()) {
            showAlert("Missing fields", "Please fill in all fields.");
            errorLabel.setText("Please fill in all fields!");
            errorLabel.setVisible(true);
            return;
        }

        boolean ok = UserStore.validateLogin(email, pass);
        if (ok) {
            errorLabel.setVisible(false);
            goToDashboard();
        } else {
            showAlert("Login failed", "Wrong email or password.");
            errorLabel.setText("Invalid email or password!");
            errorLabel.setVisible(true);
        }
    }

    @FXML
    private void goSignup(ActionEvent event) {
        try {
            Parent root = FXMLLoader.load(getClass().getResource("/com/example/go/signup.fxml"));
            Stage stage = (Stage) emailField.getScene().getWindow();
            stage.setScene(new Scene(root));
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    private void goToDashboard() {
        try {
            Parent root = FXMLLoader.load(getClass().getResource("/com/example/go/body.fxml"));
            Stage stage = (Stage) emailField.getScene().getWindow();
            stage.setScene(new Scene(root));
            stage.setTitle("Dashboard");
        } catch (IOException e) {
            e.printStackTrace();
            showAlert("Navigation error", "Unable to open dashboard.");
        }
    }

    private void showAlert(String title, String message) {
        Alert alert = new Alert(Alert.AlertType.ERROR);
        alert.setTitle(title);
        alert.setHeaderText(null);
        alert.setContentText(message);
        alert.showAndWait();
    }
}
