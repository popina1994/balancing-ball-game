package com.example.popina.projekat.logic.shape.figure.obstacle;

import com.example.popina.projekat.logic.shape.coordinate.Coordinate;
import com.example.popina.projekat.logic.shape.figure.Figure;
import com.example.popina.projekat.logic.shape.figure.hole.StartHole;

import java.util.LinkedList;

/**
 * Created by popina on 16.06.2017..
 */

public abstract class Obstacle extends Figure
{
    protected Obstacle(Coordinate center, String figureType)
    {
        super(center, figureType);
    }
    public abstract Coordinate getSpeedChangeAfterCollision(StartHole ballOld, StartHole ballNew,  Coordinate speed);
}
