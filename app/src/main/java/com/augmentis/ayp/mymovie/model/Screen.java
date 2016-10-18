package com.augmentis.ayp.mymovie.model;

import com.google.gson.annotations.SerializedName;

import java.util.List;

/**
 * Created by Waraporn on 10/18/2016.
 */

public class Screen {

    @SerializedName("showtimes")
    List<MyShowtime> showtimes;

    public List<MyShowtime> getShowtimes() {
        return showtimes;
    }

    public void setShowtimes(List<MyShowtime> showtimes) {
        this.showtimes = showtimes;
    }
}
