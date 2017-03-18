package com.example.popina.projekat.logic.game.utility;

import com.example.popina.projekat.application.game.GameModel;
import com.example.popina.projekat.logic.shape.coordinate.Coordinate;

/**
 * Created by popina on 16.03.2017..
 */

public class Utility {
    public static float FLOAT_ACCURACY = 0.01f;

    public static boolean isDistanceBetweenCoordLesThan(Coordinate coordinate1, Coordinate coordinate2,
                                                        float dist, boolean isSquared)
    {
        if (  distanceSquared(coordinate1, coordinate2)
                <= dist * ( isSquared ? 1 : dist) )
        {
            return  true;
        }
        return  false;
    }


    // Fucking accuracy.
    //
    public static boolean isOnSegment(Coordinate beginSegment, Coordinate endSegment, Coordinate point)
    {
        return  ( point.getX() + FLOAT_ACCURACY >= beginSegment.getX()  && point.getX() <= endSegment.getX() +FLOAT_ACCURACY &&
                point.getY() + FLOAT_ACCURACY >= beginSegment.getY() && point.getY() <= endSegment.getY() + FLOAT_ACCURACY);
    }

    public static float distanceSquared(Coordinate point1, Coordinate point2)
    {
        return (point1.getX() - point2.getX()) * (point1.getX() - point2.getX())
                + (point1.getY() - point2.getY()) * (point1.getY() - point2.getY());
    }
}
