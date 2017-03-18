package com.example.popina.projekat.logic.shape.figure.circle;

import android.graphics.Canvas;
import android.graphics.Paint;

import com.example.popina.projekat.logic.shape.coordinate.Coordinate;
import com.example.popina.projekat.logic.shape.figure.Figure;

/**
 * Created by popina on 05.03.2017..
 */

public abstract  class Circle extends Figure {
    private float radius;

    public Circle(float x, float y, float radius, String figureType) {
        super(new Coordinate(x, y), figureType);
        this.radius = radius;
    }

    public Circle(Coordinate c, float radius, String figureType)
    {
        super(c.clone(), figureType);
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

    @Override
    public void resize(Coordinate c) {
        radius = (float)Math.sqrt( (c.getX() - center.getX()) * (c.getX() - center.getX()) +  (c.getY() - center.getY()) * (c.getY() - center.getY()));
    }

    @Override
    public boolean hits(Circle ball) {
        return  isCoordinateInside(ball.getCenter());
    }


    @Override
    public void collide() {

    }

    @Override
    public String toString() {
        return super.toString() + " " + Float.toString(radius) + " ";
    }

}
