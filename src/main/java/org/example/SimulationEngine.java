package org.example;

import java.util.Random;

public class SimulationEngine implements IEngine, Runnable {
    private int moveDelay;
    private Vector2d startPosition;
    private Vector2d endPosition;
    protected CarMap map;
    private App app;
    public SimulationEngine(Vector2d startPosition, CarMap map, App app){
        this.startPosition = startPosition;
        this.map = map;
        this.endPosition = new Vector2d(startPosition.getX(), this.map.getMapHeight()-1);
        this.app = app;
    }
    public Vector2d getStartPosition(){
        return this.startPosition;
    }
    public void setMoveDelay(int delay){
        this.moveDelay = delay;
    }
    public void setNewCar(int randomNumber){
        if(randomNumber == 0){
            this.map.car = new Car(this.getStartPosition(), CarType.T1, this.map);
        }
        else if(randomNumber == 1){
            this.map.car = new Car(this.getStartPosition(), CarType.T2, this.map);
        }
        else if(randomNumber == 2){
            this.map.car = new Car(this.getStartPosition(), CarType.T3, this.map);
        }
        else if(randomNumber == 3){
            this.map.car = new Car(this.getStartPosition(), CarType.T4, this.map);
        }
        else{
            this.map.car = new Car(this.getStartPosition(), CarType.T5, this.map);
        }
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
    public void addWrongCars(int amountOfWrongCars, int numberOfLevel){ //dla poziomu numer 1 beda one w jednej linii poziomej jezdzily
        int cnt = 0;
        while(cnt < numberOfLevel){
            Random randomly = new Random();
            int randomY = randomly.nextInt(this.map.getMapHeight());
            for(int i = 0; i < amountOfWrongCars; i++){
                Random randoms = new Random();
                int randomX = randoms.nextInt(this.map.getMapWidth());
                Vector2d newPosition = new Vector2d(randomX, randomY);
                WrongCar wrongCar = new WrongCar(newPosition, CarType.T2, this.map, this);
                this.map.wrongCarList.add(wrongCar);
            }
            cnt++;
        }
    }
    //addWrongCars jest inne dla kazdego kolejnego poziomu!
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
        addCoins(2); //umiesc monety na mapie w liczbie dwoch (na przyklad)
        addWrongCars(1, this.app.getNumberOfLevel()); //umiesc autka przeszkadzajace w liczbie dwoch (na przyklad)
        this.map.car.pickingTheCoins();
        this.app.refresh(this.app.getRandomNumber());

    }
}
