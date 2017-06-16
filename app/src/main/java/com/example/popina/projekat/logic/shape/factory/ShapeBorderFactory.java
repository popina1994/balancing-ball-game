package com.example.popina.projekat.logic.shape.factory;

import com.example.popina.projekat.logic.shape.figure.obstacle.RectangleObstacle;
import com.example.popina.projekat.logic.shape.model.ShapeModel;
import com.example.popina.projekat.logic.shape.scale.UtilScale;

import java.util.LinkedList;

/**
 * Created by popina on 14.06.2017..
 */

public class ShapeBorderFactory extends ShapeFactory {
    public ShapeBorderFactory(UtilScale utilScale) {
        super(utilScale);
    }

    public LinkedList<RectangleObstacle> createBorders()
    {
        LinkedList<RectangleObstacle> listBorders = new LinkedList<>();

        RectangleObstacle rectangleObstacleLeftWall = new RectangleObstacle(ShapeModel.WALL_LEFT_X, ShapeModel.WALL_LEFT_Y, ShapeModel.WIDTH_RECT_WALL, ShapeModel.HEIGHT_RECT_WALL);
        rectangleObstacleLeftWall = rectangleObstacleLeftWall.scale(getUtilScale());
        RectangleObstacle rectangleObstacleRightWall = new RectangleObstacle(ShapeModel.WALL_RIGHT_X, ShapeModel.WALL_RIGHT_Y, ShapeModel.WIDTH_RECT_WALL, ShapeModel.HEIGHT_RECT_WALL);
        rectangleObstacleRightWall = rectangleObstacleRightWall.scale(getUtilScale());
        RectangleObstacle rectangleObstacleTopWall = new RectangleObstacle(ShapeModel.WALL_TOP_X, ShapeModel.WALL_TOP_Y, ShapeModel.WIDTH_RECT_WALL, ShapeModel.HEIGHT_RECT_WALL);
        rectangleObstacleTopWall = rectangleObstacleTopWall.scale(getUtilScale());
        RectangleObstacle rectangleObstacleBottomWall = new RectangleObstacle(ShapeModel.WALL_BOTTOM_X, ShapeModel.WALL_BOTTOM_Y, ShapeModel.WIDTH_RECT_WALL, ShapeModel.HEIGHT_RECT_WALL);
        rectangleObstacleBottomWall = rectangleObstacleBottomWall.scale(getUtilScale());

        listBorders.addLast(rectangleObstacleLeftWall);
        listBorders.addLast(rectangleObstacleRightWall);
        listBorders.addLast(rectangleObstacleBottomWall);
        listBorders.addLast(rectangleObstacleTopWall);

        return  listBorders;
    }


}
