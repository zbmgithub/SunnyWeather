package com.sunnyweather.android.logic.model;

import android.util.Log;

import java.util.List;

public class PlaceResponse {
    private String status;
    private List<Place> places;

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }

    public List<Place> getPlaces() {
        return places;
    }

    public void setPlaces(List<Place> places) {
        this.places = places;
    }
}

