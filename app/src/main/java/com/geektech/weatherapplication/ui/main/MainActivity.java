package com.geektech.weatherapplication.ui.main;


import android.os.Bundle;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import androidx.recyclerview.widget.RecyclerView;

import com.bumptech.glide.Glide;
import com.geektech.weatherapplication.R;
import com.geektech.weatherapplication.data.internet.RetrofitBuilder;
import com.geektech.weatherapplication.data.pojo.CurrentWeather;
import com.geektech.weatherapplication.data.pojo.ForecastWeather;
import com.geektech.weatherapplication.ui.base.BaseActivity;
import com.geektech.weatherapplication.utils.DateUtils;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

import static com.geektech.weatherapplication.BuildConfig.APP_ID;

public class MainActivity extends BaseActivity {
    private TextView tvCityName, tvTempNow, tvTempMaxToday, tvTempMinToday, tvValurForWind,
            tvValueForPressure, tvValueForHumidity, tvValueForCloudiness, tvValueForSunrise,
            tvValueForSunset, tvDateDay, tvDateMonth, tvDateYear;
    private ImageView imageForIcon;
    private RecyclerView recyclerView;
    private ForecastAdapter adapter;



    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        setUpViews();
        setUpRecyclerView();
        loadCurrentWeathers();
        loadForecastWeathers();
    }

    private void setUpRecyclerView() {
        adapter = new ForecastAdapter();
        recyclerView.setAdapter(adapter);
    }

    private void loadForecastWeathers() {
        RetrofitBuilder.getService().getForecastWeather("Bishkek", APP_ID, "metric")
                .enqueue(new Callback<ForecastWeather>() {
                    @Override
                    public void onResponse(Call<ForecastWeather> call, Response<ForecastWeather> response) {
                        if (response.isSuccessful() && response.body() != null){
                            ForecastWeather forecastWeather = response.body();
                            adapter.update(response.body().getList());
                            tvTempNow.setText(getString(R.string.celsius, forecastWeather.getList().get(0).getMain().getTemp().toString()));
                            tvTempMaxToday.setText(getString(R.string.celsius, forecastWeather.getList().get(0).getMain().getTempMax().toString()));
                            tvTempMinToday.setText(getString(R.string.celsius, forecastWeather.getList().get(0).getMain().getTempMin().toString()));
                        } else {
                            Toast.makeText(MainActivity.this, "Error", Toast.LENGTH_SHORT).show();
                        }
                    }

                    @Override
                    public void onFailure(Call<ForecastWeather> call, Throwable t) {
                        Toast.makeText(MainActivity.this, t.getLocalizedMessage(), Toast.LENGTH_SHORT).show();
                    }
                });
    }

    private void loadCurrentWeathers() {
        RetrofitBuilder.getService().getCurrentWeather("Bishkek", APP_ID, "metric")
                .enqueue(new Callback<CurrentWeather>() {
                    @Override
                    public void onResponse(Call<CurrentWeather> call, Response<CurrentWeather> response) {
                        if (response.isSuccessful() && response.body() != null){
                            CurrentWeather currentWeather = response.body();
                            addValues(currentWeather);
                            glide(response);
                        } else {
                            Toast.makeText(MainActivity.this, "Error", Toast.LENGTH_SHORT).show();
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
        tvValurForWind.setText(getString(R.string.speedForWind, currentWeather.getWind().getSpeed().toString()));
        tvValueForPressure.setText(getString(R.string.mbForPressure, currentWeather.getMain().getPressure().toString()));
        tvValueForHumidity.setText(currentWeather.getMain().getHumidity().toString() + "%");
        tvValueForCloudiness.setText(currentWeather.getClouds().getAll().toString() + "%");
        tvValueForSunrise.setText(DateUtils.parseSunset(currentWeather.getSys().getSunrise()));
        tvValueForSunset.setText(DateUtils.parseSunset(currentWeather.getSys().getSunset()));
        tvDateDay.setText(DateUtils.parseDateDay(currentWeather));
        tvDateMonth.setText(DateUtils.parseDateMonth(currentWeather));
        tvDateYear.setText(DateUtils.parseDateYear(currentWeather));
    }

    private void glide(Response<CurrentWeather> response) {
        Glide.with(MainActivity.this).load("http://openweathermap.org/img/wn/" + response.body().
                getWeather().get(0).getIcon() + "@2x.png").centerCrop().into(imageForIcon);

//        Glide.with(itemView).load("http://openweathermap.org/img/wn/" +
//                currentWeather.getWeather().get(0).getIcon() + "@2x.png").into(imageView);
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
        recyclerView = findViewById(R.id.recyclerView);
        tvDateDay = findViewById(R.id.tvDate);
        tvDateMonth = findViewById(R.id.tvMonth);
        tvDateYear = findViewById(R.id.tvYear);
    }
}
