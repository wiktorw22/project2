package org.example;
public class Coin {
    private int value; //wartosc kazdej z monet na trasie
    private Vector2d position;
    public Coin(Vector2d position){
        this.value = 1;
        this.position = position;
    }
    public Vector2d getCoinPosition(){
        return this.position;
    }
    public String toString(){
        return "$"; //monety na planszy
    }
}
