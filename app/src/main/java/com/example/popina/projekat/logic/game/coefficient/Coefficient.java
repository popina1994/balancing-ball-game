package com.example.popina.projekat.logic.game.coefficient;

import android.app.Activity;
import android.content.Context;
import android.content.SharedPreferences;
import android.util.TypedValue;

import com.example.popina.projekat.R;

/**
 * Created by popina on 12.03.2017..
 */

public class Coefficient
{
    private float scaleAccDefault;
    private float miDefault;
    private float reverseSlowDownDefault;
    private float scaleAcc;
    private float mi;
    private float reverseSlowDown;
    private Activity activity;

    public Coefficient(Activity activity)
    {
        this.activity = activity;

        readDefaultValues();
        readValues();
    }

    private void readValues()
    {
        SharedPreferences sharedPreferences = activity.getApplicationContext().getSharedPreferences(CoefficientModel.PREFERENCE_SETTINGS, Context.MODE_PRIVATE);
        scaleAcc = sharedPreferences.getFloat(CoefficientModel.ACCELERATION_COEFICIENT, scaleAccDefault);
        reverseSlowDown = sharedPreferences.getFloat(CoefficientModel.REVERSE_DLOW_DOWN_DEFAULT, reverseSlowDownDefault);
        mi = sharedPreferences.getFloat(CoefficientModel.MI_COEFICIENT, miDefault);
    }

    private float readFloatValue(int id)
    {
        TypedValue outValue = new TypedValue();
        activity.getResources().getValue(id, outValue, true);
        float value = outValue.getFloat();
        return value;
    }

    private void readDefaultValues()
    {
        scaleAccDefault = readFloatValue(R.dimen.ACCELERATION_COEFICIENT_DEFAULT);
        miDefault = readFloatValue(R.dimen.MI_COEFICIENT_DEFAULT);
        reverseSlowDownDefault = readFloatValue(R.dimen.REVERSE_SLOW_DOWN_COEFICIENT_DEFAULT);
    }

    public void updateValues()
    {
        SharedPreferences sharedPref = activity.getApplicationContext().getSharedPreferences(CoefficientModel.PREFERENCE_SETTINGS, Context.MODE_PRIVATE);
        SharedPreferences.Editor editor = sharedPref.edit();
        editor.putFloat(CoefficientModel.ACCELERATION_COEFICIENT, scaleAcc);
        editor.putFloat(CoefficientModel.MI_COEFICIENT, mi);
        editor.putFloat(CoefficientModel.REVERSE_DLOW_DOWN_DEFAULT, reverseSlowDown);
        editor.commit();
    }

    public float getScaleAcc()
    {
        return scaleAcc;
    }

    public void setScaleAcc(float scaleAcc)
    {
        this.scaleAcc = scaleAcc;
    }

    public float getMi()
    {
        return mi;
    }

    public void setMi(float mi)
    {
        this.mi = mi;
    }

    public float getReverseSlowDown()
    {
        return reverseSlowDown;
    }

    public void setReverseSlowDown(float reverseSlowDown)
    {
        this.reverseSlowDown = reverseSlowDown;
    }

    public float getScaleAccDefault()
    {
        return scaleAccDefault;
    }

    public void setScaleAccDefault(float scaleAccDefault)
    {
        this.scaleAccDefault = scaleAccDefault;
    }

    public float getMiDefault()
    {
        return miDefault;
    }

    public void setMiDefault(float miDefault)
    {
        this.miDefault = miDefault;
    }

    public float getReverseSlowDownDefault()
    {
        return reverseSlowDownDefault;
    }

    public void setReverseSlowDownDefault(float reverseSlowDownDefault)
    {
        this.reverseSlowDownDefault = reverseSlowDownDefault;
    }
}
