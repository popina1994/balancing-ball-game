package com.example.popina.projekat.application.game;

import android.content.Context;
import android.graphics.Canvas;
import android.util.AttributeSet;
import android.util.Log;
import android.view.SurfaceHolder;
import android.view.SurfaceView;

import com.example.popina.projekat.logic.shape.draw.ShapeDraw;
import com.example.popina.projekat.logic.shape.factory.ShapeFactory;
import com.example.popina.projekat.logic.shape.figure.Figure;
import com.example.popina.projekat.logic.shape.figure.hole.CircleHole;
import com.example.popina.projekat.logic.shape.figure.hole.StartHole;
import com.example.popina.projekat.logic.shape.parser.ShapeParser;
import com.example.popina.projekat.logic.shape.scale.UtilScaleNormal;

import java.util.LinkedList;

/**
 * Created by popina on 09.03.2017..
 */

public class GameView extends SurfaceView implements SurfaceHolder.Callback
{

    private GameModel model;
    private GameController controller;
    private SurfaceThread surfaceThread = null;

    public GameView(Context context)
    {
        super(context);
        init();
    }

    public GameView(Context context, AttributeSet attrs)
    {
        super(context, attrs);
        init();
    }

    public GameView(Context context, AttributeSet attrs, int defStyleAttr)
    {
        super(context, attrs, defStyleAttr);
        init();
    }

    public GameView(Context context, AttributeSet attrs, int defStyleAttr, int defStyleRes)
    {
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
        if (null != surfaceThread)
        {
            surfaceThread.interrupt();
        }

    }

    private void renderSurfaceView(Canvas canvas)
    {
        model.getShapeDraw().drawOnCanvas(model.getBall(), canvas);
    }

    @Override
    public void surfaceCreated(SurfaceHolder holder)
    {
        Canvas canvas = holder.lockCanvas();
        int height = canvas.getHeight();
        int width = canvas.getWidth();

        model.setWidth(width);
        model.setHeight(height);
        UtilScaleNormal utilScaleNormal = new UtilScaleNormal(width, height);

        ShapeFactory shapeFactory = new ShapeFactory(utilScaleNormal);
        model.setShapeFactory(shapeFactory);

        ShapeDraw shapeDraw = new ShapeDraw(getContext(), width, height);
        // Only used for synchronization.
        //
        model.setShapeDraw(shapeDraw);

        if ((null == model.getBall()) || (model.isPaused()))
        {
            ShapeParser shapeParser = new ShapeParser(shapeFactory, shapeDraw, getContext());
            model.setShapeParser(shapeParser);

            LinkedList<Figure> listFigures = shapeParser.parseFile(model.getFileName());
            for (Figure it : listFigures)
            {

                if (it instanceof StartHole)
                {
                    if (null == model.getBall())
                    {
                        model.setBall((CircleHole) it);
                    }
                    listFigures.remove(it);
                    break;
                }
            }


            model.setListFigures(listFigures);

            // Split ball from other figures.
            //
            shapeDraw.spriteOnBackground(model.getListFigures());
            controller.resume();
        }
        holder.unlockCanvasAndPost(canvas);

        surfaceThread = new SurfaceThread();
        surfaceThread.start();
        model.setSufraceInitialized(true);
        invalidateSurfaceView();
    }

    @Override
    public void surfaceChanged(SurfaceHolder holder, int format, int width, int height)
    {

    }

    @Override
    public void surfaceDestroyed(SurfaceHolder holder)
    {
        model.setSufraceInitialized(false);
        surfaceThread.running = false;
        surfaceThread.interrupt();
        try
        {
            surfaceThread.join();
        }
        catch (InterruptedException e)
        {
            e.printStackTrace();
        }
        Log.d("Surface View", "Uspesno unisten surface View");
    }

    public void setController(GameController controller)
    {
        this.controller = controller;
    }

    public GameModel getModel()
    {
        return model;
    }

    public void setModel(GameModel model)
    {
        this.model = model;
    }

    public class SurfaceThread extends Thread
    {
        private boolean running = true;

        @Override
        public void run()
        {
            SurfaceHolder surfaceHolder = GameView.this.getHolder();
            while (running)
            {
                try
                {
                    while (!interrupted())
                    {
                        sleep(100000000);
                    }
                }
                catch (InterruptedException e)
                {

                }
                if (running)
                {
                    //Log.d("Sufrace view", "Drawing");
                    Canvas canvas = surfaceHolder.lockCanvas();
                    try
                    {
                        GameView.this.renderSurfaceView(canvas);
                    }
                    finally
                    {
                        surfaceHolder.unlockCanvasAndPost(canvas);
                    }
                }
            }
        }
    }
}
