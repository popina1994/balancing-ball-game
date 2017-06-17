package com.example.popina.projekat.logic.shape.figure.obstacle;

import android.graphics.Canvas;
import android.graphics.Paint;
import android.util.Log;

import com.example.popina.projekat.logic.game.utility.Coordinate3D;
import com.example.popina.projekat.logic.game.utility.Utility;
import com.example.popina.projekat.logic.shape.constants.ShapeConst;
import com.example.popina.projekat.logic.shape.coordinate.Coordinate;
import com.example.popina.projekat.logic.shape.figure.hole.CircleHole;
import com.example.popina.projekat.logic.shape.figure.hole.StartHole;
import com.example.popina.projekat.logic.shape.scale.UtilScale;

import static com.example.popina.projekat.logic.game.utility.Utility.doesSegmentIntersectsCircle;

/**
 * Created by popina on 05.03.2017..
 */

public class RectangleObstacle extends Obstacle
{
    private float height;
    private float width;
    private float angle = 0.0f;

    public RectangleObstacle(float xCenter, float yCenter, float width, float height)
    {
        super(new Coordinate(xCenter, yCenter), ShapeConst.TYPE_OBSTACLE_RECTANGLE, ShapeConst.COLOR_OBSTACLE);
        this.height = height;
        this.width = width;
    }

    public RectangleObstacle(float xCenter, float yCenter, float width, float height, float angle)
    {
        super(new Coordinate(xCenter, yCenter), ShapeConst.TYPE_OBSTACLE_RECTANGLE, ShapeConst.COLOR_OBSTACLE);
        this.height = height;
        this.width = width;
        this.angle = angle;
    }


    public RectangleObstacle(Coordinate c, float width, float height)
    {
        super(c.clone(), ShapeConst.TYPE_OBSTACLE_RECTANGLE, ShapeConst.COLOR_OBSTACLE);
        this.height = height;
        this.width = width;
    }

    public RectangleObstacle(Coordinate c, float width, float height, float angle)
    {
        super(c.clone(), ShapeConst.TYPE_OBSTACLE_RECTANGLE, ShapeConst.COLOR_OBSTACLE);
        this.height = height;
        this.width = width;
        this.angle = angle;
    }

    public float getWidth()
    {
        return width;
    }

    public void setWidth(float width)
    {
        this.width = width;
    }

    public float getHeight()
    {
        return height;
    }

    public void setHeight(float height)
    {
        this.height = height;
    }

    public float getBottomY()
    {
        return super.center.getY() + height / 2;
    }

    public float getLeftX()
    {
        return super.center.getX() - width / 2;
    }

    public float getTopY()
    {
        return super.center.getY() - height / 2;
    }

    public float getRightX()
    {
        return super.center.getX() + width / 2;
    }

    public Coordinate getBotomLeft()
    {
        return new Coordinate(getLeftX(), getBottomY());
    }

    public Coordinate getBottomRight()
    {
        return new Coordinate(getRightX(), getBottomY());
    }

    public Coordinate getTopRight()
    {
        return new Coordinate(getRightX(), getTopY());
    }

    public Coordinate getTopLeft()
    {
        return new Coordinate(getLeftX(), getTopY());
    }

    @Override
    public void drawOnCanvas(Canvas canvas)
    {
        Paint p = new Paint();
        p.setColor(color);

        canvas.save();
        canvas.rotate((float)Utility.radianToDeg(angle), getCenter().getX(), getCenter().getY());
        canvas.drawRect(getLeftX(), getTopY(), getRightX(), getBottomY(), p);
        canvas.restore();
    }

    @Override
    public boolean isCoordinateInside(Coordinate c)
    {
        Coordinate rotatedPoint = Utility.rotatePointAroundCenter(center, -angle, c);
        if ((rotatedPoint.getX() >= getLeftX()) && (rotatedPoint.getX() <= getRightX())
                && (rotatedPoint.getY() <= getBottomY()) && (rotatedPoint.getY() >= getTopY()))
        {
            return true;
        }
        return false;
    }

    @Override
    public void resize(Coordinate c)
    {
        // TODO : if time is left update this.

        width = 2 * Math.abs(c.getX() - center.getX());
        height = 2 * Math.abs(c.getY() - center.getY());
    }

    @Override
    public String toString()
    {
        return super.toString() + " " + width + " " + height + " " + angle  + " ";
    }

    @Override
    public RectangleObstacle scale(UtilScale utilScale)
    {
        RectangleObstacle rect = new RectangleObstacle( utilScale.scaleCoordinate(getCenter()),
                                                        utilScale.scaleWidth(getWidth()),
                                                        utilScale.scaleHeight(getHeight()),
                                                        angle);
        return rect;
    }

    @Override
    public RectangleObstacle scaleReverse(UtilScale utilScale)
    {
        RectangleObstacle rectPer = new RectangleObstacle(  utilScale.scaleReverseCoordinate(getCenter()),
                                                            utilScale.scaleReverseWidth(getWidth()),
                                                            utilScale.scaleReverseHeight(getHeight()),
                                                            angle);
        return rectPer;
    }

    @Override
    public boolean doesCollide(CircleHole ball)
    {
        boolean retValue = false;
        float widthHalf = width / 2;
        float heighHalf = height / 2;

        // Translation.
        //
        float newX = Math.abs(ball.getCenter().getX() - center.getX());
        float newY = Math.abs(ball.getCenter().getY() - center.getY());

        if ((newX <= widthHalf) && (newY <= heighHalf))
        {
            retValue = true;
        } else if ((newX <= widthHalf) && (Utility.isDistanceBetweenCoordLesThan(
                new Coordinate(newX, newY),
                new Coordinate(newX, heighHalf),
                ball.getRadius(), false)))
        {
            retValue = true;
        } else if ((newY <= heighHalf) && (Utility.isDistanceBetweenCoordLesThan(
                new Coordinate(newX, newY),
                new Coordinate(widthHalf, newY),
                ball.getRadius(),
                false
        )))
        {
            retValue = true;
        } else if (((newX > widthHalf) && (newY > heighHalf)) &&
                Utility.isDistanceBetweenCoordLesThan(
                        new Coordinate(newX, newY),
                        new Coordinate(widthHalf, heighHalf),
                        ball.getRadius(),
                        false
                )
                )
        {
            retValue = true;
        }

        return retValue;
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
    public void rotate(Coordinate c, float startAngle)
    {
        this.angle = Utility.convertRadianAngleTo2PiRange(Utility.calculateAngle(center, c) - startAngle);
        Log.d("Angle", Float.toString(this.angle));
    }

    private boolean doesBallHitLine(Coordinate beginSegment, Coordinate endSegment, CircleHole ball, CircleHole ballNew, boolean isXLine)
    {
        return doesSegmentIntersectsCircle(beginSegment, endSegment, ballNew.getCenter(), ball.getRadius(), isXLine);
    }

    @Override
    public Coordinate getSpeedChangeAfterCollision(StartHole ballOld, StartHole ballNew, Coordinate3D speed)
    {
        Coordinate speedChange = new Coordinate(0, 0);

        if ((doesBallHitLine(getBotomLeft(), getBottomRight(), ballOld, ballNew, true) && speed.getY() <= 0)
                || (doesBallHitLine(getTopLeft(), getTopRight(), ballOld, ballNew, true) && speed.getY() >= 0))
        {
            speedChange.setY(-speed.getY());
        }

        if ((doesBallHitLine(getTopLeft(), getBotomLeft(), ballOld, ballNew, false) && speed.getX() >= 0)
                || (doesBallHitLine(getTopRight(), getBottomRight(), ballOld, ballNew, false) && speed.getX() <= 0))
        {
            speedChange.setX(-speed.getX());
        }

        return speedChange;
    }

    @Override
    public float calculateAngle(Coordinate point)
    {
        return super.calculateAngle(point) - angle;
    }
}
