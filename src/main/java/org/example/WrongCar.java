package org.example;

import java.util.Random;

public class WrongCar {
    private Vector2d position;
    private CarType type;
    private CarMap map;
    public WrongCar(Vector2d position, CarType type, CarMap map){
        this.position = position;
        this.type = type;
        this.map = map;
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
        //System.out.println("STEP");
        //System.out.println(step);
        //while (step >= this.map.getActualWrongFirstPosition().getX()){
        //step = random.nextInt(this.map.getMapWidth());
        //}
        this.position = new Vector2d(step, this.map.getActualWrongFirstPosition().getY());
    }
}
