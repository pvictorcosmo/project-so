package controllers;

import javafx.animation.TranslateTransition;
import javafx.fxml.FXML;
import javafx.scene.control.Label;
import javafx.scene.layout.AnchorPane;
import javafx.util.Duration;

public class CarController {
    @FXML
    private Label welcomeText;

    @FXML
    private AnchorPane carroPane;

    private TranslateTransition transition;

    @FXML
    protected void onHelloButtonClick() {
        transition = new TranslateTransition(Duration.seconds(5), carroPane);
        transition.setFromX(0);
        transition.setToX(600);
        transition.play();
    }
}