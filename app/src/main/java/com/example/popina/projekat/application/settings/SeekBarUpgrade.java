package com.example.popina.projekat.application.settings;

import android.app.Activity;
import android.view.View;
import android.view.ViewDebug;
import android.widget.Button;
import android.widget.SeekBar;
import android.widget.TextView;

import com.example.popina.projekat.logic.game.coeficient.Coeficient;

/**
 * Created by popina on 13.03.2017..
 */

public class SeekBarUpgrade {
    private SeekBar seekBar;
    private TextView textView;
    private int scaleCoef;
    private float defaulltVal;
    private float val;
    private Button button;

    public SeekBarUpgrade(final SettingsActivity settingsActivity, int idSeekBar, int idTextView, int idButton,
                          final int scaleCoef, int max, final float defaultVal, float beginVal) {
        this.seekBar = (SeekBar)settingsActivity.findViewById(idSeekBar);
        this.textView = (TextView)settingsActivity.findViewById(idTextView);
        this.button = (Button)settingsActivity.findViewById(idButton);
        this.scaleCoef = scaleCoef;
        this.defaulltVal = defaultVal;
        this.val = beginVal;
        textView.setText(Float.toString(beginVal));

        button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                seekBar.setProgress(prepareForBar(defaultVal));
                textView.setText(Float.toString(defaultVal));
                val = defaultVal;

            }
        });

        seekBar.setMax(max);
        seekBar.setOnSeekBarChangeListener(new SeekBar.OnSeekBarChangeListener() {
            @Override
            public void onProgressChanged(SeekBar seekBar, int progress, boolean fromUser) {
                textView.setText(Float.toString(getFromBar(progress)));
                val = getFromBar(progress);
            }

            @Override
            public void onStartTrackingTouch(SeekBar seekBar) {

            }

            @Override
            public void onStopTrackingTouch(SeekBar seekBar) {

            }
        });
        seekBar.setProgress(prepareForBar(beginVal));
    }

    public int prepareForBar(float val) { return (int) (scaleCoef * val);}
    public float getFromBar(int val) { return ((float)val) / scaleCoef;}

    public float getVal() {
        return val;
    }

    public void setVal(float val) {
        this.val = val;
    }
}
