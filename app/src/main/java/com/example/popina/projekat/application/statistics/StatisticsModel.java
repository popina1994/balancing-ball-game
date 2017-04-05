package com.example.popina.projekat.application.statistics;

import com.example.popina.projekat.logic.statistics.database.ScoreDatabase;

/**
 * Created by popina on 05.04.2017..
 */

public class StatisticsModel {

    private  ScoreDatabase database;
    private String selectedLevel;

    public ScoreDatabase getDatabase() {
        return database;
    }

    public void setDatabase(ScoreDatabase database) {
        this.database = database;
    }

    public String getSelectedLevel() {
        return selectedLevel;
    }

    public void setSelectedLevel(String selectedLevel) {
        this.selectedLevel = selectedLevel;
    }
}
