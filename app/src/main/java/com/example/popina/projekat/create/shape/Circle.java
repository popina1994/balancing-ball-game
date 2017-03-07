package com.example.popina.projekat.create.shape;

import android.graphics.Canvas;
import android.graphics.Paint;

/**
 * Created by popina on 05.03.2017..
 */

public class Circle extends Figure {
    private float radius;

    public Circle(float x, float y, float radius) {
        super(new Coordinate(x, y));
        this.radius = radius;
    }

    public Circle(Coordinate c, float radius)
    {

        super(c.clone());
        this.radius = radius;
    }

    public float getRadius() {
        return radius;
    }

    public void setRadius(float radius) {
        this.radius = radius;
    }

    @Override
    public void drawOnCanvas(Canvas canvas) {
        Paint p = new Paint();
        p.setColor(color);
        canvas.drawCircle(getCenter().getX(), getCenter().getY(), radius, p);
    }

    @Override
    public boolean isCoordinateInside(Coordinate c) {
        if ( (c.getX() - center.getX()) * (c.getX() - center.getX()) +  (c.getY() - center.getY()) * (c.getY() - center.getY()) <= radius  * radius)
        {
            return true;
        }
        return false;
    }
}
