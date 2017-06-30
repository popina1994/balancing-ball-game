package com.example.popina.projekat.logic.shape.figure.hole.gravity;

import com.example.popina.projekat.logic.game.utility.Utility;
import com.example.popina.projekat.logic.shape.constants.ColorConst;
import com.example.popina.projekat.logic.shape.constants.ShapeConst;
import com.example.popina.projekat.logic.shape.sound.SoundConst;
import com.example.popina.projekat.logic.shape.coordinate.Coordinate;
import com.example.popina.projekat.logic.shape.figure.hole.CircleHole;
import com.example.popina.projekat.logic.shape.scale.UtilScale;
import com.example.popina.projekat.logic.shape.sound.SoundPlayerCallback;

/**
 * Created by popina on 29.06.2017..
 */

public class VortexHole extends GravityHole
{
    public VortexHole(float x, float y, float radius)
    {
        super(x, y, radius, ShapeConst.TYPE_HOLE_VORTEX, ColorConst.COLOR_VORTEX_DOWN);
    }

    public VortexHole(Coordinate c, float radius)
    {
        super(c, radius, ShapeConst.TYPE_HOLE_VORTEX, ColorConst.COLOR_VORTEX_DOWN);
    }

    @Override
    public boolean isWon()
    {
        return false;
    }

    @Override
    public boolean doesCollideTemplateMethod(CircleHole ball)
    {
        return Utility.isDistanceBetweenCoordLesThan(center, ball.getCenter(), radius + ball.getRadius(), false);
    }

    @Override
    public VortexHole scale(UtilScale utilScale)
    {
        VortexHole vortexHole = new VortexHole(utilScale.scaleCoordinate(getCenter()), utilScale.scaleWidth(getRadius()));
        return vortexHole;
    }

    @Override
    public VortexHole scaleReverse(UtilScale utilScale)
    {
        VortexHole vortexHole = new VortexHole(utilScale.scaleReverseCoordinate(getCenter()), utilScale.scaleReverseWidth(getRadius()));
        return vortexHole;
    }

    @Override
    protected void playSoundTemplateMethod(SoundPlayerCallback soundPlayerCallback)
    {
        soundPlayerCallback.playSound(SoundConst.SOUND_ID_VORTEX);
    }
}
