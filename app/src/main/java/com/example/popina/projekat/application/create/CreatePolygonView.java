package com.example.popina.projekat.application.create;

import android.content.Context;
import android.content.res.Resources;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.PorterDuff;
import android.util.AttributeSet;
import android.util.Log;
import android.view.MotionEvent;
import android.view.SurfaceView;
import android.view.SurfaceHolder;
import android.view.View;

import com.example.popina.projekat.R;
import com.example.popina.projekat.model.shape.ShapeDraw;
import com.example.popina.projekat.model.shape.coordinate.Coordinate;
import com.example.popina.projekat.model.shape.figure.Figure;
import com.example.popina.projekat.model.shape.ShapeFactory;
import com.example.popina.projekat.model.shape.scale.UtilScaleNormal;

import java.util.LinkedList;

/**
 * Created by popina on 04.03.2017..
 */

public class CreatePolygonView extends SurfaceView implements SurfaceHolder.Callback, View.OnTouchListener {

    private CreatePolygonModel model;
    private CreatePolygonController controller;
    private SurfaceThread surfaceThread = null;

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


    public void invalidateSurfaceView()
    {
        surfaceThread.interrupt();
    }

    public class SurfaceThread extends Thread {
        private boolean running = true;

        @Override
        public void run() {
            SurfaceHolder surfaceHolder = CreatePolygonView.this.getHolder();
            while (running)
            {
                try {
                    while (!interrupted()) {
                        sleep(100000000);
                    }
                }catch (InterruptedException e)
                {

                }
                if (running) {
                    Log.d("Sufrace view", "Drawing");
                    Canvas canvas = surfaceHolder.lockCanvas();
                    try {
                        CreatePolygonView.this.renderSurfaceView(canvas);
                    } finally {
                        surfaceHolder.unlockCanvasAndPost(canvas);
                    }
                }


            }
        }
    }

    Resources resources = getContext().getResources();
    Bitmap background = BitmapFactory.decodeResource(resources, R.drawable.background);

    private void renderSurfaceView(Canvas canvas) {
        synchronized (model)
        {
            model.getShapeDraw().drawOnCanvas(model.getListFigures(), canvas);
        }

    }

    @Override
    public void surfaceCreated(SurfaceHolder holder) {
        Log.d("Surface View", "Created surface");

        Canvas canvas = holder.lockCanvas();
        int height = canvas.getHeight();
        int width = canvas.getWidth();

        UtilScaleNormal utilScaleNormal = new UtilScaleNormal(width, height);

        ShapeFactory shapeFactory = new ShapeFactory(utilScaleNormal);
        model.setShapeFactory(shapeFactory);

        ShapeDraw shapeDraw = new ShapeDraw(getContext());
        shapeDraw.setModel(model);
        model.setShapeDraw(shapeDraw);

        holder.unlockCanvasAndPost(canvas);
        setOnTouchListener(this);

        surfaceThread = new SurfaceThread();
        surfaceThread.start();
        invalidateSurfaceView();
    }

    @Override
    public void surfaceChanged(SurfaceHolder holder, int format, int width, int height) {

    }

    @Override
    public void surfaceDestroyed(SurfaceHolder holder) {
        surfaceThread.running  = false;
        surfaceThread.interrupt();
        try {
            surfaceThread.join();
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
        Log.d("Surface View", "Uspesno unisten surface View");
    }

    public void setModel(CreatePolygonModel model) {
        this.model = model;
    }

    public void setController(CreatePolygonController controller) {
        this.controller = controller;
    }

    // Prevent multitouch
    //
    @Override
    public boolean onTouch(View v, MotionEvent event) {

        float x =   event.getX();
        float y = event.getY();

        Coordinate c = new Coordinate(x, y);

        Log.d("X:", Float.toString(x));
        Log.d("Y:", Float.toString(y));

        switch (event.getAction())
        {
            case MotionEvent.ACTION_DOWN:
                Log.d("CreatePolygonView", " DOWN");
                controller.actionDownExecute(c);
                break;
            case MotionEvent.ACTION_UP:
                Log.d("CreatePolygonView", " UP");
                controller.actionUpExecute(c);
                break;
            case MotionEvent.ACTION_MOVE:
                Log.d("CreatePolygonView", " MOVE");
                controller.actionMoveExecute(c);
                break;
            default:
                return false;
        }

        return true;
    }
}