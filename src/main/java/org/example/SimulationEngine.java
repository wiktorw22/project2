package org.example;

import java.util.Random;

public class SimulationEngine implements IEngine, Runnable {
    private Vector2d startPosition;
    private Vector2d endPosition;
    protected CarMap map;
    private int numberOfWrongCars;
    private int wrongCarPositionY;
    private int moveDelay;
    private App app;

    public SimulationEngine(Vector2d startPosition, int numberOfWrongCars, CarMap map, App app){
        this.startPosition = startPosition;
        this.map = map;
        this.endPosition = new Vector2d(startPosition.getX(), this.map.getMapHeight()-1);
        this.numberOfWrongCars = numberOfWrongCars;
        this.app = app;
    }
    public Vector2d getStartPosition(){
        return this.startPosition;
    }
    public void setWrongCarPositionY(int positionY){
        this.wrongCarPositionY = positionY;
    }
    public int getWrongCarPositionY(){
        return this.wrongCarPositionY;
    }
    public void setMoveDelay(int delay){
        this.moveDelay = delay;
    }
    public void addCoins(int amountOfCoins){
        int randomX = this.map.car.getCarPosition().getX();
        for(int i = 0; i < amountOfCoins; i++){
            Random random = new Random();
            int randomY = random.nextInt(this.map.getMapHeight());
            Vector2d newPosition = new Vector2d(randomX, randomY);
            Coin coin = new Coin(newPosition);
            this.map.coins.add(coin);
        }
    }
    public void addWrongCars(int amountOfWrongCars){ //dla poziomu numer 1 beda one w jednej linii poziomej jezdzily
        Random random = new Random();
        int randomY = random.nextInt(this.map.getMapHeight());
        this.setWrongCarPositionY(randomY);
        for(int i = 0; i < amountOfWrongCars; i++){
            int randomX = random.nextInt(this.map.getMapWidth());
            Vector2d newPosition = new Vector2d(randomX, randomY);
            WrongCar wrongCar = new WrongCar(newPosition, CarType.T2, this.map, this);
            this.map.wrongCarList.add(wrongCar);
        }
    }
    public void removeCoins(){
        this.map.coins.clear();
    }
    public void removeWrongCars(){
        this.map.wrongCarList.clear();
    }
    @Override
    public void run() {

        removeCoins();
        removeWrongCars();
        addCoins(2); //umiesc monety na mapie
        //System.out.println(this.map.coins.size());
        addWrongCars(2); //umiesc autka przeszkadzajace
        //this.map.setActualWrongFirstPosition(this.wrongCarPosition);
        this.map.car.pickingTheCoins();
        this.app.refresh();


    }
}
