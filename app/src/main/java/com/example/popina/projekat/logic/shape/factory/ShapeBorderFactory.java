package com.example.popina.projekat.logic.shape.factory;

import android.graphics.Rect;
import android.graphics.drawable.shapes.Shape;

import com.example.popina.projekat.logic.shape.figure.rectangle.Rectangle;
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

    public LinkedList<Rectangle> createBorders()
    {
        LinkedList<Rectangle> listBorders = new LinkedList<>();

        Rectangle rectangleLeftWall = new Rectangle(ShapeModel.WALL_LEFT_X, ShapeModel.WALL_LEFT_Y, ShapeModel.WIDTH_RECT_WALL, ShapeModel.HEIGHT_RECT_WALL);
        rectangleLeftWall = rectangleLeftWall.scale(getUtilScale());
        Rectangle rectangleRightWall = new Rectangle(ShapeModel.WALL_RIGHT_X, ShapeModel.WALL_RIGHT_Y, ShapeModel.WIDTH_RECT_WALL, ShapeModel.HEIGHT_RECT_WALL);
        rectangleRightWall = rectangleRightWall.scale(getUtilScale());
        Rectangle rectangleTopWall = new Rectangle(ShapeModel.WALL_TOP_X, ShapeModel.WALL_TOP_Y, ShapeModel.WIDTH_RECT_WALL, ShapeModel.HEIGHT_RECT_WALL);
        rectangleTopWall  = rectangleTopWall.scale(getUtilScale());
        Rectangle rectangleBottomWall = new Rectangle(ShapeModel.WALL_BOTTOM_X, ShapeModel.WALL_BOTTOM_Y, ShapeModel.WIDTH_RECT_WALL, ShapeModel.HEIGHT_RECT_WALL);
        rectangleBottomWall = rectangleBottomWall.scale(getUtilScale());

        listBorders.addLast(rectangleLeftWall);
        listBorders.addLast(rectangleRightWall);
        listBorders.addLast(rectangleBottomWall);
        listBorders.addLast(rectangleTopWall);

        return  listBorders;
    }


}
