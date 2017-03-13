package com.example.popina.projekat.application.game;

import android.util.Log;
import android.util.Pair;

import com.example.popina.projekat.logic.game.Coordinate3D;
import com.example.popina.projekat.logic.shape.coordinate.Coordinate;
import com.example.popina.projekat.logic.shape.figure.Figure;
import com.example.popina.projekat.logic.shape.figure.circle.Circle;

/**
 * Created by popina on 09.03.2017..
 */

public class GameController {
    GameActivity gameActivity;
    GameModel model;
    GameView view;

    public GameController(GameActivity gameActivity, GameModel model, GameView view) {
        this.gameActivity = gameActivity;
        this.model = model;
        this.view = view;

        // Maybe put initialization of data from gameModel to here???
        //
    }


    // Time is ns
    public void onNewValues(float[] newAcc, long time) {
        if (model.getLastTime() != Long.MAX_VALUE)
        {
            Coordinate3D filteredAcc = model.getFilter().filter(new Coordinate3D(newAcc[0], newAcc[1], newAcc[2]));
            updatePosition(filteredAcc, time);
        }
        model.setLastTime(time);
    }

    private float opositeSign(float val) {
        if (val < 0)
        {
            return 1;
        }
        else if (val > 0)
        {
            return -1;
        }

        return 0;

    }

    private void updatePosition(Coordinate3D filteredAcc, long time) {



        // This is in case if surface view hasn't been initialized.
        //
        if (model.isSufraceInitialized())
        {

            float deltaT = (time - model.getLastTime()) / GameModel.S_NS ;
            Circle ball = model.getBall();
            Coordinate center = ball.getCenter().clone();

            scaleAcceleration(filteredAcc);
            addFrictionToAcc(filteredAcc, model.getSpeed(), deltaT);

            float newX = possibleMove(model.getSpeed().getX(), center.getX(), deltaT);
            float newY = possibleMove(model.getSpeed().getY(), center.getY(), deltaT);
            float vX = model.getSpeed().getX();
            float vY = model.getSpeed().getY();

            // Check if hits.

            if (newX - ball.getRadius() < 0)
            {
                center.setX(ball.getRadius());
                model.getSpeed().setX(reverseDir(vX));
            }
            else if (newX + ball.getRadius() >= model.getWidth())
            {
                center.setX(model.getWidth() - ball.getRadius() - 1);
                model.getSpeed().setX(reverseDir(vX));
            }
            else {
                center.setX(newX);
            }


            if (newY - ball.getRadius() < 0)
            {
                center.setY(ball.getRadius());
                model.getSpeed().setY(reverseDir(vY));
            }
            else if (newY + ball.getRadius() >= model.getHeight())
            {
                center.setY(model.getHeight() - ball.getRadius() - 1);
                model.getSpeed().setY(reverseDir(vY));
            }
            else {
                center.setY(newY);
            }

            ball.setCenter(center);
            view.invalidateSurfaceView();
            //Log.d("GameController", "VX " + Float.toString(model.getSpeed().getX()));
            //Log.d("GameController", "VY " + Float.toString(model.getSpeed().getY()));
            //Log.d("GameController", "X " + Float.toString(model.getBall().getCenter().getX()));
            //Log.d("GameController", "Y " + Float.toString(model.getBall().getCenter().getY()));
            //Log.d("GameControlle r", "AX " + Float.toString(filteredAcc.getX()));
            //Log.d("GameController", "AY " + Float.toString(filteredAcc.getY()));
            Log.d("GameController", "TIME " + Float.toString(deltaT));
        }


    }

    private void addFrictionToAcc(Coordinate3D filteredAcc,  Coordinate3D speed, float deltaT) {

        float frictionAccX =  -1 * opositeSign(speed.getX()) * model.getCoeficient().getMi() * filteredAcc.getZ();
        float frictionAccY = opositeSign(speed.getY()) * model.getCoeficient().getMi() * filteredAcc.getZ();

        float frictatedAccX = filteredAcc.getX() + frictionAccX;
        float frictatedAccY = filteredAcc.getY() + frictionAccY;

        boolean frictionMakeAccX = checkFriction(frictionAccX, filteredAcc.getX());
        boolean frictionMakeAccY = checkFriction(frictionAccY, filteredAcc.getY());

        float newSpeedX = updateSpeed(speed.getX(), frictatedAccX, deltaT, -1);
        float newSpeedY = updateSpeed(speed.getY(), frictatedAccY, deltaT, 1);

        if ( (newSpeedX * speed.getX() <= 0) && frictionMakeAccX)
        {
            newSpeedX = 0;
        }

        if ( (newSpeedY * speed.getY() <= 0) && frictionMakeAccY)
        {
            newSpeedY = 0;
        }

        Log.d("GameController", "VoldX " + Float.toString(model.getSpeed().getX()));
        Log.d("GameController", "VoldY " + Float.toString(model.getSpeed().getY()));

        speed.setX(newSpeedX);
        speed.setY(newSpeedY);

        Log.d("GameController", "VX " + Float.toString(model.getSpeed().getX()));
        Log.d("GameController", "VY " + Float.toString(model.getSpeed().getY()));
        Log.d("GameControlle r", "AX " + Float.toString(frictatedAccX));
        Log.d("GameController", "AY " + Float.toString(frictatedAccY));
        Log.d("GameController", "TIME " + Float.toString(deltaT));
        Log.d("GameController", "Friction makes X " + Boolean.toString(frictionMakeAccX));
        Log.d("GameController", "Friction makes Y " + Boolean.toString(frictionMakeAccY));
        Log.d("GameController", "********************");
    }

    private boolean checkFriction(float frictionDir, float accDir)
    {
        if (accDir * frictionDir > 0)
            return false;
        return  (Math.abs(frictionDir) -
                Math.abs(accDir) > 0);
    }

    private void scaleAcceleration(Coordinate3D acc)
    {
        acc.setX(scaleAcceleration(acc.getX()));
        acc.setY(scaleAcceleration(acc.getY()));
        //acc.setZ(scaleAcceleration(acc.getZ()));
    }

    private float scaleAcceleration(float accDir) {
        return  model.getCoeficient().getScaleAcc() * accDir;
    }

    private  float possibleMove(float speedDir, float posDir, float time)
    {
        return posDir + speedDir * time;
    }

    private float updateSpeed(float v0, float acc, float deltatT, int dir)
    {
        return v0 + dir * acc * deltatT;
    }

    private float reverseDir(float speed)
    {
        return speed * - model.getCoeficient().getReverseSlowDown();
    }

}
