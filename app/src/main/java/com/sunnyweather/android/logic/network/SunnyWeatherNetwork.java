package com.sunnyweather.android.logic.network;

import android.util.Log;

import androidx.lifecycle.MutableLiveData;

import com.sunnyweather.android.logic.model.Place;
import com.sunnyweather.android.logic.model.PlaceResponse;

import java.util.ArrayList;
import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class SunnyWeatherNetwork {
    private static PlaceService placeService;

    //网络请求回调
    public static void searchPlaces(String query, MutableLiveData<List<Place>> res) {
        placeService = ServiceCreator.create(PlaceService.class);
        placeService.searchPlaces(query).enqueue(new Callback<PlaceResponse>() {
            @Override
            public void onResponse(Call<PlaceResponse> call, Response<PlaceResponse> response) {
                res.setValue(response.body().getPlaces());
            }

            @Override
            public void onFailure(Call<PlaceResponse> call, Throwable t) {
            }
        });
    }

}
