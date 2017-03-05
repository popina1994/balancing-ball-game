package com.example.popina.projekat.create;

import android.content.Context;
import android.content.res.Resources;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.util.AttributeSet;
import android.util.Log;
import android.view.SurfaceView;
import android.view.SurfaceHolder;

import com.example.popina.projekat.R;

/**
 * Created by popina on 04.03.2017..
 */

public class CreatePolygonView extends SurfaceView implements SurfaceHolder.Callback {

    private CreatePolygonModel model;

    public CreatePolygonView(Context context) {
        super(context);
        init();
    }

    public CreatePolygonView(Context context, AttributeSet attrs) {
        super(context, attrs);
        init();
    }


    public CreatePolygonView(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        init();
    }

    public CreatePolygonView(Context context, AttributeSet attrs, int defStyleAttr, int defStyleRes) {
        super(context, attrs, defStyleAttr, defStyleRes);
        init();
    }


    private void init() {
        SurfaceHolder surfaceHolder = getHolder();
        surfaceHolder.addCallback(this);
    }

    @Override
    public void surfaceCreated(SurfaceHolder holder) {
        Log.d("Test", "It works");
        Resources resources = getContext().getResources();
        Bitmap background = BitmapFactory.decodeResource(resources, R.drawable.background);


        Canvas canvas = holder.lockCanvas();

        canvas.drawBitmap(background, 0, 0, null);

        int height = canvas.getHeight();
        int width = canvas.getWidth();

        model.setHeight(height);
        model.setWidth(width);

        Paint p = new Paint();
        // Constructor won't set a color. I don't have idea why. -_-
        //
        p.setColor(Color.RED);
        canvas.drawCircle(10, 10, 100, p);


        holder.unlockCanvasAndPost(canvas);

    }

    @Override
    public void surfaceChanged(SurfaceHolder holder, int format, int width, int height) {

    }

    @Override
    public void surfaceDestroyed(SurfaceHolder holder) {

    }

    public void setModel(CreatePolygonModel model) {
        this.model = model;
    }

}