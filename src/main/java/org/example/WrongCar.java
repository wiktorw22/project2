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
    public void setPosition(Vector2d vector2d) {
        this.position = vector2d;
    }
    public String toString(){
        return "W";
    }
}
