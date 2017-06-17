package com.example.popina.projekat.logic.shape.factory;

import com.example.popina.projekat.logic.shape.constants.ShapeConst;
import com.example.popina.projekat.logic.shape.figure.obstacle.RectangleObstacle;
import com.example.popina.projekat.logic.shape.scale.UtilScale;

import java.util.LinkedList;

/**
 * Created by popina on 14.06.2017..
 */

public class ShapeBorderFactory extends ShapeFactory
{
    public ShapeBorderFactory(UtilScale utilScale)
    {
        super(utilScale);
    }

    public LinkedList<RectangleObstacle> createBorders()
    {
        LinkedList<RectangleObstacle> listBorders = new LinkedList<>();

        RectangleObstacle rectangleObstacleLeftWall = new RectangleObstacle(ShapeConst.WALL_LEFT_X, ShapeConst.WALL_LEFT_Y, ShapeConst.WIDTH_RECT_WALL, ShapeConst.HEIGHT_RECT_WALL);
        rectangleObstacleLeftWall = rectangleObstacleLeftWall.scale(getUtilScale());
        RectangleObstacle rectangleObstacleRightWall = new RectangleObstacle(ShapeConst.WALL_RIGHT_X, ShapeConst.WALL_RIGHT_Y, ShapeConst.WIDTH_RECT_WALL, ShapeConst.HEIGHT_RECT_WALL);
        rectangleObstacleRightWall = rectangleObstacleRightWall.scale(getUtilScale());
        RectangleObstacle rectangleObstacleTopWall = new RectangleObstacle(ShapeConst.WALL_TOP_X, ShapeConst.WALL_TOP_Y, ShapeConst.WIDTH_RECT_WALL, ShapeConst.HEIGHT_RECT_WALL);
        rectangleObstacleTopWall = rectangleObstacleTopWall.scale(getUtilScale());
        RectangleObstacle rectangleObstacleBottomWall = new RectangleObstacle(ShapeConst.WALL_BOTTOM_X, ShapeConst.WALL_BOTTOM_Y, ShapeConst.WIDTH_RECT_WALL, ShapeConst.HEIGHT_RECT_WALL);
        rectangleObstacleBottomWall = rectangleObstacleBottomWall.scale(getUtilScale());

        listBorders.addLast(rectangleObstacleLeftWall);
        listBorders.addLast(rectangleObstacleRightWall);
        listBorders.addLast(rectangleObstacleBottomWall);
        listBorders.addLast(rectangleObstacleTopWall);

        return listBorders;
    }


}
