package com.example.popina.projekat.application.game;

import android.app.Dialog;
import android.content.Context;
import android.hardware.Sensor;
import android.hardware.SensorManager;
import android.widget.Toast;

import com.example.popina.projekat.application.game.model.GameModel;
import com.example.popina.projekat.logic.game.coefficient.Coefficient;
import com.example.popina.projekat.logic.game.collision.CollisionModel;
import com.example.popina.projekat.logic.game.collision.CollisionModelAbstract;
import com.example.popina.projekat.logic.game.utility.Coordinate3D;
import com.example.popina.projekat.logic.game.utility.Time;
import com.example.popina.projekat.logic.game.utility.Utility;
import com.example.popina.projekat.logic.shape.coordinate.Coordinate;
import com.example.popina.projekat.logic.shape.sound.SoundPlayer;
import com.example.popina.projekat.logic.shape.sound.SoundPlayerCallback;

import java.util.LinkedList;

/**
 * Created by popina on 09.03.2017..
 */

public class GameController
{
    private GameActivity gameActivity;
    private GameModel model;
    private GameView view;


    public GameController(GameActivity gameActivity, GameModel model, GameView view)
    {
        this.gameActivity = gameActivity;
        this.model = model;
        this.view = view;

        initActivity();
    }

    private void initActivity()
    {
        Coefficient coefficient = new Coefficient(gameActivity);
        model.setCoefficient(coefficient);

        SensorManager sensorManager = (SensorManager) gameActivity.getSystemService(Context.SENSOR_SERVICE);
        model.setSensorManager(sensorManager);

        SoundPlayerCallback soundPlayerCallback = new SoundPlayer(gameActivity);
        model.setSoundPlayerCallback(soundPlayerCallback);

        CollisionModelAbstract collisionModelAbstract = new CollisionModel(coefficient, soundPlayerCallback);
        model.setCollisionModel(collisionModelAbstract);


    }

    public void destructor()
    {
        model.getSoundPlayerCallback().destroy();
    }

    public void resume()
    {
        SensorManager sensorManager = model.getSensorManager();
        Sensor sensor = sensorManager.getDefaultSensor(Sensor.TYPE_ACCELEROMETER);

        if (null != sensor)
        {
            model.setLastTimeInitialized(false);
            sensorManager.registerListener(gameActivity, sensor, SensorManager.SENSOR_DELAY_GAME);
            model.getListTimes().addLast(new Time(System.currentTimeMillis()));
            model.setPaused(false);
        }

    }

    public void pause()
    {
        model.getSensorManager().unregisterListener(gameActivity);
        model.getListTimes().getLast().setEnd(System.currentTimeMillis());
        model.setPaused(true);
    }

    public void onNewValues(float[] newAcc, long time)
    {
        if (!model.isGameOver())
        {
            if (model.isLastTimeInitialized())
            {
                Coordinate3D filteredAcc = model.getFilter().filter(new Coordinate3D(newAcc[0], newAcc[1], newAcc[2]));
                updatePosition(filteredAcc, time);
            }
            model.getCollisionModel().setLastTime(time);
            model.setLastTimeInitialized(true);
        }
    }

    private void updatePosition(Coordinate3D filteredAcc, long time)
    {
        // This is in case if surface view hasn't been initialized.
        //
        if (model.isSurfaceInitialized())
        {
            int state = model.getCollisionModel().updateSystem(filteredAcc, time, model.getListFigures(), model.getBall());
            switch (state)
            {
                case CollisionModelAbstract.GAME_CONTINUES_COLLISION:
                    break;
                case CollisionModelAbstract.GAME_CONTINUES_NO_COLLISION:
                    break;
                case CollisionModelAbstract.GAME_OVER_LOSE:
                    model.setGameOver(true);
                    Toast.makeText(gameActivity, "Izgubio si igru", Toast.LENGTH_SHORT).show();
                    break;
                case CollisionModelAbstract.GAME_OVER_WIN:
                    model.setGameOver(true);
                    model.getListTimes().getLast().setEnd(System.currentTimeMillis());
                    Dialog dialog = new GameOverDialog(gameActivity, calcTime(model.getListTimes()), model.getLevelName());
                    dialog.show();
                    break;
            }

            view.invalidateSurfaceView();
        }
    }

    private long calcTime(LinkedList<Time> listTimes)
    {
        long timeAll = 0;
        for (Time time : listTimes)
        {
            timeAll += time.timeInt();
        }
        return timeAll;
    }

    // A * X + B * Y = Z
    //
    private Coordinate3D calculateLine(Coordinate point1, Coordinate point2)
    {
        float A = point1.getY() - point2.getY();
        float B = point2.getX() - point1.getX();
        float C = point1.getX() * point2.getY() - point1.getY() * point2.getX();
        Coordinate3D line = new Coordinate3D(A, B, C);

        return line;
    }

    private Coordinate intersectionLines(Coordinate3D line1, Coordinate3D line2)
    {
        float A1 = line1.getX();
        float B1 = line1.getY();
        float C1 = line1.getZ();

        float A2 = line2.getX();
        float B2 = line2.getY();
        float C2 = line2.getZ();

        // Parallel lines.
        //
        float det = -A1 * B2 + A2 * B1;
        if (Math.abs(det)
                < Utility.FLOAT_ACCURACY)
        {
            return null;
        }

        return new Coordinate((B2 * C1 - B1 * C2) / det, (A1 * C2 - A2 * C1) / det);
    }

    private float calculateX(Coordinate3D line, float y)
    {
        float A = line.getX();
        float B = line.getY();
        float C = line.getZ();

        // Carefull exception!!!
        //
        return (-C - B * y) / A;
    }

    private float calculateY(Coordinate3D line, float x)
    {
        float A = line.getX();
        float B = line.getY();
        float C = line.getZ();

        // Carefull exception!!!
        //
        return (-C - A * x) / B;
    }

}
