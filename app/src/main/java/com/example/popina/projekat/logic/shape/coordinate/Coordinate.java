package com.example.popina.projekat.logic.shape.coordinate;

/**
 * Created by popina on 05.03.2017..
 */

public class Coordinate implements Cloneable
{
    private float x;
    private float y;

    public Coordinate(float x, float y)
    {
        this.x = x;
        this.y = y;
    }

    public float getX()
    {
        return x;
    }

    public void setX(float x)
    {
        this.x = x;
    }

    public float getY()
    {
        return y;
    }

    public void setY(float y)
    {
        this.y = y;
    }

    @Override
    public Coordinate clone()
    {
        return new Coordinate(x, y);
    }

    @Override
    public String toString()
    {
        return Float.toString(x) + " " + Float.toString(y) + " ";
    }

    public Coordinate addToThisCoordinate(Coordinate coordinate)
    {
        x += coordinate.getX();
        y += coordinate.getY();
        return this;
    }

    public Coordinate addCoordinate(Coordinate coordinate)
    {
        return new Coordinate(x + coordinate.x, y + coordinate.y);
    }

    public Coordinate mulThisScalar(float scalar)
    {
        x *= scalar;
        y *= scalar;
        return this;
    }

    public float scalarProduct(Coordinate coord)
    {
        return x * coord.x + y * coord.y;
    }

    public Coordinate subCoordinate(Coordinate coordinate)
    {
        return new Coordinate(x - coordinate.x, y - coordinate.y);
    }

    public float magnitudeSquared()
    {
        return x * x + y * y;
    }

}
