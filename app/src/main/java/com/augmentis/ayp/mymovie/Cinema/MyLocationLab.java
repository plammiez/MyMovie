package com.augmentis.ayp.mymovie.Cinema;

import android.content.Context;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by Waraporn on 10/3/2016.
 */

public class MyLocationLab {

    private static MyLocationLab instance;
    private List<MyLocations> myLocationsList;
    private List<MyLocations> myNearyList;

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
        myNearyList = new ArrayList<>();
    }

    public List<MyLocations> getMyLocationsList() {
        return myLocationsList;
    }

    public MyLocations getLocationById(String id) {
        for (MyLocations location : myLocationsList) {
            if (location.getCinemaId().equals(id)) {
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

    public void addNearyLocation(MyLocations locations) {
//        if (myNearyList.size() != 0) {
//            for (MyLocations myLocations : myNearyList) {
//                if (!myLocations.getNameENOfLocation().equals(locations.getNameENOfLocation())) {
//                    myNearyList.add(locations);
//                    break;
//                }
//            }
//        } else {
            myNearyList.add(locations);
//        }
    }

    public void clearNearyLocation() {
        myNearyList.clear();
    }

    public List<MyLocations> getNearyLocation() {
        return myNearyList;
    }


}
