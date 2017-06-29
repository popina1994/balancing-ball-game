package com.example.popina.projekat.logic.shape.factory;

import com.example.popina.projekat.logic.shape.constants.ShapeConst;
import com.example.popina.projekat.logic.shape.figure.Figure;
import com.example.popina.projekat.logic.shape.figure.hole.gravity.FinishHole;
import com.example.popina.projekat.logic.shape.figure.hole.StartHole;
import com.example.popina.projekat.logic.shape.figure.hole.gravity.WrongHole;
import com.example.popina.projekat.logic.shape.figure.obstacle.CircleObstacle;
import com.example.popina.projekat.logic.shape.figure.obstacle.RectangleObstacle;
import com.example.popina.projekat.logic.shape.scale.UtilScale;

import java.util.LinkedList;

/**
 * Created by popina on 08.03.2017..
 */

public class ShapeFactory
{

    private UtilScale utilScale;

    public ShapeFactory(UtilScale utilScale)
    {
        this.utilScale = utilScale;
    }

    public UtilScale getUtilScale()
    {
        return utilScale;
    }

    public void setUtilScale(UtilScale utilScale)
    {
        this.utilScale = utilScale;
    }

    public StartHole createStartHole()
    {
        final StartHole DEFAULT_START = new StartHole(ShapeConst.DEFAULT_START_X, ShapeConst.DEFAULT_START_Y, ShapeConst.DEFAULT_RADIUS);
        StartHole startHole = DEFAULT_START.scale(utilScale);

        return startHole;
    }

    public FinishHole createFinishHole()
    {
        final FinishHole DEFAULT_FINISH = new FinishHole(ShapeConst.DEFAULT_FINISH_X, ShapeConst.DEFAULT_FINISH_Y, ShapeConst.DEFAULT_RADIUS);
        FinishHole finishHole = DEFAULT_FINISH.scale(utilScale);

        return finishHole;
    }

    public WrongHole createWrongHole()
    {
        final WrongHole DEFAULT_OBSTACLE_CIRCLE = new WrongHole(ShapeConst.DEFAULT_OBSTACLE_X, ShapeConst.DEFAULT_OBSTACLE_Y, ShapeConst.DEFAULT_RADIUS);
        WrongHole wrongHole = DEFAULT_OBSTACLE_CIRCLE.scale(utilScale);

        return wrongHole;
    }

    public RectangleObstacle createObstacleRectangle()
    {
        final RectangleObstacle DEFAULT_OBSTACLE_RECT = new RectangleObstacle(ShapeConst.DEFAULT_OBSTACLE_X, ShapeConst.DEFAULT_OBSTACLE_Y, ShapeConst.DEFAULT_RECT_WIDTH, ShapeConst.DEFAULT_RECT_HEIGHT);
        RectangleObstacle rect = DEFAULT_OBSTACLE_RECT.scale(utilScale);

        return rect;
    }

    public Figure createObstacleCircle()
    {
        final CircleObstacle DEFAULT_OBSTACLE_CIRC = new CircleObstacle(ShapeConst.DEFAULT_OBSTACLE_X, ShapeConst.DEFAULT_OBSTACLE_Y, ShapeConst.DEFAULT_RECT_RADIUS);
        CircleObstacle circ = DEFAULT_OBSTACLE_CIRC.scale(utilScale);
        return circ;
    }

    public Figure scaleFigure(Figure f)
    {
        return f.scale(utilScale);
    }

    public Figure scaleReverseFigure(Figure f)
    {
        return f.scaleReverse(utilScale);
    }

    public LinkedList<Figure> scaleFigure(LinkedList<Figure> listFigures)
    {
        LinkedList<Figure> list = new LinkedList<>();

        for (Figure f : listFigures)
        {
            list.addLast(f.scale(utilScale));
        }
        return list;
    }

    public LinkedList<Figure> scaleReverseFigure(LinkedList<Figure> listFigures)
    {
        LinkedList<Figure> list = new LinkedList<>();

        for (Figure f : listFigures)
        {
            list.addLast(f.scaleReverse(utilScale));
        }
        return list;
    }


}
