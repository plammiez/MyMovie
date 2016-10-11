package com.augmentis.ayp.mymovie;

import android.content.Context;

import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

/**
 * Created by Waraporn on 10/3/2016.
 */

public class MyLocationLab {

    private static MyLocationLab instance;
    private List<MyLocations> myLocationsList;

    public static MyLocationLab getInstance(Context context) {

        if (instance == null) {
            instance = new MyLocationLab(context);
        }
        return instance;
    }

    private Context context;

    public MyLocationLab(Context context) {
        this.context = context;
        myLocationsList = new ArrayList<>();
    }

    public List<MyLocations> getMyLocationsList() {
        return myLocationsList;
    }

    public MyLocations getLocationById(int id) {
        for (MyLocations location : myLocationsList) {
            if (location.getCinemaId() == id) {
                return location;
            }
        }
        return null;
    }

    public MyLocations getLocationByDistance(double distance) {
        for (MyLocations location : myLocationsList) {
            if (location.getDistance() == distance) {
                return location;
            }
        }
        return null;
    }

    public void addLocation(MyLocations locations) {
        myLocationsList.add(locations);
    }
}
