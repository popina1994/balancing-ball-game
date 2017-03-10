package com.example.popina.projekat.application.common;

import android.app.Activity;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.view.View;
import android.view.Window;
import android.view.WindowManager;

/**
 * Created by popina on 10.03.2017..
 */

public class CommonActivity extends Activity {

    private boolean isMainMenu;

    public CommonActivity(boolean isMainMenu) {
        this.isMainMenu = isMainMenu;
    }

    public CommonActivity()
    {
        isMainMenu = false;
    }

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        int uiOptions;
        super.onCreate(savedInstanceState);
        if (!isMainMenu)
        {
            requestWindowFeature(Window.FEATURE_NO_TITLE);
        }

        if (!isMainMenu)
        {
            uiOptions = CommonModel.UI_OPTIONS_NON_MAIN;

        }
        else {
            uiOptions = CommonModel.UI_OPTIONS_MAIN;
        }

        View decorView = getWindow().getDecorView();
        decorView.setSystemUiVisibility(uiOptions);

    }

    @Override
    public void onWindowFocusChanged(boolean hasFocus) {
        super.onWindowFocusChanged(hasFocus);

        View decorView = getWindow().getDecorView();
        if (hasFocus) {
            int uiOptions;
            if (!isMainMenu)
            {
                uiOptions = CommonModel.UI_OPTIONS_NON_MAIN;

            }
            else {
                uiOptions = CommonModel.UI_OPTIONS_MAIN;
            }


            decorView.setSystemUiVisibility(uiOptions);}

    }



}
