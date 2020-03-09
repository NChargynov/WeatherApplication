package com.geektech.weatherapplication.ui.main;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.geektech.weatherapplication.R;
import com.geektech.weatherapplication.data.pojo.CurrentWeather;

import java.util.ArrayList;
import java.util.List;

public class ForecastAdapter extends RecyclerView.Adapter<ForecastViewHolder> {

    private List<CurrentWeather> list = new ArrayList<>();

    public void update(List<CurrentWeather> list){
        this.list = list;
        notifyDataSetChanged();
    }

    @NonNull
    @Override
    public ForecastViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).
                inflate(R.layout.item_forecast, parent, false);
        return new ForecastViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull ForecastViewHolder holder, int position) {
            holder.bind(list.get(position));
    }

    @Override
    public int getItemCount() {
        return list.size();
    }
}
