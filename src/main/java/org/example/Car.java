package org.example;

import javafx.beans.property.DoubleProperty;
import javafx.beans.property.SimpleDoubleProperty;

public class Car {
    private Vector2d position;
    private CarType type;
    private CarMap map;
    private int sumOfCoins;
    private DoubleProperty progress;
    public Car(Vector2d position, CarType type, CarMap map){
        this.position = position;
        this.type = type;
        this.map = map;
        this.sumOfCoins = 0;
        this.progress = new SimpleDoubleProperty(0.0);
    }
    public void setProgress(double newValue){
        this.progress.set(newValue);
    }
    public DoubleProperty getProgress(){
        return this.progress;
    }
    public void setPosition(Vector2d position){
        this.position = position;
    }
    public void setSumOfCoins(int extraSum){
        this.sumOfCoins = this.sumOfCoins + extraSum;
    }
    public int getSumOfCoins(){
        return this.sumOfCoins;
    }
    public Vector2d getCarPosition(){
        return this.position;
    }
    public String toString(){
        return "C";
    }
    public void move(){
        this.position = this.position.add(new Vector2d(0, 1));
        pickingTheCoins();
        this.setProgress(this.getCarPosition().getY());
    }
    public void pickingTheCoins(){
        for(int i = 0; i < this.map.coins.size(); i++){
            if(this.map.coins.get(i).getCoinPosition().equals(this.position)){
                this.sumOfCoins = this.sumOfCoins + 1;
                this.map.coins.remove(i);
                break;
            }
        }
    }
}
