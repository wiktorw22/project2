package org.example;

import java.util.Objects;

public class Vector2d {
    private int x;
    private int y;
    public Vector2d(int x, int y){
        this.x = x;
        this.y = y;
    }
    public int getX(){
        return this.x;
    }
    public int getY(){
        return this.y;
    }
    public void setX(int newX){
        this.x = newX;
    }
    public void setY(int newY){
        this.y = newY;
    }
    public Vector2d add(Vector2d other){
        return new Vector2d(this.x +  other.x, this.y + other.y);
    }
    public String toString(){
        return "(" + x + ", " + y + ")";
    }
    @Override
    public boolean equals(Object other) {
        if (this == other) return true;
        else if (!(other instanceof Vector2d)) return false;
        else return this.x == ((Vector2d) other).x && this.y == ((Vector2d) other).y;
    }
    @Override
    public int hashCode() { return Objects.hash(x, y); };
}
