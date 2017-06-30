package com.example.popina.projekat.logic.statistics.database.table;

import android.provider.BaseColumns;

/**
 * Created by popina on 05.04.2017..
 */

public class LevelTable implements BaseColumns
{
    // TODO : levels of different difficulty.
    //
    public static final String TABLE_NAME = "level";
    public static final String TABLE_COLUMN_LEVEL_NAME = "name";
    public static final String TABLE_COLUMN_LEVEL_DIFFICULTY = "difficulty";

    public static final String TABLE_CREATE = "CREATE TABLE "
            + TABLE_NAME + "(" + _ID + " INTEGER PRIMARY KEY, " +
            TABLE_COLUMN_LEVEL_NAME + " TEXT UNIQUE, " +
            TABLE_COLUMN_LEVEL_DIFFICULTY +" INTEGER );";
    public static final String TABLE_DROP = "DROP TABLE IF EXISTS " + TABLE_NAME + ";";
}
