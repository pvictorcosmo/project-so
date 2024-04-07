package view;

import enums.Direction;
import javafx.scene.shape.Rectangle;
import model.Car;

public class ObjectCar extends Rectangle {
    private double x;
    private double y;
    private Car car;

    public ObjectCar(double x, double y, Car car) {
        super(10, 5); // Set the width and height of the car
        this.x = x;
        this.y = y;
        this.car = car;
    }

    public void setCar(Car car){
        this.car = car;
    }
    public Car getCar(){
        return car;
    }
}