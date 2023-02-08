package org.example;

import java.util.Random;

public class Car {
    private Vector2d position;
    private CarType type;
    private CarMap map;
    public Car(Vector2d position, CarType type, CarMap map){
        this.position = position;
        this.type = type;
        this.map = map;
    }
    public Vector2d getCarPosition(){
        return this.position;
    }
    public String toString(){
        return "C";
    }
    public void move(){
        this.position = this.position.add(new Vector2d(0, 1));
    }
}
