package com.example.popina.projekat.model.shape.figure.circle;

import com.example.popina.projekat.model.shape.coordinate.Coordinate;
import com.example.popina.projekat.model.shape.ShapeModel;
import com.example.popina.projekat.model.shape.scale.UtilScale;

/**
 * Created by popina on 08.03.2017..
 */

public class WrongHole extends Circle {
    public WrongHole(float x, float y, float radius) {
        super(x, y, radius, ShapeModel.TYPE_WRONG_HOLE);
        color = ShapeModel.COLOR_HOLE_WRONG;
    }

    public WrongHole(Coordinate c, float radius) {
        super(c, radius, ShapeModel.TYPE_WRONG_HOLE);
        color = ShapeModel.COLOR_HOLE_WRONG;
    }

    @Override
    public WrongHole scale(UtilScale utilScale) {
        WrongHole wrongHole = new WrongHole(utilScale.scaleCoordinate(getCenter()), utilScale.scaleWidth(getRadius()));
        return  wrongHole;
    }

    @Override
    public WrongHole scaleReverse(UtilScale utilScale) {
        WrongHole wrongHole = new WrongHole(utilScale.scaleReverseCoordinate(getCenter()), utilScale.scaleReverseWidth(getRadius()));
        return  wrongHole;
    }
}
