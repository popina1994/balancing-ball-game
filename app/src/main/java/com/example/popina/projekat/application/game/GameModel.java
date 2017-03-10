package com.example.popina.projekat.application.game;

import com.example.popina.projekat.application.common.CommonModel;
import com.example.popina.projekat.model.shape.figure.Figure;

import java.util.LinkedList;

/**
 * Created by popina on 09.03.2017..
 */

public class GameModel extends CommonModel {

    private LinkedList<Figure> listFigures = new LinkedList<>();
    private String fileName;

    public LinkedList<Figure> getListFigures() {
        return listFigures;
    }

    public void setListFigures(LinkedList<Figure> listFigures) {
        this.listFigures = listFigures;
    }

    public String getFileName() {
        return fileName;
    }

    public void setFileName(String fileName) {
        this.fileName = fileName;
    }
}
