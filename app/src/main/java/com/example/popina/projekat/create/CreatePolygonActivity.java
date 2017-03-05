package com.example.popina.projekat.create;

import android.app.Activity;
import android.os.Bundle;
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
        view.setModel();
        controller = new CreatePolygonController(this, view, model);

    }
}
