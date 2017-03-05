package com.example.popina.projekat.create.shape;

/**
 * Created by popina on 05.03.2017..
 */

public abstract class Figure {
    public Coordinate center;

    public Figure(Coordinate center) {
        this.center = center;
    }

    public Coordinate getCenter() {
        return center;
    }

    public void setCenter(Coordinate center) {
        this.center = center;
    }


    /**
     * @param x - x coordinate of vector
     * @param y - y coordinate of vectore
     * Moves figure for vector whose coordinates are passed. It shouldn't go over 100%.
     */
    public void move(float x, float y)
    {
        center.setX(center.getX() + x);
        center.setY(center.getY() + y);
    }
}
