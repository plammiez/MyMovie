package com.augmentis.ayp.mymovie.Cinema;

/**
 * Created by Waraporn on 10/12/2016.
 */

public class Cinema {

    private String cinemaId;
    private String cinemaNumber;
    private String nameTHOfLocation;
    private String nameENOfLocation;
    private String tel;
    private String location;
    private double latitude;
    private double longitude;

    public String getCinemaId() {
        return cinemaId;
    }

    public void setCinemaId(String cinemaId) {
        this.cinemaId = cinemaId;
    }

    public String getCinemaNumber() {
        return cinemaNumber;
    }

    public void setCinemaNumber(String cinemaNumber) {
        this.cinemaNumber = cinemaNumber;
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

    public String getLocation() {
        return location;
    }

    public void setLocation(String location) {
        this.location = location;
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

    public void addTimeOfCinema(String cinemaName, String time) {

    }
}
