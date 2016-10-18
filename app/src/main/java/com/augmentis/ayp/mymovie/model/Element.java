package com.augmentis.ayp.mymovie.model;

import com.google.gson.annotations.SerializedName;

import java.util.List;

/**
 * Created by Waraporn on 10/18/2016.
 */

public class Element {

    @SerializedName("cinema_id")
    String cinema_Id;

    @SerializedName("screens")
    List<Screen> screen;

    public String getCinema_Id() {
        return cinema_Id;
    }

    public void setCinema_Id(String cinema_Id) {
        this.cinema_Id = cinema_Id;
    }

    public List<Screen> getScreen() {
        return screen;
    }

    public void setScreen(List<Screen> screen) {
        this.screen = screen;
    }
}
