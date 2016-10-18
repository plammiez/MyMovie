package com.augmentis.ayp.mymovie;

import com.augmentis.ayp.mymovie.model.Element;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by Waraporn on 10/12/2016.
 */

public class Showtime {

    private String movieID;
    private ArrayList<String> cinemaID;
    private String nameMovie;
    private String nameCinema;
    private keepTimeForCinema timeAudio;

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

    public ArrayList<String> getCinemaID() {
        return cinemaID;
    }

    public void setCinemaID(ArrayList<String> cinemaID) {
        this.cinemaID = cinemaID;
    }

    public String getNameMovie() {
        return nameMovie;
    }

    public void setNameMovie(String nameMovie) {
        this.nameMovie = nameMovie;
    }

    public keepTimeForCinema getTimeAudio() {
        return timeAudio;
    }

    public void setTimeAudio(keepTimeForCinema timeAudio) {
        this.timeAudio = timeAudio;
    }
}
