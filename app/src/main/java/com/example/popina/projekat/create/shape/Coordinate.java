package com.example.popina.projekat.create.shape;

/**
 * Created by popina on 05.03.2017..
 */

public class Coordinate implements Cloneable{
    private float x;
    private float y;

    public Coordinate(float x, float y) {
        this.x = x;
        this.y = y;
    }

    public float getX() {
        return x;
    }

    public void setX(float x) {
        this.x = x;
    }

    public float getY() {
        return y;
    }

    public void setY(float y) {
        this.y = y;
    }

    @Override
    protected Coordinate clone() {
        return new Coordinate(x, y);
    }
}
