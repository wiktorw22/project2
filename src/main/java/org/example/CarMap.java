package org.example;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;

public class CarMap implements ICarMap{
    private int mapWidth;
    private int mapHeight;
    private int numWrongAnimals;
    protected Map<Vector2d, Car> wrongCars; //auta przeszkadzajace
    protected Car car; //wlasciwe autko gracza
    public CarMap(Vector2d position, CarType type){
        this.wrongCars = new HashMap<>();
        this.car = new Car(position, type);
    }
    public void setMapWidth(int mapWidth){
        this.mapWidth = mapWidth;
    }
    public void setMapHeight(int mapHeight){
        this.mapHeight = mapHeight;
    }
    public void setNumWrongAnimals(int numWrongAnimals){
        this.numWrongAnimals = numWrongAnimals;
    }
    public int getMapWidth(){
        return this.mapWidth;
    }
    public int getMapHeight(){
        return this.mapHeight;
    }
//    public Vector2d getStartPosition(){
//        return this.car.getCarPosition()startPosition;
//    }
    @Override
    public boolean canMoveTo(Vector2d position) {
        if(position.getX() >= 0 && position.getX() < mapWidth && position.getY() >= 0 && position.getY() < mapHeight){
            return !this.isOccupied(position);
        }
        return false;
    }
    @Override
    public boolean isOccupied(Vector2d position) {
        return car.getCarPosition().equals(position);
    }
//    @Override
//    public boolean place(Car car) {
//        if(canMoveTo(car.getCarPosition())){
//            cars.put(car.getCarPosition(), car);
//            return true;
//        }
//        return false;
//    }
    public Car objectAt(Vector2d position) {
        if(isOccupied(position)){
            return car;
        }
        return null;
    }
    public String toString(){return new MapVisualizer(this).draw(new Vector2d(0, 0), new Vector2d(this.getMapWidth()-1, this.getMapHeight()-1));}

}
