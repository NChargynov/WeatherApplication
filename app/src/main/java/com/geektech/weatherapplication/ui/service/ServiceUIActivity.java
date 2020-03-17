package com.geektech.weatherapplication.ui.service;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;

import com.geektech.weatherapplication.R;
import com.geektech.weatherapplication.data.PreferenceHelper;
import com.geektech.weatherapplication.data.service.ServiceBuilder;
import com.google.android.material.floatingactionbutton.FloatingActionButton;

public class ServiceUIActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_service_ui);
        FloatingActionButton fab = findViewById(R.id.fab);
        fab.setOnClickListener(v -> {
            if (PreferenceHelper.isPressed()) {
                actionService(true);
                fab.setImageDrawable(getResources().getDrawable(R.drawable.ic_stop_black_24dp));
                PreferenceHelper.setIsPressed(false);
            } else {
                actionService(false);
                fab.setImageDrawable(getResources().getDrawable(R.drawable.ic_play_arrow_black_24dp));
                PreferenceHelper.setIsPressed(true);
            }
        });
    }


    private void actionService(boolean isActivated) {
        Intent intent = new Intent(this, ServiceBuilder.class);
        intent.putExtra("isActivated", isActivated);
        startService(intent);
    }
}
