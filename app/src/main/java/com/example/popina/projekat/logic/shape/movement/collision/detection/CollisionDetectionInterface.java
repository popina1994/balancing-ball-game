package com.example.popina.projekat.logic.shape.movement.collision.detection;

import com.example.popina.projekat.logic.shape.figure.hole.CircleHole;

/**
 * Created by popina on 27.06.2017..
 */

public interface CollisionDetectionInterface
{
    public abstract boolean doesCollide(CircleHole ball);

    public abstract boolean isGameOver();

    public abstract boolean isWon();
}
