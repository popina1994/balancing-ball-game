package com.example.popina.projekat.application.game;

import android.app.Activity;
import android.app.Dialog;
import android.content.Intent;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;

import com.example.popina.projekat.R;
import com.example.popina.projekat.application.game.model.GameModel;
import com.example.popina.projekat.application.statistics.StatisticsActivity;
import com.example.popina.projekat.logic.game.utility.Utility;
import com.example.popina.projekat.logic.statistics.database.GameDatabase;

/**
 * Created by popina on 04.04.2017..
 */

public class GameOverDialog extends Dialog
{
    private Activity activity;

    public GameOverDialog(final Activity activity, long time, String levelName)
    {
        super(activity);
        this.activity = activity;
        setContentView(R.layout.dialog_game_over);
        setCancelable(false);

        final EditText editTextPlayerName = (EditText) findViewById(R.id.editTextPlayerName);
        editTextPlayerName.setOnClickListener(new View.OnClickListener()
        {
            boolean deleted = false;
            @Override
            public void onClick(View v)
            {
                if (!deleted)
                {
                    deleted = true;
                    editTextPlayerName.setText("");
                }
            }
        });
        String userName = editTextPlayerName.getText().toString();

        TextView textViewTime = (TextView) findViewById(R.id.textViewPlayerTime);
        textViewTime.setText("Vase vreme: " + Float.toString(Utility.convertMsToS(time)) + " sekundi");

        Button button = (Button) findViewById(R.id.buttonSaveResult);
        button.setOnClickListener(new ButtonSaveOnClickListener(editTextPlayerName, time, levelName));


    }

    class ButtonSaveOnClickListener implements View.OnClickListener
    {
        private EditText editText;
        private long time;
        private String levelName;

        public ButtonSaveOnClickListener(EditText editText, long time, String levelName)
        {
            this.editText = editText;
            this.time = time;
            this.levelName = levelName;
        }

        @Override
        public void onClick(View v)
        {
            GameDatabase database = new GameDatabase(activity.getApplicationContext());
            database.insertUser(editText.getText().toString(), levelName, time);
            // Memory leakage bug.
            //
            GameOverDialog.this.dismiss();
            Intent intent = new Intent(activity, StatisticsActivity.class);
            intent.setFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
            intent.putExtra(GameModel.POLYGON_NAME, levelName);
            activity.startActivity(intent);
            activity.finish();
        }
    }
}
