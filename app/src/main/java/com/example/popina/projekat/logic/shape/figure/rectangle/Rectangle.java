package com.example.popina.projekat.logic.shape.figure.rectangle;

import android.graphics.Canvas;
import android.graphics.Paint;

import com.example.popina.projekat.logic.shape.coordinate.Coordinate;
import com.example.popina.projekat.logic.shape.ShapeModel;
import com.example.popina.projekat.logic.shape.figure.Figure;
import com.example.popina.projekat.logic.shape.figure.circle.Circle;
import com.example.popina.projekat.logic.shape.scale.UtilScale;

/**
 * Created by popina on 05.03.2017..
 */

public class Rectangle extends Figure {
    private float height;
    private float width;

    public Rectangle(float xCenter, float yCenter, float width, float height) {
        super(new Coordinate(xCenter, yCenter), ShapeModel.TYPE_OBSTACLE);
        this.height = height;
        this.width = width;

        color = ShapeModel.COLOR_OBSTACLE;
    }

    public Rectangle(Coordinate c, float width, float height)
    {
        super(c.clone(), ShapeModel.TYPE_OBSTACLE);
        this.height = height;
        this.width = width;
        color = ShapeModel.COLOR_OBSTACLE;
    }

    public float getWidth() {
        return width;
    }

    public void setWidth(float width) {
        this.width = width;
    }

    public float getHeight() {
        return height;
    }

    public void setHeight(float height) {
        this.height = height;
    }

    public float getBottomY()
    {
        return super.center.getY() + height / 2;
    }

    public float getLeftX()
    {
        return super.center.getX() - width / 2;
    }

    public float getTopY()
    {
        return super.center.getY() - height / 2;
    }

    public float getRightX()
    {
        return super.center.getX() + width / 2;
    }

    public Coordinate getBotomLeft()
    {
        return new Coordinate(getLeftX(), getBottomY());
    }

    public Coordinate getTopRight()
    {
        return new Coordinate(getRightX(), getTopY());
    }

    @Override
    public void drawOnCanvas(Canvas canvas) {
        Paint p = new Paint();
        p.setColor(color);
        canvas.drawRect(getLeftX(), getTopY(), getRightX(), getBottomY(), p);
    }

    @Override
    public boolean isCoordinateInside(Coordinate c) {
        if ( (c.getX() >= getLeftX()) && (c.getX() <= getRightX()) && (c.getY() <= getBottomY()) && (c.getY() >= getTopY()))
        {
            return true;
        }
        return false;
    }

    @Override
    public void resize(Coordinate c) {
        width = 2 * Math.abs(c.getX() - center.getX());
        height = 2 * Math.abs(c.getY() - center.getY());
    }

    @Override
    public String toString() {
        return super.toString() + " " + width + " " + height + " ";
    }

    @Override
    public Rectangle scale(UtilScale utilScale) {
        Rectangle rect = new Rectangle(utilScale.scaleCoordinate(getCenter()), utilScale.scaleWidth(getWidth()), utilScale.scaleHeight(getHeight()));
        return rect;
    }

    @Override
    public Rectangle scaleReverse(UtilScale utilScale) {
        Rectangle rectPer = new Rectangle(utilScale.scaleReverseCoordinate(getCenter()), utilScale.scaleReverseWidth(getWidth()), utilScale.scaleReverseHeight(getHeight()));
        return rectPer;
    }

    @Override
    public boolean hits(Circle ball) {
        return false;
    }
}
