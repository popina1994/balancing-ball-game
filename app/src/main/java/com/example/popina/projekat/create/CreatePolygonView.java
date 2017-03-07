package com.example.popina.projekat.create;

import android.content.Context;
import android.content.res.Resources;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.graphics.PorterDuff;
import android.util.AttributeSet;
import android.util.Log;
import android.view.MotionEvent;
import android.view.SurfaceView;
import android.view.SurfaceHolder;
import android.view.View;
import android.widget.ImageView;

import com.example.popina.projekat.R;
import com.example.popina.projekat.create.shape.Coordinate;
import com.example.popina.projekat.create.shape.Figure;

import java.util.LinkedList;

/**
 * Created by popina on 04.03.2017..
 */

public class CreatePolygonView extends ImageView implements View.OnTouchListener{

    private CreatePolygonModel model;
    private CreatePolygonController controller;
    //private SurfaceThread surfaceThread = null;



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
        //SurfaceHolder surfaceHolder = getHolder();
        //surfaceHolder.addCallback(this);
    }


    public void invalidateSurfaceView()
    {
        invalidate();
        //surfaceThread.interrupt();
    }
/*
    public class SurfaceThread extends Thread {
        private boolean running = true;
        private int jobCnt = 0;

        @Override
        public void run() {
            //SurfaceHolder surfaceHolder = CreatePolygonView.this.getHolder();
            while (running)
            {
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
*/
    private void renderSurfaceView(Canvas canvas) {

        Resources resources = getContext().getResources();


        // Clear canvas of all drawings.
        //
        canvas.drawColor(Color.TRANSPARENT, PorterDuff.Mode.CLEAR);

        Bitmap background = BitmapFactory.decodeResource(resources, R.drawable.background);
        canvas.drawBitmap(background, 0, 0, null);

        //synchronized (model) {
            LinkedList<Figure> listFigures = model.getListFigures();
            for (Figure it : listFigures) {
                it.drawOnCanvas(canvas);
            }
        //}
    }

    @Override
    protected void onSizeChanged(int w, int h, int oldw, int oldh) {
        //super.onSizeChanged(w, h, oldw, oldh);
        model.setWidth(w);
        model.setHeight(h);
        UtilScale.setScreenWidth(w);
        UtilScale.setScreenHeight(h);
        setOnTouchListener(this);
        invalidateSurfaceView();
    }

    /*
        @Override
        public void surfaceCreated(SurfaceHolder holder) {
            Log.d("Surface View", "Created surface");

            Canvas canvas = holder.lockCanvas();
            int height = canvas.getHeight();
            int width = canvas.getWidth();
            UtilScale.screenHeight = height;
            UtilScale.screenWidth = width;
            holder.unlockCanvasAndPost(canvas);

            model.setHeight(height);
            model.setWidth(width);

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
        }
        */
    public void setModel(CreatePolygonModel model) {
        this.model = model;
    }

    public void setController(CreatePolygonController controller) {
        this.controller = controller;
    }

    Resources resources = getContext().getResources();
    Bitmap background = BitmapFactory.decodeResource(resources, R.drawable.background);
    @Override
    protected void onDraw(Canvas canvas) {
        Resources resources = getContext().getResources();


        // Clear canvas of all drawings.
        //
        canvas.drawColor(Color.TRANSPARENT, PorterDuff.Mode.CLEAR);
        //Bitmap background = BitmapFactory.decodeResource(resources, R.drawable.background);
        canvas.drawBitmap(background, 0, 0, null);

        synchronized (model) {
            LinkedList<Figure> listFigures = model.getListFigures();
            for (Figure it : listFigures) {
                it.drawOnCanvas(canvas);
            }
        }
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
