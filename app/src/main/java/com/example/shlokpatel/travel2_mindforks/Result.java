package com.example.shlokpatel.travel2_mindforks;

import java.util.ArrayList;

public class Result {
    ArrayList<CityDesc> results;
    String status;

    public Result() {
    }

    public Result(ArrayList results, String status) {
        this.results = results;
        this.status = status;
    }

    public ArrayList getResults() {
        return results;
    }

    public String getStatus() {
        return status;
    }
}
