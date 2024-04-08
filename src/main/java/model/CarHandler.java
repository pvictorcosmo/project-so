package model;

import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;
import java.util.List;

import controllers.CarApplication;
import view.ObjectCar;
import enums.Direction;
import enums.ApplicationState;
import enums.BridgeSide;

public class CarHandler {
    private static CarHandler instance = null;
    private Integer maxCars;
    private List<Car> cars;

    private CarHandler(Integer maxCars){
        this.maxCars = maxCars;
        this.cars = new ArrayList<Car>();
    }
    public static void newHandler(Integer maxCars){
        if(instance==null){
            instance = new CarHandler(maxCars);
        }
    }
    public static CarHandler handler(){
        return instance;
    }
    public void createCar(Double waitingTime, Double crossingTime, ApplicationState applicationState, Direction carDirection){
        Car car = new Car(
                cars.size(),
                waitingTime,
                crossingTime,
                applicationState,
                carDirection
        );
        cars.add(
          car
        );

    }
    public void createCar(Double waitingTime, Double crossingTime, Direction carDirection){
        Car car = new Car(
                cars.size(),
                waitingTime,
                crossingTime,
                carDirection
        );
        cars.add(
          car
        );
        CarApplication.updateCarPosition(new ObjectCar(20,10,car));
    }
    public void initCars(){
        for(Car car : cars){
            car.start();
        }
    }
    public void changeCarDirection(Car car){
        Direction newDirection;
        if(car.getCarDirection() == Direction.TO_RIGHT){
            newDirection = Direction.TO_LEFT;
        }
        else{
            newDirection = Direction.TO_RIGHT;
        }
        car.setCarDirection(newDirection);

    }
    public Integer getMaxCars() {
        return maxCars;
    }
    public void setMaxCars(Integer maxCars) {
        this.maxCars = maxCars;
    }
    public List<Car> getCars() {
        return cars;
    }
    public void setCars(List<Car> cars) {
        this.cars = cars;
    }

}
