package com.example.go;

import javafx.fxml.FXML;
import javafx.scene.control.*;
import javafx.event.ActionEvent;
import javafx.scene.Scene;
import javafx.stage.Stage;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;

import java.io.IOException;

public class SignupController {

    @FXML private TextField nameField;
    @FXML private TextField emailField;
    @FXML private PasswordField passField;
    @FXML private PasswordField confirmField;
    @FXML private Label errorLabel;

    @FXML
    private void handleSignup(ActionEvent event) {
        String name = nameField.getText();
        String email = emailField.getText();
        String pass = passField.getText();
        String confirm = confirmField.getText();

        if (name.isEmpty() || email.isEmpty() || pass.isEmpty() || confirm.isEmpty()) {
            errorLabel.setText("All fields are required!");
            errorLabel.setVisible(true);
            return;
        }

        if (!pass.equals(confirm)) {
            errorLabel.setText("Passwords do not match!");
            errorLabel.setVisible(true);
            return;
        }

        boolean created = UserStore.registerUser(email, pass, name);
        if (!created) {
            errorLabel.setText("Email already registered");
            errorLabel.setVisible(true);
            return;
        }

        errorLabel.setVisible(false);
        goLogin(event);
    }

    @FXML
    private void goLogin(ActionEvent event) {
        try {
            Parent root = FXMLLoader.load(getClass().getResource("/com/example/go/Login.fxml"));
            Stage stage = (Stage) nameField.getScene().getWindow();
            stage.setScene(new Scene(root));
            stage.setTitle("Login");
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}
