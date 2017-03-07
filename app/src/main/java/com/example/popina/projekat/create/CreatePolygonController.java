package com.example.popina.projekat.create;

import android.graphics.Color;

import com.example.popina.projekat.create.shape.Circle;
import com.example.popina.projekat.create.shape.Coordinate;
import com.example.popina.projekat.create.shape.Figure;
import com.example.popina.projekat.create.shape.Rectangle;

import java.util.LinkedList;

/**
 * Created by popina on 04.03.2017..
 */

public class CreatePolygonController {

    CreatePolygonActivity createPolygonActivity;
    CreatePolygonView view;
    CreatePolygonModel model;

    // Default position for created circle rectangle.
    //
    public static final float DEFAULT_OBSTACLE_X = 0.5f;
    public static final float DEFAULT_OBSTACLE_Y = 0.5f;

    public static final float DEFAULT_RADIUS = 0.1f;
    public static final float DEFAULT_RECT_WIDTH = 0.2f;
    public static final float DEFAULT_RECT_HEIGHT = 0.2f;

    public static final float DEFAULT_START_X = 0.1f;
    public static final float DEFAULT_START_Y = 0.1f;

    public static final float DEFAULT_FINISH_X = 0.9f;
    public static final float DEFAULT_FINISH_Y = 0.9f;


    public CreatePolygonController(CreatePolygonActivity createPolygonActivity, CreatePolygonView view, CreatePolygonModel model) {
        this.createPolygonActivity = createPolygonActivity;
        this.view = view;
        this.model = model;
    }


    public void actionDownExecute(Coordinate c) {
        switch (model.getCurMode())
        {
            case CreatePolygonModel.MODE_MOVE:
                LinkedList<Figure> listFigures = model.getListFigures();
                for (Figure figure : listFigures)
                {
                    if (figure.isCoordinateInside(c))
                    {
                        model.setSelectedFigure(figure);
                    }
                }
                break;
            case CreatePolygonModel.MODE_RESIZE:
                break;
            default:
                return;
        }
        view.invalidateSurfaceView();
    }

    public void actionUpExecute(Coordinate c) {
        switch (model.getCurMode())
        {
            case CreatePolygonModel.MODE_DELETE:
                synchronized (model)
                {
                    LinkedList<Figure> listFigures = model.getListFigures();
                    for (Figure f : listFigures)
                    {
                        if (f.isCoordinateInside(c))
                        {
                            listFigures.remove(f);
                            break;
                        }
                    }
                }
                break;
            case CreatePolygonModel.MODE_MOVE:
                model.setSelectedFigure(null);

                break;
            case CreatePolygonModel.MODE_RESIZE:
                break;
            default:
                return;
        }

        view.invalidateSurfaceView();
    }

    public void actionMoveExecute(Coordinate c) {
        switch (model.getCurMode())
        {
            case CreatePolygonModel.MODE_DELETE:
                break;
            case CreatePolygonModel.MODE_MOVE:
                Figure figure = model.getSelectedFigure();
                if (null != figure)
                {
                    figure.moveTo(c);
                }
                break;
            case CreatePolygonModel.MODE_RESIZE:
                break;
            default:
                return;
        }
        view.invalidateSurfaceView();
    }

    public void createFigure(int id)
    {
        Figure f = null;
        switch (id)
        {
            case CreatePolygonModel.CREATE_START:
                f = startHoleCreate();
                break;
            case CreatePolygonModel.CREATE_CIRCLE:
                f =  circleObstacleCrate();
                break;
            case CreatePolygonModel.CREATE_FINISH:
                f = finishHoleCreate();
                break;
            case CreatePolygonModel.CREATE_RECT:
                f = rectangleObstacleCreate();
                break;
        }


        synchronized (model)
        {
            model.getListFigures().addLast(f);
        }

        view.invalidateSurfaceView();

    }

    private Figure circleObstacleCrate()
    {
        final Circle DEFAULT_OBSTACLE_CIRCLE = new Circle(DEFAULT_OBSTACLE_X, DEFAULT_OBSTACLE_Y, DEFAULT_RADIUS);
        Circle circle = UtilScale.scaleCircle(DEFAULT_OBSTACLE_CIRCLE);
        circle.setColor(Color.BLACK);

        return circle;
    }

    private Figure startHoleCreate()
    {
        final Circle DEFAULT_START = new Circle(DEFAULT_START_X, DEFAULT_START_Y, DEFAULT_RADIUS);
        Circle circle = UtilScale.scaleCircle(DEFAULT_START);
        circle.setColor(Color.RED);

        return circle;
    }

    private Figure finishHoleCreate()
    {
        final Circle DEFAULT_FINISH = new Circle(DEFAULT_FINISH_X, DEFAULT_FINISH_Y, DEFAULT_RADIUS);
        Circle circle = UtilScale.scaleCircle(DEFAULT_FINISH);
        circle.setColor(Color.GREEN);

        return circle;
    }



    public Figure rectangleObstacleCreate()
    {
        final Rectangle DEFAULT_OBSTACLE_RECT = new Rectangle(DEFAULT_OBSTACLE_X, DEFAULT_OBSTACLE_Y, DEFAULT_RECT_WIDTH, DEFAULT_RECT_HEIGHT);
        Rectangle rect = UtilScale.scaleRectangle(DEFAULT_OBSTACLE_RECT);
        rect.setColor(Color.YELLOW);

        return rect;
    }
}
