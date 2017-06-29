package com.example.popina.projekat.application.create;

import android.widget.Toast;

import com.example.popina.projekat.logic.shape.constants.ShapeConst;
import com.example.popina.projekat.logic.shape.coordinate.Coordinate;
import com.example.popina.projekat.logic.shape.figure.Figure;

import java.util.LinkedList;

/**
 * Created by popina on 04.03.2017..
 */

public class CreatePolygonController
{

    CreatePolygonActivity createPolygonActivity;
    CreatePolygonView view;
    CreatePolygonModel model;


    public CreatePolygonController(CreatePolygonActivity createPolygonActivity, CreatePolygonView view, CreatePolygonModel model)
    {
        this.createPolygonActivity = createPolygonActivity;
        this.view = view;
        this.model = model;
    }


    public void actionDownExecute(Coordinate c)
    {

        if ((model.getCurMode() == CreatePolygonModel.MODE_MOVE) || (model.getCurMode() == CreatePolygonModel.MODE_RESIZE)
            || (model.getCurMode() == CreatePolygonModel.MODE_ROTATE))
        {
            LinkedList<Figure> listFigures = model.getListFigures();
            for (Figure figure : listFigures)
            {
                if (figure.isCoordinateInside(c))
                {
                    model.setSelectedFigure(figure);
                    model.setStartAngleOfRotation(figure.calculateAngle(c));
                }
            }
            view.invalidateSurfaceView();
        }
    }

    public void actionUpExecute(Coordinate c)
    {
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
            case CreatePolygonModel.MODE_RESIZE:
            case CreatePolygonModel.MODE_ROTATE:
                model.setSelectedFigure(null);
                model.setStartAngleOfRotation(null);
                break;
            default:
                return;
        }

        view.invalidateSurfaceView();
    }

    public void actionMoveExecute(Coordinate c)
    {
        switch (model.getCurMode())
        {
            case CreatePolygonModel.MODE_DELETE:
                break;
            case CreatePolygonModel.MODE_MOVE:
            {
                Figure figure = model.getSelectedFigure();
                if (null != figure)
                {
                    figure.moveTo(c);
                }
            }
            break;
            case CreatePolygonModel.MODE_RESIZE:
            {
                Figure figure = model.getSelectedFigure();
                if (null != figure)
                {
                    figure.resize(c);
                }
            }
            break;
            case CreatePolygonModel.MODE_ROTATE:
            {
                Figure figure = model.getSelectedFigure();
                if (null != figure)
                {
                    figure.rotate(c, model.getStartAngleOfRotation());
                }
            }
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
            case CreatePolygonModel.CREATE_HOLE_START:
                f = model.getShapeFactory().createStartHole();
                break;
            case CreatePolygonModel.CREATE_HOLE_WRONG:
                f = model.getShapeFactory().createWrongHole();
                break;
            case CreatePolygonModel.CREATE_HOLE_FINISH:
                f = model.getShapeFactory().createFinishHole();
                break;
            case CreatePolygonModel.CREATE_OBSTACLE_RECTANGLE:
                f = model.getShapeFactory().createObstacleRectangle();
                break;
            case CreatePolygonModel.CREATE_OBSTACLE_CIRCLE:
                f = model.getShapeFactory().createObstacleCircle();
                break;
            case CreatePolygonModel.CREATE_HOLE_SLOW_DOWN:
                f = model.getShapeFactory().createSlowDownHole();
                break;
        }


        synchronized (model)
        {
            model.getListFigures().addLast(f);
        }

        view.invalidateSurfaceView();

    }

    // Dilaog pops up and asks user to enter name of polygon or if invalid number of start and finish wholes, Toast which warns user to return.
    //
    public void savePolygon()
    {
        LinkedList<Figure> listFIgures = model.getListFigures();
        int cntStart = 0;
        int cntFinish = 0;
        for (Figure it : listFIgures)
        {
            if (ShapeConst.TYPE_HOLE_START.equals(it.getFigureType()))
            {
                cntStart++;
            }

            if (ShapeConst.TYPE_HOLE_FINISH.equals(it.getFigureType()))
            {
                cntFinish++;
            }
        }

        String errorText = null;

        if (cntStart == 0)
        {
            errorText = "Dodajte startnu poziciju";
        } else if (cntStart > 1)
        {
            errorText = "Samo jednu startnu poziciju smete da imate";
        } else if (cntFinish == 0)
        {
            errorText = "Morate da imate zavrsnu poziciju";
        } else if (cntFinish > 1)
        {
            errorText = "Samo jednu zavrsnu poziciju smete da imate";
        }

        // TODO : check overlaping

        if (null != errorText)
        {
            Toast toast = Toast.makeText(createPolygonActivity.getApplicationContext(), errorText, Toast.LENGTH_SHORT);
            toast.show();
        } else
        {
            SaveDialog dialog = new SaveDialog(createPolygonActivity, model);
            dialog.show();
        }
    }
}
