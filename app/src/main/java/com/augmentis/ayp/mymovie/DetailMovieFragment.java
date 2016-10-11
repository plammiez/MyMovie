package com.augmentis.ayp.mymovie;


import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.drawable.BitmapDrawable;
import android.graphics.drawable.Drawable;
import android.location.Location;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.webkit.WebChromeClient;
import android.webkit.WebSettings;
import android.webkit.WebView;
import android.widget.Button;
import android.widget.TextView;

import com.google.android.gms.maps.CameraUpdate;
import com.google.android.gms.maps.CameraUpdateFactory;
import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.OnMapReadyCallback;
import com.google.android.gms.maps.model.LatLng;
import com.google.android.gms.maps.model.LatLngBounds;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.IOException;
import java.io.InputStream;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

/**
 * Created by Waraporn on 10/11/2016.
 */

public class DetailMovieFragment extends Fragment {

    private static final String MOVIE_ID = "MOVIE_ID";
    private static final String KEY_LOCATION = "LOCATION";
    private static final String TAG = "DetailMovie";

    private GoogleMap mGoogleMap;
    private Location mLocation;
    private String id;

    TextView txt_director;
    TextView txt_actor;
    TextView txt_story;
    TextView txt_movie_name;

    Button button_cinema;

    Movie movie;
    List<MyLocations> locations = new ArrayList<>();

    private WebView mVideo;

    public static DetailMovieFragment newInstance() {
        Bundle args = new Bundle();
        DetailMovieFragment fragment = new DetailMovieFragment();
        fragment.setArguments(args);
        return fragment;
    }

    public static DetailMovieFragment newInstance(String movieID, Location location) {
        Bundle args = new Bundle();
        args.putString(MOVIE_ID, movieID);
        args.putParcelable(KEY_LOCATION, location);
        DetailMovieFragment fragment = new DetailMovieFragment();
        fragment.setArguments(args);
        return fragment;
    }

    @Override
    public void onCreate(Bundle bundle) {
        super.onCreate(bundle);
        setHasOptionsMenu(true);

        if (getArguments() != null) {
            id = getArguments().getString(MOVIE_ID);
            mLocation = getArguments().getParcelable(KEY_LOCATION);
        }
    }

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.detail_movie, container, false);

        locations = MyLocationLab.getInstance(getActivity()).getMyLocationsList();

        movie = MovieLab.getInstance(getActivity()).getMovieById(id);

        Log.d(TAG, "id : " + id);
        Log.d(TAG, "name : " + movie.getMovieNameEN());

        mVideo = (WebView) view.findViewById(R.id.movie_teaser);
        mVideo.getSettings().setJavaScriptEnabled(true);//อณุญาตให้ใช้ javascript ได้
        mVideo.getSettings().setPluginState(WebSettings.PluginState.ON);
        mVideo.loadUrl(movie.getUrlTrailer());
        mVideo.setWebChromeClient(new WebChromeClient());

        txt_director = (TextView) view.findViewById(R.id.director);
        txt_actor = (TextView) view.findViewById(R.id.actor);
        txt_story = (TextView) view.findViewById(R.id.story);
        txt_movie_name = (TextView) view.findViewById(R.id.movie_name);

        txt_director.setText(movie.getDirectors());
        txt_actor.setText(movie.getActors());
        txt_story.setText(movie.getSynopsis());
        txt_movie_name.setText(movie.getMovieNameTH());

        button_cinema = (Button) view.findViewById(R.id.button_cinema);
        button_cinema.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
//                Intent intent = ShowtimeActivity.newIntent(getActivity(), movie.getMovieId(),
//                        mLocation);
//                startActivity(intent);
            }
        });

        Drawable drawable = getResources().getDrawable(R.drawable.wp6);
        Bitmap bitmap = ((BitmapDrawable) drawable).getBitmap();
        Bitmap blurredBitmap = BlurBuilder.blur(getActivity(), bitmap);

        view.setBackgroundDrawable(new BitmapDrawable(getResources(), blurredBitmap));
        return view;
    }

    public static double toRadian(double degrees){
        return (degrees * Math.PI) / 180.0d;
    }

    public double calculateDistance(double lat, double lon) {

        // KM: use mile here if you want mile result
        double earthRadius = 6371.0d;

        // Latitude and Longitude of here
        double myLat = mLocation.getLatitude();
        double myLon = mLocation.getLongitude();

        // Calculate
        double dLat = toRadian(lat - myLat);
        double dLng = toRadian(lon - myLon);

        double a = Math.pow(Math.sin(dLat/2), 2)  +
                Math.cos(toRadian(myLat)) * Math.cos(toRadian(lat)) *
                        Math.pow(Math.sin(dLng/2), 2);

        double c = 2 * Math.atan2(Math.sqrt(a), Math.sqrt(1-a));

        // returns result kilometers
        return earthRadius * c;
    }

    private List<Double> orderDistance() {
        List<MyLocations> myLocationsList = MyLocationLab.getInstance(getActivity()).getMyLocationsList();

        List<Double> distance = new ArrayList<>();

        for (int i=0 ; i < myLocationsList.size() ; i++) {
            distance.add(myLocationsList.get(i).getDistance());
        }

        Collections.sort(distance);
        return distance;
    }

    private MyLocations queryDistance(double distance) {
        MyLocations location = MyLocationLab.getInstance(getActivity()).getLocationByDistance(distance);
        return location;
    }

    private void printDistance() {
        List<Double> distance = orderDistance();

        for (int i=0 ; i < 5 ; i++) {
            MyLocations myPlace = queryDistance(distance.get(i));

            Log.d("TAG", "distance near at " + i + " : " + myPlace.getNameENOfLocation()
                    + " km : " + myPlace.getDistance());
        }
    }
}
