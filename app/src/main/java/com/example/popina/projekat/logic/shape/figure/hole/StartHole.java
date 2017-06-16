package com.example.popina.projekat.logic.shape.figure.hole;

import com.example.popina.projekat.logic.shape.coordinate.Coordinate;
import com.example.popina.projekat.logic.shape.constants.ShapeConst;
import com.example.popina.projekat.logic.shape.scale.UtilScale;

/**
 * Created by popina on 08.03.2017..
 */

public class StartHole extends CircleHole
{
    public StartHole(float x, float y, float radius) {
        super(x, y, radius, ShapeConst.TYPE_START_HOLE, ShapeConst.COLOR_HOLE_START);
    }

    public StartHole(Coordinate c, float radius) {
        super(c, radius, ShapeConst.TYPE_START_HOLE, ShapeConst.COLOR_HOLE_START);
    }

    @Override
    public StartHole scale(UtilScale utilScale) {
        StartHole startHole= new StartHole(utilScale.scaleCoordinate(getCenter()), utilScale.scaleWidth(getRadius()));

        return  startHole;
    }

    @Override
    public StartHole scaleReverse(UtilScale utilScale) {
        StartHole startHole = new StartHole(utilScale.scaleReverseCoordinate(getCenter()), utilScale.scaleReverseWidth(getRadius()));
        return  startHole;
    }

    @Override
    public boolean isGameOver() {
        return false;
    }

    @Override
    public boolean isWon() {
        return false;
    }
}