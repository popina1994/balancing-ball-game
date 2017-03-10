package com.example.popina.projekat.application.game;

import android.content.Context;
import android.graphics.Canvas;
import android.util.AttributeSet;
import android.util.Log;
import android.view.MotionEvent;
import android.view.SurfaceHolder;
import android.view.SurfaceView;
import android.view.View;

import com.example.popina.projekat.application.create.CreatePolygonView;
import com.example.popina.projekat.model.shape.ShapeDraw;
import com.example.popina.projekat.model.shape.ShapeFactory;
import com.example.popina.projekat.model.shape.ShapeParser;
import com.example.popina.projekat.model.shape.scale.UtilScaleNormal;

/**
 * Created by popina on 09.03.2017..
 */

public class GameView extends SurfaceView implements SurfaceHolder.Callback, View.OnTouchListener {

    private GameModel model;
    private GameController controller;
    private SurfaceThread surfaceThread = null;

    public GameView(Context context) {
        super(context);
        init();
    }

    public GameView(Context context, AttributeSet attrs) {
        super(context, attrs);
        init();
    }

    public GameView(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        init();
    }

    public GameView(Context context, AttributeSet attrs, int defStyleAttr, int defStyleRes) {
        super(context, attrs, defStyleAttr, defStyleRes);
        init();
    }

    private void init()
    {
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
            SurfaceHolder surfaceHolder = GameView.this.getHolder();
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
                        GameView.this.renderSurfaceView(canvas);
                    } finally {
                        surfaceHolder.unlockCanvasAndPost(canvas);
                    }
                }


            }
        }
    }

    private void renderSurfaceView(Canvas canvas) {
        model.getShapeDraw().drawOnCanvas(model.getListFigures(), canvas);
    }

    @Override
    public void surfaceCreated(SurfaceHolder holder) {
        Canvas canvas = holder.lockCanvas();
        int height = canvas.getHeight();
        int width = canvas.getWidth();

        UtilScaleNormal utilScaleNormal = new UtilScaleNormal(width, height);

        ShapeFactory shapeFactory = new ShapeFactory(utilScaleNormal);
        model.setShapeFactory(shapeFactory);

        ShapeDraw shapeDraw = new ShapeDraw(getContext());
        // Only used for synchronization.
        //
        //shapeDraw.setModel(model);
        model.setShapeDraw(shapeDraw);

        ShapeParser shapeParser = new ShapeParser(shapeFactory, shapeDraw, getContext());
        model.setShapeParser(shapeParser);

        model.setListFigures(shapeParser.parseFile(model.getFileName()));

        //shapeParser.pa

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

    @Override
    public boolean onTouch(View v, MotionEvent event) {
        return false;
    }

    public void setModel(GameModel model) {
        this.model = model;
    }

    public void setController(GameController controller) {
        this.controller = controller;
    }
}
