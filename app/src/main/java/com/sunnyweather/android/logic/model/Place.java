package com.sunnyweather.android.logic.model;

import com.google.gson.annotations.SerializedName;

public class Place {
    private String name;
    private Location location;
    @SerializedName("formatted_address")
    private String address;

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public Location getLocation() {
        return location;
    }

    public void setLocation(Location location) {
        this.location = location;
    }

    public String getAddress() {
        return address;
    }

    public void setAddress(String address) {
        this.address = address;
    }
}
