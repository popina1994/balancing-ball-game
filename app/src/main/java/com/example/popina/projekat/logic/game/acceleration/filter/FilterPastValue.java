package com.example.popina.projekat.logic.game.acceleration.filter;

import com.example.popina.projekat.logic.game.utility.Coordinate3D;

/**
 * Created by popina on 12.03.2017..
 */

public class FilterPastValue implements FilterInterface
{
    public static float ALPHA = 0.9f;
    private Coordinate3D oldVals;
    private float alpha;

    public FilterPastValue(float alpha)
    {
        this.alpha = alpha;
    }

    public Coordinate3D filter(Coordinate3D newVals)
    {
        if (null == oldVals)
        {
            oldVals = newVals;
        } else
        {
            oldVals.setX(filterVal(oldVals.getX(), newVals.getX()));
            oldVals.setY(filterVal(oldVals.getY(), newVals.getY()));
            oldVals.setZ(filterVal(oldVals.getZ(), newVals.getZ()));
        }

        return oldVals.clone();
    }

    private float filterVal(float oldVal, float newVal)
    {
        return newVal * alpha + (1 - alpha) * oldVal;
    }
}
