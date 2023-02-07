package org.example;

public class SimulationEngine implements IEngine {
    private Vector2d startPosition;
    private Vector2d endPosition;
    protected CarMap map;
    public SimulationEngine(Vector2d startPosition){
        this.startPosition = startPosition;
        this.endPosition = new Vector2d(startPosition.getX(), this.map.getMapHeight()-1);
    }
    @Override
    public void run(){
        //this.map.toString();
        for(int i = 0; i < this.map.getMapHeight(); i++){
            this.map.car.move();
            System.out.println(this.map.toString());
            System.out.println();
        }
    }
}
