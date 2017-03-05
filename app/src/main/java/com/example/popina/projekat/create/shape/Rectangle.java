package com.example.popina.projekat.create.shape;

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
        return super.center.getY() - height / 2;
    }

    public float getLeftX()
    {
        return super.center.getX() - width / 2;
    }

    public float getTopY()
    {
        return super.center.getY() + height / 2;
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
}
