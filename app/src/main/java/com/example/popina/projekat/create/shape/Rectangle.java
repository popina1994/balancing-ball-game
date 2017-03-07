package com.example.popina.projekat.create.shape;

import android.content.Context;
import android.graphics.Canvas;
import android.graphics.Paint;

/**
 * Created by popina on 05.03.2017..
 */

public class Rectangle extends Figure{
    private float height;
    private float width;

    public Rectangle(float xCenter, float yCenter, float height, float width) {
        super(new Coordinate(xCenter, yCenter));
        this.height = height;
        this.width = width;
    }

    public Rectangle(Coordinate c, float height, float width)
    {
        super(c.clone());
        this.height = height;
        this.width = width;
    }

    public float getWidth() {
        return width;
    }

    public void setWidth(float width) {
        this.width = width;
    }

    public float getHeight() {
        return height;
    }

    public void setHeight(float height) {
        this.height = height;
    }

    public float getBottomY()
    {
        return super.center.getY() + height / 2;
    }

    public float getLeftX()
    {
        return super.center.getX() - width / 2;
    }

    public float getTopY()
    {
        return super.center.getY() - height / 2;
    }

    public float getRightX()
    {
        return super.center.getX() + width / 2;
    }

    public Coordinate getBotomLeft()
    {
        return new Coordinate(getLeftX(), getBottomY());
    }

    public Coordinate getTopRight()
    {
        return new Coordinate(getRightX(), getTopY());
    }

    @Override
    public void drawOnCanvas(Canvas canvas) {
        Paint p = new Paint();
        p.setColor(color);
        canvas.drawRect(getLeftX(), getTopY(), getRightX(), getBottomY(), p);
    }

    @Override
    public boolean isCoordinateInside(Coordinate c) {
        if ( (c.getX() >= getLeftX()) && (c.getX() <= getRightX()) && (c.getY() <= getBottomY()) && (c.getY() >= getTopY()))
        {
            return true;
        }
        return false;
    }
}
