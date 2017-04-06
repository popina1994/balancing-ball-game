package com.example.popina.projekat.logic.statistics.database.table;

import android.provider.BaseColumns;

/**
 * Created by popina on 05.04.2017..
 */

public class UserScoreTable implements BaseColumns{

    public static final String TABLE_NAME = "user_score";
    public static final String TABLE_COLUMN_USER_NAME = "user_name";
    public static final String TABLE_COLUMN_TIME = "time";
    public static final String TABLE_COLUMN_FK_LEVEL = "level_id";

    public static final String TABLE_CREATE = "CREATE TABLE "
            + TABLE_NAME + "(" + _ID + " INTEGER PRIMARY KEY, "
            + TABLE_COLUMN_USER_NAME + " TEXT,"
            + TABLE_COLUMN_TIME + " INTEGER, "
            + TABLE_COLUMN_FK_LEVEL + " INTEGER REFERENCES "
            + LevelTable.TABLE_NAME +"(" + _ID + ")"
            + " ON UPDATE CASCADE ON DELETE CASCADE);";


    public static final String TABLE_DROP = "DROP TABLE IF EXISTS " + TABLE_NAME +";";
}
