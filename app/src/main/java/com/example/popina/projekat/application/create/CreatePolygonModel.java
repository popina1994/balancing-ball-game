package com.example.popina.projekat.application.create;

import com.example.popina.projekat.application.common.CommonModel;
import com.example.popina.projekat.logic.shape.figure.Figure;

import java.util.LinkedList;

/**
 * Created by popina on 04.03.2017..
 */

public class CreatePolygonModel extends CommonModel
{
    public static final int MODE_MOVE = 0;
    public static final int MODE_RESIZE = 1;
    public static final int MODE_DELETE = 2;
    public static final int MODE_ROTATE = 3;

    public static final int CREATE_HOLE_START = 0;
    public static final int CREATE_HOLE_FINISH = 1;
    public static final int CREATE_HOLE_WRONG = 2;
    public static final int CREATE_OBSTACLE_RECTANGLE = 3;
    public static final int CREATE_OBSTACLE_CIRCLE = 4;
    public static final int CREATE_HOLE_VORTEX = 5;
    // List of unscaled figures.
    //
    private LinkedList<Figure> listFigures = new LinkedList<>();
    private String fileName;

    private int curMode = MODE_MOVE;

    private Figure selectedFigure = null;
    private boolean initializedScreen = false;
    private boolean editMode = false;
    private int levelDifficulty;


    public Float getStartAngleOfRotation()
    {
        return startAngleOfRotation;
    }

    public void setStartAngleOfRotation(Float startAngleOfRotation)
    {
        this.startAngleOfRotation = startAngleOfRotation;
    }

    private Float startAngleOfRotation = null;

    public Figure getSelectedFigure()
    {
        return selectedFigure;
    }

    public void setSelectedFigure(Figure selectedFigure)
    {
        this.selectedFigure = selectedFigure;
    }


    public int getCurMode()
    {
        return curMode;
    }

    public void setCurMode(int curMode)
    {
        this.curMode = curMode;
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

    public boolean isInitializedScreen()
    {
        return initializedScreen;
    }

    public void setInitializedScreen(boolean initializedScreen)
    {
        this.initializedScreen = initializedScreen;
    }

    public boolean isEditMode()
    {
        return editMode;
    }

    public void setEditMode(boolean editMode)
    {
        this.editMode = editMode;
    }

    public void setLevelDifficulty(int levelDifficulty)
    {
        this.levelDifficulty = levelDifficulty;
    }

    public int getLevelDifficulty()
    {
        return levelDifficulty;
    }
}
