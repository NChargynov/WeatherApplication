package com.geektech.weatherapplication.ui.service.maps;

import android.graphics.Color;
import android.graphics.PointF;
import android.location.Location;
import android.os.Bundle;

import androidx.annotation.NonNull;

import com.geektech.weatherapplication.R;
import com.geektech.weatherapplication.ui.base.BaseActivity;
import com.google.android.gms.location.FusedLocationProviderClient;
import com.google.android.gms.location.LocationServices;
import com.mapbox.geojson.LineString;
import com.mapbox.geojson.Point;
import com.mapbox.mapboxsdk.Mapbox;
import com.mapbox.mapboxsdk.camera.CameraPosition;
import com.mapbox.mapboxsdk.camera.CameraUpdateFactory;
import com.mapbox.mapboxsdk.geometry.LatLng;
import com.mapbox.mapboxsdk.location.LocationComponent;
import com.mapbox.mapboxsdk.location.LocationComponentActivationOptions;
import com.mapbox.mapboxsdk.location.LocationComponentOptions;
import com.mapbox.mapboxsdk.maps.MapView;
import com.mapbox.mapboxsdk.maps.MapboxMap;
import com.mapbox.mapboxsdk.maps.OnMapReadyCallback;
import com.mapbox.mapboxsdk.maps.Style;
import com.mapbox.mapboxsdk.style.layers.LineLayer;
import com.mapbox.mapboxsdk.style.layers.PropertyFactory;
import com.mapbox.mapboxsdk.style.sources.GeoJsonSource;

import java.util.ArrayList;
import java.util.Objects;

import butterknife.BindView;

import static com.geektech.weatherapplication.BuildConfig.MAP_KEY;

public abstract class BaseMapActivity extends BaseActivity implements OnMapReadyCallback {

    private MapboxMap mapboxMap;
    private LocationComponent locationComponent;
    private FusedLocationProviderClient fusedLocationProviderClient;
    private final String SOURCE = "line-source";
    private final String LAYER = "line-layer";
    @BindView(R.id.mapView)
    MapView mapView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        Mapbox.getInstance(this, MAP_KEY);
        super.onCreate(savedInstanceState);

        mapView.onCreate(savedInstanceState);
        mapView.getMapAsync(this);
        fusedLocationProviderClient = LocationServices.getFusedLocationProviderClient(this);
    }

    private void getLastLocation() {
        fusedLocationProviderClient.getLastLocation().addOnSuccessListener(this::animateCamera);
    }

    private void animateCamera(Location location) {
        CameraPosition cameraPosition = new CameraPosition.Builder()
                .target(new LatLng(location.getLatitude(), location.getLongitude()))
                .zoom(15).build();
        mapboxMap.animateCamera(CameraUpdateFactory.newCameraPosition(cameraPosition), 7000);
    }

    @Override
    public void onMapReady(@NonNull MapboxMap mapboxMap) {
        mapboxMap.setStyle(Style.TRAFFIC_DAY, style -> {
            this.mapboxMap = mapboxMap;
            showDeviceLocation();
            initLayer();
            initSource();
        });
    }

    protected void showDeviceLocation() {
        LocationComponentOptions locationComponentOptions =
                LocationComponentOptions.builder(this).build();

        LocationComponentActivationOptions locationComponentActivationOptions = LocationComponentActivationOptions
                .builder(this, Objects.requireNonNull(mapboxMap.getStyle()))
                .locationComponentOptions(locationComponentOptions)
                .build();

        locationComponent = mapboxMap.getLocationComponent();
        locationComponent.activateLocationComponent(locationComponentActivationOptions);
        locationComponent.setLocationComponentEnabled(true);
        getLastLocation();
    }

    private void initSource() {
        mapboxMap.getStyle().addSource(new GeoJsonSource(SOURCE));
    }

    private void initLayer() {
        mapboxMap.getStyle().addLayer(new LineLayer(LAYER, SOURCE).
                withProperties(PropertyFactory.lineWidth(8F),
                        PropertyFactory.lineColor(Color.parseColor("#232323"))));
    }

    protected void drawLines(ArrayList<Point> list){
        ((GeoJsonSource) mapboxMap.getStyle().getSource(SOURCE))
                .setGeoJson(LineString.fromLngLats(list));
    }

    @Override
    protected void onStart() {
        super.onStart();
        mapView.onStart();
    }

    @Override
    protected void onResume() {
        super.onResume();
        mapView.onResume();
    }

    @Override
    protected void onPause() {
        super.onPause();
        mapView.onPause();
    }

    @Override
    protected void onStop() {
        super.onStop();
        mapView.onStop();
    }

    @Override
    protected void onSaveInstanceState(@NonNull Bundle outState) {
        super.onSaveInstanceState(outState);
        mapView.onSaveInstanceState(outState);
    }

    @Override
    public void onLowMemory() {
        super.onLowMemory();
        mapView.onLowMemory();
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        mapView.onDestroy();
    }
}
