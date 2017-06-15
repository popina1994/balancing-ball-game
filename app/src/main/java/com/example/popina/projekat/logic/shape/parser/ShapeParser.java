package com.example.popina.projekat.logic.shape.parser;

import android.content.Context;
import android.graphics.Canvas;
import android.util.Log;

import com.example.popina.projekat.logic.shape.draw.ShapeDraw;
import com.example.popina.projekat.logic.shape.model.ShapeModel;
import com.example.popina.projekat.logic.shape.factory.ShapeFactory;
import com.example.popina.projekat.logic.shape.figure.Figure;
import com.example.popina.projekat.logic.shape.figure.circle.FinishHole;
import com.example.popina.projekat.logic.shape.figure.circle.StartHole;
import com.example.popina.projekat.logic.shape.figure.circle.WrongHole;
import com.example.popina.projekat.logic.shape.figure.rectangle.Rectangle;

import java.io.BufferedReader;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.LinkedList;

/**
 * Created by popina on 08.03.2017..
 */

public class ShapeParser {
    private ShapeFactory shapeFactory;
    private ShapeDraw shapeDraw;
    private  Context context;


    public ShapeParser(ShapeFactory shapeFactory, ShapeDraw shapeDraw, Context context) {
        this.shapeFactory = shapeFactory;
        this.shapeDraw = shapeDraw;
        this.context = context;
    }

    public ShapeFactory getShapeFactory() {
        return shapeFactory;
    }

    public void setShapeFactory(ShapeFactory shapeFactory) {
        this.shapeFactory = shapeFactory;
    }

    private Figure parseLine(String line)
    {
        String[] tokens = line.split("\\s+");
        Figure figure = null;

        switch (tokens[ShapeModel.FIGURE_TYPE_IDX])
        {
            case ShapeModel.TYPE_START_HOLE:
                figure = new StartHole(
                        Float.parseFloat(tokens[ShapeModel.FIGURE_COORDINATE_X_IDX]),
                        Float.parseFloat(tokens[ShapeModel.FIGURE_COORDINATE_Y_IDX]),
                        Float.parseFloat(tokens[ShapeModel.FIGURE_CIRCLE_RADIUS_IDX])
                );
                break;
            case ShapeModel.TYPE_FINISH_HOLE:
                figure = new FinishHole(
                        Float.parseFloat(tokens[ShapeModel.FIGURE_COORDINATE_X_IDX]),
                        Float.parseFloat(tokens[ShapeModel.FIGURE_COORDINATE_Y_IDX]),
                        Float.parseFloat(tokens[ShapeModel.FIGURE_CIRCLE_RADIUS_IDX])
                );
                break;
            case ShapeModel.TYPE_WRONG_HOLE:
                figure = new WrongHole(
                        Float.parseFloat(tokens[ShapeModel.FIGURE_COORDINATE_X_IDX]),
                        Float.parseFloat(tokens[ShapeModel.FIGURE_COORDINATE_Y_IDX]),
                        Float.parseFloat(tokens[ShapeModel.FIGURE_CIRCLE_RADIUS_IDX])
                );
                break;
            case ShapeModel.TYPE_OBSTACLE:
                figure = new Rectangle(
                        Float.parseFloat(tokens[ShapeModel.FIGURE_COORDINATE_X_IDX]),
                        Float.parseFloat(tokens[ShapeModel.FIGURE_COORDINATE_Y_IDX]),
                        Float.parseFloat(tokens[ShapeModel.FIGURE_RECT_WIDTH_IDX]),
                        Float.parseFloat(tokens[ShapeModel.FIGURE_RECT_HEIGHT_IDX])
                );
        }

        figure.setColor(Integer.parseInt(tokens[ShapeModel.FIGURE_COLOR_IDX]));

        return  figure;
    }

    public LinkedList<Figure> parseFile(String fileName)
    {
        FileInputStream file = null;
        LinkedList<Figure> listFigures = new LinkedList<>();
        try {
            file = context.openFileInput(fileName);
            BufferedReader bufferedReader = new BufferedReader(new InputStreamReader(file));
            String curLine = null;
            while ( (curLine = bufferedReader.readLine()) != null)
            {
                listFigures.addLast(shapeFactory.scaleFigure(parseLine(curLine)));
            }

        } catch (java.io.IOException e) {
            e.printStackTrace();
        }
        finally {
            if (file != null)
            {
                try {
                    file.close();
                } catch (IOException e) {
                    Log.d("Shape Parser", " Neko te prokleo");
                }
            }
        }

        return  listFigures;
    }

    public void drawImageFromFile(Canvas canvas, String fileName) {
        LinkedList<Figure> list = parseFile(fileName);

        shapeDraw.drawOnCanvas(list,canvas);
    }


}
