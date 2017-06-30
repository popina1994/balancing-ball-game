package com.example.popina.projekat.application.game.model;

import com.example.popina.projekat.logic.game.utility.Time;
import com.example.popina.projekat.logic.shape.figure.Figure;
import com.example.popina.projekat.logic.shape.figure.hole.CircleHole;

import java.util.LinkedList;

/**
 * Created by popina on 30.06.2017..
 */

public class LevelElements
{
    private String levelName;
    private boolean gameOver = false;
    private boolean lastTimeInitialized = false;
    private LinkedList<Time> listTimes = new LinkedList<>();
    private CircleHole ball;
    private LinkedList<Figure> listFigures = new LinkedList<>();
    private boolean levelLoaded = false;

    public LevelElements(String levelName)
    {
        this.levelName = levelName;
    }

    public String getLevelName()
    {
        return levelName;
    }

    public void setLevelName(String levelName)
    {
        this.levelName = levelName;
    }

    public boolean isGameOver()
    {
        return gameOver;
    }

    public void setGameOver(boolean gameOver)
    {
        this.gameOver = gameOver;
    }

    public boolean isLastTimeInitialized()
    {
        return lastTimeInitialized;
    }

    public void setLastTimeInitialized(boolean lastTimeInitialized)
    {
        this.lastTimeInitialized = lastTimeInitialized;
    }

    public LinkedList<Time> getListTimes()
    {
        return listTimes;
    }

    public void setListTimes(LinkedList<Time> listTimes)
    {
        this.listTimes = listTimes;
    }

    public CircleHole getBall()
    {
        return ball;
    }

    public void setBall(CircleHole ball)
    {
        this.ball = ball;
    }

    public LinkedList<Figure> getListFigures()
    {
        return listFigures;
    }

    public void setListFigures(LinkedList<Figure> listFigures)
    {
        this.listFigures = listFigures;
    }

    public boolean isLevelLoaded()
    {
        return levelLoaded;
    }

    public void setLevelLoaded(boolean levelLoaded)
    {
        this.levelLoaded = levelLoaded;
    }
}
