package com.example.shlokpatel.travel2_mindforks;

class ReviewInfo {
    String relative_time_description,text;

    public ReviewInfo() {
    }

    public ReviewInfo(String relative_time_description, String text) {
        this.relative_time_description = relative_time_description;
        this.text = text;
    }

    public String getRelative_time_description() {
        return relative_time_description;
    }

    public String getText() {
        return text;
    }
}
