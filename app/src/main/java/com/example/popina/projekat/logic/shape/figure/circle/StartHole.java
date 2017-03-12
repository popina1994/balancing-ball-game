package com.example.popina.projekat.model.shape.figure.circle;

import com.example.popina.projekat.model.shape.coordinate.Coordinate;
import com.example.popina.projekat.model.shape.ShapeModel;
import com.example.popina.projekat.model.shape.scale.UtilScale;

/**
 * Created by popina on 08.03.2017..
 */

public class StartHole extends Circle {
    public StartHole(float x, float y, float radius) {
        super(x, y, radius, ShapeModel.TYPE_START_HOLE);
        color = ShapeModel.COLOR_HOLE_START;
    }

    public StartHole(Coordinate c, float radius) {
        super(c, radius, ShapeModel.TYPE_START_HOLE);
        color = ShapeModel.COLOR_HOLE_START;
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
}
