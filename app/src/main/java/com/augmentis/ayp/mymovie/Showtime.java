package com.augmentis.ayp.mymovie;

import java.util.ArrayList;

/**
 * Created by Waraporn on 10/12/2016.
 */

public class Showtime {

    private String movieID;
    private String cinemaID;
    private String nameMovie;
    private String nameCinema;
    private ArrayList<String> time;
    private ArrayList<String> audio;

    public String getMovieID() {
        return movieID;
    }

    public void setMovieID(String movieID) {
        this.movieID = movieID;
    }

    public ArrayList<String> getAudio() {
        return audio;
    }

    public void setAudio(ArrayList<String> audio) {
        this.audio = audio;
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

    public ArrayList<String> getTime() {
        return time;
    }

    public void setTime(ArrayList<String> time) {
        this.time = time;
    }
}
