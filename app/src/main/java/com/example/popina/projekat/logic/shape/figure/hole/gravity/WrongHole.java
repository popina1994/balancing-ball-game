package com.example.popina.projekat.logic.shape.figure.hole.gravity;

import com.example.popina.projekat.logic.shape.constants.ColorConst;
import com.example.popina.projekat.logic.shape.constants.ShapeConst;
import com.example.popina.projekat.logic.shape.constants.SoundConst;
import com.example.popina.projekat.logic.shape.coordinate.Coordinate;
import com.example.popina.projekat.logic.shape.figure.hole.CircleHole;
import com.example.popina.projekat.logic.shape.scale.UtilScale;
import com.example.popina.projekat.logic.shape.sound.SoundPlayerCallback;

/**
 * Created by popina on 08.03.2017..
 */

public class WrongHole extends GravityHole
{
    public WrongHole(float x, float y, float radius)
    {
        super(x, y, radius, ShapeConst.TYPE_WRONG_HOLE, ColorConst.COLOR_HOLE_WRONG);
    }

    public WrongHole(Coordinate c, float radius)
    {
        super(c, radius, ShapeConst.TYPE_WRONG_HOLE, ColorConst.COLOR_HOLE_WRONG);
    }

    @Override
    public WrongHole scale(UtilScale utilScale)
    {
        WrongHole wrongHole = new WrongHole(utilScale.scaleCoordinate(getCenter()), utilScale.scaleWidth(getRadius()));
        return wrongHole;
    }

    @Override
    public WrongHole scaleReverse(UtilScale utilScale)
    {
        WrongHole wrongHole = new WrongHole(utilScale.scaleReverseCoordinate(getCenter()), utilScale.scaleReverseWidth(getRadius()));
        return wrongHole;
    }

    @Override
    public boolean isWon()
    {
        return false;
    }


    @Override
    protected void playSoundTemplateMethod(SoundPlayerCallback soundPlayerCallback)
    {
        soundPlayerCallback.playSound(SoundConst.SOUND_ID_MISS);
    }
}
