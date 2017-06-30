package com.example.popina.projekat.logic.game.utility;

import com.example.popina.projekat.logic.shape.coordinate.Coordinate;

/**
 * Created by popina on 16.03.2017..
 */

public class Utility
{
    public static final float S_NS = 1000000000;
    public static float FLOAT_ACCURACY = 0.01f;
    public static float S_MS = 1000;

    public static boolean isDistanceBetweenCoordLesThan(Coordinate coordinate1, Coordinate coordinate2,
                                                        float dist, boolean isSquared)
    {
        if (distanceSquared(coordinate1, coordinate2)
                <= dist * (isSquared ? 1 : dist) + FLOAT_ACCURACY)
        {
            return true;
        }
        return false;
    }

    public static boolean isDimBetweenDims(float dimBegin, float dimEnd, float dim)
    {
        return (dimBegin - FLOAT_ACCURACY <= dim) && (dimEnd + FLOAT_ACCURACY >= dim);
    }


    // Fucking accuracy.
    //
    public static boolean isOnSegment(Coordinate beginSegment, Coordinate endSegment, Coordinate point)
    {
        return (isDimBetweenDims(beginSegment.getX(), endSegment.getX(), point.getX()) &&
                isDimBetweenDims(beginSegment.getY(), endSegment.getY(), point.getY()));
    }

    public static float distanceSquared(Coordinate point1, Coordinate point2)
    {
        return (point1.getX() - point2.getX()) * (point1.getX() - point2.getX())
                + (point1.getY() - point2.getY()) * (point1.getY() - point2.getY());
    }

    public static void swap(float var1, float var2)
    {
        float tmp = var1;
        var1 = var2;
        var2 = tmp;
    }

    public static float convertMsToS(float ms)
    {
        return ms / S_MS;
    }

    public static float convertNsToS(float ns)
    {
        return ns / S_NS;
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
        } else
        {
            return Utility.isDimBetweenDims(beginSegment.getY(), endSegment.getY(), center.getY())
                    && Math.abs(center.getX() - beginSegment.getX()) <= Utility.FLOAT_ACCURACY + radius;
        }
    }

    public static float opositeSign(float val)
    {
        if (val < 0)
        {
            return 1;
        } else if (val > 0)
        {
            return -1;
        }

        return 0;
    }

    public static double degToRadian(float deg)
    {
        return deg * 2 * Math.PI / 360;
    }

    public static double radianToDeg(float rad)
    {
        return 360 * rad / (2 * Math.PI);
    }

    public static float calculateAngle(Coordinate center, Coordinate point)
    {
        float angle = (float)Math.atan2(point.getY() - center.getY(), point.getX() - center.getX());
        if (angle < 0)
        {
            angle += 2 * Math.PI;
        }
        return  angle;
    }

    public static  Coordinate rotatePointAroundCenter(Coordinate center, double sine, double cosine, Coordinate point)
    {
        float pointTranX = point.getX() - center.getX();
        float pointTranY = point.getY()  - center.getY();

        double rotatedX = pointTranX * cosine - pointTranY * sine;
        double rotatedY = pointTranX * sine + pointTranY* cosine;

        float rotatedTranBackX = (float)rotatedX + center.getX();
        float rotatedTranBackY = (float)rotatedY + center.getY();

        return new Coordinate(rotatedTranBackX, rotatedTranBackY);
    }


    public static Coordinate rotatePointAroundCenter(Coordinate center, float angle, Coordinate point)
    {
        double cosine = Math.cos(angle);
        double sine = Math.sin(angle);

        return rotatePointAroundCenter(center, sine, cosine, point);
    }

    public static Coordinate rotatePointAroundCenter(float angle, Coordinate point)
    {
        return rotatePointAroundCenter(new Coordinate(0, 0), angle, point);
    }

    public static Coordinate rotatePointAroundCenter(double sine, double cosine, Coordinate point)
    {
        return rotatePointAroundCenter(new Coordinate(0, 0), sine, cosine, point);
    }

    public static float convertRadianAngleTo2PiRange(float angle)
    {
        float cnt = (float)Math.abs(Math.floor(angle / (2 * Math.PI))) ;


        if ( (angle < 0) || (angle >= 2 * Math.PI))
        {
            angle += opositeSign(angle) * 2 * Math.PI * cnt;
        }
        return angle;
    }

    public static double randomNumberInInterval(int startInterval, int endInterval)
    {
        return Math.random() * (endInterval - startInterval) + startInterval;
    }

}
