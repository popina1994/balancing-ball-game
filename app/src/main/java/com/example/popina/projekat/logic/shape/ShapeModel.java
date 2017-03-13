package com.example.popina.projekat.logic.shape;

import android.graphics.Color;

/**
 * Created by popina on 08.03.2017..
 */

public class ShapeModel {
    public static final int COLOR_HOLE_START = Color.RED;
    public static final int COLOR_HOLE_FINISH = Color.GREEN;
    public static final int COLOR_HOLE_WRONG = Color.BLACK;
    public static final int COLOR_OBSTACLE = Color.YELLOW;

    public static final String TYPE_START_HOLE = "START_HOLE";
    public static final String TYPE_FINISH_HOLE = "FINISH_HOLE";
    public static final String TYPE_WRONG_HOLE = "WRONG_HOLE";
    public static final String TYPE_OBSTACLE = "OBSTACLE";

    public static final String FIGURE_TYPE = "FIGURE_TYPE";
    public static final String FIGURE_COLOR= "FIGURE_COLOR";
    public static final String FIGURE_CENTER = "FIGURE_CENTER";
    // Default position for created circle rectangle.
    //
    public static final float DEFAULT_OBSTACLE_X = 0.5f;
    public static final float DEFAULT_OBSTACLE_Y = 0.5f;
    public static final float DEFAULT_RADIUS = 0.1f;
    public static final float DEFAULT_RECT_WIDTH = 0.2f;
    public static final float DEFAULT_RECT_HEIGHT = 0.2f;
    public static final float DEFAULT_START_X = 0.1f;
    public static final float DEFAULT_START_Y = 0.1f;
    public static final float DEFAULT_FINISH_X = 0.9f;
    public static final float DEFAULT_FINISH_Y = 0.9f;

    public static int FIGURE_TYPE_IDX = 1;
    public static int FIGURE_COLOR_IDX = 3;
    public static int FIGURE_COORDINATE_X_IDX = 5;
    public static int FIGURE_COORDINATE_Y_IDX = 6;
    public static int FIGURE_CIRCLE_RADIUS_IDX = 7;
    public static int FIGURE_RECT_WIDTH_IDX = 7;
    public static int FIGURE_RECT_HEIGHT_IDX = 8;

}
