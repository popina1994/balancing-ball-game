package com.example.popina.projekat.create.shape;

import android.graphics.Canvas;
import android.graphics.Color;

/**
 * Created by popina on 05.03.2017..
 */

public abstract class Figure {
    protected Coordinate center;
    protected int color;

    public Figure(Coordinate center) {
        this.center = center;
    }

    public Coordinate getCenter() {
        return center;
    }

    public void setCenter(Coordinate center) {
        this.center = center;
    }

    public int getColor() {
        return color;
    }

    public void setColor(int color) {
        this.color = color;
    }

    /**
     * @param x - x coordinate of vector
     * @param y - y coordinate of vectore
     * Moves figure for vector whose coordinates are passed. It shouldn't go over 100%.
     */
    public void moveFor(float x, float y)
    {
        center.setX(center.getX() + x);
        center.setY(center.getY() + y);
    }

    public void moveFor(Coordinate coordinate)
    {
        center.setX(center.getX() + coordinate.getX());
        center.setY(center.getY() + coordinate.getY());
    }

    public void moveTo(float x, float y)
    {
        center.setX(x);
        center.setY(y);
    }

    public void moveTo(Coordinate coordinate)
    {
        center = coordinate.clone();
    }

    public abstract void drawOnCanvas(Canvas canvas);

    public abstract boolean isCoordinateInside(Coordinate c);
}
