package com.example.popina.projekat.logic.shape.factory;

import com.example.popina.projekat.logic.shape.model.ShapeModel;
import com.example.popina.projekat.logic.shape.figure.Figure;
import com.example.popina.projekat.logic.shape.figure.hole.FinishHole;
import com.example.popina.projekat.logic.shape.figure.obstacle.RectangleObstacle;
import com.example.popina.projekat.logic.shape.figure.hole.StartHole;
import com.example.popina.projekat.logic.shape.figure.hole.WrongHole;
import com.example.popina.projekat.logic.shape.scale.UtilScale;

import java.util.LinkedList;

/**
 * Created by popina on 08.03.2017..
 */

public class ShapeFactory {

    private UtilScale utilScale;

    public ShapeFactory(UtilScale utilScale) {
        this.utilScale = utilScale;
    }

    public UtilScale getUtilScale() {
        return utilScale;
    }

    public void setUtilScale(UtilScale utilScale) {
        this.utilScale = utilScale;
    }

    public  StartHole createStartHole()
    {
        final StartHole DEFAULT_START = new StartHole(ShapeModel.DEFAULT_START_X, ShapeModel.DEFAULT_START_Y, ShapeModel.DEFAULT_RADIUS);
        StartHole startHole = DEFAULT_START.scale(utilScale);

        return startHole;
    }

    public FinishHole createFinishHole()
    {
        final FinishHole DEFAULT_FINISH = new FinishHole(ShapeModel.DEFAULT_FINISH_X, ShapeModel.DEFAULT_FINISH_Y, ShapeModel.DEFAULT_RADIUS);
        FinishHole finishHole = DEFAULT_FINISH.scale(utilScale);

        return finishHole;
    }

    public WrongHole createWrongHole()
    {
        final WrongHole DEFAULT_OBSTACLE_CIRCLE = new WrongHole(ShapeModel.DEFAULT_OBSTACLE_X, ShapeModel.DEFAULT_OBSTACLE_Y, ShapeModel.DEFAULT_RADIUS);
        WrongHole wrongHole = DEFAULT_OBSTACLE_CIRCLE.scale(utilScale);

        return wrongHole;
    }

    public RectangleObstacle createObstacle()
    {
        final RectangleObstacle DEFAULT_OBSTACLE_RECT = new RectangleObstacle(ShapeModel.DEFAULT_OBSTACLE_X, ShapeModel.DEFAULT_OBSTACLE_Y, ShapeModel.DEFAULT_RECT_WIDTH, ShapeModel.DEFAULT_RECT_HEIGHT);
        RectangleObstacle rect = DEFAULT_OBSTACLE_RECT.scale(utilScale);

        return rect;
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
        LinkedList <Figure> list = new LinkedList<>();

        for (Figure f : listFigures)
        {
            list.addLast(f.scale(utilScale));
        }
        return  list;
    }

    public LinkedList<Figure> scaleReverseFigure(LinkedList<Figure> listFigures)
    {
        LinkedList <Figure> list = new LinkedList<>();

        for (Figure f : listFigures)
        {
            list.addLast(f.scaleReverse(utilScale));
        }
        return  list;
    }

}
