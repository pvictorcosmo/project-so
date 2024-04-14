package controllers;
import enums.ApplicationState;
import enums.Direction;
import enums.Priority;
import javafx.animation.AnimationTimer;
import javafx.animation.TranslateTransition;
import javafx.application.Application;
import javafx.application.Platform;
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

import java.util.List;
import java.util.concurrent.Semaphore;

public class CarApplication extends Application {
    private static Group root;
    private static final Semaphore bridgeSemaphore = new Semaphore(1);

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

        // Adicionando carros à ponte
        addCarsToBridge();

        // Inicializando a ponte
        Bridge.newBridge(50.0, Priority.LEFT_RIGHT);

        // Iniciando a aplicação
        primaryStage.setTitle("Car App");
        primaryStage.setScene(scene);
        primaryStage.show();

        // Iniciando o timer para atualizar a posição dos carros
        AnimationTimer timer = new AnimationTimer() {
            @Override
            public void handle(long now) {
                // Atualizando a posição dos carros na ponte
                CarApplication.updateCarPosition(CarHandler.handler().getCars());
            }
        };
        timer.start();
    }

    // Método para adicionar carros à ponte
    private void addCarsToBridge() {
        CarHandler.newHandler(10);
        CarHandler handler = CarHandler.handler();

        // Criando os carros
        handler.createCar(7.0, 12.0, Direction.TO_LEFT, 50 + 20, 225);
        handler.createCar(3.0, 20.0, Direction.TO_LEFT, 50 + 20, 225);
        handler.createCar(9.0, 6.0, Direction.TO_LEFT, 50 + 20, 225);
        handler.createCar(5.0, 9.0, Direction.TO_RIGHT, 50 + 20, 225);
        handler.createCar(7.0, 12.0, Direction.TO_RIGHT, 50 + 20, 225);
        handler.createCar(3.0, 20.0, Direction.TO_LEFT, 50 + 20, 225);
        handler.createCar(9.0, 6.0, Direction.TO_LEFT, 50 + 20, 225);
        handler.createCar(7.0, 12.0, Direction.TO_RIGHT, 50 + 20, 225);
        handler.createCar(3.0, 20.0, Direction.TO_LEFT, 50 + 20, 225);

        // Iniciando os carros
        handler.initCars();
    }

    // Método para atualizar a posição dos carros na tela
    static public void updateCarPosition(List<Car> cars) {
        for (Car car : cars) {
            // Verificando se o carro está cruzando e não foi atribuído
            if (car.getApplicationState() == ApplicationState.CROSSING && !car.isAssigned) {
                // Tentando adquirir o semáforo da ponte

                    car.isAssigned = true;
                    ObjectCar objCar = new ObjectCar(50 + 20, 225, car);

                    // Movendo o carro na ponte
                    Platform.runLater(() -> {
                        TranslateTransition translateTransition = new TranslateTransition(Duration.seconds(car.getCrossingTime()), objCar);

                        // Definindo a posição final com base na direção do carro
                        double startX;
                        double bridgeWidth = 550 - 50; // Largura da ponte
                        if (car.getCarDirection() == Direction.TO_RIGHT) {
                            startX = 550; // Começa do final da ponte para a direita
                            translateTransition.setToX(-bridgeWidth); // Movimento até o início da ponte
                        } else {
                            startX = 50 + 20; // Começa do começo da ponte para a esquerda
                            translateTransition.setToX(bridgeWidth); // Movimento até o final da ponte
                        }

                        // Configurando a posição inicial do carro na tela
                        objCar.setX(startX);
                        objCar.setY(225);

                        // Lidando com a finalização da transição do carro
                        translateTransition.setOnFinished(event -> {
                            // Ao terminar a transição, remove o carro da cena e libera o semáforo da ponte
                            root.getChildren().remove(objCar);
                            car.isAssigned = false;

                        });

                        // Iniciando a transição de movimento do carro
                        translateTransition.play();
                        root.getChildren().add(objCar); // Adicionando o carro à cena
                    });

            }
        }
    }

    public static void main(String[] args) {
        launch(args);
    }
}
