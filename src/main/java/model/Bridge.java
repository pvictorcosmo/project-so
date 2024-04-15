package model;

import java.io.FileWriter;
import java.io.IOException;
import java.lang.ProcessBuilder.Redirect;
import java.util.List;
import java.util.concurrent.Semaphore;

import enums.Direction;
import enums.Priority;

public class Bridge{
    private static Bridge instance = null;
    public static Semaphore releaseBridge = new Semaphore(1,true);
    public static Semaphore car = new Semaphore(0,true);
    public static Semaphore mutex = new Semaphore(1,true);
    private Car firstCar;                //first Car
    private Direction bridgeDirection;               //Direção da ponte
    private Priority priority;              //Prioridade da ponte
    public static int aux;                            //Guarda o numero de Cars do outro lado da ponte +1


    public Bridge(Priority priority) {
        super();
        this.firstCar = null;
        this.bridgeDirection = Direction.STOP;
        this.priority = priority;
        this.releaseBridge = new Semaphore(1);
        this.car = new Semaphore(0);
        this.mutex = new Semaphore(1);
        this.aux = 0;

    }
    public static void newBridge(Priority priority){
        if(instance == null){
            instance = new Bridge(priority);
        }
    }
    public static Bridge bridge(){
        return instance;
    }

    public Semaphore getReleaseBridge() {
        return releaseBridge;
    }
    public void setReleaseBridge(Semaphore releaseBridge) {
        this.releaseBridge = releaseBridge;
    }

    public Semaphore getCar() {
        return car;
    }
    public void setCar(Semaphore Car) {
        car = Car;
    }

    public Semaphore getMutex() {
        return mutex;
    }
    public void setMutex(Semaphore mutex) {
        this.mutex = mutex;
    }

    public Direction getBridgeDirection() {
        return bridgeDirection;
    }
    public void setBridgeDirection(Direction bridgeDirection) {
        this.bridgeDirection = bridgeDirection;
    }

    public Priority getPriority() {
        return priority;
    }
    public void setPriority(Priority priority) {
        this.priority = priority;
    }

    public int getAux() {
        return aux;
    }
    public void setAux(int aux) {
        this.aux = aux;
    }
}
