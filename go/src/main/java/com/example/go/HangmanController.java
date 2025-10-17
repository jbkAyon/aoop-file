package com.example.go;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.stage.Stage;

import java.io.IOException;
import java.util.Random;

public class HangmanController {

    @FXML private ImageView img;
    @FXML private TextField tf1, tf2, tf3, tf4, tf5, tf6, tf7, tf8;
    @FXML private TextField input;
    @FXML private Label hint;
    @FXML private Label letter_count;

    private Image image2;
    private Image image3;
    private Image image4;
    private Image image5;
    private Image image6;
    private Image image7;

    private final String[] data = new String[]{
            "MEXICO COUNTRY",
            "HEDWIG BIRD",
            "KUAKATA BEACH",
            "CANADA COUNTRY",
            "DOCTOR PROFESSION",
            "FOOTBALL GAME",
            "TEACHER MENTOR",
            "LEOPARD ANIMAL",
            "BICYCLE TRANSPORT",
            "SALMON FISH",
            "SPARROW BIRD",
            "PARROTS BIRD",
            "EAGLE BIRD",
            "TRAIN TRANSPORT",
            "SHIP TRANSPORT",
            "ENGINEER PROFESSION",
            "BANKER PROFESSION",
            "CRICKET GAME"
    };

    private int life = 6;
    private String word;
    private String hintStr;

    @FXML
    private void initialize() {
        // Load images lazily and null-safe so scene doesn't crash if assets are missing
        image2 = loadImage("/com/example/go/images/2.png");
        image3 = loadImage("/com/example/go/images/3.png");
        image4 = loadImage("/com/example/go/images/4.png");
        image5 = loadImage("/com/example/go/images/5.png");
        image6 = loadImage("/com/example/go/images/6.png");
        image7 = loadImage("/com/example/go/images/7.png");
        if (image2 != null) {
            img.setImage(image2); // show initial state so image is visible immediately
        }

        int random = new Random().nextInt(data.length);
        String wordHint = data[random];
        String[] split = wordHint.split(" ", 2);
        word = split[0];
        hintStr = split[1];

        hint.setText(hintStr);
        int letterSize = word.length();
        letter_count.setText(letterSize + " Letters");

        if (letterSize <= 7) tf8.setVisible(false);
        if (letterSize <= 6) tf7.setVisible(false);
        if (letterSize <= 5) tf6.setVisible(false);
        if (letterSize <= 4) tf5.setVisible(false);
    }

    private Image loadImage(String path) {
        java.net.URL url = getClass().getResource(path);
        if (url == null) {
            System.err.println("Hangman image missing: " + path + " (place under src/main/resources" + path + ")");
            return null;
        }
        return new Image(url.toExternalForm());
    }

    @FXML
    private void checkInput() {
        String str = input.getText();
        if (str == null || str.isBlank()) return;
        if (word.contains(str)) {
            int index = 0;
            for (int i = 0; i < word.length(); i++) {
                char c = word.charAt(i);
                if (String.valueOf(c).equalsIgnoreCase(str)) {
                    setLetter(index, Character.toString(c));
                }
                index++;
            }
        } else {
            setImage();
        }
        input.clear();
    }

    private void setLetter(int index, String str) {
        switch (index) {
            case 0 -> tf1.setText(str);
            case 1 -> tf2.setText(str);
            case 2 -> tf3.setText(str);
            case 3 -> tf4.setText(str);
            case 4 -> tf5.setText(str);
            case 5 -> tf6.setText(str);
            case 6 -> tf7.setText(str);
            case 7 -> tf8.setText(str);
        }
    }

    private void setImage() {
        if (life == 6 && image2 != null) img.setImage(image2);
        else if (life == 5 && image3 != null) img.setImage(image3);
        else if (life == 4 && image4 != null) img.setImage(image4);
        else if (life == 3 && image5 != null) img.setImage(image5);
        else if (life == 2 && image6 != null) img.setImage(image6);
        else if (life == 1 && image7 != null) img.setImage(image7);
        life--;
    }

    @FXML
    private void backToMenu(ActionEvent event) throws IOException {
        Parent parent = FXMLLoader.load(getClass().getResource("/com/example/go/body.fxml"));
        Stage window = (Stage) ((Node) event.getSource()).getScene().getWindow();
        window.setTitle("Dashboard");
        window.setScene(new Scene(parent, 600, 400));
        window.show();
    }
}


