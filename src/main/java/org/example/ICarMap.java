package org.example;

public interface ICarMap {
    boolean isOccupied(Vector2d position);
    boolean isOccupiedWrong(Vector2d position);
    boolean isOccupiedCoin(Vector2d position);
    boolean isOccupiedAsphalt(Vector2d position);
}
