package com.example.popina.projekat.create;

import com.example.popina.projekat.create.shape.Circle;
import com.example.popina.projekat.create.shape.Coordinate;
import com.example.popina.projekat.create.shape.Rectangle;

/**
 * Created by popina on 06.03.2017..
 */

public abstract class UtilScale {
    public static float screenWidth;
    public static float screenHeight;

    public static float getScreenWidth() {
        return screenWidth;
    }

    public static void setScreenWidth(float screenWidth) {
        UtilScale.screenWidth = screenWidth;
    }

    public static float getScreenHeight() {
        return screenHeight;
    }

    public static void setScreenHeight(float screenHeight) {
        UtilScale.screenHeight = screenHeight;
    }

    public static float scaleWidth(float percentWidth)
    {
        return screenWidth * percentWidth;
    }

    public static float scaleHeight(float percentHeight)
    {
        return screenHeight * percentHeight;
    }

    public static Coordinate scaleCoordinate(Coordinate coordinate)
    {
        float scaledX = scaleWidth(coordinate.getX());
        float sclaedY = scaleHeight(coordinate.getY());

        return new Coordinate(scaledX, sclaedY);
    }

    public static Circle  scaleCircle(Circle circlePer)
    {
        Circle circle = new Circle(scaleCoordinate(circlePer.getCenter()), scaleWidth(circlePer.getRadius()));
        return circle;
    }

    public static Rectangle scaleRectangle(Rectangle rectPer) {
        Rectangle rect = new Rectangle(scaleCoordinate(rectPer.getCenter()), scaleWidth(rectPer.getWidth()), scaleHeight(rectPer.getHeight()));
        return  rect;
    }
}
