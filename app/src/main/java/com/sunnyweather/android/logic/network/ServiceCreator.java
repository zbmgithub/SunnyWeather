package com.sunnyweather.android.logic.network;

import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

public class ServiceCreator {
    private static final String BASE_URL = "https://api.caiyunapp.com/";
    private static Retrofit retrofit;

    public static <Any> Any create(Class<Any> serviceClass) {
        retrofit = new Retrofit.Builder()
                .baseUrl(BASE_URL)
                .addConverterFactory(GsonConverterFactory.create())
                .build();
        return retrofit.create(serviceClass);
    }

}
