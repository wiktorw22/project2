package org.example;

import java.awt.event.MouseEvent;

public class SimulationEngine implements IEngine, Runnable {
    private Vector2d startPosition;
    private Vector2d endPosition;
    protected CarMap map;
    private int numberOfWrongCars;
    private Vector2d wrongCarPosition;
    private int moveDelay;
    private App app;

    public SimulationEngine(Vector2d startPosition, int numberOfWrongCars, Vector2d wrongCarPosition, CarMap map, App app){
        this.startPosition = startPosition;
        this.map = map;
        this.endPosition = new Vector2d(startPosition.getX(), this.map.getMapHeight()-1);
        this.numberOfWrongCars = numberOfWrongCars;
        this.wrongCarPosition = wrongCarPosition;
        this.app = app;
    }
    public void setMoveDelay(int delay){
        this.moveDelay = delay;
    }
    public Vector2d findMinWrongCarPosition(){
        Vector2d res = new Vector2d((int)Double.POSITIVE_INFINITY, (int)Double.POSITIVE_INFINITY);
        int resX;
        int resY;
        for(int i = 0; i < this.map.wrongCarList.size(); i++){
            if(!this.map.wrongCarList.get(i).getCarPosition().equals(this.wrongCarPosition)) {
                resX = Math.min(res.getX(), this.map.wrongCarList.get(i).getCarPosition().getX());
                resY = this.map.wrongCarList.get(i).getCarPosition().getY();
            }
            else{
                resX = this.wrongCarPosition.getX();
                resY = this.wrongCarPosition.getY();
            }
            res = new Vector2d(resX, resY);
        }
        return res;
    }
    @Override
    public void run() {
        //this.map.toString();
        this.map.setActualWrongFirstPosition(this.wrongCarPosition);
//        for (int i = 0; i < this.numberOfWrongCars; i++) {
//            this.map.place(wrongCarPosition, new WrongCar(wrongCarPosition, CarType.T2, this.map));
//        }
        this.app.newGrid();
        //for (int i = 0; i < this.map.getMapHeight(); i++){
//            try {
//                Thread.sleep(moveDelay);
//            } catch (InterruptedException e) {
//                throw new RuntimeException(e);
//            }
//            for (int j = 0; j < this.numberOfWrongCars; j++) {
//                WrongCar tmpCar = this.map.wrongCarList.get(j);
//                //Vector2d tmpPosition = this.map.wrongCarList.get(i).getCarPosition();
//                tmpCar.moveWrongCar();
//                //System.out.println(tmpCar.getCarPosition());
//                this.map.setActualWrongFirstPosition(this.findMinWrongCarPosition());
//            }
            try {
                Thread.sleep(moveDelay);
            } catch (InterruptedException e) {
                throw new RuntimeException(e);
            }
            this.map.car.move();
            this.app.refresh();
            //this.app.newGrid();
            System.out.println(this.map.toString());
            //System.out.println("TUTAJ");
        //}
    }
}
