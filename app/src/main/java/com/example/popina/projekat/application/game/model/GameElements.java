package com.example.popina.projekat.application.game.model;

import com.example.popina.projekat.logic.game.utility.Time;
import com.example.popina.projekat.logic.shape.figure.Figure;
import com.example.popina.projekat.logic.shape.figure.hole.CircleHole;

import java.util.LinkedList;

/**
 * Created by popina on 30.06.2017..
 */

public class GameElements
{
    private String levelName;
    private boolean gameOver = false;
    private boolean lastTimeInitialized = false;
    private LinkedList<Time> listTimes = new LinkedList<>();
    private CircleHole ball;
    private LinkedList<Figure> listFigures = new LinkedList<>();
    private boolean levelLoaded = false;
}
