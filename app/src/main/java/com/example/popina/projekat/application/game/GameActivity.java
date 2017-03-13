package com.example.popina.projekat.application.game;

import android.app.ActionBar;
import android.app.Activity;
import android.content.Context;
import android.hardware.Sensor;
import android.hardware.SensorEvent;
import android.hardware.SensorEventListener;
import android.hardware.SensorManager;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.view.Window;

import com.example.popina.projekat.R;
import com.example.popina.projekat.application.begin.MainModel;
import com.example.popina.projekat.application.common.CommonActivity;
import com.example.popina.projekat.logic.game.coeficient.Coeficient;

public class GameActivity extends CommonActivity implements SensorEventListener{
    private GameModel model;
    private GameController controller;
    private GameView view;

    private SensorManager sensorManager;

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
        Coeficient coeficient = new Coeficient(this);

        model.setCoeficient(coeficient);

        view = (GameView)findViewById(R.id.surfaceViewGame);
        controller = new GameController(this, model, view);
        view.setModel(model);
        view.setController(controller);

        sensorManager = (SensorManager)getSystemService(Context.SENSOR_SERVICE);


    }

    @Override
    protected void onResume() {
        super.onResume();
        Sensor s = sensorManager.getDefaultSensor(Sensor.TYPE_ACCELEROMETER);
        if (null != s)
        {
            model.setLastTime(Long.MAX_VALUE);
            sensorManager.registerListener(this, s, SensorManager.SENSOR_DELAY_GAME);
        }
    }

    @Override
    protected void onPause() {
        super.onPause();
        sensorManager.unregisterListener(this);
    }

    @Override
    public void onSensorChanged(SensorEvent event) {
        controller.onNewValues(event.values, event.timestamp);
        {

        }
    }

    @Override
    public void onAccuracyChanged(Sensor sensor, int accuracy) {

    }
}
