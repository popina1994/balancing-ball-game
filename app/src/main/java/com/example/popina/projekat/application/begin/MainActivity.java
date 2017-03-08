package com.example.popina.projekat.application.begin;

import android.app.Activity;
import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.Canvas;
import android.graphics.Point;
import android.os.Bundle;
import android.util.Log;
import android.view.ContextMenu;
import android.view.Display;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.SimpleAdapter;
import android.widget.TextView;
import android.widget.Toast;

import com.example.popina.projekat.R;
import com.example.popina.projekat.application.Model;
import com.example.popina.projekat.application.statistics.StatisticsActivity;
import com.example.popina.projekat.application.create.CreatePolygonActivity;
import com.example.popina.projekat.application.settings.SettingsActivity;
import com.example.popina.projekat.model.shape.ShapeDraw;
import com.example.popina.projekat.model.shape.ShapeFactory;
import com.example.popina.projekat.model.shape.ShapeParser;
import com.example.popina.projekat.model.shape.scale.UtilScaleNormal;

import java.io.File;
import java.io.FilenameFilter;
import java.util.HashMap;
import java.util.LinkedList;
import java.util.List;

public class MainActivity extends Activity {

    private static final String TAG = "BEGIN_ACTIVITY";
    private  MainModel model;
    private String[] createdPolygons = null;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        model = new MainModel();
        init();
        loadList();
    }

    private void init(){
        Display display = getWindowManager().getDefaultDisplay();
        Point size = new Point();
        display.getSize(size);
        float width = size.x * MainModel.SCALE_SCREEN_WIDTH;
        float height = size.y * MainModel.SCALE_SCREEN_HEIGHT;

        UtilScaleNormal utilScaleNormal = new UtilScaleNormal(width, height);
        ShapeFactory shapeFactory = new ShapeFactory(utilScaleNormal);
        model.setShapeFactory(shapeFactory);

        ShapeDraw shapeDraw = new ShapeDraw(this);
        model.setShapeDraw(shapeDraw);
        ShapeParser shapeParser = new ShapeParser(shapeFactory, shapeDraw, this);
        model.setShapeParser(shapeParser);
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        MenuInflater inflater = getMenuInflater();
        inflater.inflate(R.menu.menu_main, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        Intent intent = null;

        Class <?> classStart = null;
        int requestCode = 0;
        switch (item.getItemId()) {
            case R.id.menuItemCreatePoligon:
                classStart = CreatePolygonActivity.class;
                requestCode = MainModel.REQUEST_CODE_CREATE_POLYGON;
                break;
            case R.id.menuItemStatistics:
                classStart = StatisticsActivity.class;
                requestCode = MainModel.REQUEST_CODE_STATISTIC;
                break;
            case R.id.menuItemSettings:
                classStart = SettingsActivity.class;
                requestCode = MainModel.REQUEST_CODE_SETTINGS;
                break;
            default:
                return super.onOptionsItemSelected(item);
        }
        intent  = new Intent(this, classStart);
        startActivityForResult(intent, requestCode);
        return true;
    }


    private void loadList()
    {
        ListView listView = (ListView)findViewById(R.id.listViewPolygons);

        String [] from = new String[]{MainModel.POLYGON_NAME, MainModel.POLYGON_IMAGE};
        int [] to = new int[] {R.id.textViewListItemPolygonName, R.id.imageViewPolygon};

        createdPolygons = getFilesDir().list(new FilenameFilter() {
            @Override
            public boolean accept(File dir, String name) {
                if (!name.equals(MainModel.INSTANT_RUN))
                {
                    return  true;
                }
                return false;
            }
        });

        List<HashMap<String, String>> data = new LinkedList<>();
        for (String fileName : createdPolygons)
        {
            HashMap<String, String> hashMap = new HashMap<>();
            hashMap.put(MainModel.POLYGON_NAME, fileName);
            hashMap.put(MainModel.POLYGON_IMAGE, fileName);
            data.add(hashMap);
        }

        SimpleAdapter adapter = new SimpleAdapter(
                getApplicationContext(),
                data,
                R.layout.list_item_polygon,
                from,
                to
        );

        adapter.setViewBinder(new SimpleAdapter.ViewBinder() {
            @Override
            public boolean setViewValue(View view, Object data, String textRepresentation) {
                if (view.getId() == R.id.imageViewPolygon) {
                    ImageView imageViewPolygon = (ImageView)view;

                    Bitmap bmp = Bitmap.createBitmap(
                            (int)model.getShapeFactory().getUtilScale().getScreenWidth(),
                            (int)model.getShapeFactory().getUtilScale().getScreenHeight(),
                            Bitmap.Config.ARGB_8888);
                    Canvas canvas = new Canvas(bmp);
                    model.getShapeParser().drawImageFromFile(canvas, (String)data);
                    imageViewPolygon.setImageBitmap(bmp);
                    return  true;
                }

                return false;
            }
        });

        listView.setAdapter(adapter);
        registerForContextMenu(listView);
    }

    @Override
    public void onCreateContextMenu(ContextMenu menu, View v, ContextMenu.ContextMenuInfo menuInfo) {
        super.onCreateContextMenu(menu, v, menuInfo);
        menu.setHeaderTitle("Izaberite opciju");
        menu.add(0, v.getId(), 0, MainModel.SELECT_DELETE);
    }

    @Override
    public boolean onContextItemSelected(MenuItem item) {
        AdapterView.AdapterContextMenuInfo menuInfo = (AdapterView.AdapterContextMenuInfo) item.getMenuInfo();
        int id = (int)menuInfo.id;
        if (item.getTitle().equals(MainModel.SELECT_DELETE))
        {
            File file = new File(getFilesDir(), createdPolygons[id]);
            boolean delted = file.delete();
            Log.d("Main Activity", Boolean.toString(delted));
            Toast.makeText(getApplicationContext(),"Izbrisan poilgon" + createdPolygons[id], Toast.LENGTH_LONG).show();
            init();
            loadList();
            return false;
        }
        return  true;
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        switch (requestCode)
        {
            case MainModel.REQUEST_CODE_CREATE_POLYGON:
                if (resultCode == RESULT_OK)
                {
                    init();
                    loadList();
                }
        }
    }
}