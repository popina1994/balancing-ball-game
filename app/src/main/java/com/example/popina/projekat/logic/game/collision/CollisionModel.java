package com.example.popina.projekat.logic.game.collision;

import android.util.Log;

import com.example.popina.projekat.logic.game.coefficient.Coefficient;
import com.example.popina.projekat.logic.game.utility.Coordinate3D;
import com.example.popina.projekat.logic.game.utility.Utility;
import com.example.popina.projekat.logic.shape.coordinate.Coordinate;
import com.example.popina.projekat.logic.shape.figure.Figure;
import com.example.popina.projekat.logic.shape.figure.hole.CircleHole;
import com.example.popina.projekat.logic.shape.figure.hole.StartHole;
import com.example.popina.projekat.logic.shape.figure.obstacle.Obstacle;
import com.example.popina.projekat.logic.shape.sound.SoundPlayerCallback;

import java.util.LinkedList;

import static com.example.popina.projekat.logic.game.utility.Utility.isDimBetweenDims;
import static com.example.popina.projekat.logic.game.utility.Utility.opositeSign;

/**
 * Created by popina on 29.06.2017..
 */

public class CollisionModel extends CollisionModelAbstract
{
    public static int MAX_LAGGING_COUNTER = 9;
    private Coordinate3D speed = new Coordinate3D(5, 5, 0);
    private int laggingCount = 0;

    private Coordinate speedChange = new Coordinate(0, 0);
    private StartHole newBallPos = new StartHole(0, 0, 0);

    public CollisionModel(Coefficient coefficient, SoundPlayerCallback soundPlayerCallback)
    {
        super(coefficient, soundPlayerCallback);
    }


    @Override
    public int updateSystem(Coordinate3D filteredAcceleration, long time, LinkedList<Figure> listFigures, CircleHole ball)
    {
        int retVal = CollisionModelAbstract.GAME_CONTINUES_NO_COLLISION;
        float deltaT = Utility.convertNsToS(time - lastTime);
        scaleAcceleration(filteredAcceleration);
        addFrictionToAcc(filteredAcceleration, speed, deltaT);

        float newX = possibleMove(speed.getX(), ball.getCenter().getX(), deltaT);
        float newY = possibleMove(speed.getY(), ball.getCenter().getY(), deltaT);
        float vX = speed.getX();
        float vY = speed.getY();

        // Only for speed improvement.
        //
        newBallPos.getCenter().setX(newX);
        newBallPos.getCenter().setY(newY);
        newBallPos.setRadius(ball.getRadius());

        speedChange.setX(0);
        speedChange.setY(0);

        Log.d("VX", Float.toString(vX));
        Log.d("VY", Float.toString(vY));


        for (Figure itFigure : listFigures)
        {
            if (itFigure.doesCollide(newBallPos))
            {
                if (itFigure.isGameOver())
                {
                    // In case of Finish hole.
                    //
                    if (itFigure.isWon())
                    {
                        retVal = CollisionModelAbstract.GAME_OVER_WIN;
                    }
                    else
                    {
                        retVal = CollisionModelAbstract.GAME_OVER_LOSE;
                    }
                }
                else
                {
                    // In case of obstacle collision.
                    //
                    Obstacle obstacle = (Obstacle) itFigure;

                    Coordinate speedChangeFigure = obstacle.getSpeedChangeAfterCollision((StartHole) ball, newBallPos, speed);
                    speedChangeFigure.mulScalar(2);

                    Log.d("SPEEDCHANGECOLX", Float.toString(speedChangeFigure.getX()));
                    Log.d("SPEEDCHANGECOLY", Float.toString(speedChangeFigure.getY()));

                    speedChange.addCoordinate(speedChangeFigure);
                    if (retVal == CollisionModelAbstract.GAME_CONTINUES_NO_COLLISION)
                    {
                        retVal = CollisionModelAbstract.GAME_CONTINUES_COLLISION;
                    }
                }
                itFigure.playSound(soundPlayerCallback);
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

        int localLaggingCount = 0;
        if (xAxisChange && yAxisChange)
        {
            localLaggingCount = laggingCount;
            localLaggingCount++;
            if (localLaggingCount > MAX_LAGGING_COUNTER)
            {
                newBallPos.getCenter().setX(ball.getCenter().getX());
                newBallPos.getCenter().setY(newY);
                if (!doesCollide(newBallPos, listFigures))
                {
                    ball.getCenter().setY(newY);
                }

                newBallPos.getCenter().setX(newX);
                newBallPos.getCenter().setY(ball.getCenter().getY());
                if (!doesCollide(newBallPos, listFigures))
                {
                    ball.getCenter().setX(newX);
                }
                localLaggingCount = 0;
            }
        }
        laggingCount = localLaggingCount;

        speedChange.addCoordinate(new Coordinate(vX, vY));

        if (xAxisChange)
        {
            Log.d("XAXISCHange", "TRUE");
            speedChange.setX(coefficient.getReverseSlowDown() * speedChange.getX());
            speed.setX(speedChange.getX());
        }

        if (yAxisChange)
        {
            Log.d("YAXISCHange", "TRUE");
            speedChange.setY(coefficient.getReverseSlowDown() * speedChange.getY());
            speed.setY(speedChange.getY());
        }

        return retVal;
    }


    private boolean doesCollide(StartHole possibleBall, LinkedList<Figure> listFigures)
    {
        for (Figure it : listFigures)
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
        float frictionAccX = -1 * opositeSign(speed.getX()) * coefficient.getMi() * filteredAcc.getZ();
        float frictionAccY = opositeSign(speed.getY()) * coefficient.getMi() * filteredAcc.getZ();


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
        return coefficient.getScaleAcc() * accDir;
    }

    private float possibleMove(float speedDir, float posDir, float time)
    {
        return posDir + speedDir * time;
    }

    private float updateSpeed(float v0, float acc, float deltaT, int dir)
    {
        return v0 + dir * acc * deltaT;
    }
}
