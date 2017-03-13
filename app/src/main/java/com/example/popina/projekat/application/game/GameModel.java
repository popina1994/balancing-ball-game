package com.example.popina.projekat.application.game;

import com.example.popina.projekat.application.common.CommonModel;
import com.example.popina.projekat.logic.game.Coordinate3D;
import com.example.popina.projekat.logic.game.coeficient.Coeficient;
import com.example.popina.projekat.logic.shape.figure.Figure;
import com.example.popina.projekat.logic.shape.figure.circle.Circle;

import java.util.LinkedList;

/**
 * Created by popina on 09.03.2017..
 */

public class GameModel extends CommonModel {

    public static float ALPHA = 0.9f;
    public static final float S_NS = 1000000000;
    public static final float REVERSE_SLOW_DOWN = 0.99f;
    public static final float SCALE_ACC = 10;

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
}
