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

public class CarApplication extends Application {
    static Group root;
    static boolean isCarCrossing = false; // Variável para controlar se um carro está cruzando a ponte

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
        if (car.getCar().getApplicationState() == ApplicationState.CROSSING && !car.isAssigned) {
            // Verifica se já existe um carro cruzando na direção oposta
            if (!hasCarCrossingInOppositeDirection(car.getCar().getCarDirection())) {
                
                car.isAssigned = true;
                System.out.println(car.getCar().getApplicationState());
                System.out.println(car.getCar().getCarDirection());

                if (root == null) {
                    Platform.runLater(() -> updateCarPosition(car));
                    return;
                }
                double bridgeWidth = 550 - 50; // Largura da ponte

                Platform.runLater(() -> {
                    TranslateTransition translateTransition = new TranslateTransition(Duration.seconds(car.getCar().getCrossingTime()), car);

                    // Define a posição inicial com base na direção do carro
                    double startX;
                    if (car.getCar().getCarDirection() == Direction.TO_RIGHT) {
                        startX = 550; // Inicia do final da ponte para a direita
                        translateTransition.setToX(-bridgeWidth); // Movimento até o início da ponte
                    } else {
                        startX = 50 + 20; // Inicia do começo da ponte para a direita
                        translateTransition.setToX(bridgeWidth); // Movimento até o final da ponte
                    }

                    car.setX(startX);
                    car.setY(225);

                    translateTransition.setOnFinished(event -> {
                        // Ao terminar a transição, remove o carro da cena e libera a flag de cruzamento
                        root.getChildren().remove(car);
                        car.isAssigned = false;
                    });

                    translateTransition.play();
                    root.getChildren().add(car);
                });
            }
        } else {
            // Se não estiver cruzando ou o carro já estiver atribuído, remove o carro da cena
            root.getChildren().remove(car);
        }
    }

    // Método para verificar se já existe um carro cruzando na direção oposta
    static boolean hasCarCrossingInOppositeDirection(Direction direction) {
        for (Car car : CarHandler.handler().getCars()) {
            if (car.getApplicationState() == ApplicationState.CROSSING &&
                    car.getCarDirection() != direction) {
                return true;
            }
        }
        return false;
    }

    public static void main(String[] args) {
        Bridge.newBridge(50.0, Priority.LEFT_RIGHT);
        CarHandler.newHandler(10);
        CarHandler handler = CarHandler.handler();

        // Dentro do método main() da classe CarApplication
        handler.createCar(7.0, 12.0, Direction.TO_LEFT,50 + 20, 225);
        handler.createCar(3.0, 20.0, Direction.TO_LEFT,50 + 20, 225);
        handler.createCar(9.0, 6.0, Direction.TO_LEFT,50 + 20, 225);
        handler.createCar(5.0, 9.0, Direction.TO_RIGHT,50 + 20, 225);
        handler.createCar(7.0, 12.0, Direction.TO_RIGHT,50 + 20, 225);
        handler.createCar(3.0, 20.0, Direction.TO_LEFT,50 + 20, 225);
        handler.createCar(9.0, 6.0, Direction.TO_LEFT,50 + 20, 225);
        handler.createCar(7.0, 12.0, Direction.TO_RIGHT,50 + 20, 225);
        handler.createCar(3.0, 20.0, Direction.TO_LEFT,50 + 20, 225);

        handler.initCars();
        launch(args);
    }
}
