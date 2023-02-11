package org.example;

public class MovingWrongCars implements Runnable {
    private CarMap map;
    public MovingWrongCars(CarMap map){
        this.map = map;
    }
    @Override
    public void run() {
        int cnt = 0;
        while(cnt < 5){
            for(int i = 0; i < this.map.wrongCarList.size(); i++){
                this.map.wrongCarList.get(i).moveWrongCar();
            }
            cnt++;
        }
    }
}
