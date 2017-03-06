package com.example.popina.projekat.create;

import android.app.Activity;
import android.os.Bundle;
import android.view.View;
import android.view.Window;

import com.example.popina.projekat.R;

public class CreatePolygonActivity extends Activity {

    private CreatePolygonView view;
    private CreatePolygonController controller;
    private CreatePolygonModel model;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        // Prevent Title.
        //
        requestWindowFeature(Window.FEATURE_NO_TITLE);

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
            case R.id.radioButtonAdd:
                curMode = CreatePolygonModel.MODE_ADD;
                break;
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
}
