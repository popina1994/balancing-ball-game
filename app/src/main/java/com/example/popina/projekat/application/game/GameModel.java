package com.example.popina.projekat.application.game;

import android.hardware.SensorManager;

import com.example.popina.projekat.application.common.CommonModel;
import com.example.popina.projekat.logic.game.acceleration.filter.FilterPastValue;
import com.example.popina.projekat.logic.game.acceleration.filter.FilterInterface;
import com.example.popina.projekat.logic.game.coefficient.Coefficient;
import com.example.popina.projekat.logic.game.collision.CollisionModelAbstract;
import com.example.popina.projekat.logic.game.utility.Time;
import com.example.popina.projekat.logic.shape.figure.Figure;
import com.example.popina.projekat.logic.shape.figure.hole.CircleHole;
import com.example.popina.projekat.logic.shape.sound.SoundPlayerCallback;

import java.util.LinkedList;

/**
 * Created by popina on 09.03.2017..
 */

public class GameModel extends CommonModel
{
    private CircleHole ball;
    private LinkedList<Figure> listFigures = new LinkedList<>();

    private FilterInterface filter = new FilterPastValue(FilterPastValue.ALPHA);
    private CollisionModelAbstract collisionModel;

    public static final String POLYGON_NAME = "POLYGON_NAME";
    private String fileName;

    private int height;
    private int width;
    private boolean surfaceInitialized;
    private boolean gameOver = false;
    private boolean paused = false;
    private boolean lastTimeInitialized = false;
    private LinkedList<Time> listTimes = new LinkedList<>();

    private SensorManager sensorManager;
    private Coefficient coefficient;
    private SoundPlayerCallback soundPlayerCallback;

    public CollisionModelAbstract getCollisionModel()
    {
        return collisionModel;
    }

    public void setCollisionModel(CollisionModelAbstract collisionModel)
    {
        this.collisionModel = collisionModel;
    }

    public boolean isLastTimeInitialized()
    {
        return lastTimeInitialized;
    }

    public void setLastTimeInitialized(boolean lastTimeInitialized)
    {
        this.lastTimeInitialized = lastTimeInitialized;
    }

    public SoundPlayerCallback getSoundPlayerCallback()
    {
        return soundPlayerCallback;
    }

    public void setSoundPlayerCallback(SoundPlayerCallback soundPlayerCallback)
    {
        this.soundPlayerCallback = soundPlayerCallback;
    }

    public LinkedList<Figure> getListFigures()
    {
        return listFigures;
    }

    public void setListFigures(LinkedList<Figure> listFigures)
    {
        this.listFigures = listFigures;
    }

    public String getFileName()
    {
        return fileName;
    }

    public void setFileName(String fileName)
    {
        this.fileName = fileName;
    }

    public CircleHole getBall()
    {
        return ball;
    }

    public void setBall(CircleHole ball)
    {
        this.ball = ball;
    }

    public FilterInterface getFilter()
    {
        return filter;
    }

    public void setFilter(FilterInterface filter)
    {
        this.filter = filter;
    }


    public int getHeight()
    {
        return height;
    }

    public void setHeight(int height)
    {
        this.height = height;
    }

    public int getWidth()
    {
        return width;
    }

    public void setWidth(int width)
    {
        this.width = width;
    }

    public boolean isSurfaceInitialized()
    {
        return surfaceInitialized;
    }

    public void setSurfaceInitialized(boolean surfaceInitialized)
    {
        this.surfaceInitialized = surfaceInitialized;
    }

    public Coefficient getCoefficient()
    {
        return coefficient;
    }

    public void setCoefficient(Coefficient coefficient)
    {
        this.coefficient = coefficient;
    }

    public boolean isGameOver()
    {
        return gameOver;
    }

    public void setGameOver(boolean gameOver)
    {
        this.gameOver = gameOver;
    }

    public boolean isPaused()
    {
        return paused;
    }

    public void setPaused(boolean paused)
    {
        this.paused = paused;
    }


    public SensorManager getSensorManager()
    {
        return sensorManager;
    }

    public void setSensorManager(SensorManager sensorManager)
    {
        this.sensorManager = sensorManager;
    }

    public LinkedList<Time> getListTimes()
    {
        return listTimes;
    }

    public void setListTimes(LinkedList<Time> listTimes)
    {
        this.listTimes = listTimes;
    }

}
