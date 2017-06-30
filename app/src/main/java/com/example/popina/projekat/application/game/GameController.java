package com.example.popina.projekat.application.game;

import android.app.Dialog;
import android.content.Context;
import android.hardware.Sensor;
import android.hardware.SensorManager;
import android.widget.Toast;

import com.example.popina.projekat.application.game.model.LevelElements;
import com.example.popina.projekat.application.game.model.GameModel;
import com.example.popina.projekat.logic.game.coefficient.Coefficient;
import com.example.popina.projekat.logic.game.collision.CollisionModel;
import com.example.popina.projekat.logic.game.collision.CollisionModelAbstract;
import com.example.popina.projekat.logic.game.utility.Coordinate3D;
import com.example.popina.projekat.logic.game.utility.Time;
import com.example.popina.projekat.logic.game.utility.Utility;
import com.example.popina.projekat.logic.shape.figure.Figure;
import com.example.popina.projekat.logic.shape.figure.hole.CircleHole;
import com.example.popina.projekat.logic.shape.figure.hole.StartHole;
import com.example.popina.projekat.logic.shape.parser.ShapeParser;
import com.example.popina.projekat.logic.shape.sound.SoundPlayer;
import com.example.popina.projekat.logic.shape.sound.SoundPlayerCallback;
import com.example.popina.projekat.logic.statistics.database.GameDatabase;

import java.util.LinkedList;

/**
 * Created by popina on 09.03.2017..
 */

public class GameController
{
    private GameActivity gameActivity;
    private GameModel model;
    private GameView view;


    public GameController(GameActivity gameActivity, GameModel model, GameView view, String levelName)
    {
        this.gameActivity = gameActivity;
        this.model = model;
        this.view = view;

        initActivity(levelName);
    }

    private void initActivity(String levelName)
    {
        Coefficient coefficient = new Coefficient(gameActivity);
        model.setCoefficient(coefficient);

        SensorManager sensorManager = (SensorManager) gameActivity.getSystemService(Context.SENSOR_SERVICE);
        model.setSensorManager(sensorManager);

        SoundPlayerCallback soundPlayerCallback = new SoundPlayer(gameActivity);
        model.setSoundPlayerCallback(soundPlayerCallback);

        CollisionModelAbstract collisionModelAbstract = new CollisionModel(coefficient, soundPlayerCallback);
        model.setCollisionModel(collisionModelAbstract);

        LevelElements levelElements = null;
        if (model.getCurrentMode() == GameModel.MODE_ADVENTURE)
        {
            levelName = generateNextLevel();
            model.setCurrentLevel(getDifficulty(levelName));
        }
        levelElements = new LevelElements(levelName);
        model.setLevelElements(levelElements);
    }

    private String generateNextLevel()
    {

        databaseInitialize();
        String level = null;

        for (   int difficulty = (model.getCurrentLevel() + 1) % model.NUM_DIFFICULTIES;
                difficulty != model.getCurrentLevel();
                difficulty = (difficulty + 1) % model.NUM_DIFFICULTIES
            )
        {
            LinkedList<String> listLevels = model.getGameDatabase().getLevels(difficulty);
            if (!listLevels.isEmpty())
            {
                level = listLevels.get((int)Utility.randomNumberInInterval(0, listLevels.size()));
                break;
            }
        }
        return level;

    }

    private int getDifficulty(String level)
    {
        databaseInitialize();
        return model.getGameDatabase().getDifficulty(level);
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
            model.getLevelElements().setLastTimeInitialized(false);
            sensorManager.registerListener(gameActivity, sensor, SensorManager.SENSOR_DELAY_GAME);
            model.getLevelElements().getListTimes().addLast(new Time(System.currentTimeMillis()));
            model.setPaused(false);
        }

    }

    public void pause()
    {
        model.getSensorManager().unregisterListener(gameActivity);
        model.getLevelElements().getListTimes().getLast().setEnd(System.currentTimeMillis());
        model.setPaused(true);
    }

    public void onNewValues(float[] newAcc, long time)
    {
        if (!model.isGameOver())
        {
            if (model.getLevelElements().isLastTimeInitialized())
            {
                Coordinate3D filteredAcc = model.getFilter().filter(new Coordinate3D(newAcc[0], newAcc[1], newAcc[2]));
                updatePosition(filteredAcc, time);
            }
            model.getCollisionModel().setLastTime(time);
            model.getLevelElements().setLastTimeInitialized(true);
        }
    }

    private void updatePosition(Coordinate3D filteredAcc, long time)
    {
        // This is in case if surface view hasn't been initialized.
        //
        if (model.isSurfaceInitialized())
        {
            int state = model.getCollisionModel().updateSystem(filteredAcc, time, model.getLevelElements().getListFigures(), model.getLevelElements().getBall());
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
                    model.getLevelElements().getListTimes().getLast().setEnd(System.currentTimeMillis());
                    Dialog dialog = new GameOverDialog(gameActivity, calcTime(model.getLevelElements().getListTimes()),
                                                        model.getLevelElements().getLevelName());
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

     void loadLevel()
    {
        ShapeParser shapeParser = new ShapeParser(model.getShapeFactory(), model.getShapeDraw(), view.getContext());
        model.setShapeParser(shapeParser);

        LinkedList<Figure> listFigures = (LinkedList<Figure>) shapeParser.parseFile(model.getLevelElements().getLevelName());
        for (Figure it : listFigures)
        {
            if (it instanceof StartHole)
            {
                model.getLevelElements().setBall((CircleHole) it);
                listFigures.remove(it);
                break;
            }
        }

        model.getLevelElements().setListFigures(listFigures);
    }

    public void databaseInitialize()
    {
        if (null == model.getGameDatabase())
        {
            GameDatabase database = new GameDatabase(gameActivity.getApplicationContext());
            model.setGameDatabase(database);
        }
    }
}
