package com.example.popina.projekat.application.game;

import android.hardware.SensorManager;
import android.media.SoundPool;

import com.example.popina.projekat.application.common.CommonModel;
import com.example.popina.projekat.logic.game.utility.Coordinate3D;
import com.example.popina.projekat.logic.game.coeficient.Coeficient;
import com.example.popina.projekat.logic.game.utility.Utility;
import com.example.popina.projekat.logic.shape.figure.Figure;
import com.example.popina.projekat.logic.shape.figure.circle.Circle;

import java.util.LinkedList;

/**
 * Created by popina on 09.03.2017..
 */

public class GameModel extends CommonModel {

    public static final int BIT_LEFT_COLISION = 0x1 << 0;
    public static final int BIT_RIGHT_COLISION = 0x1 << 1;
    public static final int BIT_TOP_COLISION = 0x1 << 2;
    public static final int BIT_BOTTOM_COLISION = 0x1 << 3;

    public static final float FLOAT_ACCURACY = Utility.FLOAT_ACCURACY;

    public static float ALPHA = 0.9f;

    public static final float S_NS = 1000000000;
    public static final float REVERSE_SLOW_DOWN = 0.99f;
    public static final float SCALE_ACC = 10;

    public  static final int MAX_STREAMS = 10;

    private LinkedList<Figure> listFigures = new LinkedList<>();
    private String fileName;
    private Circle ball;
    private Filter filter = new Filter(ALPHA);
    // OnInit
    //
    private long lastTime = Long.MAX_VALUE;
    private Coordinate3D speed = new Coordinate3D(5, 5, 0);
    private int height;
    private int width;
    private boolean sufraceInitialized;
    private Coeficient coeficient;
    private boolean gameOver = false;
    private boolean paused = false;

    private SoundPool soundPool;
    private int soundsId[];

    public static final int SOUND_PRIORITY = 1;
    public static final float SOUND_LEFT_VOLUME = 1;
    public static final float SOUND_RIGHT_VOLUME = 1;
    public static final int SOUND_PRIORITY_STREAM = 1;
    public static final int SOUND_NO_LOOP = 0;
    public static final float SOUND_RATE_PLAYBACK = 1;

    public static final int SOUND_ID_COLLISION = 0;
    public static final int SOUND_ID_MISS = 1;
    public static final int SOUND_ID_SUCCESS = 2;
    public static final int SOUND_MAX = 3;

    private SensorManager sensorManager;




    public LinkedList<Figure> getListFigures() {
        return listFigures;
    }

    public void setListFigures(LinkedList<Figure> listFigures) {
        this.listFigures = listFigures;
    }

    public String getFileName() {
        return fileName;
    }

    public void setFileName(String fileName) {
        this.fileName = fileName;
    }

    public Circle getBall() {
        return ball;
    }

    public void setBall(Circle ball) {
        this.ball = ball;
    }

    public Filter getFilter() {
        return filter;
    }

    public void setFilter(Filter filter) {
        this.filter = filter;
    }

    public long getLastTime() {
        return lastTime;
    }

    public void setLastTime(long lastTime) {
        this.lastTime = lastTime;
    }

    public Coordinate3D getSpeed() {
        return speed;
    }

    public void setSpeed(Coordinate3D speed) {
        this.speed = speed;
    }

    public int getHeight() {
        return height;
    }

    public void setHeight(int height) {
        this.height = height;
    }

    public int getWidth() {
        return width;
    }

    public void setWidth(int width) {
        this.width = width;
    }

    public boolean isSufraceInitialized() {
        return sufraceInitialized;
    }

    public void setSufraceInitialized(boolean sufraceInitialized) {
        this.sufraceInitialized = sufraceInitialized;
    }

    public Coeficient getCoeficient() {
        return coeficient;
    }

    public void setCoeficient(Coeficient coeficient) {
        this.coeficient = coeficient;
    }

    public boolean isGameOver() {
        return gameOver;
    }

    public void setGameOver(boolean gameOver) {
        this.gameOver = gameOver;
    }

    public boolean isPaused() {
        return paused;
    }

    public void setPaused(boolean paused) {
        this.paused = paused;
    }

    public SoundPool getSoundPool() {
        return soundPool;
    }

    public void setSoundPool(SoundPool soundPool) {
        this.soundPool = soundPool;
    }

    public int[] getSoundsId() {
        return soundsId;
    }

    public void setSoundsId(int[] soundsId) {
        this.soundsId = soundsId;
    }

    public SensorManager getSensorManager() {
        return sensorManager;
    }

    public void setSensorManager(SensorManager sensorManager) {
        this.sensorManager = sensorManager;
    }
}
