package com.example.shlokpatel.travel2_mindforks;

import java.util.ArrayList;

class CityDesc {
    String formatted_address;
    String name;
    Float rating;
    String place_id;
    CityGeometry geometry;
    ArrayList<PhotoInfo> photos;

    public CityDesc() {
    }

    public CityDesc(String formatted_address, CityGeometry geometry, String name, Float rating, String place_id, ArrayList<PhotoInfo> photos) {
        this.formatted_address = formatted_address;
        this.geometry = geometry;
        this.name = name;
        this.rating = rating;
        this.place_id = place_id;
        this.photos = photos;
    }

    public String getFormatted_address() {
        return formatted_address;
    }

    public CityGeometry getGeometry() {
        return geometry;
    }

    public String getName() {
        return name;
    }

    public Float getRating() {
        return rating;
    }

    public String getPlace_id() {
        return place_id;
    }

    public ArrayList<PhotoInfo> getPhotos() {
        return photos;
    }
}
