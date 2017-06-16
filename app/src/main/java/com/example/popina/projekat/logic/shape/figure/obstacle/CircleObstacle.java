package com.example.popina.projekat.logic.shape.figure.obstacle;

import android.graphics.Canvas;

import com.example.popina.projekat.logic.shape.coordinate.Coordinate;
import com.example.popina.projekat.logic.shape.figure.Figure;
import com.example.popina.projekat.logic.shape.figure.hole.CircleHole;
import com.example.popina.projekat.logic.shape.scale.UtilScale;

/**
 * Created by popina on 16.06.2017..
 */

public class CircleObstacle extends Figure
{
    public CircleObstacle(Coordinate center, String figureType)
    {
        super(center, figureType);
    }

    @Override
    public void drawOnCanvas(Canvas canvas)
    {

    }

    @Override
    public boolean isCoordinateInside(Coordinate c)
    {
        return false;
    }

    @Override
    public void resize(Coordinate c)
    {

    }

    @Override
    public Figure scale(UtilScale utilScale)
    {
        return null;
    }

    @Override
    public Figure scaleReverse(UtilScale utilScale)
    {
        return null;
    }

    @Override
    public boolean hits(CircleHole ball)
    {
        return false;
    }

    @Override
    public boolean isGameOver()
    {
        return false;
    }

    @Override
    public boolean isWon()
    {
        return false;
    }
}
