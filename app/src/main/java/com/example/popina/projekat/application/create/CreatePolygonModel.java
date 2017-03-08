package com.example.popina.projekat.application.create;

import com.example.popina.projekat.application.Model;
import com.example.popina.projekat.model.shape.ShapeDraw;
import com.example.popina.projekat.model.shape.figure.Figure;
import com.example.popina.projekat.model.shape.ShapeFactory;

import java.util.LinkedList;

/**
 * Created by popina on 04.03.2017..
 */

public class CreatePolygonModel extends Model{

    public static final int MODE_MOVE = 0;
    public static final int MODE_RESIZE = 1;
    public static final int MODE_DELETE = 2;

    public static final int CREATE_START_HOLE = 0;
    public static final int CREATE_FINISH_HOLE = 1;
    public static final int CREATE_WRONG_HOLE = 2;
    public static final int CREATE_OBSTACLE = 3;


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
