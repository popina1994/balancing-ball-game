package com.example.popina.projekat.logic.statistics.database;

import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

import com.example.popina.projekat.logic.statistics.database.table.LevelTable;
import com.example.popina.projekat.logic.statistics.database.table.UserScoreTable;

/**
 * Created by popina on 05.04.2017..
 */

public class ScoreDatabaseHelper extends SQLiteOpenHelper
{

    public static final String DATABASE_NAME = "scores.db";
    public static final int DATABASE_VERSION = 1;

    public ScoreDatabaseHelper(Context context)
    {
        super(context, DATABASE_NAME, null, DATABASE_VERSION);
    }

    @Override
    public void onCreate(SQLiteDatabase db)
    {
        db.execSQL(LevelTable.TABLE_CREATE);
        db.execSQL(UserScoreTable.TABLE_CREATE);
    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion)
    {
        db.execSQL(UserScoreTable.TABLE_DROP);
        db.execSQL(LevelTable.TABLE_DROP);
        onCreate(db);

    }
}
