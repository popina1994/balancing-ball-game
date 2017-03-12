package com.example.popina.projekat.model.shape.figure;

import android.graphics.Canvas;

import com.example.popina.projekat.model.shape.coordinate.Coordinate;
import com.example.popina.projekat.model.shape.ShapeModel;
import com.example.popina.projekat.model.shape.scale.UtilScale;

/**
 * Created by popina on 05.03.2017..
 */

public abstract class Figure {
    protected Coordinate center;
    protected int color;
    protected String figureType;

    public Figure(Coordinate center, String figureType) {
        this.center = center;
        this.figureType = figureType;
    }

    public Coordinate getCenter() {
        return center;
    }

    public void setCenter(Coordinate center) {
        this.center = center;
    }

    public int getColor() {
        return color;
    }

    public void setColor(int color) {
        this.color = color;
    }

    public String getFigureType() {
        return figureType;
    }

    public void setFigureType(String figureType) {
        this.figureType = figureType;
    }

    /**
     * @param x - x coordinate of vector
     * @param y - y coordinate of vectore
     *          Moves figure for vector whose coordinates are passed. It shouldn't go over 100%.
     */
    public void moveFor(float x, float y) {
        center.setX(center.getX() + x);
        center.setY(center.getY() + y);
    }

    public void moveFor(Coordinate coordinate) {
        center.setX(center.getX() + coordinate.getX());
        center.setY(center.getY() + coordinate.getY());
    }

    public void moveTo(float x, float y) {
        center.setX(x);
        center.setY(y);
    }

    public void moveTo(Coordinate coordinate) {
        center = coordinate.clone();
    }

    public abstract void drawOnCanvas(Canvas canvas);

    public abstract boolean isCoordinateInside(Coordinate c);

    public abstract void resize(Coordinate c);

    @Override
    public String toString() {
        return ShapeModel.FIGURE_TYPE + " " + figureType + " "
                + ShapeModel.FIGURE_COLOR + " " + color + " "
                + ShapeModel.FIGURE_CENTER + " " + center;
    }

    public abstract Figure scale(UtilScale utilScale);

    public abstract  Figure scaleReverse(UtilScale utilScale);

}
