package com.geektech.weatherapplication.data.internet;

import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

public class RetrofitBuilder {

    private static RetrofitService service;

    public static RetrofitService getService(){
        if (service == null){
            service = buildRetrofit();
        }
        return service;
    }

    private static RetrofitService buildRetrofit() {
        return new Retrofit.Builder()
                .baseUrl("http://api.openweathermap.org/")
                .addConverterFactory(GsonConverterFactory.create())
                .build().create(RetrofitService.class);
    }
}
