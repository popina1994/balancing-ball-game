package com.example.popina.projekat.logic.shape.figure.obstacle;

import com.example.popina.projekat.logic.game.utility.Coordinate3D;
import com.example.popina.projekat.logic.shape.coordinate.Coordinate;
import com.example.popina.projekat.logic.shape.figure.Figure;
import com.example.popina.projekat.logic.shape.figure.hole.StartHole;

import java.util.LinkedList;

/**
 * Created by popina on 16.06.2017..
 */

public abstract class Obstacle extends Figure
{
    protected Obstacle(Coordinate center, String figureType, int color)
    {
        super(center, figureType, color);
    }
    public abstract Coordinate getSpeedChangeAfterCollision(StartHole ballOld, StartHole ballNew,  Coordinate3D speed);
}
