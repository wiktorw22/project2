package org.example;

public class Car {
    private Vector2d position;
    private CarType type;
    public Car(Vector2d position, CarType type){
        this.position = position;
        this.type = type;
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
