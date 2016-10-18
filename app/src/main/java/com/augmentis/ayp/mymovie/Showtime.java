package com.augmentis.ayp.mymovie;

import com.augmentis.ayp.mymovie.model.Element;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by Waraporn on 10/12/2016.
 */

public class Showtime {

    private String movieID;
    private List<Element> cinemaID;
    private String nameMovie;
    private String nameCinema;
    private String time;
    private String audio;

    public String getMovieID() {
        return movieID;
    }

    public void setMovieID(String movieID) {
        this.movieID = movieID;
    }

    public String getAudio() {
        return audio;
    }

    public void setAudio(String audio) {
        this.audio = audio;
    }

    public String getNameCinema() {
        return nameCinema;
    }

    public void setNameCinema(String nameCinema) {
        this.nameCinema = nameCinema;
    }

    public List<Element> getCinemaID() {
        return cinemaID;
    }

    public void setCinemaID(List<Element> cinemaID) {
        this.cinemaID = cinemaID;
    }

    public String getNameMovie() {
        return nameMovie;
    }

    public void setNameMovie(String nameMovie) {
        this.nameMovie = nameMovie;
    }

    public String getTime() {
        return time;
    }

    public void setTime(String time) {
        this.time = time;
    }
}
