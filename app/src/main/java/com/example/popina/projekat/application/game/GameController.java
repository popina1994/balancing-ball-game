package com.example.popina.projekat.application.game;

import android.app.Dialog;
import android.content.Context;
import android.hardware.Sensor;
import android.hardware.SensorManager;
import android.util.Log;
import android.widget.Toast;

import com.example.popina.projekat.logic.game.coefficient.Coefficient;
import com.example.popina.projekat.logic.game.utility.Coordinate3D;
import com.example.popina.projekat.logic.game.utility.Time;
import com.example.popina.projekat.logic.game.utility.Utility;
import com.example.popina.projekat.logic.shape.coordinate.Coordinate;
import com.example.popina.projekat.logic.shape.figure.Figure;
import com.example.popina.projekat.logic.shape.figure.hole.CircleHole;
import com.example.popina.projekat.logic.shape.figure.hole.StartHole;
import com.example.popina.projekat.logic.shape.figure.obstacle.Obstacle;
import com.example.popina.projekat.logic.shape.sound.SoundPlayer;
import com.example.popina.projekat.logic.shape.sound.SoundPlayerCallback;

import java.util.LinkedList;

import static com.example.popina.projekat.logic.game.utility.Utility.isDimBetweenDims;
import static com.example.popina.projekat.logic.game.utility.Utility.opositeSign;

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
            model.setLastTime(Long.MAX_VALUE);
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
            if (model.getLastTime() != Long.MAX_VALUE)
            {
                Coordinate3D filteredAcc = model.getFilter().filter(new Coordinate3D(newAcc[0], newAcc[1], newAcc[2]));
                updatePosition(filteredAcc, time);
            }
            model.setLastTime(time);
        }
    }

    private void updatePosition(Coordinate3D filteredAcc, long time)
    {
        // This is in case if surface view hasn't been initialized.
        //
        if (model.isSurfaceInitialized())
        {

            // model.getLastTime
            // model.getBall
            // model.getSpeed
            // model.getLIstFigures
            float deltaT = Utility.convertNsToS(time - model.getLastTime());
            scaleAcceleration(filteredAcc);
            addFrictionToAcc(filteredAcc, model.getSpeed(), deltaT);


            CircleHole ball = model.getBall();



            float newX = possibleMove(model.getSpeed().getX(), ball.getCenter().getX(), deltaT);
            float newY = possibleMove(model.getSpeed().getY(), ball.getCenter().getY(), deltaT);
            float vX = model.getSpeed().getX();
            float vY = model.getSpeed().getY();

            StartHole newBallPos = new StartHole(newX, newY, ball.getRadius());
            // TODO: remove memory allocation
            // TODO: remove speed change memory allocation
            Coordinate speedChange = new Coordinate(0, 0);

            Log.d("VX", Float.toString(vX));
            Log.d("VY", Float.toString(vY));

            for (Figure itFigure : model.getListFigures())
            {
                if (itFigure.doesCollide(newBallPos))
                {
                    if (itFigure.isGameOver())
                    {
                        model.setGameOver(true);
                        // In case of Finish hole.
                        //
                        if (itFigure.isWon())
                        {
                            model.getListTimes().getLast().setEnd(System.currentTimeMillis());
                            Dialog dialog = new GameOverDialog(gameActivity, calcTime(model.getListTimes()), model.getFileName());
                            dialog.show();
                        }
                        // In case of wrong hole.
                        //
                        else
                        {
                            Toast.makeText(gameActivity, "Izgubio si igru", Toast.LENGTH_SHORT).show();
                        }
                    } else
                    {
                        // In case of obstacle collision.
                        //
                        Obstacle obstacle = (Obstacle) itFigure;

                        Coordinate speedChangeFigure = obstacle.getSpeedChangeAfterCollision((StartHole) ball, newBallPos, model.getSpeed());
                        speedChangeFigure.mulScalar(2);

                        Log.d("SPEEDCHANGECOLX", Float.toString(speedChangeFigure.getX()));
                        Log.d("SPEEDCHANGECOLY", Float.toString(speedChangeFigure.getY()));

                        speedChange.addCoordinate(speedChangeFigure);
                    }
                    itFigure.playSound(model.getSoundPlayerCallback());
                }
            }

            boolean xAxisChange = false;
            boolean yAxisChange = false;

            Log.d("SPEED CHANGE X", Float.toString(speedChange.getX()));
            Log.d("SPEED CHANGE Y", Float.toString(speedChange.getY()));

            // It is faster to update without neccessary copying of center of ball, but it is not safe. (vsync...)
            // if there is no collison on x axis.
            //
            if (isDimBetweenDims(0, 0, speedChange.getX()))
            {
                ball.getCenter().setX(newX);
            } else
            {
                xAxisChange = true;
            }

            // If there is no collsion on y axis.
            //
            if (isDimBetweenDims(0, 0, speedChange.getY()))
            {
                ball.getCenter().setY(newY);
            } else
            {
                yAxisChange = true;
            }


            if (xAxisChange && yAxisChange)
            {
                int laggingCount = model.getLaggingCount();
                laggingCount ++;
                if (laggingCount > GameModel.MAX_LAGGING_COUNTER)
                {
                    newBallPos = new StartHole(ball.getCenter().getX(), newY, ball.getRadius());
                    if (!doesCollide(newBallPos))
                    {
                        ball.getCenter().setY(newY);
                    }

                    newBallPos = new StartHole(newX, ball.getCenter().getY(), ball.getRadius());
                    if (!doesCollide(newBallPos))
                    {
                        ball.getCenter().setX(newX);
                    }
                    laggingCount = 0;
                }
                model.setLaggingCount(laggingCount);
            }

            speedChange.addCoordinate(new Coordinate(vX, vY));

            if (xAxisChange)
            {
                Log.d("XAXISCHange", "TRUE");
                speedChange.setX(model.getCoefficient().getReverseSlowDown() * speedChange.getX());
                model.getSpeed().setX(speedChange.getX());
            }

            if (yAxisChange)
            {
                Log.d("YAXISCHange", "TRUE");
                speedChange.setY(model.getCoefficient().getReverseSlowDown() * speedChange.getY());
                model.getSpeed().setY(speedChange.getY());
            }



            view.invalidateSurfaceView();
        }
    }


    private boolean doesCollide(StartHole possibleBall)
    {
        for (Figure it : model.getListFigures())
        {
            if (it.doesCollide(possibleBall))
            {
                return true;
            }
        }
        return false;
    }

    private void addFrictionToAcc(Coordinate3D filteredAcc, Coordinate3D speed, float deltaT)
    {
        float frictionAccX = -1 * opositeSign(speed.getX()) * model.getCoefficient().getMi() * filteredAcc.getZ();
        float frictionAccY = opositeSign(speed.getY()) * model.getCoefficient().getMi() * filteredAcc.getZ();


        float frictatedAccX = filteredAcc.getX() + frictionAccX;
        float frictatedAccY = filteredAcc.getY() + frictionAccY;

        boolean frictionMakeAccX = checkFriction(frictionAccX, filteredAcc.getX());
        boolean frictionMakeAccY = checkFriction(frictionAccY, filteredAcc.getY());

        float newSpeedX = updateSpeed(speed.getX(), frictatedAccX, deltaT, -1);
        float newSpeedY = updateSpeed(speed.getY(), frictatedAccY, deltaT, 1);



        if ((newSpeedX * speed.getX() <= 0) && frictionMakeAccX)
        {
            newSpeedX = 0;
        }

        if ((newSpeedY * speed.getY() <= 0) && frictionMakeAccY)
        {
            newSpeedY = 0;
        }


        Log.d("filteredAccX", Float.toString(filteredAcc.getX()));
        Log.d("filteredAccY", Float.toString(filteredAcc.getY()));
        Log.d("frictionMakeAccX", Boolean.toString(frictionMakeAccX));
        Log.d("frictionMakeAccY", Boolean.toString(frictionMakeAccY));
        Log.d("FRICTATEDACCY", Float.toString(frictatedAccY));
        Log.d("FRICTATEDACCX", Float.toString(frictatedAccX));
        Log.d("SPEEDX", Float.toString(speed.getX()));
        Log.d("SPEEDY", Float.toString(speed.getY()));
        Log.d("NEWSPEEDX", Float.toString(newSpeedX));
        Log.d("NEWSPEEDY", Float.toString(newSpeedY));
        Log.d("********", "********");
        speed.setX(newSpeedX);
        speed.setY(newSpeedY);
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


    private boolean checkFriction(float frictionDir, float accDir)
    {
        if (accDir * frictionDir > 0)
            return false;
        return (Math.abs(frictionDir) - Math.abs(accDir) > 0);
    }

    private void scaleAcceleration(Coordinate3D acc)
    {
        acc.setX(scaleAcceleration(acc.getX()));
        acc.setY(scaleAcceleration(acc.getY()));
    }

    private float scaleAcceleration(float accDir)
    {
        return model.getCoefficient().getScaleAcc() * accDir;
    }

    private float possibleMove(float speedDir, float posDir, float time)
    {
        return posDir + speedDir * time;
    }

    private float updateSpeed(float v0, float acc, float deltaT, int dir)
    {
        return v0 + dir * acc * deltaT;
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
