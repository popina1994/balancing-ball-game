package com.example.popina.projekat.application.game;

import android.hardware.Sensor;
import android.hardware.SensorEvent;
import android.hardware.SensorEventListener;
import android.os.Bundle;

import com.example.popina.projekat.R;
import com.example.popina.projekat.application.begin.MainModel;
import com.example.popina.projekat.application.common.CommonActivity;
import com.example.popina.projekat.application.game.model.GameModel;

public class GameActivity extends CommonActivity implements SensorEventListener
{
    private GameModel model;
    private GameController controller;
    private GameView view;

    @Override
    protected void onCreate(Bundle savedInstanceState)
    {
        String fileName = null;
        super.onCreate(savedInstanceState);

        setContentView(R.layout.activity_game);
        Bundle extras = getIntent().getExtras();
        if (null != extras)
        {
            fileName = extras.getString(MainModel.POLYGON_NAME);
        }

        model = new GameModel();
        model.setLevelName(fileName);

        view = (GameView) findViewById(R.id.surfaceViewGame);
        controller = new GameController(this, model, view);
        view.setModel(model);
        view.setController(controller);
    }

    @Override
    protected void onResume()
    {
        super.onResume();
    }

    @Override
    protected void onPause()
    {
        super.onPause();
        controller.pause();
    }

    @Override
    public void onSensorChanged(SensorEvent event)
    {
        controller.onNewValues(event.values, event.timestamp);
    }

    @Override
    public void onAccuracyChanged(Sensor sensor, int accuracy)
    {
    }

    // Carefull, this is needed to be called.
    //
    @Override
    protected void onDestroy()
    {
        super.onDestroy();
        controller.destructor();
    }
}
