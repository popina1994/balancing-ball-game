package com.example.popina.projekat.application.game.model;

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
import com.example.popina.projekat.logic.statistics.database.GameDatabase;

import java.util.LinkedList;

/**
 * Created by popina on 09.03.2017..
 */

public class GameModel extends CommonModel
{
    private FilterInterface filter = new FilterPastValue(FilterPastValue.ALPHA);
    private CollisionModelAbstract collisionModel;

    public static final String POLYGON_NAME = "POLYGON_NAME";
    public static final int MODE_ONE_GAME = 0;
    public static final int MODE_ADVENTURE = 1;
    public static final int NUM_DIFFICULTIES = 10;

    private int height;
    private int width;
    private boolean surfaceInitialized;
    private boolean gameOver = false;
    private boolean paused = false;
    private boolean levelLoaded = false;

    private SensorManager sensorManager;
    private Coefficient coefficient;
    private LevelElements levelElements;
    private SoundPlayerCallback soundPlayerCallback;
    private GameDatabase gameDatabase;

    private int currentMode;
    private int currentLevel = NUM_DIFFICULTIES;


    public CollisionModelAbstract getCollisionModel()
    {
        return collisionModel;
    }

    public void setCollisionModel(CollisionModelAbstract collisionModel)
    {
        this.collisionModel = collisionModel;
    }


    public SoundPlayerCallback getSoundPlayerCallback()
    {
        return soundPlayerCallback;
    }

    public void setSoundPlayerCallback(SoundPlayerCallback soundPlayerCallback)
    {
        this.soundPlayerCallback = soundPlayerCallback;
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

    public boolean isLevelLoaded()
    {
        return levelLoaded;
    }

    public void setLevelLoaded(boolean levelLoaded)
    {
        this.levelLoaded = levelLoaded;
    }

    public int getCurrentMode()
    {
        return currentMode;
    }

    public void setCurrentMode(int currentMode)
    {
        this.currentMode = currentMode;
    }

    public LevelElements getLevelElements()
    {
        return levelElements;
    }

    public void setLevelElements(LevelElements levelElements)
    {
        this.levelElements = levelElements;
    }

    public GameDatabase getGameDatabase()
    {
        return gameDatabase;
    }

    public void setGameDatabase(GameDatabase gameDatabase)
    {
        this.gameDatabase = gameDatabase;
    }

    public int getCurrentLevel()
    {
        return currentLevel;
    }

    public void setCurrentLevel(int currentLevel)
    {
        this.currentLevel = currentLevel;
    }
}
