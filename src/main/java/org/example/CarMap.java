package org.example;

import java.util.ArrayList;

public class CarMap implements ICarMap{
    private int mapWidth;
    private int mapHeight;
    private int numWrongCars;
    private App app;
    protected Car car; //wlasciwe autko gracza
    protected ArrayList<WrongCar> wrongCarList; //lista aut przeszkadzajacych
    protected ArrayList<Coin> coins; //lista monet na planszy
    protected ArrayList<Car> possibleCarsToGet; //lista aut mozliwych do zdobycia przez gracza
    public CarMap(App app){
        this.wrongCarList = new ArrayList<>();
        this.coins = new ArrayList<>();
        this.possibleCarsToGet = new ArrayList<>();
        this.app = app;
    }
    public int getMapWidth(){
        return this.mapWidth;
    }
    public int getMapHeight(){
        return this.mapHeight;
    }
    public App getApp(){
        return this.app;
    }
    public void setMapWidth(int mapWidth){
        this.mapWidth = mapWidth;
    }
    public void setMapHeight(int mapHeight){
        this.mapHeight = mapHeight;
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
        boolean tmp = false;
        for (WrongCar wrongCar : this.wrongCarList) {
            if (wrongCar.getCarPosition().getY() == position.getY()) {
                tmp = true;
                break;
            }
        }
        return position.getX() == carXPosition || tmp;
    }
    public void makePossibleCars(){
        for(int i = 0; i < 5; i++){
            Car newCar;
            if(i == 0){
                newCar = new Car(this.car.getCarPosition(), CarType.T1, this);
            }
            else if(i == 1){
                newCar = new Car(this.car.getCarPosition(), CarType.T2, this);
            }
            else if(i == 2){
                newCar = new Car(this.car.getCarPosition(), CarType.T3, this);
            }
            else if(i == 3){
                newCar = new Car(this.car.getCarPosition(), CarType.T4, this);
            }
            else{
                newCar = new Car(this.car.getCarPosition(), CarType.T5, this);
            }
            this.possibleCarsToGet.add(newCar);
        }
    }
//    public WrongCar objectAtWrong(Vector2d position) {
//        for (WrongCar value : this.wrongCarList) {
//            if (value.getCarPosition().equals(position)) {
//                return value;
//            }
//        }
//        return null;
//    }
//    public Coin objectAtCoin(Vector2d position) {
//        for (Coin value : this.coins) {
//            if (value.getCoinPosition().equals(position)) {
//                return value;
//            }
//        }
//        return null;
//    }
//    public Car objectAt(Vector2d position) {
//        if(isOccupied(position)){
//            return car;
//        }
//        return null;
//    }
    //public String toString(){return new MapVisualizer(this).draw(new Vector2d(0, 0), new Vector2d(this.getMapWidth()-1, this.getMapHeight()-1));}
}
