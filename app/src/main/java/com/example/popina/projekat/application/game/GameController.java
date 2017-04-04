package com.example.popina.projekat.application.game;

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
import com.example.popina.projekat.logic.game.utility.Utility;
import com.example.popina.projekat.logic.shape.coordinate.Coordinate;
import com.example.popina.projekat.logic.shape.figure.Figure;
import com.example.popina.projekat.logic.shape.figure.circle.Circle;
import com.example.popina.projekat.logic.shape.figure.circle.StartHole;
import com.example.popina.projekat.logic.shape.figure.rectangle.Rectangle;

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
        }

    }

    public void pause() {
        model.getSensorManager().unregisterListener(gameActivity);
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

            float deltaT = (time - model.getLastTime()) / GameModel.S_NS;
            Circle ball = model.getBall();
            Coordinate center = ball.getCenter().clone();

            scaleAcceleration(filteredAcc);
            addFrictionToAcc(filteredAcc, model.getSpeed(), deltaT);

            float newX = possibleMove(model.getSpeed().getX(), center.getX(), deltaT);
            float newY = possibleMove(model.getSpeed().getY(), center.getY(), deltaT);
            float vX = model.getSpeed().getX();
            float vY = model.getSpeed().getY();

            boolean rightLeftCollision = false;
            boolean topBottomCollision = false;

            StartHole newBallPos = new StartHole(newX, newY, ball.getRadius());

            for (Figure itFigure : model.getListFigures()) {
                if (itFigure.hits(newBallPos)) {
                    //itFigure.playMusic(musicPlayer);
                    if (itFigure.isGameOver()) {
                        model.setGameOver(true);
                        // In case of Finish hole.
                        //
                        if (itFigure.isWon()) {
                            playSound(GameModel.SOUND_ID_SUCCESS);
                            Toast.makeText(gameActivity, "Pobedio si igru", Toast.LENGTH_SHORT).show();
                        }
                        // In case of wrong hole.
                        //
                        else {
                            playSound(GameModel.SOUND_ID_MISS);
                            Toast.makeText(gameActivity, "Izgubio si igru", Toast.LENGTH_SHORT).show();
                        }
                    } else {
                        // Here shouldn't be case of start hole at all.
                        //

                        Rectangle rectangle = (Rectangle) itFigure;

                        int val = isCollisionAndUpdate(rectangle, ball, newBallPos, center);
                        if ((val & (GameModel.BIT_LEFT_COLISION | GameModel.BIT_RIGHT_COLISION)) != 0) {
                            model.getSpeed().setX(reverseDir(vX));
                            rightLeftCollision = true;
                        }
                        if ((val & (GameModel.BIT_TOP_COLISION | GameModel.BIT_BOTTOM_COLISION)) != 0) {
                            model.getSpeed().setY(reverseDir(vY));
                            topBottomCollision = true;
                        }

                    }
                    break;
                }
            }

            // Check if hits.

            if (!rightLeftCollision) {

                if (newX - ball.getRadius() < 0) {
                    center.setX(ball.getRadius());
                    model.getSpeed().setX(reverseDir(vX));
                    rightLeftCollision = true;
                } else if (newX + ball.getRadius() >= model.getWidth()) {
                    center.setX(model.getWidth() - ball.getRadius() - 1);
                    model.getSpeed().setX(reverseDir(vX));
                    rightLeftCollision = true;
                } else {
                    center.setX(newX);
                }
            }

            if (!topBottomCollision) {
                if (newY - ball.getRadius() < 0) {
                    center.setY(ball.getRadius());
                    model.getSpeed().setY(reverseDir(vY));
                    topBottomCollision = true;
                } else if (newY + ball.getRadius() >= model.getHeight()) {
                    center.setY(model.getHeight() - ball.getRadius() - 1);
                    model.getSpeed().setY(reverseDir(vY));
                    topBottomCollision = true;
                } else {
                    center.setY(newY);
                }
            }

            ball.setCenter(center);
            view.invalidateSurfaceView();
            if (rightLeftCollision || topBottomCollision)
            {
                playSound(GameModel.SOUND_ID_COLLISION);
            }
            //Log.d("GameController", "VX " + Float.toString(model.getSpeed().getX()));
            //Log.d("GameController", "VY " + Float.toString(model.getSpeed().getY()));
            //Log.d("GameController", "X " + Float.toString(model.getBall().getCenter().getX()));
            //Log.d("GameController", "Y " + Float.toString(model.getBall().getCenter().getY()));
            //Log.d("GameControlle r", "AX " + Float.toString(filteredAcc.getX()));
            //Log.d("GameController", "AY " + Float.toString(filteredAcc.getY()));
            //Log.d("GameController", "TIME " + Float.toString(deltaT));
        }


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
                < GameModel.FLOAT_ACCURACY) {
            return null;
        }

        return new Coordinate((B2 * C1 - B1 * C2) / det, (A1 * C2 - A2 * C1) / det);
    }

    private float distanceBetweenParallelLines(Coordinate3D line1, Coordinate3D line2) {
        float A1 = line1.getX();
        float B1 = line1.getY();
        float C1 = line1.getZ();

        float A2 = line2.getX();
        float B2 = line2.getY();
        float C2 = line2.getZ();

        if (A1 != 0) {
            // A2 cannot be 0 if they are parallel.
            //
            B2 = B2 / A2 * A1;
            C2 = C2 / A2 * A1;
            A2 = A1;
        } else if (B1 != 0) {
            // B2 cannot be zero if they are parellel.
            //
            A2 = A2 / B2 * B1;
            C2 = C2 / B2 * B1;
            B2 = B1;
        }

        return (C2 - C1) * (C2 - C1) / (A1 * A1 + B1 * B1);
    }

    private boolean doesBallCenterHitsLine(Coordinate intersectionPoint, Coordinate beginSegment, Coordinate endSegment,
                                          float minDist, Circle ball, Circle ballNew, Coordinate3D lineCenter,
                                          Coordinate3D line, boolean isXLine)
    {
        return  ((null != intersectionPoint) &&
                Utility.isOnSegment(beginSegment, endSegment, intersectionPoint)
                && doesSegmentIntersectsCircle(beginSegment, endSegment, ballNew.getCenter(), ball.getRadius(),
                isXLine)
                && Utility.isDistanceBetweenCoordLesThan(intersectionPoint, ball.getCenter(), minDist, true))
                ||
                ( (intersectionPoint == null) &&
                        distanceBetweenParallelLines(line, lineCenter) <= ball.getRadius() * ball.getRadius()) ;
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

    // Returns -1 in case it doesn't hit a line.
    // If less than min dist returns that distance squared.
    //
    public float distanceHitFromBeginPosition(Coordinate intersectionPoint, Circle ball,
                                             Circle ballNew,
                                             Coordinate beginSegment, Coordinate endSegment,
                                             Coordinate3D lineCenter,
                                             float minDist,
                                             boolean isXLine)
    {
        // Paralele center line and line.
        //
        if (null != intersectionPoint)
        {
            // In case of intersection with xLine of rectangle.
            //
            if (isXLine)
            {
                Circle ballUsed = ball;
                if (Math.abs(intersectionPoint.getY() - ball.getCenter().getY()) <= Utility.FLOAT_ACCURACY)
                {
                    if (Math.abs(intersectionPoint.getY() - ballNew.getCenter().getY()) <= Utility.FLOAT_ACCURACY)
                    {
                        ballUsed = null;
                    }
                    else
                    {
                        ballUsed = ballNew;
                    }
                }

                float xDistanceFromIntersection =
                        Math.abs(intersectionPoint.getX() - ballUsed.getCenter().getX()) * ballUsed.getRadius() /
                        Math.abs(intersectionPoint.getY() - ballUsed.getCenter().getY());
                float leftPotentialX = intersectionPoint.getX() - xDistanceFromIntersection;
                float rightPotentialX = intersectionPoint.getX() + xDistanceFromIntersection;

                Coordinate leftPotential = new Coordinate(leftPotentialX, calculateY(lineCenter, leftPotentialX));
                Coordinate rightPotential = new Coordinate(rightPotentialX, calculateY(lineCenter, rightPotentialX));

                float beginX = ball.getCenter().getX();
                float endX = ballNew.getCenter().getX();

                if (beginX > endX)
                {
                    float tmp = beginX;
                    beginX = endX;
                    endX = tmp;
                }

                if (Utility.isDimBetweenDims(beginX, endX, leftPotential.getX())
                        && doesSegmentIntersectsCircle(beginSegment, endSegment, leftPotential, ball.getRadius(),
                        isXLine)
                        && isFirstHit(intersectionPoint, leftPotential, minDist))
                {
                    return Utility.distanceSquared(ball.getCenter(), leftPotential);
                }

                if (Utility.isDimBetweenDims(beginX, endX, rightPotential.getX())
                        && doesSegmentIntersectsCircle(beginSegment, endSegment, rightPotential, ball.getRadius(),
                        isXLine)
                        && isFirstHit(intersectionPoint, rightPotential, minDist))
                {
                    return Utility.distanceSquared(ball.getCenter(), rightPotential);
                }

            }
            else
            {
                // TO DO update everything on y.
                //
                Circle ballUsed = ball;
                if (Math.abs(intersectionPoint.getX() - ball.getCenter().getX()) <= Utility.FLOAT_ACCURACY)
                {
                    if (Math.abs(intersectionPoint.getX() - ballNew.getCenter().getX()) <= Utility.FLOAT_ACCURACY)
                    {
                        ballUsed = null;
                    }
                    else
                    {
                        ballUsed = ballNew;
                    }
                }


                float yDistanceFromIntersection =
                        Math.abs(intersectionPoint.getY() - ballUsed.getCenter().getY()) * ballUsed.getRadius() /
                                Math.abs(intersectionPoint.getX() - ballUsed.getCenter().getX());
                float topPotentialY = intersectionPoint.getY() - yDistanceFromIntersection;
                float bottomPotentialY = intersectionPoint.getY() + yDistanceFromIntersection;
                Coordinate topPotential = new Coordinate(calculateX(lineCenter, topPotentialY), topPotentialY);
                Coordinate bottomPotential = new Coordinate(calculateX(lineCenter, bottomPotentialY), bottomPotentialY);

                float beginY = ball.getCenter().getY();
                float endY = ballNew.getCenter().getY();

                if (beginY > endY)
                {
                    float tmp = beginY;
                    beginY = endY;
                    endY = tmp;
                }

                if ( (Utility.isDimBetweenDims(beginY, endY, topPotential.getY())
                        && doesSegmentIntersectsCircle(beginSegment, endSegment, topPotential, ball.getRadius(), isXLine)
                        && isFirstHit(intersectionPoint, topPotential, minDist))
                        )
                {
                    //.d("GameCon", "Top");
                    return Utility.distanceSquared(ball.getCenter(), topPotential);
                }

                if (Utility.isDimBetweenDims(beginY, endY, bottomPotential.getY())
                        && doesSegmentIntersectsCircle(beginSegment, endSegment, bottomPotential, ball.getRadius(), isXLine)
                        && isFirstHit(intersectionPoint, bottomPotential, minDist))
                {
                    //Log.d("GameCon", "Bottom");
                    return Utility.distanceSquared(ball.getCenter(), bottomPotential);
                }



            }
            if (doesSegmentIntersectsCircle(beginSegment, endSegment, ballNew.getCenter(), ball.getRadius(), isXLine))
            {
                //Log.d("GameCon", "Zero");
                return 0;
            }

        }
        return  -1;
    }


    public int isCollisionAndUpdate(Rectangle rectangle, Circle ball, Circle ballNew, Coordinate center)
    {
        int retVal = 0x0;
        float minDist = Float.MAX_VALUE;

        // Carefull there is no movement case.
        //
        Coordinate3D lineCenter = calculateLine(ball.getCenter(), ballNew.getCenter());
        Coordinate3D lineRectBottom = calculateLine(rectangle.getBotomLeft(), rectangle.getBottomRight());
        Coordinate3D lineRectTop = calculateLine(rectangle.getTopLeft(), rectangle.getTopRight());
        Coordinate3D lineRectLeft = calculateLine(rectangle.getBotomLeft(), rectangle.getTopLeft());
        Coordinate3D lineRectRight = calculateLine(rectangle.getBottomRight(), rectangle.getTopRight());

        Coordinate centerBottomIntersection = intersectionLines(lineCenter, lineRectBottom);
        Coordinate centerTopIntersection = intersectionLines(lineCenter, lineRectTop);
        Coordinate centerLeftIntersection = intersectionLines(lineCenter, lineRectLeft);
        Coordinate centerRightIntersection = intersectionLines(lineCenter, lineRectRight);

        if (doesBallCenterHitsLine(centerBottomIntersection, rectangle.getBotomLeft(),
                rectangle.getBottomRight(), minDist, ball, ballNew, lineCenter, lineRectBottom, true))
        {
            float val;
            if (centerBottomIntersection == null)
            {
                val =  distanceBetweenParallelLines(lineRectBottom, lineCenter);
            }
            else
            {
                val = Utility.distanceSquared(centerBottomIntersection, ball.getCenter());
            }

            if (minDist >= val)
            {
                retVal = GameModel.BIT_BOTTOM_COLISION;
                minDist = val;
            }
        }

        if (doesBallCenterHitsLine(centerTopIntersection, rectangle.getTopLeft(), rectangle.getTopRight(),
                minDist, ball, ballNew, lineCenter, lineRectTop, true))
        {
            float val;
            if (centerTopIntersection == null)
            {
                val =  distanceBetweenParallelLines(lineRectTop, lineCenter);
            }
            else
            {
                val = Utility.distanceSquared(centerTopIntersection, ball.getCenter());
            }
            if (minDist >= val)
            {
                retVal = GameModel.BIT_TOP_COLISION;
                minDist = val;
            }
        }

        if (doesBallCenterHitsLine(centerLeftIntersection, rectangle.getTopLeft(), rectangle.getBotomLeft(),
                minDist, ball, ballNew, lineCenter, lineRectTop, false))
        {
            float val;
            if (centerLeftIntersection == null)
            {
                val =  distanceBetweenParallelLines(lineRectLeft, lineCenter);
            }
            else
            {
                val = Utility.distanceSquared(centerLeftIntersection, ball.getCenter());
            }

            if (minDist >= val)
            {
                retVal |= GameModel.BIT_LEFT_COLISION;
                //retVal = GameModel.BIT_LEFT_COLISION;
                minDist = val;
            }
        }

        if (doesBallCenterHitsLine(centerRightIntersection, rectangle.getTopRight(), rectangle.getBottomRight(), minDist,
                ball, ballNew, lineCenter, lineRectRight, false))
        {
            float val;
            if (centerRightIntersection == null)
            {
                val =  distanceBetweenParallelLines(lineRectRight, lineCenter);
            }
            else
            {
                val = Utility.distanceSquared(centerRightIntersection, ball.getCenter());
            }

            if (minDist >= val)
            {
                //retVal = GameModel.BIT_RIGHT_COLISION;
                retVal = (retVal & ~GameModel.BIT_LEFT_COLISION) | GameModel.BIT_RIGHT_COLISION;
                minDist = val;
            }
        }

        if (retVal == 0)
        {
            // It means center doesn't intersect with neither of lines.
            //

            // Min dist needs to be added.
            //
            float distBottom =  distanceHitFromBeginPosition(centerBottomIntersection, ball,
                    ballNew, rectangle.getBotomLeft(), rectangle.getBottomRight(),
                    lineCenter, minDist, true);
            float distTop =  distanceHitFromBeginPosition(centerTopIntersection, ball,
                    ballNew, rectangle.getTopLeft(), rectangle.getTopRight(),
                    lineCenter, minDist, true);
            float distLeft = distanceHitFromBeginPosition(centerLeftIntersection, ball,
                    ballNew, rectangle.getTopLeft(), rectangle.getBotomLeft(),
                    lineCenter, minDist, false);
            float distRight = distanceHitFromBeginPosition(centerRightIntersection, ball,
                    ballNew, rectangle.getTopRight(), rectangle.getBottomRight(),
                    lineCenter, minDist, false);

            if  ( (distTop >= 0) && (distTop <= minDist))
            {
                minDist = distTop;
                retVal = GameModel.BIT_TOP_COLISION;
            }

            if ((distBottom >= 0) && (distBottom <= minDist))
            {
                minDist = distBottom;
                retVal = GameModel.BIT_BOTTOM_COLISION;
            }

            if ((distLeft >= 0) && (distLeft <= minDist))
            {
                minDist = distLeft;
                retVal |= GameModel.BIT_LEFT_COLISION;
                //retVal = GameModel.BIT_LEFT_COLISION;
            }

            if ( (distRight >= 0) && (distRight <= minDist))
            {
                minDist = distLeft;
                retVal = (retVal & ~GameModel.BIT_LEFT_COLISION) | GameModel.BIT_RIGHT_COLISION;
                //retVal = GameModel.BIT_RIGHT_COLISION;
            }
        }
        else

        if (retVal == 0) {
            Log.d("Game controller", "WARNING!!!");
        }

        int cnt = GameModel.BIT_LEFT_COLISION & retVal << GameModel.BIT_LEFT_COLISION
                + GameModel.BIT_RIGHT_COLISION & retVal << GameModel.BIT_RIGHT_COLISION
                + GameModel.BIT_BOTTOM_COLISION & retVal << GameModel.BIT_BOTTOM_COLISION
                + GameModel.BIT_TOP_COLISION & retVal << GameModel.BIT_TOP_COLISION;

        float dist = ball.getRadius();

        if (cnt > 1)
        {
            dist = dist * 1.4142f / 2;
        }

        if ( (retVal & GameModel.BIT_LEFT_COLISION) != 0)
        {
            center.setX(rectangle.getLeftX() - dist);
        }

        if ( (retVal & GameModel.BIT_RIGHT_COLISION) != 0)
        {
            center.setX(rectangle.getRightX() + dist);
        }

        if ( (retVal & GameModel.BIT_BOTTOM_COLISION) != 0)
        {
            center.setY(rectangle.getBottomY() + dist);
        }

        if ( (retVal & GameModel.BIT_TOP_COLISION) != 0)
        {
            center.setY(rectangle.getTopY() - dist);
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
