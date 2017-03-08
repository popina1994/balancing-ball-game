package com.example.popina.projekat.application.create;

import android.app.Dialog;
import android.content.Context;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;

import com.example.popina.projekat.R;
import com.example.popina.projekat.model.shape.figure.Figure;

import java.io.FileOutputStream;
import java.io.IOException;
import java.util.LinkedList;

/**
 * Created by popina on 07.03.2017..
 */

public class SaveDialog extends Dialog {
    private CreatePolygonActivity activity;
    private CreatePolygonModel model;

    public SaveDialog(CreatePolygonActivity activity, CreatePolygonModel model)
    {
        super(activity);
        this.activity = activity;
        this.model = model;
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        initDialog();
    }

    private void initDialog()
    {
        setContentView(R.layout.dialog_save_polygon);

        // In future add check if file exists (maybe).
        //
        Button buttonSave = (Button)findViewById(R.id.buttonDialogSavePolygon);
        buttonSave.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                EditText editTextFileName = (EditText)SaveDialog.this.findViewById(R.id.editTextNewNameOfPolygon);
                String fileName = editTextFileName.getText().toString();
                Log.d("Save dialog", fileName);

                FileOutputStream outputStream = null;

                try {
                    outputStream =  activity.openFileOutput(fileName, Context.MODE_PRIVATE);
                    StringBuilder stringBuilder = new StringBuilder();
                    LinkedList<Figure> listFigure = model.getListFigures();
                    listFigure = model.getShapeFactory().scaleReverseFigure(listFigure);

                    for (Figure it : listFigure)
                    {
                        stringBuilder.append(it.toString() + "\n");
                    }
                    outputStream.write(stringBuilder.toString().getBytes());
                } catch (IOException e) {
                    e.printStackTrace();
                }
                finally {
                    if (null != outputStream)
                    {
                        try {
                            outputStream.close();
                        } catch (IOException e) {
                            Log.d("Save dialog", "Neko te prokleo");
                            e.printStackTrace();
                        }
                    }
                }
                SaveDialog.this.dismiss();


            }
        });
    }
}
