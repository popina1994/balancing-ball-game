package com.example.popina.projekat.logic.shape.parser;

import android.content.Context;
import android.graphics.Canvas;
import android.util.Log;

import com.example.popina.projekat.logic.shape.constants.ShapeConst;
import com.example.popina.projekat.logic.shape.draw.ShapeDraw;
import com.example.popina.projekat.logic.shape.factory.ShapeFactory;
import com.example.popina.projekat.logic.shape.figure.Figure;
import com.example.popina.projekat.logic.shape.figure.hole.gravity.FinishHole;
import com.example.popina.projekat.logic.shape.figure.hole.StartHole;
import com.example.popina.projekat.logic.shape.figure.hole.gravity.VortexHole;
import com.example.popina.projekat.logic.shape.figure.hole.gravity.WrongHole;
import com.example.popina.projekat.logic.shape.figure.obstacle.CircleObstacle;
import com.example.popina.projekat.logic.shape.figure.obstacle.RectangleObstacle;

import java.io.BufferedReader;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.LinkedList;

/**
 * Created by popina on 08.03.2017..
 */

public class ShapeParser extends ShapeParserAbstract
{
    public ShapeParser(ShapeFactory shapeFactory, ShapeDraw shapeDraw, Context context)
    {
        super(shapeFactory, shapeDraw, context);
    }

    public Figure parseLine(String line)
    {
        String[] tokens = line.split("\\s+");
        Figure figure = null;

        switch (tokens[ShapeConst.FIGURE_TYPE_IDX])
        {
            case ShapeConst.TYPE_HOLE_START:
                figure = new StartHole(
                        Float.parseFloat(tokens[ShapeConst.FIGURE_COORDINATE_X_IDX]),
                        Float.parseFloat(tokens[ShapeConst.FIGURE_COORDINATE_Y_IDX]),
                        Float.parseFloat(tokens[ShapeConst.FIGURE_CIRCLE_RADIUS_IDX])
                );
                break;
            case ShapeConst.TYPE_HOLE_FINISH:
                figure = new FinishHole(
                        Float.parseFloat(tokens[ShapeConst.FIGURE_COORDINATE_X_IDX]),
                        Float.parseFloat(tokens[ShapeConst.FIGURE_COORDINATE_Y_IDX]),
                        Float.parseFloat(tokens[ShapeConst.FIGURE_CIRCLE_RADIUS_IDX])
                );
                break;
            case ShapeConst.TYPE_HOLE_WRONG:
                figure = new WrongHole(
                        Float.parseFloat(tokens[ShapeConst.FIGURE_COORDINATE_X_IDX]),
                        Float.parseFloat(tokens[ShapeConst.FIGURE_COORDINATE_Y_IDX]),
                        Float.parseFloat(tokens[ShapeConst.FIGURE_CIRCLE_RADIUS_IDX])
                );
                break;
            case ShapeConst.TYPE_HOLE_VORTEX:
                figure = new VortexHole(
                        Float.parseFloat(tokens[ShapeConst.FIGURE_COORDINATE_X_IDX]),
                        Float.parseFloat(tokens[ShapeConst.FIGURE_COORDINATE_Y_IDX]),
                        Float.parseFloat(tokens[ShapeConst.FIGURE_CIRCLE_RADIUS_IDX])
                );
                break;
            case ShapeConst.TYPE_OBSTACLE_RECTANGLE:
                figure = new RectangleObstacle(
                        Float.parseFloat(tokens[ShapeConst.FIGURE_COORDINATE_X_IDX]),
                        Float.parseFloat(tokens[ShapeConst.FIGURE_COORDINATE_Y_IDX]),
                        Float.parseFloat(tokens[ShapeConst.FIGURE_RECT_WIDTH_IDX]),
                        Float.parseFloat(tokens[ShapeConst.FIGURE_RECT_HEIGHT_IDX]),
                        Float.parseFloat(tokens[ShapeConst.FIGURE_RECT_ANGLE_IDX]));
                break;
            case ShapeConst.TYPE_OBSTACLE_CIRCLE:
                figure = new CircleObstacle(
                        Float.parseFloat(tokens[ShapeConst.FIGURE_COORDINATE_X_IDX]),
                        Float.parseFloat(tokens[ShapeConst.FIGURE_COORDINATE_Y_IDX]),
                        Float.parseFloat(tokens[ShapeConst.FIGURE_CIRCLE_RADIUS_IDX]));
        }

        figure.setColor(Integer.parseInt(tokens[ShapeConst.FIGURE_COLOR_IDX]));

        return figure;
    }

    public LinkedList<Figure> parseFile(String fileName)
    {
        FileInputStream file = null;
        LinkedList<Figure> listFigures = new LinkedList<>();
        try
        {
            file = context.openFileInput(fileName);
            BufferedReader bufferedReader = new BufferedReader(new InputStreamReader(file));
            String curLine = null;
            while ((curLine = bufferedReader.readLine()) != null)
            {
                listFigures.addLast(shapeFactory.scaleFigure(parseLine(curLine)));
            }

        }
        catch (java.io.IOException e)
        {
            e.printStackTrace();
        }
        finally
        {
            if (file != null)
            {
                try
                {
                    file.close();
                }
                catch (IOException e)
                {
                    Log.d("Shape Parser", " Neko te prokleo");
                }
            }
        }

        return listFigures;
    }

    public void drawImageFromFile(Canvas canvas, String fileName)
    {
        LinkedList<Figure> list = parseFile(fileName);

        shapeDraw.drawOnCanvas(list, canvas);
    }


}
