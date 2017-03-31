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
                <= dist * ( isSquared ? 1 : dist) + FLOAT_ACCURACY )
        {
            return  true;
        }
        return  false;
    }

    public static boolean isDimBetweenDims(float dimBegin, float dimEnd, float dim)
    {
        return (dimBegin - FLOAT_ACCURACY <= dim) && (dimEnd + FLOAT_ACCURACY >= dim);
    }


    // Fucking accuracy.
    //
    public static boolean isOnSegment(Coordinate beginSegment, Coordinate endSegment, Coordinate point)
    {
        return  ( isDimBetweenDims(beginSegment.getX(), endSegment.getX(), point.getX())&&
                    isDimBetweenDims(beginSegment.getY(), endSegment.getY(), point.getY()));
    }

    public static float distanceSquared(Coordinate point1, Coordinate point2)
    {
        return (point1.getX() - point2.getX()) * (point1.getX() - point2.getX())
                + (point1.getY() - point2.getY()) * (point1.getY() - point2.getY());
    }

    public static void swap(float var1, float var2) {
        float tmp = var1;
        var1 = var2;
        var2 = tmp;
    }
}
