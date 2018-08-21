package com.example.shlokpatel.travel2_mindforks;


import android.os.Parcelable;

import java.io.Serializable;

class CityLocation implements Serializable{
    Double lat,lng;

    public CityLocation() {
    }

    public CityLocation(Double lat, Double lng) {
        this.lat = lat;
        this.lng = lng;
    }

    public Double getLat() {
        return lat;
    }

    public Double getLng() {
        return lng;
    }
}
