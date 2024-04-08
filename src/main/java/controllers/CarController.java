package controllers;

import javafx.animation.TranslateTransition;
import javafx.fxml.FXML;
import javafx.scene.control.Label;
import javafx.scene.layout.AnchorPane;
import javafx.util.Duration;
import view.ObjectCar;

public class CarController {
    @FXML
    private Label welcomeText;

    @FXML
    private AnchorPane carroPane;

    private TranslateTransition transition;

    @FXML
    protected void onHelloButtonClick() {


    }
}