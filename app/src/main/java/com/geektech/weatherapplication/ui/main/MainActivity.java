package com.geektech.weatherapplication.ui.main;


import android.os.Bundle;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.bumptech.glide.Glide;
import com.geektech.weatherapplication.R;
import com.geektech.weatherapplication.data.internet.RetrofitBuilder;
import com.geektech.weatherapplication.data.pojo.CurrentWeather;
import com.geektech.weatherapplication.ui.base.BaseActivity;
import com.geektech.weatherapplication.utils.DateUtils;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class MainActivity extends BaseActivity {
    TextView tvCityName, tvTempNow, tvTempMaxToday, tvTempMinToday, tvValurForWind,
            tvValueForPressure, tvValueForHumidity, tvValueForCloudiness, tvValueForSunrise,
            tvValueForSunset;
    ImageView imageForIcon;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        setUpViews();
        loadData();
    }

    private void loadData() {
        RetrofitBuilder.getService().getCurrentWeather("Bishkek", "4bbf5a1ed98cd9f688ebb3651474cdd2", "metric")
                .enqueue(new Callback<CurrentWeather>() {
                    @Override
                    public void onResponse(Call<CurrentWeather> call, Response<CurrentWeather> response) {
                        if (response.isSuccessful() && response.body() != null){
                            CurrentWeather currentWeather = response.body();
                            addValues(currentWeather);
                            glide(response);
                        }
                    }

                    @Override
                    public void onFailure(Call<CurrentWeather> call, Throwable t) {
                        Toast.makeText(MainActivity.this, t.getLocalizedMessage(), Toast.LENGTH_SHORT).show();
                    }
                });
    }

    private void addValues(CurrentWeather currentWeather) {
        tvCityName.setText(getString(R.string.comma,currentWeather.getName()
                ,currentWeather.getSys().getCountry()));
        tvTempNow.setText(getString(R.string.celsius, currentWeather.getMain().getTemp().toString()));
        tvTempMaxToday.setText(getString(R.string.celsius, currentWeather.getMain().getTempMax().toString()));
        tvTempMinToday.setText(getString(R.string.celsius, currentWeather.getMain().getTempMin().toString()));
        tvValurForWind.setText(getString(R.string.speedForWind, currentWeather.getWind().getSpeed().toString()));
        tvValueForPressure.setText(getString(R.string.mbForPressure, currentWeather.getMain().getPressure().toString()));
        tvValueForHumidity.setText(currentWeather.getMain().getHumidity().toString() + "%");
        tvValueForCloudiness.setText(currentWeather.getClouds().getAll().toString() + "%");
        tvValueForSunrise.setText(DateUtils.parseSunset(currentWeather.getSys().getSunrise()));
        tvValueForSunset.setText(DateUtils.parseSunset(currentWeather.getSys().getSunset()));
    }

    private void glide(Response<CurrentWeather> response) {
        Glide.with(MainActivity.this).load("http://openweathermap.org/img/wn/" + response.body().
                getWeather().get(0).getIcon() + "@2x.png").centerCrop().into(imageForIcon);
    }

    private void setUpViews() {
        tvCityName = findViewById(R.id.cityName);
        tvTempNow = findViewById(R.id.tempNow);
        tvTempMaxToday = findViewById(R.id.tempTodayMax);
        tvTempMinToday = findViewById(R.id.tempTodayMin);
        tvValurForWind = findViewById(R.id.valueForWind);
        tvValueForPressure = findViewById(R.id.valueForPressure);
        tvValueForHumidity = findViewById(R.id.valueForHumidity);
        tvValueForCloudiness = findViewById(R.id.valueForCloudiness);
        tvValueForSunrise = findViewById(R.id.valueForSunrise);
        tvValueForSunset = findViewById(R.id.valueForSunset);
        imageForIcon = findViewById(R.id.imageIcon);
    }
}
