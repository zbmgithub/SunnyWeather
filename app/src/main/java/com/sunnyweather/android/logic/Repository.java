package com.sunnyweather.android.logic;

import android.util.Log;

import androidx.lifecycle.LiveData;
import androidx.lifecycle.MutableLiveData;

import com.sunnyweather.android.logic.model.Place;
import com.sunnyweather.android.logic.model.PlaceResponse;
import com.sunnyweather.android.logic.network.SunnyWeatherNetwork;

import java.util.List;

public class Repository {
    public static MutableLiveData<List<Place>> searchPlaces(String query) {
        MutableLiveData<List<Place>> res = new MutableLiveData<>();
        //PlaceResponse placeResponse = SunnyWeatherNetwork.searchPlaces(query,res);
        //res.setValue(placeResponse.getPlaces());
        //if (placeResponse.getStatus() == "ok") return res;
        //else return null;
        SunnyWeatherNetwork.searchPlaces(query,res);
        return res;
    }
}
