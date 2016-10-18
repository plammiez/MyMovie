package com.augmentis.ayp.mymovie.GsonShowtime;

import com.google.gson.annotations.SerializedName;

/**
 * Created by Waraporn on 10/18/2016.
 */

public class MyShowtime {

    @SerializedName("audio")
    String audio;

    @SerializedName("time")
    String time;

    @SerializedName("movie_id")
    String movieID;

    public String getAudio() {
        return audio;
    }

    public void setAudio(String audio) {
        this.audio = audio;
    }

    public String getTime() {
        return time;
    }

    public void setTime(String time) {
        this.time = time;
    }

    public String getMovieID() {
        return movieID;
    }

    public void setMovieID(String movieID) {
        this.movieID = movieID;
    }
}
