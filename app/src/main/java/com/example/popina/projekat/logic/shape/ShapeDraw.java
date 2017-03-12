package com.example.popina.projekat.model.shape;

import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.PorterDuff;

import com.example.popina.projekat.R;
import com.example.popina.projekat.application.common.CommonModel;
import com.example.popina.projekat.model.shape.figure.Figure;

import java.util.LinkedList;

/**
 * Created by popina on 08.03.2017..
 */

public class ShapeDraw {
    private Context context;
    private  Bitmap background;
    private CommonModel commonModel;
    private int height;
    private int width;

    // I have chosen width/height constructor instead of canvas
    // So that background can be resized in canvas.
    //
    public ShapeDraw(Context context, int width, int height) {
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

    public CommonModel getCommonModel() {
        return commonModel;
    }

    public void setCommonModel(CommonModel commonModel) {
        this.commonModel = commonModel;
    }

    public void spriteOnBackground(LinkedList<Figure> listFigures)
    {
        Canvas canvas = new Canvas(background);
        canvas.drawBitmap(background, 0, 0, null);
        for (Figure it : listFigures) {
            it.drawOnCanvas(canvas);
        }
    }

    public  void drawOnCanvas(LinkedList<Figure> listFigures, Canvas canvas)
    {
        canvas.drawBitmap(background, 0, 0, null);

        if (commonModel != null) {
            synchronized (commonModel) {
                for (Figure it : listFigures) {
                    it.drawOnCanvas(canvas);
                }
            }
        }
        else
        {
            for (Figure it : listFigures) {
                it.drawOnCanvas(canvas);
            }
        }
    }

    public void drawOnCanvas(Figure figure, Canvas canvas)
    {
        canvas.drawBitmap(background, 0, 0, null);
        if (commonModel != null)
        {
            synchronized (commonModel)
            {
                figure.drawOnCanvas(canvas);
            }
        }
        else
        {
            figure.drawOnCanvas(canvas);
        }
    }


}
