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
import static com.example.popina.projekat.logic.game.utility.Utility.rotatePointAroundCenter;

/**
 * Created by popina on 05.03.2017..
 */

public class RectangleObstacle extends Obstacle
{
    private float height;
    private float width;
    private float angle = 0.0f;
    private double sine;
    private double cosine;
    private double cosineOfOpositeAngle;
    private double sineOfOpositeAngle;

    public RectangleObstacle(Coordinate c, float width, float height, float angle)
    {
        super(c.clone(), ShapeConst.TYPE_OBSTACLE_RECTANGLE, ShapeConst.COLOR_OBSTACLE);
        this.height = height;
        this.width = width;
        this.angle = angle;
        sine = Math.sin(angle);
        cosine = Math.cos(angle);
        cosineOfOpositeAngle = Math.cos(-angle);
        sineOfOpositeAngle = Math.sin(-angle);
    }

    public RectangleObstacle(float xCenter, float yCenter, float width, float height, float angle)
    {
        this(new Coordinate(xCenter, yCenter), width, height, angle);
    }


    public RectangleObstacle(Coordinate c, float width, float height)
    {
        super(c.clone(), ShapeConst.TYPE_OBSTACLE_RECTANGLE, ShapeConst.COLOR_OBSTACLE);
        this.height = height;
        this.width = width;
    }

    public RectangleObstacle(float xCenter, float yCenter, float width, float height)
    {
        this(new Coordinate(xCenter, yCenter), width, height);
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
        float newX = -1;
        float newY = -1;

        //Coordinate rotatedPoint = Utility.rotatePointAroundCenter(getCenter(), -angle, ball.getCenter());;
        Coordinate rotatedPoint = Utility.rotatePointAroundCenter(getCenter(), sineOfOpositeAngle, cosineOfOpositeAngle, ball.getCenter());;
        newX = rotatedPoint.getX();
        newY = rotatedPoint.getY();

        newX = Math.abs(newX - center.getX());
        newY = Math.abs(newY - center.getY());

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

    // TODO : refactor, remove unecessary things.
    //

    private boolean doesBallHitLine(Coordinate beginSegment, Coordinate endSegment, Coordinate ballCenter, float radius, boolean isXLine)
    {
        return doesSegmentIntersectsCircle(beginSegment, endSegment, ballCenter, radius, isXLine);
    }

    @Override
    public Coordinate getSpeedChangeAfterCollision(StartHole ballOld, StartHole ballNew, Coordinate3D speed)
    {
        Coordinate speedChange = new Coordinate(0, 0);

        //Coordinate speedRot = rotatePointAroundCenter(-angle, new Coordinate(speed.getX(), speed.getY()));
        Coordinate speedRot = rotatePointAroundCenter(sineOfOpositeAngle, cosineOfOpositeAngle, new Coordinate(speed.getX(), speed.getY()));
        //Coordinate centerBallRot = rotatePointAroundCenter(getCenter(), -angle, ballNew.getCenter());
        Coordinate centerBallRot = rotatePointAroundCenter(getCenter(), sineOfOpositeAngle, cosineOfOpositeAngle, ballNew.getCenter());

        if ((doesBallHitLine(getBotomLeft(), getBottomRight(), centerBallRot, ballNew.getRadius(), true) && speedRot.getY() <= 0)
                || (doesBallHitLine(getTopLeft(), getTopRight(), centerBallRot, ballNew.getRadius(), true) && speedRot.getY() >= 0))
        {
            speedChange.setY(-speedRot.getY());
        }

        if ((doesBallHitLine(getTopLeft(), getBotomLeft(), centerBallRot, ballNew.getRadius(), false) && speedRot.getX() >= 0)
                || (doesBallHitLine(getTopRight(), getBottomRight(), centerBallRot, ballNew.getRadius(), false) && speedRot.getX() <= 0))
        {
            speedChange.setX(-speedRot.getX());
        }

        //return rotatePointAroundCenter(angle, speedChange);
        return rotatePointAroundCenter(sine, cosine, speedChange);
    }

    @Override
    public float calculateAngle(Coordinate point)
    {
        return super.calculateAngle(point) - angle;
    }
}
