package com.example.popina.projekat.application.game;

import android.app.Dialog;
import android.content.Context;
import android.hardware.Sensor;
import android.hardware.SensorManager;
import android.media.AudioAttributes;
import android.media.AudioManager;
import android.media.SoundPool;
import android.util.Log;
import android.widget.Toast;

import com.example.popina.projekat.R;
import com.example.popina.projekat.logic.game.coeficient.Coeficient;
import com.example.popina.projekat.logic.game.utility.Coordinate3D;
import com.example.popina.projekat.logic.game.utility.Time;
import com.example.popina.projekat.logic.game.utility.Utility;
import com.example.popina.projekat.logic.shape.coordinate.Coordinate;
import com.example.popina.projekat.logic.shape.figure.Figure;
import com.example.popina.projekat.logic.shape.figure.hole.CircleHole;
import com.example.popina.projekat.logic.shape.figure.hole.StartHole;
import com.example.popina.projekat.logic.shape.figure.obstacle.RectangleObstacle;

import java.util.LinkedList;

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

        initActivity();
        initGameSound();
        loadSounds();
        // Maybe put initialization of data from gameModel to here???
        //
    }

    private void initActivity() {
        Coeficient coeficient = new Coeficient(gameActivity);
        model.setCoeficient(coeficient);
        SensorManager sensorManager = (SensorManager)gameActivity.getSystemService(Context.SENSOR_SERVICE);
        model.setSensorManager(sensorManager);
    }

    public void destructor() {
        model.getSoundPool().release();
    }

    public void resume() {
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

    public void pause() {
        model.getSensorManager().unregisterListener(gameActivity);
        model.getListTimes().getLast().setEnd(System.currentTimeMillis());
        model.setPaused(true);
    }

    private void loadSounds() {
        int sounds[] = new int[GameModel.SOUND_MAX];
        model.setSoundsId(sounds);

        // Raw files should start with small letters.
        //
        sounds[GameModel.SOUND_ID_COLLISION] = model.getSoundPool().load(gameActivity, R.raw.collision, GameModel.SOUND_PRIORITY);
        sounds[GameModel.SOUND_ID_MISS] = model.getSoundPool().load(gameActivity, R.raw.miss, GameModel.SOUND_PRIORITY);
        sounds[GameModel.SOUND_ID_SUCCESS] = model.getSoundPool().load(gameActivity, R.raw.success, GameModel.SOUND_PRIORITY);
    }

    private void playSound(int idSound)
    {
        model.getSoundPool().play(model.getSoundsId()[idSound],
                                    GameModel.SOUND_LEFT_VOLUME,
                                    GameModel.SOUND_RIGHT_VOLUME,
                                    GameModel.SOUND_PRIORITY_STREAM,
                                    GameModel.SOUND_NO_LOOP,
                                    GameModel.SOUND_RATE_PLAYBACK);
    }

    private void initGameSound() {
        AudioAttributes attributes = new AudioAttributes.Builder().
                setUsage(AudioAttributes.USAGE_GAME)
                .setContentType(AudioAttributes.CONTENT_TYPE_SONIFICATION)
                .build();
        SoundPool soundPool = new SoundPool.Builder()
                .setMaxStreams(GameModel.MAX_STREAMS)
                .setAudioAttributes(attributes)
                .build();

        model.setSoundPool(soundPool);
        gameActivity.setVolumeControlStream(AudioManager.STREAM_MUSIC);
    }


    // Time is ns
    public void onNewValues(float[] newAcc, long time) {
        if (!model.isGameOver()) {
            if (model.getLastTime() != Long.MAX_VALUE) {
                Coordinate3D filteredAcc = model.getFilter().filter(new Coordinate3D(newAcc[0], newAcc[1], newAcc[2]));
                updatePosition(filteredAcc, time);
            }
            model.setLastTime(time);
        }
    }

    private float opositeSign(float val) {
        if (val < 0) {
            return 1;
        } else if (val > 0) {
            return -1;
        }

        return 0;

    }

    private void updatePosition(Coordinate3D filteredAcc, long time) {


        // This is in case if surface view hasn't been initialized.
        //
        if (model.isSufraceInitialized()) {

            float deltaT = Utility.convertNsToS(time - model.getLastTime());
            CircleHole ball = model.getBall();
            Coordinate center = ball.getCenter().clone();


            // TODO : separate friction to one which is done via x and one via y, and in case if direction is changed via some
            // axis, then do not add friction in that case.

            float newX = possibleMove(model.getSpeed().getX(), center.getX(), deltaT);
            float newY = possibleMove(model.getSpeed().getY(), center.getY(), deltaT);
            float vX = model.getSpeed().getX();
            float vY = model.getSpeed().getY();

            boolean rightLeftCollision = false;
            boolean topBottomCollision = false;

            StartHole newBallPos = new StartHole(newX, newY, ball.getRadius());

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
                            playSound(GameModel.SOUND_ID_SUCCESS);
                            model.getListTimes().getLast().setEnd(System.currentTimeMillis());
                            Dialog dialog = new GameOverDialog(gameActivity, calcTime(model.getListTimes()), model.getFileName());
                            dialog.show();
                        }
                        // In case of wrong hole.
                        //
                        else
                        {
                            playSound(GameModel.SOUND_ID_MISS);
                            Toast.makeText(gameActivity, "Izgubio si igru", Toast.LENGTH_SHORT).show();
                        }
                    }
                    else
                    {
                        // In case of obstacle collision.
                        //
                        playSound(GameModel.SOUND_ID_COLLISION);
                        RectangleObstacle rectangleObstacle = (RectangleObstacle) itFigure;

                        int val = isCollisionAndUpdate(rectangleObstacle, ball, newBallPos, center, model.getSpeed());
                        if ((val & (GameModel.BIT_LEFT_COLISION | GameModel.BIT_RIGHT_COLISION)) != 0)
                        {
                            model.getSpeed().setX(reverseDir(vX));
                            rightLeftCollision = true;
                        }
                        if ((val & (GameModel.BIT_TOP_COLISION | GameModel.BIT_BOTTOM_COLISION)) != 0)
                        {
                            model.getSpeed().setY(reverseDir(vY));
                            topBottomCollision = true;
                        }

                    }
                    break;
                }
            }

            if (!rightLeftCollision)
            {
                center.setX(newX);
            }

            if (!topBottomCollision)
            {
                center.setY(newY);
            }

            ball.setCenter(center);

            scaleAcceleration(filteredAcc);
            addFrictionToAcc(filteredAcc, model.getSpeed(), deltaT);

            view.invalidateSurfaceView();
            //Log.d("GameController", "VX " + Float.toString(model.getSpeed().getX()));
            //Log.d("GameController", "VY " + Float.toString(model.getSpeed().getY()));
            //Log.d("GameController", "X " + Float.toString(model.getBall().getCenter().getX()));
            //Log.d("GameController", "Y " + Float.toString(model.getBall().getCenter().getY()));
            //Log.d("GameControlle r", "AX " + Float.toString(filteredAcc.getX()));
            //Log.d("GameController", "AY " + Float.toString(filteredAcc.getY()));
            //Log.d("GameController", "TIME " + Float.toString(deltaT));
        }


    }

    private long calcTime(LinkedList<Time> listTimes) {
        long timeAll = 0;
        for (Time time : listTimes)
        {
            timeAll += time.timeInt();
        }
        return  timeAll;
    }

    // A * X + B * Y = Z
    //
    private Coordinate3D calculateLine(Coordinate point1, Coordinate point2) {
        float A = point1.getY() - point2.getY();
        float B = point2.getX() - point1.getX();
        float C = point1.getX() * point2.getY() - point1.getY() * point2.getX();
        Coordinate3D line = new Coordinate3D(A, B, C);

        return line;
    }

    private Coordinate intersectionLines(Coordinate3D line1, Coordinate3D line2) {
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
                < Utility.FLOAT_ACCURACY) {
            return null;
        }

        return new Coordinate((B2 * C1 - B1 * C2) / det, (A1 * C2 - A2 * C1) / det);
    }

    private boolean doesBallCenterHitsLine(Coordinate beginSegment, Coordinate endSegment, CircleHole ball, CircleHole ballNew, boolean isXLine)
    {
        boolean doesIntersect = doesSegmentIntersectsCircle(beginSegment, endSegment, ballNew.getCenter(), ball.getRadius(), isXLine);
        if (!doesIntersect)
        {
            return  false;
        }

        return doesIntersect;
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

    private  float calculateY(Coordinate3D line, float x)
    {
        float A = line.getX();
        float B = line.getY();
        float C = line.getZ();

        // Carefull exception!!!
        //
        return (-C - A * x) / B;
    }

    private boolean isFirstHit(Coordinate intersectionPoint, Coordinate potentialPointOfCollision, float minDist)
    {
        return Utility.isDistanceBetweenCoordLesThan(intersectionPoint, potentialPointOfCollision, minDist, true);
    }

    private boolean doesSegmentIntersectsCircle(Coordinate beginSegment, Coordinate endSegment, Coordinate center, float radius,
                                          boolean isXLine)
    {

        // End/begin segment circle intersection.
        //
        if (Utility.isDistanceBetweenCoordLesThan(beginSegment, center, radius, false)
                || Utility.isDistanceBetweenCoordLesThan(endSegment, center, radius, false))
        {
            //Log.d("GameController", "Begin/End");
            return true;
        }

        // "Inside" segment circle intersection
        //
        if (isXLine)
        {
            //Log.d("GameController", "X");
            return Utility.isDimBetweenDims(beginSegment.getX(), endSegment.getX(), center.getX())
                    && Math.abs(center.getY() - beginSegment.getY()) <= Utility.FLOAT_ACCURACY + radius;
        }
        else
        {
            //Log.d("GameController", "Y");
            return Utility.isDimBetweenDims(beginSegment.getY(), endSegment.getY(), center.getY())
                    && Math.abs(center.getX() - beginSegment.getX()) <= Utility.FLOAT_ACCURACY + radius;
        }

    }

    public int isCollisionAndUpdate(RectangleObstacle rectangleObstacle, CircleHole ball, CircleHole ballNew, Coordinate center, Coordinate3D speed)
    {
        int retVal = 0x0;
        /*
        float minDist = Float.MAX_VALUE;

        // Carefull there is no movement case.
        //

        Coordinate3D lineCenter = calculateLine(ball.getCenter(), ballNew.getCenter());
        Coordinate3D lineRectBottom = calculateLine(rectangleObstacle.getBotomLeft(), rectangleObstacle.getBottomRight());
        Coordinate3D lineRectTop = calculateLine(rectangleObstacle.getTopLeft(), rectangleObstacle.getTopRight());
        Coordinate3D lineRectLeft = calculateLine(rectangleObstacle.getBotomLeft(), rectangleObstacle.getTopLeft());
        Coordinate3D lineRectRight = calculateLine(rectangleObstacle.getBottomRight(), rectangleObstacle.getTopRight());

        Coordinate centerBottomIntersection = intersectionLines(lineCenter, lineRectBottom);
        Coordinate centerTopIntersection = intersectionLines(lineCenter, lineRectTop);
        Coordinate centerLeftIntersection = intersectionLines(lineCenter, lineRectLeft);
        Coordinate centerRightIntersection = intersectionLines(lineCenter, lineRectRight);
        */
        if (doesBallCenterHitsLine(rectangleObstacle.getBotomLeft(), rectangleObstacle.getBottomRight(), ball, ballNew, true) && speed.getY() <= 0)
        {
            retVal = GameModel.BIT_BOTTOM_COLISION;
        }

        if (doesBallCenterHitsLine(rectangleObstacle.getTopLeft(), rectangleObstacle.getTopRight(), ball, ballNew, true) && speed.getY() >= 0)
        {
            retVal = GameModel.BIT_TOP_COLISION;
        }

        if (doesBallCenterHitsLine(rectangleObstacle.getTopLeft(), rectangleObstacle.getBotomLeft(), ball, ballNew, false) && speed.getX() >= 0)
        {
            retVal |= GameModel.BIT_LEFT_COLISION;
        }

        if (doesBallCenterHitsLine( rectangleObstacle.getTopRight(), rectangleObstacle.getBottomRight(), ball, ballNew, false) && speed.getX() <= 0)
        {
            retVal = (retVal & ~GameModel.BIT_LEFT_COLISION) | GameModel.BIT_RIGHT_COLISION;
        }

        return retVal;
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

        //Log.d("GameController", "VoldX " + Float.toString(model.getSpeed().getX()));
        //Log.d("GameController", "VoldY " + Float.toString(model.getSpeed().getY()));

        speed.setX(newSpeedX);
        speed.setY(newSpeedY);
        /*
        Log.d("GameController", "VX " + Float.toString(model.getSpeed().getX()));
        Log.d("GameController", "VY " + Float.toString(model.getSpeed().getY()));
        Log.d("GameControlle r", "AX " + Float.toString(frictatedAccX));
        Log.d("GameController", "AY " + Float.toString(frictatedAccY));
        Log.d("GameController", "TIME " + Float.toString(deltaT));
        Log.d("GameController", "Friction makes X " + Boolean.toString(frictionMakeAccX));
        Log.d("GameController", "Friction makes Y " + Boolean.toString(frictionMakeAccY));
        Log.d("GameController", "********************");
        */
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
