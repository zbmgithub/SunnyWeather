package com.sunnyweather.android.ui.place;

import android.util.Log;

import androidx.lifecycle.LiveData;
import androidx.lifecycle.MutableLiveData;
import androidx.lifecycle.Transformations;
import androidx.lifecycle.ViewModel;

import com.sunnyweather.android.logic.Repository;
import com.sunnyweather.android.logic.model.Place;

import java.util.ArrayList;
import java.util.List;

public class PlaceViewModel extends ViewModel {
    private MutableLiveData<String> searchLiveData = new MutableLiveData<String>();
    ArrayList<Place> placeList = new ArrayList<>();

    MutableLiveData<List<Place>> placeLiveData = (MutableLiveData<List<Place>>) Transformations.switchMap(searchLiveData, query -> {
        return Repository.searchPlaces(query);
    });

    void searchPlaces(String query) {
        searchLiveData.setValue(query);
    }

}
