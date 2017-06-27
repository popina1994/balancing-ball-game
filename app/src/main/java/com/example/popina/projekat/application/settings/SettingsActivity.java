package com.example.popina.projekat.application.settings;

import android.os.Bundle;
import android.widget.SeekBar;
import android.widget.TextView;

import com.example.popina.projekat.R;
import com.example.popina.projekat.application.common.CommonActivity;
import com.example.popina.projekat.logic.game.coefficient.Coefficient;

public class SettingsActivity extends CommonActivity
{

    // Some kind of Shared model.
    // Better design can be done.
    //
    Coefficient coefficient;

    TextView textViewAcc;
    SeekBar seekBarAcc;
    SeekBarUpgrade seekBarUpgradeAcc;
    SeekBarUpgrade seekBarUpgradeMi;
    SeekBarUpgrade seekBarUpgradeReverseSlowDown;

    @Override
    protected void onCreate(Bundle savedInstanceState)
    {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_settings);

        coefficient = new Coefficient(this);
        // Idea for future.
        // Some interface.
        //

        seekBarUpgradeAcc = new SeekBarUpgrade(
                this,
                R.id.seekBarCoeficientAcceleration,
                R.id.textViewCoeficientAcceleration,
                R.id.buttonResetCoeficientAcceleration,
                SettingsModel.SCALE_PROGRES_BAR_ACC,
                SettingsModel.MAX_SEEK_BAR_ACC,
                coefficient.getScaleAccDefault(),
                coefficient.getScaleAcc()
        );
        seekBarUpgradeMi = new SeekBarUpgrade(
                this,
                R.id.seekBarCoeficientMi,
                R.id.textViewCoeficientMi,
                R.id.buttonResetCoeficientMi,
                SettingsModel.SCALE_PROGRES_BAR_MI,
                SettingsModel.MAX_SEEK_BAR_MI,
                coefficient.getMiDefault(),
                coefficient.getMi()
        );
        seekBarUpgradeReverseSlowDown = new SeekBarUpgrade(
                this,
                R.id.seekBarCoeficientReverseSlowDown,
                R.id.textViewCoeficientReverseSlowDown,
                R.id.buttonResetCoeficientReverseSlowDown,
                SettingsModel.SCALE_PROGRES_BAR_REVERSE_SLOW_DOWN,
                SettingsModel.MAX_SEEK_BAR_REVERSE_SLOW_DOWN,
                coefficient.getReverseSlowDownDefault(),
                coefficient.getReverseSlowDown()
        );
    }

    @Override
    public void onBackPressed()
    {
        super.onBackPressed();
        coefficient.setScaleAcc(seekBarUpgradeAcc.getVal());
        coefficient.setMi(seekBarUpgradeMi.getVal());
        coefficient.setReverseSlowDown(seekBarUpgradeReverseSlowDown.getVal());
        coefficient.updateValues();
    }
}
