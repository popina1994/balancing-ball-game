package com.example.popina.projekat.application.create;

import android.content.Context;
import android.util.Log;
import android.widget.Toast;

import com.example.popina.projekat.logic.shape.constants.ShapeConst;
import com.example.popina.projekat.logic.shape.coordinate.Coordinate;
import com.example.popina.projekat.logic.shape.factory.ShapeBorderFactory;
import com.example.popina.projekat.logic.shape.figure.Figure;
import com.example.popina.projekat.logic.shape.figure.obstacle.RectangleObstacle;
import com.example.popina.projekat.logic.statistics.database.ScoreDatabase;

import java.io.FileOutputStream;
import java.io.IOException;
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
            case CreatePolygonModel.CREATE_HOLE_VORTEX:
                f = model.getShapeFactory().createVortexHole();
                break;
        }


        synchronized (model)
        {
            model.getListFigures().addLast(f);
        }

        view.invalidateSurfaceView();

    }

    boolean canSavePolygon()
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
            return false;
        }
        return true;

    }

    // Dilaog pops up and asks user to enter name of polygon or if invalid number of start and finish wholes, Toast which warns user to return.
    //
    public void savePolygon()
    {
        if (canSavePolygon())
        {
            SaveDialog dialog = new SaveDialog(createPolygonActivity, this, model);
            dialog.show();
        }
    }

    LinkedList<Figure> initWalls()
    {
        ShapeBorderFactory shapeBorderFactory = (ShapeBorderFactory) model.getShapeFactory();
        LinkedList<Figure> listWalls = new LinkedList<>();
        LinkedList<RectangleObstacle> listWallsRect = shapeBorderFactory.createBorders();

        for (RectangleObstacle itRect : listWallsRect)
        {
            listWalls.addLast(itRect);
        }

        return listWalls;
    }

    public void savePolygonInFileAndDB()
    {
        if (model.getFileName() != null)
        {
            FileOutputStream outputStream = null;

            try
            {
                outputStream = createPolygonActivity.openFileOutput(model.getFileName(), Context.MODE_PRIVATE);
                StringBuilder stringBuilder = new StringBuilder();
                LinkedList<Figure> listFigure = model.getListFigures();
                listFigure = model.getShapeFactory().scaleReverseFigure(listFigure);

                for (Figure it : listFigure)
                {
                    stringBuilder.append(it.toString() + "\n");
                }

                outputStream.write(stringBuilder.toString().getBytes());

                ScoreDatabase database = new ScoreDatabase(createPolygonActivity.getApplicationContext());
                database.insertLevel(model.getFileName(), model.getLevelDifficulty());

            }
            catch (IOException e)
            {
                e.printStackTrace();
            }
            finally
            {
                if (null != outputStream)
                {
                    try
                    {
                        outputStream.close();
                    }
                    catch (IOException e)
                    {
                        Log.d("Save dialog", "Neko te prokleo");
                        e.printStackTrace();
                    }
                }
            }
        }
        else
        {
            Toast toast = Toast.makeText(createPolygonActivity.getApplicationContext(), "Morate da date ime poligonu (kliknite sacuvaj)", Toast.LENGTH_SHORT);
            toast.show();
        }
    }

}
