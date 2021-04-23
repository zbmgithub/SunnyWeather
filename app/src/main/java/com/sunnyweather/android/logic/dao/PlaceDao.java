package com.sunnyweather.android.logic.dao;

import android.content.Context;
import android.content.SharedPreferences;
import android.util.Log;

import com.google.gson.Gson;
import com.sunnyweather.android.SunnyWeatherApplication;
import com.sunnyweather.android.logic.model.Place;

import org.json.JSONStringer;

public class PlaceDao {
    private static SharedPreferences sharedPreference() {
        return SunnyWeatherApplication.context.getSharedPreferences("sunny_weather", Context.MODE_PRIVATE);
    }

    public static void savePlace(Place place) {
        //TODO SharedPreference put过一定记得apply
        sharedPreference().edit().putString("place", new Gson().toJson(place)).apply();
        //Log.d("Tag", new Gson().toJson(place));
    }

    public static Place getSavedPlace() {
        String placeJson = sharedPreference().getString("place", "");
        return new Gson().fromJson(placeJson, Place.class);
    }

    public static boolean isPlaceSaved() {
        return sharedPreference().contains("place");
    }
}
