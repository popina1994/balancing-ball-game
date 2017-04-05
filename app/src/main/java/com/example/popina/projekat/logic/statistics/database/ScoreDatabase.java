package com.example.popina.projekat.logic.statistics.database;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.util.Log;

import com.example.popina.projekat.logic.statistics.database.table.LevelTable;
import com.example.popina.projekat.logic.statistics.database.table.UserScoreTable;

/**
 * Created by popina on 05.04.2017..
 */

public class ScoreDatabase {
    public static final  int OK = 0x0;
    public static final int NO_LEVEL = 0x1;
    public static final int INSERT_ERROR = 0x2;

    ScoreDatabaseHelper databaseHelper;

    public String getFirstLevel()
    {
        SQLiteDatabase database = databaseHelper.getReadableDatabase();
        String levelName = null;

        Cursor cursor = null;
        try {
            cursor = database.query(
                    LevelTable.TABLE_NAME,
                    new String[]{LevelTable._ID, LevelTable.TABLE_COLUMN_LEVEL_NAME},
                    null,
                    null,
                    null,
                    null,
                    null
            );
            cursor.moveToFirst();
            levelName = cursor.getString(cursor.getColumnIndex(LevelTable.TABLE_COLUMN_LEVEL_NAME));
        }
        catch (Exception e)
        {
            e.printStackTrace();
        }
        finally {
            cursor.close();
        }
        return levelName;

    }

    private int getIdLevelName(SQLiteDatabase database, String levelName)
    {
        int id = -1;

        String selectionArg = null;

        if (levelName != null)
        {
            selectionArg = LevelTable.TABLE_COLUMN_LEVEL_NAME + "=\"" + levelName +"\"";
        }
        else
        {
            selectionArg = "";
        }

        Cursor cursor = null;
        try {
            cursor = database.query(
                    LevelTable.TABLE_NAME,
                    new String[]{LevelTable._ID, LevelTable.TABLE_COLUMN_LEVEL_NAME},
                    selectionArg,
                    null,
                    null,
                    null,
                    null
            );
            if (!cursor.moveToFirst())
            {
                Log.d("ScoreDatabase", "Error with currsor insert user");
                return id;
            }
            cursor.moveToFirst();
            id = cursor.getInt(cursor.getColumnIndex(LevelTable._ID));
        }
        catch (Exception e)
        {
            e.printStackTrace();
        }
        finally {
            cursor.close();
        }
        return id;
    }

    public ScoreDatabase(Context context) {
        databaseHelper = new ScoreDatabaseHelper(context);
    }

    public int insertUser(String user, String levelName, long time)
    {
        SQLiteDatabase database = databaseHelper.getWritableDatabase();

        ContentValues values = new ContentValues();
        String z = levelName;
        values.put(UserScoreTable.TABLE_COLUMN_USER_NAME, user);
        values.put(UserScoreTable.TABLE_COLUMN_TIME, time);

        int id = getIdLevelName(database, levelName);

        if (-1 == id)
        {
            return  NO_LEVEL;
        }

        values.put(UserScoreTable.TABLE_COLUMN_FK_LEVEL, id);

        if (-1 == database.insert(UserScoreTable.TABLE_NAME, null, values))
        {
            return INSERT_ERROR;
        }
        return OK;
    }

    public int insertLevel(String levelName)
    {
        SQLiteDatabase database = databaseHelper.getWritableDatabase();

        ContentValues values = new ContentValues();
        values.put(LevelTable.TABLE_COLUMN_LEVEL_NAME, levelName);

        if (-1 == database.insert(LevelTable.TABLE_NAME, null, values))
        {
            return  INSERT_ERROR;
        }

        return OK;
    }

    public  Cursor queryHighScore(String levelName)
    {
        SQLiteDatabase database = databaseHelper.getReadableDatabase();
        int id = getIdLevelName(database, levelName);

        Cursor cursor = null;
        try {
            cursor = database.query(
                    UserScoreTable.TABLE_NAME,
                    new String[]{UserScoreTable.TABLE_COLUMN_USER_NAME,
                                 UserScoreTable.TABLE_COLUMN_TIME,
                                 UserScoreTable._ID},
                    UserScoreTable.TABLE_COLUMN_FK_LEVEL + " = " + id,
                    null,
                    null,
                    null,
                    UserScoreTable.TABLE_COLUMN_TIME + " ASC"

            );
        }
        catch (Exception e)
        {
            e.printStackTrace();
        }
        return cursor;

    }

    public Cursor queryLevels() {
        SQLiteDatabase database = databaseHelper.getReadableDatabase();

        Cursor cursor = null;
        try {
            cursor = database.query(
                    LevelTable.TABLE_NAME,
                    new String[]{LevelTable._ID, LevelTable.TABLE_COLUMN_LEVEL_NAME},
                    null,
                    null,
                    null,
                    null,
                    null
            );
        }
        catch (Exception e)
        {
            e.printStackTrace();
        }
        return cursor;
    }

    public int deleteHighScore(String level)
    {
        SQLiteDatabase database = databaseHelper.getWritableDatabase();
        String selectionArg = null;

        if (null == level)
        {
            selectionArg  = null;
        }
        else
        {
            int id = getIdLevelName(database, level);
            selectionArg = UserScoreTable.TABLE_COLUMN_FK_LEVEL +"=" + id;
        }

        // TODO : Add more checks
        //
        database.delete(UserScoreTable.TABLE_NAME,
                        selectionArg,
                        null);
        return OK;
    }
}
