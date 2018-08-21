package com.example.shlokpatel.travel2_mindforks;

import android.app.Activity;
import android.content.Intent;
import android.net.Uri;
import android.support.v4.app.FragmentActivity;
import android.support.v4.view.ViewPager;
import android.support.v4.widget.NestedScrollView;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.StaggeredGridLayoutManager;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.ProgressBar;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;

import com.google.android.gms.maps.CameraUpdateFactory;
import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.MapView;
import com.google.android.gms.maps.OnMapReadyCallback;
import com.google.android.gms.maps.SupportMapFragment;
import com.google.android.gms.maps.model.LatLng;
import com.google.android.gms.maps.model.MarkerOptions;
import com.google.gson.Gson;
import com.wang.avi.AVLoadingIndicatorView;

import java.io.IOException;
import java.util.ArrayList;
import java.util.Locale;

import me.relex.circleindicator.CircleIndicator;
import okhttp3.Call;
import okhttp3.Callback;
import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.Response;

public class PlaceInfo extends AppCompatActivity {
    RecyclerView recyclerView;
    ViewPager viewPager;
    Button button;
    CircleIndicator circleIndicator;
    AVLoadingIndicatorView spinner2;
    ImageView imageView;
    TextView websiteView;
    Double lat, lng;
    NestedScrollView nestedScrollView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_place_info);
        Log.e("TAG", "onCreate: 123");
        String address = getIntent().getStringExtra("ADDRESS");
        String placeName = getIntent().getStringExtra("NAME");
        this.getSupportActionBar().setTitle(placeName);
        nestedScrollView = findViewById(R.id.infoView);
        button = findViewById(R.id.siteBtn);
        nestedScrollView.setVisibility(View.GONE);
        spinner2 = findViewById(R.id.spinner2);
        spinner2.show();
        imageView = findViewById(R.id.placeMarker);
        imageView.setClickable(true);
        websiteView = findViewById(R.id.siteAddress);
        websiteView.setText("Address:" + address);
        CityLocation cityLocation = (CityLocation) getIntent().getSerializableExtra("LATLNG");
        lat = cityLocation.getLat();
        lng = cityLocation.getLng();
        String place_id = getIntent().getStringExtra("PLACEID");
        OkHttpClient client = new OkHttpClient();
        doPicShit(client, place_id);
    }

    private void doPicShit(OkHttpClient client, String place_id) {
        final Request request = new Request.Builder()
        .url("https://maps.googleapis.com/maps/api/place/details/json?placeid=" + place_id + "&key=" + Constants.REVIEW_KEY)
                .build();
        client.newCall(request).enqueue(new Callback() {
            @Override
            public void onFailure(Call call, IOException e) {
                e.printStackTrace();
            }

            @Override
            public void onResponse(Call call, Response response) throws IOException {
                String resultJson = response.body().string();
                Log.e("TAG", "onResponse 2: " + resultJson);
                Gson gson = new Gson();
                ResultFinal resultFinal = gson.fromJson(resultJson, ResultFinal.class);
                final ResultPhoto resultPhoto = resultFinal.getResult();
                final ArrayList<ReviewInfo> reviewInfos = resultPhoto.getReviews();
                final ArrayList<PicInfo> arrayPic = resultPhoto.getPhotos();
                final String mapUrl = resultPhoto.getUrl();
                final String site = resultPhoto.getWebsite();
                PlaceInfo.this.runOnUiThread(new Runnable() {
                    @Override
                    public void run() {
                        circleIndicator = findViewById(R.id.circleIndicator);
                        viewPager = findViewById(R.id.viewPager);
                        PhotoAdapter photoAdapter = new PhotoAdapter(arrayPic, getBaseContext());
                        viewPager.setAdapter(photoAdapter);
                        circleIndicator.setViewPager(viewPager);
                        recyclerView = findViewById(R.id.reviewView);
                        /*recyclerView.setLayoutManager(new StaggeredGridLayoutManager(2,StaggeredGridLayoutManager.VERTICAL));*/
                        recyclerView.setLayoutManager(new LinearLayoutManager(getBaseContext()));
                        ReviewAdapter reviewAdapter = new ReviewAdapter(reviewInfos, getBaseContext());
                       /* if(site!=null)
                            //site address
                        else
                            //site*/
                        imageView.setOnClickListener(new View.OnClickListener() {
                            @Override
                            public void onClick(View v) {
                                Intent intent = new Intent();
                                intent.setAction(Intent.ACTION_VIEW);
                                intent.setData(Uri.parse(mapUrl));
                                startActivity(intent);
                            }
                        });
                        button.setOnClickListener(new View.OnClickListener() {
                            @Override
                            public void onClick(View v) {
                                if (resultPhoto.getWebsite() != null) {
                                    Intent intent = new Intent();
                                    intent.setAction(Intent.ACTION_VIEW);
                                    intent.setData(Uri.parse(resultPhoto.getWebsite()));
                                    startActivity(intent);
                                }
                                else
                                    Toast.makeText(PlaceInfo.this, "Site not available", Toast.LENGTH_SHORT).show();
                            }

                        });
                        recyclerView.setAdapter(reviewAdapter);
                        spinner2.hide();
                        nestedScrollView.setVisibility(View.VISIBLE);

                    }
                });
            }
        });

    }

}
