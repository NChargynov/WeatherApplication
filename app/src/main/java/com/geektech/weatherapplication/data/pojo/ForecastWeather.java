package com.geektech.weatherapplication.data.pojo;

import java.util.List;

public class ForecastWeather {
    public List<CurrentWeather> getList() {
        return list;
    }

    public void setList(List<CurrentWeather> list) {
        this.list = list;
    }

    private List<CurrentWeather> list;
}
