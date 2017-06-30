package com.example.popina.projekat.application.create;

import android.app.Dialog;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.RatingBar;

import com.example.popina.projekat.R;

/**
 * Created by popina on 07.03.2017..
 */

public class SaveDialog extends Dialog
{
    private CreatePolygonActivity activity;
    private CreatePolygonModel model;
    private CreatePolygonController controller;

    public SaveDialog(CreatePolygonActivity activity, CreatePolygonController controller, CreatePolygonModel model)
    {
        super(activity);
        this.activity = activity;
        this.model = model;
        this.controller = controller;
    }

    @Override
    protected void onCreate(Bundle savedInstanceState)
    {
        super.onCreate(savedInstanceState);
        initDialog();
    }

    private void initDialog()
    {
        setContentView(R.layout.dialog_save_polygon);
        if (model.isEditMode())
        {
            EditText editTextFileName = (EditText) findViewById(R.id.editTextNewNameOfPolygon);
            RatingBar ratingBarDifficulty =  (RatingBar)findViewById(R.id.ratingBarDifficulty);

            editTextFileName.setText(model.getFileName());
            ratingBarDifficulty.setRating(model.getLevelDifficulty());
        }

        // TODO: Check if file exists.
        //
        Button buttonSave = (Button) findViewById(R.id.buttonDialogSavePolygon);
        buttonSave.setOnClickListener(new View.OnClickListener()
        {
            @Override
            public void onClick(View v)
            {
                EditText editTextFileName = (EditText) SaveDialog.this.findViewById(R.id.editTextNewNameOfPolygon);
                RatingBar ratingBarDifficulty = (RatingBar)SaveDialog.this.findViewById(R.id.ratingBarDifficulty);
                String fileName = editTextFileName.getText().toString();
                model.setFileName(fileName);
                model.setLevelDifficulty((int)ratingBarDifficulty.getRating());

                controller.savePolygonInFileAndDB();
                SaveDialog.this.dismiss();


            }
        });
    }
}
