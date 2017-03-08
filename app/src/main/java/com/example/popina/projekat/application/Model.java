package com.example.popina.projekat.application;

import com.example.popina.projekat.model.shape.ShapeDraw;
import com.example.popina.projekat.model.shape.ShapeFactory;
import com.example.popina.projekat.model.shape.ShapeParser;

/**
 * Created by popina on 08.03.2017..
 */

public abstract class Model {
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
}
