package com.example.popina.projekat.logic.shape.figure;

import com.example.popina.projekat.logic.game.utility.Utility;
import com.example.popina.projekat.logic.shape.constants.ShapeConst;
import com.example.popina.projekat.logic.shape.coordinate.Coordinate;
import com.example.popina.projekat.logic.shape.draw.ShapeDrawInterface;
import com.example.popina.projekat.logic.shape.figure.hole.CircleHole;
import com.example.popina.projekat.logic.shape.movement.collision.detection.CollisionDetectionInterface;
import com.example.popina.projekat.logic.shape.parser.ShapeParserInterface;
import com.example.popina.projekat.logic.shape.scale.UtilScale;
import com.example.popina.projekat.logic.shape.sound.SoundInterface;
import com.example.popina.projekat.logic.shape.sound.SoundPlayerCallback;

/**
 * Created by popina on 05.03.2017..
 */

public abstract class Figure implements ShapeParserInterface, ShapeDrawInterface, SoundInterface, CollisionDetectionInterface
{
    protected Coordinate center;
    protected int color;
    protected String figureType;
    private boolean shouldPlaySound = false;
    private boolean lastCheckCollision = false;

    @Override
    public final boolean doesCollide(CircleHole ball)
    {
        boolean isThereCollision = doesCollideTemplateMethod(ball);
        if (isThereCollision && !lastCheckCollision)
        {
            shouldPlaySound = true;
        }
        else
        {
            shouldPlaySound = false;
        }
        lastCheckCollision = isThereCollision;
        return isThereCollision;
    }

    protected abstract boolean doesCollideTemplateMethod(CircleHole circleHole);

    protected Figure(Coordinate center, String figureType, int color)
    {
        this.center = center;
        this.figureType = figureType;
        this.color = color;
    }

    public Coordinate getCenter()
    {
        return center;
    }

    public void setCenter(Coordinate center)
    {
        this.center = center;
    }

    public int getColor()
    {
        return color;
    }

    public void setColor(int color)
    {
        this.color = color;
    }

    public String getFigureType()
    {
        return figureType;
    }

    public void setFigureType(String figureType)
    {
        this.figureType = figureType;
    }


    public void moveTo(Coordinate coordinate)
    {
        center = coordinate.clone();
    }

    public float calculateAngle(Coordinate point)
    {
        return Utility.calculateAngle(getCenter(), point);
    }

    @Override
    public String toString()
    {
        return ShapeConst.FIGURE_TYPE + " " + figureType + " "
                + ShapeConst.FIGURE_COLOR + " " + color + " "
                + ShapeConst.FIGURE_CENTER + " " + center;
    }

    public abstract Figure scale(UtilScale utilScale);

    public abstract Figure scaleReverse(UtilScale utilScale);

    @Override
    public final void playSound(SoundPlayerCallback soundPlayerCallback)
    {
        if (shouldPlaySound)
        {
            playSoundTemplateMethod(soundPlayerCallback);
        }
    }

    protected abstract void playSoundTemplateMethod(SoundPlayerCallback soundPlayerCallback);
}
