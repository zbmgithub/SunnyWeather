package com.sunnyweather.android.logic.network;

import android.util.Log;

import androidx.lifecycle.MutableLiveData;

import com.sunnyweather.android.logic.model.DailyResponse;
import com.sunnyweather.android.logic.model.Place;
import com.sunnyweather.android.logic.model.PlaceResponse;
import com.sunnyweather.android.logic.model.RealtimeResponse;
import com.sunnyweather.android.logic.model.Weather;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.ArrayBlockingQueue;
import java.util.concurrent.BlockingQueue;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class SunnyWeatherNetwork {
    private static PlaceService placeService;
    private static WeatherService weatherService = ServiceCreator.create(WeatherService.class);

    //网络请求回调
    public static void searchPlaces(String query, MutableLiveData<List<Place>> res) {
        placeService = ServiceCreator.create(PlaceService.class);
        placeService.searchPlaces(query).enqueue(new Callback<PlaceResponse>() {
            @Override
            public void onResponse(Call<PlaceResponse> call, Response<PlaceResponse> response) {
                res.postValue(response.body().getPlaces());
            }

            @Override
            public void onFailure(Call<PlaceResponse> call, Throwable t) {
            }
        });
    }


    public static void getDailyWeather(String lng, String lat, MutableLiveData<Weather> res) {
        weatherService.getDailyWeather(lng, lat).enqueue(new Callback<DailyResponse>() {
            @Override
            public void onResponse(Call<DailyResponse> call, Response<DailyResponse> response) {
                Weather weather;
                if (res.getValue() == null)
                    weather = new Weather();
                else weather = res.getValue();
                weather.setDaily(response.body().getResult().getDaily());
                res.postValue(weather);
            }

            @Override
            public void onFailure(Call<DailyResponse> call, Throwable t) {
            }
        });
    }

    public static void getRealtimeWeather(String lng, String lat, MutableLiveData<Weather> res) {
        weatherService.getRealtimeWeather(lng, lat).enqueue(new Callback<RealtimeResponse>() {
            @Override
            public void onResponse(Call<RealtimeResponse> call, Response<RealtimeResponse> response) {
                synchronized (res) {
                    Weather weather;
                    if (res.getValue() == null)
                        weather = new Weather();
                    else weather = res.getValue();
                    weather.setRealtime(response.body().getResult().getRealtime());
                    res.postValue(weather);
                    //TODO 给LiveData在子线程中设置数据，一定要使用postValue
                }
            }

            @Override
            public void onFailure(Call<RealtimeResponse> call, Throwable t) {
            }
        });
    }


}
