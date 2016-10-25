package com.augmentis.ayp.mymovie.Cinema;

/**
 * Created by Waraporn on 10/3/2016.
 */
public class MyLocations {

    private String cinemaId;
    private String cinemaNumber;
    private String nameTHOfLocation;
    private String nameENOfLocation;
    private String tel;
    private double latitude;
    private double longitude;
    private double distance;

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

    public double getDistance() {
        return distance;
    }

    public void setDistance(double distance) {
        this.distance = distance;
    }

}
