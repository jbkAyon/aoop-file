package com.example.go;

import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.control.Label;

public class BodyController {

	@FXML private Label coinsLabel;
	@FXML private Button shopBtn;
	@FXML private Button playBtn;
	@FXML private Button friendsBtn;
	@FXML private Button settingsBtn;

	@FXML private void onShop() {
		System.out.println("Shop clicked");
	}

	@FXML private void onPlay() {
		System.out.println("Play clicked");
	}

	@FXML private void onFriends() {
		System.out.println("Friends clicked");
	}

	@FXML private void onSettings() {
		System.out.println("Settings clicked");
	}
}
