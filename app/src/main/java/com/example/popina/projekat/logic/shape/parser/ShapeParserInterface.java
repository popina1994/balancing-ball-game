package com.example.popina.projekat.logic.shape.parser;

import com.example.popina.projekat.logic.shape.scale.UtilScale;

/**
 * Created by popina on 27.06.2017..
 */

public interface ShapeParserInterface
{
    ShapeParserInterface scale(UtilScale utilScale);

    ShapeParserInterface scaleReverse(UtilScale utilScale);

    String toString();
}
