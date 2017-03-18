package com.example.popina.projekat.application.game;

import android.util.Log;
import android.widget.PopupMenu;
import android.widget.Toast;

import com.example.popina.projekat.logic.game.utility.Coordinate3D;
import com.example.popina.projekat.logic.game.utility.Utility;
import com.example.popina.projekat.logic.shape.coordinate.Coordinate;
import com.example.popina.projekat.logic.shape.figure.Figure;
import com.example.popina.projekat.logic.shape.figure.circle.Circle;
import com.example.popina.projekat.logic.shape.figure.circle.StartHole;
import com.example.popina.projekat.logic.shape.figure.rectangle.Rectangle;
import com.example.popina.projekat.logic.shape.scale.UtilScale;

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
        if (!model.isGameOver()) {
            if (model.getLastTime() != Long.MAX_VALUE) {
                Coordinate3D filteredAcc = model.getFilter().filter(new Coordinate3D(newAcc[0], newAcc[1], newAcc[2]));
                updatePosition(filteredAcc, time);
            }
            model.setLastTime(time);
        }
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
                            Toast.makeText(gameActivity, "Pobedio si igru", Toast.LENGTH_SHORT).show();

                        }
                        // In case of wrong hole.
                        //
                        else {
                            Toast.makeText(gameActivity, "Izgubio si igru", Toast.LENGTH_SHORT).show();
                        }
                    } else {
                        // Here shouldn't be case of start hole at all.
                        //

                        Rectangle rectangle = (Rectangle) itFigure;

                        int val = isCollisionAndUpdate(rectangle, ball, newBallPos, center);
                        if ( (val & (GameModel.BIT_LEFT_COLISION | GameModel.BIT_RIGHT_COLISION)) != 0)
                        {
                            model.getSpeed().setX(reverseDir(vX));
                            rightLeftCollision = true;
                        }
                        if ( (val & (GameModel.BIT_TOP_COLISION | GameModel.BIT_BOTTOM_COLISION)) != 0)
                        {
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
                } else if (newX + ball.getRadius() >= model.getWidth()) {
                    center.setX(model.getWidth() - ball.getRadius() - 1);
                    model.getSpeed().setX(reverseDir(vX));
                }
                else {
                    center.setX(newX);
                }
            }

            if (!topBottomCollision) {
                if (newY - ball.getRadius() < 0) {
                    center.setY(ball.getRadius());
                    model.getSpeed().setY(reverseDir(vY));
                } else if (newY + ball.getRadius() >= model.getHeight()) {
                    center.setY(model.getHeight() - ball.getRadius() - 1);
                    model.getSpeed().setY(reverseDir(vY));
                }
                else {
                    center.setY(newY);
                }
            }

            ball.setCenter(center);
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
        float det = -A1 * B2 + A2 * B1 ;
        if ( Math.abs(det)
                < GameModel.FLOAT_ACCURACY)
        {
            return null;
        }

        return new Coordinate( (B2 * C1 - B1 * C2 ) / det, (A1 * C2 - A2 * C1) / det);
    }

    private float distanceBetweenParallelLines(Coordinate3D line1, Coordinate3D line2)
    {
        float A1 = line1.getX();
        float B1 = line1.getY();
        float C1 = line1.getZ();

        float A2 = line2.getX();
        float B2 = line2.getY();
        float C2 = line2.getZ();

        if (A1 != 0)
        {
            // A2 cannot be 0 if they are parallel.
            //
            B2 = B2 / A2 * A1;
            C2 = C2 / A2 * A1;
            A2 = A1;
        }
        else if (B1 != 0)
        {
            // B2 cannot be zero if they are parellel.
            //
            A2 = A2 / B2 * B1;
            C2 = C2 / B2 * B1;
            B2 = B1;
        }

        return (C2 - C1) * (C2 - C1) /(A1 * A1 + B1 * B1);
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

        if ( ( (centerBottomIntersection != null) &&
                        Utility.isOnSegment(
                                rectangle.getBotomLeft(),
                                rectangle.getBottomRight(),
                                centerBottomIntersection)
                    && Utility.isDistanceBetweenCoordLesThan(
                        centerBottomIntersection,
                        ball.getCenter(),
                        minDist,
                        true) )
                ||( (centerBottomIntersection == null) &&
                    distanceBetweenParallelLines(lineRectBottom, lineCenter) <= ball.getRadius() * ball.getRadius() )
                )
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

        if ( (centerTopIntersection != null) &&
                Utility.isOnSegment(
                        rectangle.getTopLeft(),
                        rectangle.getTopRight(),
                        centerTopIntersection)
                && Utility.isDistanceBetweenCoordLesThan(
                centerTopIntersection,
                ball.getCenter(),
                minDist,
                true)
                ||( (centerTopIntersection == null) &&
                distanceBetweenParallelLines(lineRectTop, lineCenter) <= ball.getRadius() * ball.getRadius() )
                )
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

        if ( (centerLeftIntersection != null) &&
                Utility.isOnSegment(
                        rectangle.getTopLeft(),
                        rectangle.getBotomLeft(),
                        centerLeftIntersection)
                && Utility.isDistanceBetweenCoordLesThan(
                centerLeftIntersection,
                ball.getCenter(),
                minDist,
                true)
                ||( (centerLeftIntersection == null) &&
                distanceBetweenParallelLines(lineRectLeft, lineCenter) <= ball.getRadius() * ball.getRadius() )
                )
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
                minDist = val;
            }
        }

        if ( (centerRightIntersection != null) &&
                Utility.isOnSegment(
                        rectangle.getTopRight(),
                        rectangle.getBottomRight(),
                        centerRightIntersection)
                && Utility.isDistanceBetweenCoordLesThan(
                centerRightIntersection,
                ball.getCenter(),
                minDist,
                true)
                ||( (centerRightIntersection == null) &&
                distanceBetweenParallelLines(lineRectRight, lineCenter) <= ball.getRadius() * ball.getRadius() )
                )
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
                retVal = (retVal & ~GameModel.BIT_LEFT_COLISION) | GameModel.BIT_RIGHT_COLISION;
                minDist = val;
            }
        }

        if ( (retVal & GameModel.BIT_LEFT_COLISION) != 0)
        {
            center.setX(rectangle.getLeftX() - ball.getRadius());
        }

        if ( (retVal & GameModel.BIT_RIGHT_COLISION) != 0)
        {
            center.setX(rectangle.getRightX() + ball.getRadius());
        }

        if ( (retVal & GameModel.BIT_BOTTOM_COLISION) != 0)
        {
            center.setY(rectangle.getBottomY() + ball.getRadius());
        }

        if ( (retVal & GameModel.BIT_TOP_COLISION) != 0)
        {
            center.setY(rectangle.getTopY() - ball.getRadius());
        }

        if (retVal == 0)
        {
            Log.d("Game controller", "WARNING!!!");
            Log.d("Game Controller", "Nesto ovde nije dobro");
            Log.d("Game Controller tl", rectangle.getTopLeft().toString());
            Log.d("Game Controller botr", rectangle.getBottomRight().toString());
            Log.d("Game Controller Bottom", centerBottomIntersection.toString());
            Log.d("Game Controller Top", centerTopIntersection.toString());
            Log.d("Game Controller Left", centerLeftIntersection.toString());
            Log.d("Game Controller Right", centerRightIntersection.toString());
            Log.d("Game controller Ball", ball.getCenter().toString());
            Log.d("Game controller New B", ballNew.getCenter().toString());
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
