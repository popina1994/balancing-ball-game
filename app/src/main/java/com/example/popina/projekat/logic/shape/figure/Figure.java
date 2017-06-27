package com.example.popina.projekat.logic.shape.figure;

import android.content.Intent;
import android.util.Log;

import com.example.popina.projekat.logic.game.utility.Utility;
import com.example.popina.projekat.logic.shape.constants.ShapeConst;
import com.example.popina.projekat.logic.shape.constants.SoundConst;
import com.example.popina.projekat.logic.shape.coordinate.Coordinate;
import com.example.popina.projekat.logic.shape.draw.ShapeDrawInterface;
import com.example.popina.projekat.logic.shape.movement.collision.detection.CollisionDetectionInterface;
import com.example.popina.projekat.logic.shape.parser.ShapeParserInterface;
import com.example.popina.projekat.logic.shape.scale.UtilScale;
import com.example.popina.projekat.logic.shape.sound.SoundInterface;
import com.example.popina.projekat.logic.shape.sound.SoundPlayerCallback;

import static com.example.popina.projekat.logic.shape.constants.SoundConst.SOUND_PLAY_WAIT_TIME_MS;

/**
 * Created by popina on 05.03.2017..
 */

public abstract class Figure implements ShapeParserInterface, ShapeDrawInterface, SoundInterface, CollisionDetectionInterface
{
    protected Coordinate center;
    protected int color;
    protected String figureType;
    private long lastSoundTime = 0;

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
        long curTime = System.currentTimeMillis();
        if ((curTime - lastSoundTime) > SoundConst.SOUND_PLAY_WAIT_TIME_MS)
        {
            playSoundIfItIsNotSoon(soundPlayerCallback);
            lastSoundTime = curTime;
            Log.d("SOUND", Long.toString(curTime));
        }


    }

    protected abstract void playSoundIfItIsNotSoon(SoundPlayerCallback soundPlayerCallback);
}
