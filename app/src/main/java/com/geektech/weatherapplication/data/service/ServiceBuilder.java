package com.geektech.weatherapplication.data.service;

import android.app.Service;
import android.content.Intent;
import android.os.IBinder;

import androidx.annotation.Nullable;

public class ServiceBuilder extends Service {
    @Nullable
    @Override
    public IBinder onBind(Intent intent) {
        return null;
    }

    @Override
    public int onStartCommand(Intent intent, int flags, int startId) {
        if (intent.getBooleanExtra("isActivated", false)){
            startForeground(1, NotificationHelper.createNotification(this).build());
        } else {
            stopSelf();
        }
        return START_STICKY;
    }
}
