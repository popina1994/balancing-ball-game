package com.example.popina.projekat.create;

import com.example.popina.projekat.create.shape.Figure;

import java.util.LinkedList;

/**
 * Created by popina on 04.03.2017..
 */

public class CreatePolygonModel {

    public static final int MODE_ADD = 0;
    public static final int MODE_MOVE = 1;
    public static final int MODE_RESIZE = 2;
    public static final int MODE_DELETE = 3;

    private int height;
    private int width;
    private LinkedList<Figure> listFigures;

    private int curMode = MODE_ADD;

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
}
