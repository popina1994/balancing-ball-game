package com.example.popina.projekat.create;

import com.example.popina.projekat.create.shape.Figure;

import java.util.LinkedList;

/**
 * Created by popina on 04.03.2017..
 */

public class CreatePolygonModel {
    private int height;
    private int width;
    private LinkedList<Figure> listFigures;

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
}
