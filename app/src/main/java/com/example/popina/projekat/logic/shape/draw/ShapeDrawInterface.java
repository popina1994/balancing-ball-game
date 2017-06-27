package com.example.popina.projekat.logic.shape.draw;

import android.graphics.Canvas;

import com.example.popina.projekat.logic.shape.coordinate.Coordinate;

/**
 * Created by popina on 27.06.2017..
 */

public interface ShapeDrawInterface
{
    void drawOnCanvas(Canvas canvas);

    void moveTo(Coordinate coordinate);

    void resize(Coordinate c);

    boolean isCoordinateInside(Coordinate c);

    void rotate(Coordinate c, float angle);

    float calculateAngle(Coordinate point);
}
