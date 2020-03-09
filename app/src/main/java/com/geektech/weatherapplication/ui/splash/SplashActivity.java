package com.geektech.weatherapplication.ui.splash;

import android.content.Intent;
import android.os.Bundle;

import com.geektech.weatherapplication.R;
import com.geektech.weatherapplication.data.PreferenceHelper;
import com.geektech.weatherapplication.ui.base.BaseActivity;
import com.geektech.weatherapplication.ui.main.MainActivity;
import com.geektech.weatherapplication.ui.onboard.OnBoardActivity;

public class SplashActivity extends BaseActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_splash);
        chooseScreen();
    }

    private void chooseScreen() {
        if (PreferenceHelper.isShown()) {
            startActivity(new Intent(this, MainActivity.class));
        } else {
            startActivity(new Intent(this, OnBoardActivity.class));
        }
        finish();
    }
}
