package com.augmentis.ayp.mymovie.Showtime;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by Waraporn on 10/18/2016.
 */

class KeepTimeForCinema {

    private String cinemaID;
    private ArrayList<String> time;
    private ArrayList<String> audio;

    public String getCinemaID() {
        return cinemaID;
    }

    public void setCinemaID(String cinemaID) {
        this.cinemaID = cinemaID;
    }

    public ArrayList<String> getAudio() {
        return audio;
    }

    public void setAudio(ArrayList<String> audio) {
        this.audio = audio;
    }

    public ArrayList<String> getTime() {
        return time;
    }

    public void setTime(ArrayList<String> time) {
        this.time = time;
    }
}
