package com.example.popina.projekat.logic.shape.parser;

import android.content.Context;
import android.graphics.Canvas;

import com.example.popina.projekat.logic.shape.draw.ShapeDraw;
import com.example.popina.projekat.logic.shape.factory.ShapeFactory;

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

    protected abstract ShapeParserInterface parseLine(String line);

    public abstract LinkedList<? extends ShapeParserInterface> parseFile(String fileName);

    public  abstract void drawImageFromFile(Canvas canvas, String fileName);
}
