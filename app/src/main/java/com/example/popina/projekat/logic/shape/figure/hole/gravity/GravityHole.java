package com.example.popina.projekat.logic.shape.figure.hole.gravity;

import com.example.popina.projekat.logic.game.utility.Coordinate3D;
import com.example.popina.projekat.logic.game.utility.Utility;
import com.example.popina.projekat.logic.shape.coordinate.Coordinate;
import com.example.popina.projekat.logic.shape.figure.hole.CircleHole;
import com.example.popina.projekat.logic.shape.figure.hole.StartHole;
import com.example.popina.projekat.logic.shape.movement.collision.handling.CollisionHandlingInterface;

/**
 * Created by popina on 29.06.2017..
 */

public abstract  class GravityHole extends CircleHole implements CollisionHandlingInterface
{

    public static final int GRAVITY_CONSTANT = 1000;
    protected boolean isGameOver = false;

    public GravityHole(float x, float y, float radius, String figureType, int color)
    {
        super(x, y, radius, figureType, color);
    }

    public GravityHole(Coordinate c, float radius, String figureType, int color)
    {
        super(c, radius, figureType, color);
    }

    public GravityHole(float x, float y, float radius, String figureType)
    {
        super(x, y, radius, figureType);
    }

    public GravityHole(Coordinate c, float radius, String figureType)
    {
        super(c, radius, figureType);
    }

    @Override
    public boolean isGameOver()
    {
        return isGameOver;
    }

    @Override
    public boolean doesCollideTemplateMethod(CircleHole ball)
    {
        isGameOver = isCoordinateInside(ball.getCenter());
        return Utility.isDistanceBetweenCoordLesThan(center, ball.getCenter(), radius + ball.getRadius(), false);
    }


    @Override
    public Coordinate getSpeedChangeAfterCollision(StartHole ballOld, StartHole ballNew, Coordinate3D speed)
    {
        Coordinate vectorSpeed = getCenter().subCoordinate(ballNew.getCenter());
        if (!Utility.isDimBetweenDims(0, 0, Utility.distanceSquared(ballNew.getCenter(), getCenter())))
            vectorSpeed.mulThisScalar(GRAVITY_CONSTANT / Utility.distanceSquared(ballNew.getCenter(), getCenter()));
        return vectorSpeed;
    }
}
