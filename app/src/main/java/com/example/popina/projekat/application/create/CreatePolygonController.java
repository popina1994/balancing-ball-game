package com.example.popina.projekat.application.create;

import android.widget.Toast;

import com.example.popina.projekat.logic.shape.coordinate.Coordinate;
import com.example.popina.projekat.logic.shape.figure.Figure;
import com.example.popina.projekat.logic.shape.model.ShapeModel;

import java.util.LinkedList;

/**
 * Created by popina on 04.03.2017..
 */

public class CreatePolygonController {

    CreatePolygonActivity createPolygonActivity;
    CreatePolygonView view;
    CreatePolygonModel model;


    public CreatePolygonController(CreatePolygonActivity createPolygonActivity, CreatePolygonView view, CreatePolygonModel model) {
        this.createPolygonActivity = createPolygonActivity;
        this.view = view;
        this.model = model;
    }




    public void actionDownExecute(Coordinate c) {

        // Only for MOVE and RESIZE remember figure.
        //
        if ( (model.getCurMode() == CreatePolygonModel.MODE_MOVE) || (model.getCurMode() == CreatePolygonModel.MODE_RESIZE)) {
                LinkedList<Figure> listFigures = model.getListFigures();
                for (Figure figure : listFigures) {
                    if (figure.isCoordinateInside(c)) {
                        model.setSelectedFigure(figure);
                    }
                }
            view.invalidateSurfaceView();
        }
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
            case CreatePolygonModel.MODE_RESIZE:
                model.setSelectedFigure(null);
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
            case CreatePolygonModel.MODE_MOVE: {
                Figure figure = model.getSelectedFigure();
                if (null != figure) {
                    figure.moveTo(c);
                }
            }
                break;
            case CreatePolygonModel.MODE_RESIZE: {
                Figure figure = model.getSelectedFigure();
                if (null != figure) {
                    figure.resize(c);
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
            case CreatePolygonModel.CREATE_START_HOLE:
                f = model.getShapeFactory().createStartHole();
                break;
            case CreatePolygonModel.CREATE_WRONG_HOLE:
                f =  model.getShapeFactory().createWrongHole();
                break;
            case CreatePolygonModel.CREATE_FINISH_HOLE:
                f = model.getShapeFactory().createFinishHole();
                break;
            case CreatePolygonModel.CREATE_OBSTACLE:
                f = model.getShapeFactory().createObstacle();
                break;
        }


        synchronized (model)
        {
            model.getListFigures().addLast(f);
        }

        view.invalidateSurfaceView();

    }
    // Question to think about.
    //

    // Dilaog pops up and asks user to enter name of polygon or if invalid number of start and finish wholes, Toast which warns user to return.
    //
    public void savePolygon() {
        LinkedList<Figure> listFIgures = model.getListFigures();
        int cntStart = 0;
        int cntFinish = 0;
        for (Figure it : listFIgures)
        {
            if (ShapeModel.TYPE_START_HOLE.equals(it.getFigureType()))
            {
                cntStart ++;
            }

            if (ShapeModel.TYPE_FINISH_HOLE.equals(it.getFigureType()))
            {
                cntFinish ++;
            }
        }

        String errorText = null;

        if (cntStart ==0) {
            errorText = "Dodajte startnu poziciju";
        }
        else if (cntStart > 1) {
            errorText = "Samo jednu startnu poziciju smete da imate";
        }
        else if (cntFinish == 0) {
            errorText = "Morate da imate zavrsnu poziciju";
        }
        else if (cntFinish > 1) {
            errorText = "Samo jednu zavrsnu poziciju smete da imate";
        }

        // TODO : check overlaping

        if (null != errorText) {
            Toast toast = Toast.makeText(createPolygonActivity.getApplicationContext(), errorText, Toast.LENGTH_SHORT);
            toast.show();
        }
        else
        {
            // Dialog to save
            // TODO: generate rectangles.
            SaveDialog dialog = new SaveDialog(createPolygonActivity, model);
            dialog.show();
        }
    }
}
