package org.example;

import java.util.Random;

public class WrongCar {
    private Vector2d position;
    private CarType type;
    private CarMap map;
    private SimulationEngine engine;
    public WrongCar(Vector2d position, CarType type, CarMap map, SimulationEngine engine){
        this.position = position;
        this.type = type;
        this.map = map;
        this.engine = engine;
    }
    public Vector2d getCarPosition(){
        return this.position;
    }
    public String toString(){
        return "W";
    }
    public void moveWrongCar(){
        Random random = new Random();
        int step = random.nextInt(this.map.getMapWidth());
        this.position = new Vector2d(step, this.engine.getWrongCarPositionY());
    }
}
