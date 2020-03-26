package com.geektech.weatherapplication.ui.main;


import android.content.Intent;
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
import com.geektech.weatherapplication.ui.service.ServiceUIActivity;
import com.geektech.weatherapplication.utils.DateUtils;

import butterknife.BindView;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

import static com.geektech.weatherapplication.BuildConfig.APP_ID;

public class MainActivity extends BaseActivity {

    @BindView(R.id.cityName)
    TextView tvCityName;
    @BindView(R.id.tempNow)
    TextView tvTempNow;
    @BindView(R.id.tempTodayMax)
    TextView tvTempMaxToday;
    @BindView(R.id.tempTodayMin)
    TextView tvTempMinToday;
    @BindView(R.id.valueForWind)
    TextView tvValueForWind;
    @BindView(R.id.valueForPressure)
    TextView tvValueForPressure;
    @BindView(R.id.valueForHumidity)
    TextView tvValueForHumidity;
    @BindView(R.id.valueForCloudiness)
    TextView tvValueForCloudiness;
    @BindView(R.id.valueForSunrise)
    TextView tvValueForSunrise;
    @BindView(R.id.valueForSunset)
    TextView tvValueForSunset;
    @BindView(R.id.tvDate)
    TextView tvDateDay;
    @BindView(R.id.tvMonth)
    TextView tvDateMonth;
    @BindView(R.id.tvYear)
    TextView tvDateYear;
    @BindView(R.id.descCloud)
    TextView tvDescCloud;
    @BindView(R.id.imageIcon)
    ImageView imageForIcon;
    @BindView(R.id.imageMap)
    ImageView imageMap;
    @BindView(R.id.recyclerView)
    RecyclerView recyclerView;
    private ForecastAdapter adapter;


    @Override
    public int getViewId() {
        return R.layout.activity_main;
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setUpRecyclerView();
        loadCurrentWeathers();
        loadForecastWeathers();
        listeners();
    }

    private void listeners() {
        imageMap.setOnClickListener(v ->
                startActivity(new Intent(this, ServiceUIActivity.class)));
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
                            adapter.update(response.body().getList());
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
        tvValueForWind.setText(getString(R.string.speedForWind, currentWeather.getWind().getSpeed().toString()));
        tvValueForPressure.setText(getString(R.string.mbForPressure, currentWeather.getMain().getPressure().toString()));
        tvValueForHumidity.setText(currentWeather.getMain().getHumidity().toString() + "%");
        tvValueForCloudiness.setText(currentWeather.getClouds().getAll().toString() + "%");
        tvValueForSunrise.setText(DateUtils.parseSunset(currentWeather.getSys().getSunrise()));
        tvValueForSunset.setText(DateUtils.parseSunset(currentWeather.getSys().getSunset()));
        tvDateDay.setText(DateUtils.parseDateDay(currentWeather));
        tvDateMonth.setText(DateUtils.parseDateMonth(currentWeather));
        tvDateYear.setText(DateUtils.parseDateYear(currentWeather));
        tvTempNow.setText(getString(R.string.celsius, currentWeather.getMain().getTemp().toString()));
        tvTempMaxToday.setText(getString(R.string.celsius, currentWeather.getMain().getTempMax().toString()));
        tvTempMinToday.setText(getString(R.string.celsius, currentWeather.getMain().getTempMin().toString()));
        tvDescCloud.setText(currentWeather.getWeather().get(0).getDescription());
    }

    private void glide(Response<CurrentWeather> response) {
        Glide.with(MainActivity.this).load("http://openweathermap.org/img/wn/" + response.body().
                getWeather().get(0).getIcon() + "@2x.png").centerCrop().into(imageForIcon);
    }
}
