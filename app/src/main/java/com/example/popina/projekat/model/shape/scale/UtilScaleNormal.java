package com.example.popina.projekat.model.shape.scale;

import com.example.popina.projekat.model.shape.coordinate.Coordinate;

/**
 * Created by popina on 06.03.2017..
 * Singleton pattern would be best, but this solves small issues.
 */

public class UtilScaleNormal extends UtilScale{
    public UtilScaleNormal(float screenWidth, float screenHeight) {
        super(screenWidth, screenHeight);
    }

    public float scaleWidth(float percentWidth)
    {
        return screenWidth * percentWidth;
    }

    public float scaleHeight(float percentHeight)
    {
        return screenHeight * percentHeight;
    }

    public Coordinate scaleCoordinate(Coordinate coordinate)
    {
        float scaledX = scaleWidth(coordinate.getX());
        float sclaedY = scaleHeight(coordinate.getY());

        return new Coordinate(scaledX, sclaedY);
    }

    public float scaleReverseWidth(float width) { return width / screenWidth; }

    public float scaleReverseHeight(float height) { return height / screenHeight; }

    public Coordinate scaleReverseCoordinate(Coordinate coordinate)
    {
        float perX = scaleReverseWidth(coordinate.getX());
        float perY = scaleReverseHeight(coordinate.getY());

        return new Coordinate(perX, perY);
    }

}
