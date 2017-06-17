package com.example.popina.projekat.logic.shape.figure.obstacle;

import android.graphics.Canvas;
import android.graphics.Paint;

import com.example.popina.projekat.logic.game.utility.Coordinate3D;
import com.example.popina.projekat.logic.game.utility.Utility;
import com.example.popina.projekat.logic.shape.constants.ShapeConst;
import com.example.popina.projekat.logic.shape.coordinate.Coordinate;
import com.example.popina.projekat.logic.shape.figure.Figure;
import com.example.popina.projekat.logic.shape.figure.hole.CircleHole;
import com.example.popina.projekat.logic.shape.figure.hole.StartHole;
import com.example.popina.projekat.logic.shape.scale.UtilScale;

/**
 * Created by popina on 16.06.2017..
 */

public class CircleObstacle extends Obstacle
{
    private float radius;
    public CircleObstacle(float x, float y, float radius)
    {
        super(new Coordinate(x, y), ShapeConst.TYPE_OBSTACLE_CIRCLE, ShapeConst.COLOR_OBSTACLE);
        this.radius = radius;
    }

    public CircleObstacle(Coordinate coordinate, float radius)
    {
        super(coordinate.clone(), ShapeConst.TYPE_OBSTACLE_CIRCLE, ShapeConst.COLOR_OBSTACLE);
        this.radius = radius;
    }

    public float getRadius()
    {
        return radius;
    }

    public void setRadius(float radius)
    {
        this.radius = radius;
    }

    @Override
    public Coordinate getSpeedChangeAfterCollision(StartHole ballOld, StartHole ballNew, Coordinate3D speed)
    {
        return new Coordinate(0, 0);
    }

    @Override
    public void drawOnCanvas(Canvas canvas)
    {
        Paint p = new Paint();
        p.setColor(color);
        canvas.drawCircle(getCenter().getX(), getCenter().getY(), radius, p);
    }

    @Override
    public boolean isCoordinateInside(Coordinate c)
    {
        if ( (c.getX() - center.getX()) * (c.getX() - center.getX()) +  (c.getY() - center.getY()) * (c.getY() - center.getY()) <= radius  * radius)
        {
            return true;
        }
        return false;
    }

    @Override
    public void resize(Coordinate c)
    {
        radius = (float)Math.sqrt( (c.getX() - center.getX()) * (c.getX() - center.getX()) +  (c.getY() - center.getY()) * (c.getY() - center.getY()));
    }

    @Override
    public CircleObstacle scale(UtilScale utilScale)
    {
        CircleObstacle circleObstacle = new CircleObstacle(utilScale.scaleCoordinate(getCenter()), utilScale.scaleWidth(getRadius()));
        return circleObstacle;
    }

    @Override
    public Figure scaleReverse(UtilScale utilScale)
    {
        CircleObstacle circleObstacle = new CircleObstacle(utilScale.scaleReverseCoordinate(getCenter()), utilScale.scaleReverseWidth(getRadius()));
        return circleObstacle;
    }

    @Override
    public boolean doesCollide(CircleHole ball)
    {
        return Utility.isDistanceBetweenCoordLesThan(center, ball.getCenter(), radius + ball.getRadius(), false);
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

    @Override
    public String toString()
    {
        return super.toString() + " " + Float.toString(radius) + " ";
    }
}
