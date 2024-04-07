package model;

import enums.ApplicationState;
import enums.Direction;


public class Car extends Thread {
    private Integer id;
    private Double waitingTime;
    private Double crossingTime;
    private ApplicationState state;
    private Direction carDirection;


    private Double expectedTime;
    private Double timeCrossing;

    public Car(Integer id, Double waitingTime, Double crossingTime,
               ApplicationState state, Direction carDirection) {
        super();
        this.id = id;
        this.waitingTime = waitingTime;
        this.crossingTime = crossingTime;
        this.state = state;
        this.carDirection = carDirection;
    }

    public Car(Integer id, Double waitingTime, Double crossingTime,
               Direction carDirection) {
        super();
        this.id = id;
        this.waitingTime = waitingTime;
        this.crossingTime = crossingTime;
        this.state = state.STOPPED;
        this.carDirection = carDirection;
    }

    @Override
    public void run() {
        double lastTime, actualTime;
        actualTime = 0;
        lastTime = System.currentTimeMillis();
        expectedTime = timeCrossing = 0.0;
        Double actualTimeCrossing;
        try {
            while(true){

                if(state == state.STOPPED){
                    actualTime = System.currentTimeMillis();
                    expectedTime += actualTime - lastTime;
                    lastTime = actualTime;
                    if(expectedTime/1000 >= waitingTime){
                        state = ApplicationState.WAITING;
                        expectedTime = 0.0;
                    }
                }
                else if(state == ApplicationState.WAITING){
                    Bridge.bridge().getMutex().acquire();

                    if((Bridge.bridge().getBridgeDirection()== Direction.STOP)||(carDirection != Bridge.bridge().getBridgeDirection())) {
                        if (carDirection != Bridge.bridge().getBridgeDirection() && Bridge.bridge().getBridgeDirection() != Direction.STOP) {
                            Bridge.bridge().setAux(Bridge.bridge().getAux() + 1);
                        }
                        Bridge.bridge().getMutex().release();
                        Bridge.bridge().getReleaseBridge().acquire();
                        Bridge.bridge().getMutex().acquire();
                        Bridge.bridge().setBridgeDirection(carDirection);
                    }
                    Bridge.bridge().getCar().release();
                    Bridge.bridge().getMutex().release();
                    state = ApplicationState.CROSSING;
                    timeCrossing = 0.0;
                    actualTime = 0.0;
                    lastTime = System.currentTimeMillis();
                }
                else if(state == ApplicationState.CROSSING){
                    actualTime = System.currentTimeMillis();
                    timeCrossing += actualTime - lastTime;
                    lastTime = actualTime;
                    if(timeCrossing/1000 >= timeCrossing){
                        Bridge.bridge().getMutex().acquire();
                        Bridge.bridge().getCar().acquire();
                        if(Bridge.bridge().getCar().availablePermits() == 0) {
                            if (Bridge.bridge().getAux() == 0) {
                                Bridge.bridge().setBridgeDirection(Direction.STOP);
                                Bridge.bridge().getReleaseBridge().release();
                                Bridge.bridge().setAux(0);
                            }
                            else {
                                Bridge.bridge().setBridgeDirection(Direction.STOP);
                                Bridge.bridge().getReleaseBridge().release(Bridge.bridge().getAux());
                                Bridge.bridge().setAux(0);
                            }

                        }
                        Bridge.bridge().getMutex().release();
                        CarHandler.handler().changeCarDirection(this);
                        state = state.STOPPED;
                        expectedTime = 0.0;
                        actualTime = 0.0;
                        lastTime = System.currentTimeMillis();
                    }
                }
            }
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
    }
    public long getId() {
        return id;
    }
    public void setId(Integer id) {
        this.id = id;
    }

    public Double getWaitingTime() {
        return waitingTime;
    }
    public void setWaitingTime(Double waitingTime) {
        this.waitingTime = waitingTime;
    }

    public Double getCrossingTime() {
        return this.crossingTime;
    }
    public void setCrossingTime(Double crossingTime) {
        this.timeCrossing = crossingTime;
    }

    public ApplicationState getApplicationState() {
        return this.state;
    }
    public void setApplicationState(ApplicationState state) {
        this.state = state;
    }

    public Double getExpectedTime() {
        return this.expectedTime;
    }
    public void setExpectedTime(Double expectedTime) {
        this.expectedTime = expectedTime;
    }

    public Direction getCarDirection() {
        return carDirection;
    }
    public void setCarDirection(Direction carDirection) {
        this.carDirection = carDirection;
    }

    public Double getTimeCrossing() {
        return this.timeCrossing;
    }
    public void setTimeCrossing(Double timeCrossing) {
        this.timeCrossing = timeCrossing;
    }

}
