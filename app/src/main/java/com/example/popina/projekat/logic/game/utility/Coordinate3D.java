package com.example.popina.projekat.logic.game.utility;

/**
 * Created by popina on 12.03.2017..
 */

public class Coordinate3D implements Cloneable
{
    private float x;
    private float y;
    private float z;

    public Coordinate3D(float x, float y, float z)
    {
        this.x = x;
        this.y = y;
        this.z = z;
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

    public float getZ()
    {
        return z;
    }

    public void setZ(float z)
    {
        this.z = z;
    }

    @Override
    public Coordinate3D clone()
    {
        return new Coordinate3D(x, y, z);
    }
}
