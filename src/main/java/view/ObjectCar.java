package view;

import enums.Direction;
import javafx.scene.Group;
import javafx.scene.paint.Color;
import javafx.scene.shape.Circle;
import javafx.scene.shape.Polygon;
import javafx.scene.shape.Rectangle;
import model.Car;

public class ObjectCar extends Group {
    private double x;
    public boolean isAssigned = false;
    private double y;
    private Car car;

    public ObjectCar(double x, double y, Car car) {
        this.x = x;
        this.y = y;
        this.car = car;
        Rectangle body = new Rectangle(x, y - 10, 100, 20);
        body.setFill(Color.LIGHTBLUE);

        // Janela
        Rectangle window = new Rectangle(x + 5, y - 8, 30, 15);
        window.setFill(Color.LIGHTGRAY);

        // Rodas
        Circle wheel1 = new Circle(x + 15, y + 10, 5);
        wheel1.setFill(Color.BLACK);

        Circle wheel2 = new Circle(x + 85, y + 10, 5);
        wheel2.setFill(Color.BLACK);

        // Adicionando todos os elementos ao grupo
        this.getChildren().addAll(body, window, wheel1, wheel2);

    }

    public void setCar(Car car){
        this.car = car;
    }
    public Car getCar(){
        return car;
    }

    public void setX(double x) {
        this.x =x;
    }

    public void setY(double y) {
        this.y=y;
    }
}