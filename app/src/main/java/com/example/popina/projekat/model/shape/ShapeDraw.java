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
    Context context;
    Bitmap background;
    CommonModel commonModel;

    public ShapeDraw(Context context) {
        this.context = context;
        background = BitmapFactory.decodeResource(context.getResources(), R.drawable.background);
    }

    public CommonModel getCommonModel() {
        return commonModel;
    }

    public void setCommonModel(CommonModel commonModel) {
        this.commonModel = commonModel;
    }

    public  void drawOnCanvas(LinkedList<Figure> listFigures, Canvas canvas)
    {
        canvas.drawColor(Color.TRANSPARENT, PorterDuff.Mode.CLEAR);

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
}
