package com.example.popina.projekat.application.create;

import android.os.Bundle;
import android.view.View;

import com.example.popina.projekat.R;
import com.example.popina.projekat.application.begin.MainModel;
import com.example.popina.projekat.application.common.CommonActivity;

public class CreatePolygonActivity extends CommonActivity
{

    private CreatePolygonView view;
    private CreatePolygonController controller;
    private CreatePolygonModel model;

    @Override
    protected void onCreate(Bundle savedInstanceState)
    {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_create_polygon);

        Bundle extras = getIntent().getExtras();

        model = new CreatePolygonModel();
        if (null != extras)
        {
            String fileName = extras.getString(MainModel.POLYGON_NAME);
            int difficulty = extras.getInt(MainModel.POLYGON_DIFFICULTY);
            if (fileName != null)
            {
                model.setFileName(fileName);
                model.setLevelDifficulty(difficulty);
                model.setEditMode(true);
            }
        }
        view = (CreatePolygonView) findViewById(R.id.surfaceViewCreatePolygon);
        controller = new CreatePolygonController(this, view, model);
        view.setModel(model);
        view.setController(controller);
    }

    public void onModeSelected(View v)
    {
        int curMode;
        switch (v.getId())
        {
            case R.id.radioButtonDelete:
                curMode = CreatePolygonModel.MODE_DELETE;
                break;
            case R.id.radioButtonResize:
                curMode = CreatePolygonModel.MODE_RESIZE;
                break;
            case R.id.radioButtonMove:
                curMode = CreatePolygonModel.MODE_MOVE;
                break;
            case R.id.radioButtonRotate:
                curMode = CreatePolygonModel.MODE_ROTATE;
                break;
            default:
                return;
        }

        model.setCurMode(curMode);
    }

    public void onButtonClicked(View v)
    {
        switch (v.getId())
        {
            case R.id.buttonStartHole:
                controller.createFigure(CreatePolygonModel.CREATE_HOLE_START);
                break;
            case R.id.buttonFinishHole:
                controller.createFigure(CreatePolygonModel.CREATE_HOLE_FINISH);
                break;
            case R.id.buttonWrongHole:
                controller.createFigure(CreatePolygonModel.CREATE_HOLE_WRONG);
                break;
            case R.id.buttonVortexHole:
                controller.createFigure(CreatePolygonModel.CREATE_HOLE_VORTEX);
                break;
            case R.id.buttonObstacleRectangle:
                controller.createFigure(CreatePolygonModel.CREATE_OBSTACLE_RECTANGLE);
                break;
            case R.id.buttonObstacleCircle:
                controller.createFigure(CreatePolygonModel.CREATE_OBSTACLE_CIRCLE);
                break;
            case R.id.buttonSavePolygon:
                controller.savePolygon();
            default:
                return;
        }
    }

    @Override
    public void onBackPressed()
    {
        boolean canGoBack = false;
        if (model.isEditMode())
        {
            if (controller.canSavePolygon())
            {
                controller.savePolygonInFileAndDB();
                canGoBack = true;
            }
        }
        else
        {
            canGoBack = true;
        }

        if (canGoBack)
        {
            setResult(RESULT_OK);
            super.onBackPressed();
        }
    }
}
