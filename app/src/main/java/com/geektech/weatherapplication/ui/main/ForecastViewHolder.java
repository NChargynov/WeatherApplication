package com.geektech.weatherapplication.ui.main;

import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.bumptech.glide.Glide;
import com.geektech.weatherapplication.R;
import com.geektech.weatherapplication.data.pojo.CurrentWeather;

import retrofit2.Response;

public class ForecastViewHolder extends RecyclerView.ViewHolder {

    private TextView tvMinTemp, tvMaxTemp, tvNowTemp;
    private ImageView imageView;

    public ForecastViewHolder(@NonNull View itemView) {
        super(itemView);
        tvNowTemp = itemView.findViewById(R.id.tvTempNowItem);
        tvMaxTemp = itemView.findViewById(R.id.tvTempMaxItem);
        tvMinTemp = itemView.findViewById(R.id.tvTempMinItem);
        imageView = itemView.findViewById(R.id.imageIconItem);
    }

    public void bind(CurrentWeather currentWeather) {
        tvNowTemp.setText(currentWeather.getMain().getTemp().toString() + "C");
        tvMaxTemp.setText(currentWeather.getMain().getTempMax().toString() + "C");
        tvMinTemp.setText(currentWeather.getMain().getTempMin().toString() + "C");
        Glide.with(itemView).load("http://openweathermap.org/img/wn/" +
                currentWeather.getWeather().get(0).getIcon() + "@2x.png").centerCrop().into(imageView);
    }
}
