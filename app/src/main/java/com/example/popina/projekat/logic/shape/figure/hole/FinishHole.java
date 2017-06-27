package com.example.popina.projekat.logic.shape.figure.hole;

import com.example.popina.projekat.logic.shape.constants.ColorConst;
import com.example.popina.projekat.logic.shape.constants.ShapeConst;
import com.example.popina.projekat.logic.shape.constants.SoundConst;
import com.example.popina.projekat.logic.shape.coordinate.Coordinate;
import com.example.popina.projekat.logic.shape.scale.UtilScale;
import com.example.popina.projekat.logic.shape.sound.SoundPlayerCallback;

/**
 * Created by popina on 08.03.2017..
 */

public class FinishHole extends CircleHole
{
    public FinishHole(float x, float y, float radius)
    {
        super(x, y, radius, ShapeConst.TYPE_FINISH_HOLE, ColorConst.COLOR_HOLE_FINISH);
    }

    public FinishHole(Coordinate c, float radius)
    {
        super(c, radius, ShapeConst.TYPE_FINISH_HOLE, ColorConst.COLOR_HOLE_FINISH);
    }

    @Override
    public FinishHole scale(UtilScale utilScale)
    {
        FinishHole finishHole = new FinishHole(utilScale.scaleCoordinate(getCenter()), utilScale.scaleWidth(getRadius()));
        return finishHole;
    }

    @Override
    public FinishHole scaleReverse(UtilScale utilScale)
    {
        FinishHole finishHole = new FinishHole(utilScale.scaleReverseCoordinate(getCenter()), utilScale.scaleReverseWidth(getRadius()));
        return finishHole;
    }

    @Override
    public boolean isWon()
    {
        return true;
    }

    @Override
    public boolean isGameOver()
    {
        return true;
    }

    @Override
    public void playSound(SoundPlayerCallback soundPlayerCallback)
    {
        soundPlayerCallback.playSound(SoundConst.SOUND_ID_SUCCESS);
    }
}
