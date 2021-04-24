package com.sunnyweather.android.logic;

import android.util.Log;

import androidx.lifecycle.LiveData;
import androidx.lifecycle.MutableLiveData;

import com.sunnyweather.android.logic.dao.PlaceDao;
import com.sunnyweather.android.logic.model.DailyResponse;
import com.sunnyweather.android.logic.model.Place;
import com.sunnyweather.android.logic.model.PlaceResponse;
import com.sunnyweather.android.logic.model.RealtimeResponse;
import com.sunnyweather.android.logic.model.Weather;
import com.sunnyweather.android.logic.network.SunnyWeatherNetwork;

import java.lang.reflect.Array;
import java.util.ArrayList;
import java.util.List;

import static java.lang.Thread.sleep;

public class Repository {
    public static MutableLiveData<List<Place>> searchPlaces(String query) {
        MutableLiveData<List<Place>> res = new MutableLiveData<>();
        SunnyWeatherNetwork.searchPlaces(query, res);
        return res;
    }

    public static MutableLiveData<Weather> refreshWeatehr(String lng, String lat) {
        MutableLiveData<Weather> weatherLiveData = new MutableLiveData<>();
        SunnyWeatherNetwork.getRealtimeWeather(lng, lat, weatherLiveData);
        SunnyWeatherNetwork.getDailyWeather(lng, lat, weatherLiveData);
        return weatherLiveData;
    }

    public static void savePlace(Place place) {
        PlaceDao.savePlace(place);
    }

    public static Place getSavedPlace() {
        return PlaceDao.getSavedPlace();
    }

    public static boolean isPlaceSaved() {
        return PlaceDao.isPlaceSaved();
    }
}
