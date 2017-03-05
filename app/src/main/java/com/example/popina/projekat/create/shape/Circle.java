package com.example.popina.projekat.create.shape;

/**
 * Created by popina on 05.03.2017..
 */

public class Circle extends Figure {
    private float radius;

    public Circle(float x, float y, float radius) {
        super(new Coordinate(x, y));
        this.radius = radius;
    }

    public float getRadius() {
        return radius;
    }

    public void setRadius(float radius) {
        this.radius = radius;
    }
}
