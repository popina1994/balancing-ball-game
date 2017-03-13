package com.example.popina.projekat.application.common;

import android.view.View;

import com.example.popina.projekat.logic.shape.ShapeDraw;
import com.example.popina.projekat.logic.shape.ShapeFactory;
import com.example.popina.projekat.logic.shape.ShapeParser;

/**
 * Created by popina on 08.03.2017..
 */

public abstract class CommonModel {
    private ShapeFactory shapeFactory;
    private ShapeDraw shapeDraw;
    private ShapeParser shapeParser;

    public ShapeFactory getShapeFactory() {
        return shapeFactory;
    }

    public void setShapeFactory(ShapeFactory shapeFactory) {
        this.shapeFactory = shapeFactory;
    }

    public ShapeDraw getShapeDraw() {
        return shapeDraw;
    }

    public void setShapeDraw(ShapeDraw shapeDraw) {
        this.shapeDraw = shapeDraw;
    }

    public ShapeParser getShapeParser() {
        return shapeParser;
    }

    public void setShapeParser(ShapeParser shapeParser) {
        this.shapeParser = shapeParser;
    }

    public static final int UI_OPTIONS_MAIN =

             View.SYSTEM_UI_FLAG_LAYOUT_HIDE_NAVIGATION
            | View.SYSTEM_UI_FLAG_HIDE_NAVIGATION
            | View.SYSTEM_UI_FLAG_IMMERSIVE_STICKY;

    public static final int UI_OPTIONS_NON_MAIN =
            View.SYSTEM_UI_FLAG_LAYOUT_STABLE
            | View.SYSTEM_UI_FLAG_LAYOUT_HIDE_NAVIGATION
            | View.SYSTEM_UI_FLAG_LAYOUT_FULLSCREEN
            | View.SYSTEM_UI_FLAG_HIDE_NAVIGATION
            | View.SYSTEM_UI_FLAG_FULLSCREEN
            | View.SYSTEM_UI_FLAG_IMMERSIVE_STICKY;

}
