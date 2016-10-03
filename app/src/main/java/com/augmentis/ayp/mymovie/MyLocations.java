package com.augmentis.ayp.mymovie;

import java.util.ArrayList;

/**
 * Created by Waraporn on 10/3/2016.
 */
public class MyLocations {

    private int cinemaId;
    private String nameOfLocation;
    private double latitude;
    private double longitude;

    private ArrayList<MyLocations> myLocationsArrayList = new ArrayList<>();

    public int getCinemaId() {
        return cinemaId;
    }

    public void setCinemaId(int cinemaId) {
        this.cinemaId = cinemaId;
    }

    public String getNameOfLocation() {
        return nameOfLocation;
    }

    public void setNameOfLocation(String nameOfLocation) {
        this.nameOfLocation = nameOfLocation;
    }

    public double getLatitude() {
        return latitude;
    }

    public void setLatitude(double latitude) {
        this.latitude = latitude;
    }

    public double getLongitude() {
        return longitude;
    }

    public void setLongitude(double longitude) {
        this.longitude = longitude;
    }

    public ArrayList<MyLocations> getMyLocationsArrayList() {
        return myLocationsArrayList;
    }

    public void setMyLocationsArrayList(ArrayList<MyLocations> myLocationsArrayList) {
        this.myLocationsArrayList = myLocationsArrayList;
    }

    public void addLocation(MyLocations myLocations) {
        myLocationsArrayList.add(myLocations);
    }
}
