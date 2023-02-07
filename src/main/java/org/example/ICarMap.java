package org.example;

public interface ICarMap {
    public boolean canMoveTo(Vector2d position);
    public boolean isOccupied(Vector2d position);
    //boolean place(Car car);
    Car objectAt(Vector2d position);
}
