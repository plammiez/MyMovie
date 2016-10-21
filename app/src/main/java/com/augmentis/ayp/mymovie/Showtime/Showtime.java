package com.augmentis.ayp.mymovie.Showtime;

import java.util.ArrayList;

/**
 * Created by Waraporn on 10/12/2016.
 */

public class Showtime {

    private String movieID;
//    private ArrayList<String> cinemaID;
    private String cinemaID;
    private String nameMovie;
    private String nameCinema;
    private KeepTimeForCinema timeAudio;

    public String getMovieID() {
        return movieID;
    }

    public void setMovieID(String movieID) {
        this.movieID = movieID;
    }

    public String getNameCinema() {
        return nameCinema;
    }

    public void setNameCinema(String nameCinema) {
        this.nameCinema = nameCinema;
    }

    public String getCinemaID() {
        return cinemaID;
    }

    public void setCinemaID(String cinemaID) {
        this.cinemaID = cinemaID;
    }

    public String getNameMovie() {
        return nameMovie;
    }

    public void setNameMovie(String nameMovie) {
        this.nameMovie = nameMovie;
    }

    public KeepTimeForCinema getTimeAudio() {
        return timeAudio;
    }

    public void setTimeAudio(KeepTimeForCinema timeAudio) {
        this.timeAudio = timeAudio;
    }
}
