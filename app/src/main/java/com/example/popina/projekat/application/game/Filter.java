package com.example.popina.projekat.logic.game;

import com.example.popina.projekat.logic.game.Coordinate3D;

/**
 * Created by popina on 12.03.2017..
 */

public class Filter {

    private Coordinate3D oldVals;
    private float alpha;

    public Filter(float alpha) {
        this.alpha = alpha;
    }

    public void filter(Coordinate3D newVals) {
        if (null == oldVals)
        {
            oldVals = newVals;
        }
        else
        {
            oldVals.setX(filterVal(oldVals.getX(), newVals.getX()));
            oldVals.setY(filterVal(oldVals.getY(), newVals.getY()));
            oldVals.setZ(filterVal(oldVals.getZ(), newVals.getZ()));
        }
    }

    private float filterVal(float oldVal, float newVal)
    {
        return newVal * alpha + (1 - alpha) * oldVal;
    }
}
