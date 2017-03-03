package com.example.popina.projekat.begin;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;

import com.example.popina.projekat.R;
import com.example.popina.projekat.statistics.Statistics;
import com.example.popina.projekat.create.CreatePolygon;
import com.example.popina.projekat.settings.Settings;

public class MainActivity extends Activity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
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
        switch (item.getItemId()) {
            case R.id.menuItemCreatePoligon:
                classStart = CreatePolygon.class;
                break;
            case R.id.menuItemStatistics:
                classStart = Statistics.class;
                break;
            case R.id.menuItemSettings:
                classStart = Settings.class;
                break;
            default:
                return super.onOptionsItemSelected(item);
        }
        intent  = new Intent(this, classStart);
        startActivity(intent);
        return true;
    }
}
