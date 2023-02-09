package org.example;

import java.util.ArrayList;

public class CarMap implements ICarMap{
    private int mapWidth;
    private int mapHeight;
    private int numWrongCars;
    //private Vector2d actualWrongFirstPosition;
    protected ArrayList<WrongCar> wrongCarList; //lista aut przeszkadzajacych
    protected ArrayList<Coin> coins; //lista monet na planszy
    protected Car car; //wlasciwe autko gracza
    public CarMap(Vector2d position, CarType type){
        this.wrongCarList = new ArrayList<>();
        this.coins = new ArrayList<>();
        this.car = new Car(position, type, this);
    }
    public int getNumWrongCars(){
        return this.numWrongCars;
    }
//    public Vector2d getActualWrongFirstPosition(){
//        return this.actualWrongFirstPosition;
//    }
    public void setMapWidth(int mapWidth){
        this.mapWidth = mapWidth;
    }
    public void setMapHeight(int mapHeight){
        this.mapHeight = mapHeight;
    }
    public void setNumWrongAnimals(int numWrongAnimals){
        this.numWrongCars = numWrongAnimals;
    }
//    public void setActualWrongFirstPosition(Vector2d position){
//        this.actualWrongFirstPosition = position;
//    }
    public int getMapWidth(){
        return this.mapWidth;
    }
    public int getMapHeight(){
        return this.mapHeight;
    }
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
    public boolean isOccupiedCoin(Vector2d position) {
        for (Coin value : this.coins) {
            if (value.getCoinPosition().equals(position)) {
                return true;
            }
        }
        return false;
    }
    public boolean isOccupiedAsphalt(Vector2d position){
        int carXPosition = this.car.getCarPosition().getX();
        //int wrongCarsYPosition = this.wrongCarList.get(0).getCarPosition().getY();
        return position.getX() == carXPosition;// || position.getY() == wrongCarsYPosition;
    }
    public WrongCar objectAtWrong(Vector2d position) {
        for (WrongCar value : this.wrongCarList) {
            if (value.getCarPosition().equals(position)) {
                return value;
            }
        }
        return null;
    }
    public Coin objectAtCoin(Vector2d position) {
        for (Coin value : this.coins) {
            if (value.getCoinPosition().equals(position)) {
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
