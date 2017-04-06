package com.example.popina.projekat.application.statistics;

import android.database.Cursor;
import android.os.Bundle;
import android.view.View;
import android.widget.Adapter;
import android.widget.AdapterView;
import android.widget.Button;
import android.widget.ListView;
import android.widget.SimpleCursorAdapter;
import android.widget.Spinner;

import com.example.popina.projekat.R;
import com.example.popina.projekat.application.common.CommonActivity;
import com.example.popina.projekat.application.game.GameModel;
import com.example.popina.projekat.logic.statistics.database.ScoreDatabase;
import com.example.popina.projekat.logic.statistics.database.table.LevelTable;
import com.example.popina.projekat.logic.statistics.database.table.UserScoreTable;

import java.util.logging.Level;

public class StatisticsActivity extends CommonActivity {
    private StatisticsModel model;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_statistics);

        model = new StatisticsModel();
        ScoreDatabase database = new ScoreDatabase(getApplicationContext());
        model.setDatabase(database);
        Bundle extras = getIntent().getExtras();
        String level = null;
        if (null != extras)
        {
            level = extras.getString(GameModel.POLYGON_NAME);
        }

        loadListView(level);
        loadSpinner();


        Button buttonResetCurrent = (Button)findViewById(R.id.buttonResetCurrentHighScore);
        buttonResetCurrent.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                model.getDatabase().deleteHighScore(model.getSelectedLevel());
                loadListView(model.getSelectedLevel());
            }
        });

        Button buttonResetAll = (Button)findViewById(R.id.buttonResetAllHighScore);
        buttonResetAll.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                model.getDatabase().deleteHighScore(null);
                loadListView(model.getSelectedLevel());
            }
        });


    }

    private void loadListView(String selectedLevel)
    {

        final String columnList[] = new String[] {UserScoreTable.TABLE_COLUMN_USER_NAME,
                UserScoreTable.TABLE_COLUMN_TIME,
                UserScoreTable._ID};
        final int viewIdList[] = new int[]{R.id.textViewStatisticsUser,
                R.id.textViewStatisticsTime,
                R.id.textViewStatisticsId};

        Cursor cursor = null;

        cursor = model.getDatabase().queryHighScore(selectedLevel);

        if (selectedLevel == null)
        {
            model.setSelectedLevel(model.getDatabase().getFirstLevel());
        }
        else
        {
            model.setSelectedLevel(selectedLevel);
        }

        ListView listView = (ListView)findViewById(R.id.listViewHighScore);

        SimpleCursorAdapter simpleCursorAdapter =  new SimpleCursorAdapter(this,
                R.layout.list_item_highscore_polygon,
                cursor,
                columnList,
                viewIdList,
                0
        );
        // TODO : VIew binder.
        //
        listView.setAdapter(simpleCursorAdapter);

    }


    private void loadSpinner()
    {
        Spinner spinner = (Spinner)findViewById(R.id.spinnerPolygon);
        Cursor cursor = model.getDatabase().queryLevels();
        final String[] columnFromLevel = new String[]{LevelTable._ID,
                                      LevelTable.TABLE_COLUMN_LEVEL_NAME};
        final int[] idToSpinner = new int[]{ R.id.textViewStatisticsSpinnerId,
                                            R.id.textViewStatisticsSpinnerLevelName
                                        };
        cursor.moveToFirst();
        int idx = 0;
        if (!cursor.isBeforeFirst()) {
            while (!cursor.getString(cursor.getColumnIndex(LevelTable.TABLE_COLUMN_LEVEL_NAME)).equals(model.getSelectedLevel()))
            {
                cursor.moveToNext();
                idx++;
            }
            SimpleCursorAdapter adapter = new SimpleCursorAdapter(
                    getApplicationContext(),
                    R.layout.spinner_item_polygon,
                    cursor,
                    columnFromLevel,
                    idToSpinner,
                    0
            );

            spinner.setAdapter(adapter);
            spinner.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
                @Override
                public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                    Cursor cursor = (Cursor) parent.getItemAtPosition(position);
                    String selectedLevelName = cursor.getString(cursor.getColumnIndex(LevelTable.TABLE_COLUMN_LEVEL_NAME));
                    loadListView(selectedLevelName);
                }

                @Override
                public void onNothingSelected(AdapterView<?> parent) {

                }
            });
            spinner.setSelection(idx);
        }
    }
}
