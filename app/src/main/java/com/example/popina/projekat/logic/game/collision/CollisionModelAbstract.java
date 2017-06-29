package com.example.popina.projekat.logic.game.collision;

import com.example.popina.projekat.logic.game.coefficient.Coefficient;
import com.example.popina.projekat.logic.game.utility.Coordinate3D;
import com.example.popina.projekat.logic.shape.figure.Figure;
import com.example.popina.projekat.logic.shape.figure.hole.CircleHole;
import com.example.popina.projekat.logic.shape.sound.SoundPlayerCallback;

import java.util.LinkedList;

/**
 * Created by popina on 29.06.2017..
 */

public abstract class CollisionModelAbstract
{

    protected Coefficient coefficient;
    protected SoundPlayerCallback soundPlayerCallback;
    protected long lastTime;

    public long getLastTime()
    {
        return lastTime;
    }

    public void setLastTime(long lastTime)
    {
        this.lastTime = lastTime;
    }

    CollisionModelAbstract(Coefficient coefficient, SoundPlayerCallback soundPlayerCallback)
    {
        this.coefficient = coefficient;
        this.soundPlayerCallback = soundPlayerCallback;
    }

    public abstract int updateSystem(Coordinate3D filteredAcceleration, long time, LinkedList<Figure> listFigures, CircleHole ball);

    public static final int GAME_OVER_WIN = 0;
    public static final int GAME_OVER_LOSE = 1;
    public static final int GAME_CONTINUES_COLLISION = 2;
    public static final int GAME_CONTINUES_NO_COLLISION = 3;
}
