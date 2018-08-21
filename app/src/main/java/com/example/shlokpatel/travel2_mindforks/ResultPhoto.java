package com.example.shlokpatel.travel2_mindforks;

import java.util.ArrayList;

class ResultPhoto {
    ArrayList<PicInfo> photos;
    ArrayList<ReviewInfo> reviews;
    String url;
    String website;

    public ResultPhoto() {
    }

    public ResultPhoto(ArrayList<PicInfo> photos, ArrayList<ReviewInfo> reviews, String url, String website) {
        this.photos = photos;
        this.reviews = reviews;
        this.url = url;
        this.website = website;
    }

    public ArrayList<PicInfo> getPhotos() {
        return photos;
    }

    public ArrayList<ReviewInfo> getReviews() {
        return reviews;
    }

    public String getUrl() {
        return url;
    }

    public String getWebsite() {
        return website;
    }
}
