package com.example.popina.projekat.model.shape.figure.circle;

import com.example.popina.projekat.model.shape.coordinate.Coordinate;
import com.example.popina.projekat.model.shape.ShapeModel;
import com.example.popina.projekat.model.shape.scale.UtilScale;

/**
 * Created by popina on 08.03.2017..
 */

public class FinishHole extends Circle {
    public FinishHole(float x, float y, float radius) {
        super(x, y, radius, ShapeModel.TYPE_FINISH_HOLE);
        color = ShapeModel.COLOR_HOLE_FINISH;
    }

    public FinishHole(Coordinate c, float radius) {
        super(c, radius, ShapeModel.TYPE_FINISH_HOLE);
        color = ShapeModel.COLOR_HOLE_FINISH;
    }

    @Override
    public FinishHole scale(UtilScale utilScale) {
        FinishHole finishHole = new FinishHole(utilScale.scaleCoordinate(getCenter()), utilScale.scaleWidth(getRadius()));
        return finishHole;
    }

    @Override
    public FinishHole scaleReverse(UtilScale utilScale) {
        FinishHole finishHole = new FinishHole(utilScale.scaleReverseCoordinate(getCenter()), utilScale.scaleReverseWidth(getRadius()));
        return  finishHole;
    }
}