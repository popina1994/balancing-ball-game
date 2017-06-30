package com.example.popina.projekat.application.begin;

import com.example.popina.projekat.application.common.CommonModel;
import com.example.popina.projekat.logic.statistics.database.ScoreDatabase;

/**
 * Created by popina on 08.03.2017..
 */

public class MainModel extends CommonModel
{

    public static final String POLYGON_NAME = "POLYGON_NAME";
    public static final String POLYGON_IMAGE = "POLYGON_IMAGE";
    public static final String POLYGON_DIFFICULTY = "POLYGON_DIFFICULTY";
    public static final String INSTANT_RUN = "instant-run";

    public static final float SCALE_SCREEN_WIDTH = 1f;
    public static final float SCALE_SCREEN_HEIGHT = 0.8f;

    public static final String SELECT_DELETE = "Brisi poligon";
    public static final String SELECT_EDIT = "Uredi poligon";

    public static final int REQUEST_CODE_CREATE_POLYGON = 1;
    public static final int REQUEST_CODE_STATISTIC = 2;
    public static final int REQUEST_CODE_SETTINGS = 3;
    public static final int REQUEST_CODE_NEW_GAME = 4;

    private static final String TAG = "BEGIN_ACTIVITY";


    private ScoreDatabase scoreDatabase;
    private String[] createdPolygons;

    public ScoreDatabase getScoreDatabase()
    {
        return scoreDatabase;
    }

    public void setScoreDatabase(ScoreDatabase scoreDatabase)
    {
        this.scoreDatabase = scoreDatabase;
    }

    public String[] getCreatedPolygons()
    {
        return createdPolygons;
    }

    public void setCreatedPolygons(String[] createdPolygons)
    {
        this.createdPolygons = createdPolygons;
    }
}
