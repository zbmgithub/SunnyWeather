package com.sunnyweather.android.ui.weather;

import android.util.Log;

import androidx.lifecycle.MutableLiveData;
import androidx.lifecycle.Transformations;
import androidx.lifecycle.ViewModel;

import com.sunnyweather.android.logic.Repository;
import com.sunnyweather.android.logic.dao.PlaceDao;
import com.sunnyweather.android.logic.model.Location;
import com.sunnyweather.android.logic.model.Place;
import com.sunnyweather.android.logic.model.Weather;

public class WeatherViewModel extends ViewModel {
    MutableLiveData<Location> locationLiveData = new MutableLiveData<>();
    public String locationLng = "";
    public String locationLat = "";
    public String placeName = "";

    MutableLiveData<Weather> weatherLiveData = (MutableLiveData<Weather>) Transformations.switchMap(locationLiveData, location -> {
        return Repository.refreshWeatehr(location.getLng(), location.getLat());
    });

    void refreshWeather(String lng, String lat) {
        //Log.d("Tag","获取到 位置变化"+lng+"---"+lat);
        locationLiveData.setValue(new Location(lng, lat));
    }

}
