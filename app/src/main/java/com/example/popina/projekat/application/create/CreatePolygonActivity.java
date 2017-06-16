package com.example.popina.projekat.application.create;

import android.os.Bundle;
import android.view.View;

import com.example.popina.projekat.R;
import com.example.popina.projekat.application.common.CommonActivity;

public class CreatePolygonActivity extends CommonActivity{

    private CreatePolygonView view;
    private CreatePolygonController controller;
    private CreatePolygonModel model;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        // Prevent Title.
        //
        //requestWindowFeature(Window.FEATURE_NO_TITLE);

        setContentView(R.layout.activity_create_polygon);

        model = new CreatePolygonModel();
        view = (CreatePolygonView)findViewById(R.id.surfaceViewCreatePolygon);
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
                controller.createFigure(CreatePolygonModel.CREATE_START_HOLE);
                break;
            case R.id.buttonFinishHole:
                controller.createFigure(CreatePolygonModel.CREATE_FINISH_HOLE);
                break;
            case R.id.buttonWrongHole:
                controller.createFigure(CreatePolygonModel.CREATE_WRONG_HOLE);
                break;
            case R.id.buttonObstacleRectangle:
                controller.createFigure(CreatePolygonModel.CREATE_OBSTACLE_RECTANGLE);
                break;
            case R.id.buttonObstacleCircle:
                controller.createFigure(CreatePolygonModel.CREATE_OBSTACLE_CIRCLE);
                break;
            case  R.id.buttonSavePolygon:
                controller.savePolygon();
            default:
                return;
        }
    }

    @Override
    public void onBackPressed() {
        setResult(RESULT_OK);
        super.onBackPressed();
    }
}
