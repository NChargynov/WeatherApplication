package com.geektech.weatherapplication.data.service;

import android.app.Service;
import android.content.Intent;
import android.os.IBinder;
import android.os.Looper;
import android.util.Log;

import androidx.annotation.Nullable;

import com.google.android.gms.location.FusedLocationProviderClient;
import com.google.android.gms.location.LocationCallback;
import com.google.android.gms.location.LocationRequest;
import com.google.android.gms.location.LocationResult;
import com.google.android.gms.location.LocationServices;
import com.mapbox.geojson.Point;
import com.mapbox.mapboxsdk.geometry.LatLng;

import org.greenrobot.eventbus.EventBus;

import java.util.ArrayList;

public class ServiceBuilder extends Service {

    private FusedLocationProviderClient fusedLocationProviderClient;
    private LocationCallback locationCallback;
    private LocationRequest locationRequest;
    private ArrayList<Point> latLngArrayList;

    @Nullable
    @Override
    public IBinder onBind(Intent intent) {
        return null;
    }

    private void startLocationListen() {
        latLngArrayList = new ArrayList<>();

        fusedLocationProviderClient =
                LocationServices.getFusedLocationProviderClient(getApplicationContext());

        locationRequest = LocationRequest.create();
        locationRequest.setPriority(LocationRequest.PRIORITY_HIGH_ACCURACY);
        locationRequest.setInterval(2_000);

        locationCallback = new LocationCallback() {
            @Override
            public void onLocationResult(LocationResult locationResult) {
                super.onLocationResult(locationResult);

                if (locationResult.getLastLocation() != null) {
                    latLngArrayList.add(Point.fromLngLat(locationResult.getLastLocation().getLongitude(),
                            locationResult.getLastLocation().getLatitude()));
                    EventBus.getDefault().post(latLngArrayList);

                    Log.e("Location", locationResult.getLastLocation().getLatitude() + "------------" +
                            locationResult.getLastLocation().getLongitude());
                }
            }
        };
        fusedLocationProviderClient.
                requestLocationUpdates(locationRequest, locationCallback, Looper.myLooper());
    }

    @Override
    public void onDestroy() {
        super.onDestroy();
        if (locationCallback != null){
            fusedLocationProviderClient.removeLocationUpdates(locationCallback);
            latLngArrayList.clear();
        } else {

        }
    }

    @Override
    public int onStartCommand(Intent intent, int flags, int startId) {
        if (intent.getBooleanExtra("isActivated", false)) {
            startForeground(1, NotificationHelper.createNotification(this).build());
            startLocationListen();
        } else {
            stopSelf();
        }
        return START_STICKY;
    }
}
