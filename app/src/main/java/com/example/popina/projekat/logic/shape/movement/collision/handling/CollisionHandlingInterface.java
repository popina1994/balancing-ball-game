package com.example.popina.projekat.logic.shape.movement.collision.handling;

import com.example.popina.projekat.logic.game.utility.Coordinate3D;
import com.example.popina.projekat.logic.shape.coordinate.Coordinate;
import com.example.popina.projekat.logic.shape.figure.hole.StartHole;

/**
 * Created by popina on 27.06.2017..
 */

public interface CollisionHandlingInterface
{
    public abstract Coordinate getSpeedChangeAfterCollision(StartHole ballOld, StartHole ballNew, Coordinate3D speed);
}
