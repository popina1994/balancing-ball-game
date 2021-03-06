package com.example.popina.projekat.logic.shape.constants;

import android.graphics.Color;

/**
 * Created by popina on 08.03.2017..
 */

public abstract class ShapeConst
{

    public static final String TYPE_HOLE_START = "START_HOLE";
    public static final String TYPE_HOLE_FINISH = "FINISH_HOLE";
    public static final String TYPE_HOLE_WRONG = "WRONG_HOLE";
    public static final String TYPE_HOLE_VORTEX = "VORTEX_HOLE";
    public static final String TYPE_OBSTACLE_RECTANGLE = "OBSTACLE_RECTANGLE";
    public static final String TYPE_OBSTACLE_CIRCLE = "OBSTACLE_CIRCLE";

    public static final String FIGURE_TYPE = "FIGURE_TYPE";
    public static final String FIGURE_COLOR = "FIGURE_COLOR";
    public static final String FIGURE_CENTER = "FIGURE_CENTER";
    // Default position for created circle rectangle.
    //
    public static final float DEFAULT_OBSTACLE_X = 0.5f;
    public static final float DEFAULT_OBSTACLE_Y = 0.5f;
    public static final float DEFAULT_RADIUS = 0.1f;
    public static final float DEFAULT_RECT_WIDTH = 0.2f;
    public static final float DEFAULT_RECT_HEIGHT = 0.2f;
    public static final float DEFAULT_RECT_RADIUS = 0.2f;
    public static final float DEFAULT_START_X = 0.1f;
    public static final float DEFAULT_START_Y = 0.1f;
    public static final float DEFAULT_FINISH_X = 0.9f;
    public static final float DEFAULT_FINISH_Y = 0.9f;

    // Constants used for initialization of border walls.
    //

    public static final float WIDTH_RECT_WALL = 1.2f;
    public static final float HEIGHT_RECT_WALL = 1.2f;

    public static final float WALL_LEFT_X = -WIDTH_RECT_WALL / 2;
    public static final float WALL_LEFT_Y = HEIGHT_RECT_WALL / 2;

    public static final float WALL_RIGHT_X = 1 + WIDTH_RECT_WALL / 2;
    public static final float WALL_RIGHT_Y = HEIGHT_RECT_WALL / 2;

    public static final float WALL_TOP_X = WIDTH_RECT_WALL / 2;
    public static final float WALL_TOP_Y = -HEIGHT_RECT_WALL / 2;

    public static final float WALL_BOTTOM_X = WIDTH_RECT_WALL / 2;
    public static final float WALL_BOTTOM_Y = 1 + HEIGHT_RECT_WALL / 2;


    // Represents idx of data which is saved in file, this is used in shape parser.
    //
    public static int FIGURE_TYPE_IDX = 1;
    public static int FIGURE_COLOR_IDX = 3;
    public static int FIGURE_COORDINATE_X_IDX = 5;
    public static int FIGURE_COORDINATE_Y_IDX = 6;
    public static int FIGURE_CIRCLE_RADIUS_IDX = 7;
    public static int FIGURE_RECT_WIDTH_IDX = 7;
    public static int FIGURE_RECT_HEIGHT_IDX = 8;
    public static int FIGURE_RECT_ANGLE_IDX = 9;

}
