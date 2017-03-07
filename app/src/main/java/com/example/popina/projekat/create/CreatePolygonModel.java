package com.example.popina.projekat.create;

import com.example.popina.projekat.create.shape.Figure;

import java.util.LinkedList;

/**
 * Created by popina on 04.03.2017..
 */

public class CreatePolygonModel {

    public static final int MODE_MOVE = 0;
    public static final int MODE_RESIZE = 1;
    public static final int MODE_DELETE = 2;

    public static final int CREATE_START = 0;
    public static final int CREATE_FINISH = 1;
    public static final int CREATE_CIRCLE = 2;
    public static final int CREATE_RECT = 3;



    private int height;
    private int width;
    // List of unscaled figures.
    //
    private LinkedList<Figure> listFigures = new LinkedList<>();

    private int curMode = MODE_MOVE;

    private Figure selectedFigure = null;

    public Figure getSelectedFigure() {
        return selectedFigure;
    }

    public void setSelectedFigure(Figure selectedFigure) {
        this.selectedFigure = selectedFigure;
    }

    public int getHeight() {
        return height;
    }

    public void setHeight(int height) {
        this.height = height;
    }

    public int getWidth() {
        return width;
    }

    public void setWidth(int width) {
        this.width = width;
    }

    public int getCurMode() {
        return curMode;
    }

    public void setCurMode(int curMode) {
        this.curMode = curMode;
    }

    public LinkedList<Figure> getListFigures() {
        return listFigures;
    }

    public void setListFigures(LinkedList<Figure> listFigures) {
        this.listFigures = listFigures;
    }
}
