package controllers;

import enums.Direction;
import enums.Priority;
import javafx.animation.AnimationTimer;
import javafx.animation.TranslateTransition;
import javafx.application.Application;
import javafx.scene.Group;
import javafx.scene.Scene;
import javafx.scene.paint.Color;
import javafx.scene.shape.Line;
import javafx.stage.Stage;
import javafx.util.Duration;
import model.Bridge;
import model.Car;
import model.CarHandler;
import view.ObjectCar;

public class CarApplication extends Application {
    static Group root;
    @Override
    public void start(Stage primaryStage) {
        root = new Group();
        Scene scene = new Scene(root, 800, 600);


        // Criando as linhas horizontais para representar a ponte
        Line linhaSuperior = new Line(50, 200, 550, 200); // Ponte superior
        Line linhaInferior = new Line(50, 250, 550, 250); // Ponte inferior
        linhaSuperior.setStroke(Color.BROWN); // Cor marrom para a ponte
        linhaInferior.setStroke(Color.BROWN); // Cor marrom para a ponte

        // Adicionando as linhas ao painel
        root.getChildren().addAll(linhaSuperior, linhaInferior);

        // Criando a cena e adicionando-a ao palco

        primaryStage.setScene(scene);
        primaryStage.setTitle("Ponte Bonita");
        primaryStage.show();
        primaryStage.setTitle("Car App");
        primaryStage.setScene(scene);
        primaryStage.show();

        // Add cars to the root group
        // Start an animation timer to update car positions
        AnimationTimer timer = new AnimationTimer() {
            @Override
            public void handle(long now) {
            }
        };
        timer.start();
    }

    static public void updateCarPosition(ObjectCar car) {

        double carWidth = car.getWidth();
        double carHeight = car.getHeight();
        switch (car.getCar().getCarDirection()) {
            case TO_RIGHT:
                car.setX(car.getX() + car.getCar().getCrossingTime());
                if (car.getX() + carWidth > 800) {
                    car.setX(0);
                }
                break;
            case TO_LEFT:
                car.setX(car.getX() - car.getCar().getCrossingTime());
                if (car.getX() < 0) {
                    car.setX(800 - carWidth);
                }
                break;
            default:
                break;
        }

        TranslateTransition translateTransition = new TranslateTransition(Duration.seconds(car.getCar().getCrossingTime()), car);
        translateTransition.setByX(car.getWidth());
        translateTransition.setCycleCount(TranslateTransition.INDEFINITE);
        translateTransition.play();
        root.getChildren().add(car);

    }

    public static void main(String[] args) {
        Bridge.newBridge(50.0, Priority.LEFT_RIGHT);
        CarHandler.newHandler(10);
        CarHandler handler = CarHandler.handler();

        handler.createCar(5.0, 9.0, Direction.TO_LEFT);
        handler.createCar(7.0, 12.0, Direction.TO_RIGHT);
        handler.createCar(3.0, 20.0, Direction.TO_LEFT);
        handler.createCar(9.0, 6.0, Direction.TO_LEFT);
        handler.createCar(5.0, 9.0, Direction.TO_RIGHT);
        handler.createCar(7.0, 12.0, Direction.TO_RIGHT);
        handler.createCar(3.0, 20.0, Direction.TO_LEFT);
        handler.createCar(9.0, 6.0, Direction.TO_LEFT);
        handler.createCar(7.0, 12.0, Direction.TO_RIGHT);
        handler.createCar(3.0, 20.0, Direction.TO_LEFT);

        handler.initCars();
        launch(args);
    }
}