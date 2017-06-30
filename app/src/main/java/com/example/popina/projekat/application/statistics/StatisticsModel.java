package com.example.popina.projekat.application.statistics;

import com.example.popina.projekat.logic.statistics.database.GameDatabase;

/**
 * Created by popina on 05.04.2017..
 */

public class StatisticsModel
{

    private GameDatabase database;
    private String selectedLevel;

    public GameDatabase getDatabase()
    {
        return database;
    }

    public void setDatabase(GameDatabase database)
    {
        this.database = database;
    }

    public String getSelectedLevel()
    {
        return selectedLevel;
    }

    public void setSelectedLevel(String selectedLevel)
    {
        this.selectedLevel = selectedLevel;
    }
}
