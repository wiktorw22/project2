package org.example;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;

public class CarMap implements ICarMap{
    private int mapWidth;
    private int mapHeight;
    private int numWrongCars;
    private Vector2d actualWrongFirstPosition;
    //protected Map<Vector2d, Car> wrongCars; //auta przeszkadzajace
    protected ArrayList<WrongCar> wrongCarList; //lista aut przeszkadzajacych
    protected Car car; //wlasciwe autko gracza
    public CarMap(Vector2d position, CarType type){
        //this.wrongCars = new HashMap<>();
        this.wrongCarList = new ArrayList<>();
        this.car = new Car(position, type, this);
    }
    public int getNumWrongCars(){
        return this.numWrongCars;
    }
    public Vector2d getActualWrongFirstPosition(){
        return this.actualWrongFirstPosition;
    }
    public void setMapWidth(int mapWidth){
        this.mapWidth = mapWidth;
    }
    public void setMapHeight(int mapHeight){
        this.mapHeight = mapHeight;
    }
    public void setNumWrongAnimals(int numWrongAnimals){
        this.numWrongCars = numWrongAnimals;
    }
    public void setActualWrongFirstPosition(Vector2d position){
        this.actualWrongFirstPosition = position;
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
    public void place(Vector2d position, WrongCar wrongCar) {
        //wrongCars.put(position, wrongCar);
        wrongCarList.add(wrongCar);
    }
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

    public boolean isOccupiedWrong(Vector2d position) {
        for (WrongCar value : this.wrongCarList) {
            if (value.getCarPosition().equals(position)) {
                return true;
            }
        }
        return false;
    }
//    @Override
//    public boolean place(Car car) {
//        if(canMoveTo(car.getCarPosition())){
//            cars.put(car.getCarPosition(), car);
//            return true;
//        }
//        return false;
//    }
    public WrongCar objectAtWrong(Vector2d position) {
        for (WrongCar value : this.wrongCarList) {
            if (value.getCarPosition().equals(position)) {
                return value;
            }
        }
        return null;
    }
    public Car objectAt(Vector2d position) {
        if(isOccupied(position)){
            return car;
        }
        return null;
    }
    public String toString(){return new MapVisualizer(this).draw(new Vector2d(0, 0), new Vector2d(this.getMapWidth()-1, this.getMapHeight()-1));}

}
