package com.augmentis.ayp.mymovie;

import java.util.ArrayList;

/**
 * Created by Waraporn on 10/3/2016.
 */
public class MyLocations {

    private int cinemaId;
    private String nameTHOfLocation;
    private String nameENOfLocation;
    private String tel;
    private double latitude;
    private double longitude;

    public int getCinemaId() {
        return cinemaId;
    }

    public void setCinemaId(int cinemaId) {
        this.cinemaId = cinemaId;
    }

    public String getNameTHOfLocation() {
        return nameTHOfLocation;
    }

    public void setNameTHOfLocation(String nameTHOfLocation) {
        this.nameTHOfLocation = nameTHOfLocation;
    }

    public String getNameENOfLocation() {
        return nameENOfLocation;
    }

    public void setNameENOfLocation(String nameENOfLocation) {
        this.nameENOfLocation = nameENOfLocation;
    }

    public String getTel() {
        return tel;
    }

    public void setTel(String tel) {
        this.tel = tel;
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

}
