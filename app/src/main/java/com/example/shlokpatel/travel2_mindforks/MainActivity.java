package com.example.shlokpatel.travel2_mindforks;

import android.graphics.Color;
import android.graphics.Typeface;
import android.os.AsyncTask;
import android.os.Handler;
import android.support.design.widget.FloatingActionButton;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.view.inputmethod.InputMethodManager;
import android.widget.AutoCompleteTextView;
import android.widget.EditText;
import android.widget.ProgressBar;
import android.widget.Spinner;
import android.widget.Toast;

import com.daprlabs.cardstack.SwipeDeck;
import com.daprlabs.cardstack.SwipeFrameLayout;
import com.google.android.gms.common.api.Status;
import com.google.android.gms.location.places.Place;
import com.google.android.gms.location.places.ui.PlaceAutocompleteFragment;
import com.google.android.gms.location.places.ui.PlaceSelectionListener;
import com.google.gson.Gson;
import com.wang.avi.AVLoadingIndicatorView;

import java.io.IOException;
import java.util.ArrayList;

import okhttp3.Call;
import okhttp3.Callback;
import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.Response;

public class MainActivity extends AppCompatActivity {
    SwipeDeck swipeDeck;
    String city = "";
    AVLoadingIndicatorView spinner;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        this.getSupportActionBar().hide();
        spinner = findViewById(R.id.spinner);
        final InputMethodManager inputManager = (InputMethodManager)
                getSystemService(this.INPUT_METHOD_SERVICE);
        final OkHttpClient okHttpClient = new OkHttpClient();
        final PlaceAutocompleteFragment autocompleteFragment = (PlaceAutocompleteFragment)
                getFragmentManager().findFragmentById(R.id.place_autocomplete_fragment);
        int color = Color.WHITE;
        ((EditText) autocompleteFragment.getView().findViewById(R.id.place_autocomplete_search_input)).setTextColor(color);
        autocompleteFragment.setOnPlaceSelectedListener(new PlaceSelectionListener() {
            @Override
            public void onPlaceSelected(Place place) {
                // TODO: Get info about the selected place.
                Log.i("TAG", "Place: " + place.getName());
                city = (String) place.getName();
                doTheShit(okHttpClient, city);
                spinner.show();

            }

            @Override
            public void onError(Status status) {
                // TODO: Handle the error.
                Log.i("TAG", "An error occurred: " + status);
            }
        });

    }

    private void doTheShit(OkHttpClient okHttpClient, String city) {
        final Request request = new Request.Builder()
                .url("https://maps.googleapis.com/maps/api/place/textsearch/json?query=" + city + "+points+of+interest&language=en&key=" + Constants.API_KEY)
                .build();

        okHttpClient.newCall(request).enqueue(new Callback() {
            @Override
            public void onFailure(Call call, IOException e) {
                e.printStackTrace();
            }

            @Override
            public void onResponse(final Call call, Response response) throws IOException {
                String json = response.body().string();
                Log.e("MAIN", "onResponse: " + json);
                Gson gson = new Gson();
                final Result result = gson.fromJson(json, Result.class);
                final ArrayList<CityDesc> arrayList = result.getResults();
                MainActivity.this.runOnUiThread(new Runnable() {
                    @Override
                    public void run() {
                        swipeDeck = findViewById(R.id.swipe_deck);
                        Adapter adapter = new Adapter(arrayList, getBaseContext());
                        swipeDeck.setAdapter(adapter);
                        spinner.hide();
                        swipeDeck.setEventCallback(new SwipeDeck.SwipeEventCallback() {
                            @Override
                            public void cardSwipedLeft(int position) {
                                Log.e("TAG", "cardSwipedLeft: left " + position);
                                final Toast toast = Toast.makeText(MainActivity.this, arrayList.size() - position - 1 + " cards left", Toast.LENGTH_SHORT);
                                toast.show();
                                Handler handler = new Handler();
                                handler.postDelayed(new Runnable() {
                                    @Override
                                    public void run() {
                                        toast.cancel();
                                    }
                                }, 800);
                            }

                            @Override
                            public void cardSwipedRight(int position) {
                                Log.e("TAG", "cardSwipedLeft: right " + position);
                                final Toast toast = Toast.makeText(MainActivity.this, arrayList.size() - position - 1 + " cards left", Toast.LENGTH_SHORT);
                                toast.show();
                                Handler handler = new Handler();
                                handler.postDelayed(new Runnable() {
                                    @Override
                                    public void run() {
                                        toast.cancel();
                                    }
                                }, 800);

                            }

                            @Override
                            public void cardsDepleted() {
//                                editText.setVisibility(View.VISIBLE);
                            }

                            @Override
                            public void cardActionDown() {

                            }

                            @Override
                            public void cardActionUp() {

                            }
                        });
                        if(arrayList.size()==0)
                            Toast.makeText(MainActivity.this, "No places to roam around", Toast.LENGTH_SHORT).show();
                        else
                            Toast.makeText(MainActivity.this, "Results found :" + arrayList.size(), Toast.LENGTH_SHORT).show();
                    }
                });
            }
        });
    }


}
