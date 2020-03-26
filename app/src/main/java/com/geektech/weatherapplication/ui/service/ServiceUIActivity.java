package com.geektech.weatherapplication.ui.service;


import android.content.Intent;
import android.content.pm.PackageManager;
import android.os.Bundle;


import androidx.annotation.NonNull;

import com.geektech.weatherapplication.R;
import com.geektech.weatherapplication.data.PreferenceHelper;
import com.geektech.weatherapplication.data.service.ServiceBuilder;
import com.geektech.weatherapplication.ui.base.BaseActivity;
import com.geektech.weatherapplication.ui.service.maps.BaseMapActivity;
import com.geektech.weatherapplication.ui.service.maps.PermissionUtils;
import com.google.android.material.floatingactionbutton.FloatingActionButton;
import com.mapbox.geojson.Point;
import com.mapbox.mapboxsdk.geometry.LatLng;

import org.greenrobot.eventbus.EventBus;
import org.greenrobot.eventbus.Subscribe;
import org.greenrobot.eventbus.ThreadMode;

import java.util.ArrayList;

import butterknife.BindView;


public class ServiceUIActivity extends BaseMapActivity {

    @BindView(R.id.fab)
    FloatingActionButton fab;

    @Override
    public int getViewId() {
        return R.layout.activity_service_ui;
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setUpListeners();
    }


    private void setUpListeners() {
        fab.setOnClickListener(v -> {
            if (PreferenceHelper.isPressed()) {
                if (PermissionUtils.isPermissionLocationGranted(this)){
                    actionService(true);
                    fab.setImageDrawable(getResources().getDrawable(R.drawable.ic_stop_black_24dp));
                    PreferenceHelper.setIsPressed(false);
                }
            } else {
                actionService(false);
                fab.setImageDrawable(getResources().getDrawable(R.drawable.ic_play_arrow_black_24dp));
                PreferenceHelper.setIsPressed(true);
            }
        });
    }

    @Subscribe(threadMode = ThreadMode.MAIN)
    public void showLines(ArrayList<Point> latLng){
        drawLines(latLng);
    }

    @Override
    protected void onStart() {
        super.onStart();
        EventBus.getDefault().register(this);
    }

    @Override
    protected void onStop() {
        super.onStop();
        EventBus.getDefault().unregister(this);
    }

    @Override
    public void onRequestPermissionsResult(int requestCode, @NonNull String[] permissions, @NonNull int[] grantResults) {
        super.onRequestPermissionsResult(requestCode, permissions, grantResults);
        if (requestCode == PermissionUtils.REQUEST_CODE){
            if (grantResults[0] == PackageManager.PERMISSION_GRANTED &&
            grantResults[1] == PackageManager.PERMISSION_GRANTED){
                actionService(true);
            }
        }
    }

    private void actionService(boolean isActivated) {
        Intent intent = new Intent(this, ServiceBuilder.class);
        intent.putExtra("isActivated", isActivated);
        startService(intent);
    }
}
