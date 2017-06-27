package com.example.popina.projekat.logic.shape.draw;

import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.PorterDuff;

import com.example.popina.projekat.R;
import com.example.popina.projekat.application.common.CommonModel;
import com.example.popina.projekat.logic.shape.figure.Figure;

import java.util.LinkedList;

/**
 * Created by popina on 08.03.2017..
 */

public class ShapeDraw
{
    private Context context;
    private Bitmap background;
    private CommonModel commonModel;
    private int height;
    private int width;

    // I have chosen width/height constructor instead of canvas
    // So that background can be resized in canvas.
    //
    public ShapeDraw(Context context, int width, int height)
    {
        this.context = context;
        this.width = width;
        this.height = height;
        background = Bitmap.createBitmap(width, height, Bitmap.Config.ARGB_8888);
        Bitmap bmpBack;
        Canvas canvas = new Canvas(background);
        bmpBack = BitmapFactory.decodeResource(context.getResources(), R.drawable.background);
        canvas.drawColor(Color.TRANSPARENT, PorterDuff.Mode.CLEAR);
        canvas.drawBitmap(bmpBack, 0, 0, null);
    }

    public CommonModel getCommonModel()
    {
        return commonModel;
    }

    public void setCommonModel(CommonModel commonModel)
    {
        this.commonModel = commonModel;
    }

    public void spriteOnBackground(LinkedList<? extends ShapeDrawInterface> listFigures)
    {
        Canvas canvas = new Canvas(background);
        canvas.drawBitmap(background, 0, 0, null);
        for (ShapeDrawInterface it : listFigures)
        {
            it.drawOnCanvas(canvas);
        }
    }

    public void drawOnCanvas(LinkedList<? extends ShapeDrawInterface> listFigures, Canvas canvas)
    {
        canvas.drawBitmap(background, 0, 0, null);

        if (commonModel != null)
        {
            synchronized (commonModel)
            {
                for (ShapeDrawInterface it : listFigures)
                {
                    it.drawOnCanvas(canvas);
                }
            }
        } else
        {
            for (ShapeDrawInterface it : listFigures)
            {
                it.drawOnCanvas(canvas);
            }
        }
    }

    public void drawOnCanvas(ShapeDrawInterface shapeDrawInterface, Canvas canvas)
    {
        canvas.drawBitmap(background, 0, 0, null);
        if (commonModel != null)
        {
            synchronized (commonModel)
            {
                shapeDrawInterface.drawOnCanvas(canvas);
            }
        } else
        {
            shapeDrawInterface.drawOnCanvas(canvas);
        }
    }


}
