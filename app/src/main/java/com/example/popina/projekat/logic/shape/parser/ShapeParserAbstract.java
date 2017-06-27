package com.example.popina.projekat.logic.shape.parser;

import android.content.Context;
import android.graphics.Canvas;
import android.util.Log;

import com.example.popina.projekat.logic.shape.constants.ShapeConst;
import com.example.popina.projekat.logic.shape.draw.ShapeDraw;
import com.example.popina.projekat.logic.shape.factory.ShapeFactory;
import com.example.popina.projekat.logic.shape.figure.Figure;
import com.example.popina.projekat.logic.shape.figure.hole.FinishHole;
import com.example.popina.projekat.logic.shape.figure.hole.StartHole;
import com.example.popina.projekat.logic.shape.figure.hole.WrongHole;
import com.example.popina.projekat.logic.shape.figure.obstacle.CircleObstacle;
import com.example.popina.projekat.logic.shape.figure.obstacle.RectangleObstacle;

import java.io.BufferedReader;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.LinkedList;

/**
 * Created by popina on 27.06.2017..
 */

public abstract class ShapeParserAbstract
{
    protected ShapeFactory shapeFactory;
    protected ShapeDraw shapeDraw;
    protected Context context;


    public ShapeParserAbstract(ShapeFactory shapeFactory, ShapeDraw shapeDraw, Context context)
    {
        this.shapeFactory = shapeFactory;
        this.shapeDraw = shapeDraw;
        this.context = context;
    }

    public ShapeFactory getShapeFactory()
    {
        return shapeFactory;
    }

    public void setShapeFactory(ShapeFactory shapeFactory)
    {
        this.shapeFactory = shapeFactory;
    }

    public abstract ShapeParserInterface parseLine(String line);

    public abstract LinkedList<? extends ShapeParserInterface> parseFile(String fileName);

    public  abstract void drawImageFromFile(Canvas canvas, String fileName);
}
