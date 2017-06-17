package com.example.popina.projekat.logic.shape.scale;

import com.example.popina.projekat.logic.shape.coordinate.Coordinate;

/**
 * Created by popina on 08.03.2017..
 */

public abstract class UtilScale
{
    public float screenWidth;
    public float screenHeight;

    public UtilScale(float screenWidth, float screenHeight)
    {
        this.screenWidth = screenWidth;
        this.screenHeight = screenHeight;
    }

    public float getScreenWidth()
    {
        return screenWidth;
    }

    public void setScreenWidth(float screenWidth)
    {
        this.screenWidth = screenWidth;
    }

    public float getScreenHeight()
    {
        return screenHeight;
    }

    public void setScreenHeight(float screenHeight)
    {
        this.screenHeight = screenHeight;
    }

    public abstract float scaleWidth(float percentWidth);

    public abstract float scaleHeight(float percentHeight);

    public abstract Coordinate scaleCoordinate(Coordinate coordinate);

    public abstract float scaleReverseWidth(float width);

    public abstract float scaleReverseHeight(float height);

    public abstract Coordinate scaleReverseCoordinate(Coordinate coordinate);
}
