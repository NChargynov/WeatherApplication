package com.geektech.weatherapplication.ui.main;

import androidx.appcompat.app.AppCompatActivity;

import android.content.SharedPreferences;
import android.os.Bundle;

import com.geektech.weatherapplication.R;
import com.geektech.weatherapplication.ui.base.BaseActivity;

public class MainActivity extends BaseActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        startOnBoardActivity();
    }

    private void startOnBoardActivity() {
    }
}
