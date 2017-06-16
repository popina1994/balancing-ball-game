package com.example.popina.projekat.logic.game.utility;

import com.example.popina.projekat.application.game.GameModel;
import com.example.popina.projekat.logic.shape.coordinate.Coordinate;

/**
 * Created by popina on 16.03.2017..
 */

public class Utility {
    public static float FLOAT_ACCURACY = 0.01f;
    public static float S_MS = 1000;
    public static final float S_NS = 1000000000;

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

    public static float convertMsToS(float ms)
    {
        return  ms / S_MS;
    }

    public static float convertNsToS(float ns)
    {
        return  ns / S_NS;
    }

    public static boolean doesSegmentIntersectsCircle(Coordinate beginSegment, Coordinate endSegment, Coordinate center, float radius,
                                                boolean isXLine)
    {

        // End/begin segment circle intersection.
        //
        if (Utility.isDistanceBetweenCoordLesThan(beginSegment, center, radius, false)
            || Utility.isDistanceBetweenCoordLesThan(endSegment, center, radius, false))
        {
            return true;
        }

        // "Inside" segment circle intersection
        //
        if (isXLine)
        {
            return Utility.isDimBetweenDims(beginSegment.getX(), endSegment.getX(), center.getX())
                    && Math.abs(center.getY() - beginSegment.getY()) <= Utility.FLOAT_ACCURACY + radius;
        }
        else
        {
            return Utility.isDimBetweenDims(beginSegment.getY(), endSegment.getY(), center.getY())
                    && Math.abs(center.getX() - beginSegment.getX()) <= Utility.FLOAT_ACCURACY + radius;
        }
    }

    public static float opositeSign(float val) {
        if (val < 0) {
            return 1;
        } else if (val > 0) {
            return -1;
        }

        return 0;

    }

}
