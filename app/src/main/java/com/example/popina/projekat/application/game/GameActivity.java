package com.example.popina.projekat.application.game;

import android.app.ActionBar;
import android.app.Activity;
import android.os.Bundle;
import android.view.View;
import android.view.Window;

import com.example.popina.projekat.R;
import com.example.popina.projekat.application.begin.MainModel;
import com.example.popina.projekat.application.common.CommonActivity;

public class GameActivity extends CommonActivity {
    private GameModel model;
    private GameController controller;
    private GameView view;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        String fileName = null;
        super.onCreate(savedInstanceState);

        setContentView(R.layout.activity_game);
        Bundle extras = getIntent().getExtras();
        if (null != extras)
        {
            fileName = extras.getString(MainModel.POLYGON_NAME);
        }


        model = new GameModel();
        model.setFileName(fileName);
        view = (GameView)findViewById(R.id.surfaceViewGame);
        controller = new GameController(this, model, view);
        view.setModel(model);
        view.setController(controller);


    }


}
