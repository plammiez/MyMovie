package com.augmentis.ayp.mymovie.Movie;

import android.os.Environment;

import java.io.File;
import java.util.ArrayList;
import java.util.StringTokenizer;
import java.util.UUID;

/**
 * Created by Waraporn on 9/22/2016.
 */
public class Movie {

    private String movieId;
    private String movieNameTH;
    private String movieNameEN;
    private String urlPoster;
    private String urlTrailer;
    private String synopsis;
    private String genres;
    private String directors;
    private String actors;

    public String getMovieId() {
        return movieId;
    }

    public void setMovieId(String movieId) {
        this.movieId = movieId;
    }

    public String getMovieNameTH() {
        return movieNameTH;
    }

    public void setMovieNameTH(String movieNameTH) {
        this.movieNameTH = movieNameTH;
    }

    public String getMovieNameEN() {
        return movieNameEN;
    }

    public void setMovieNameEN(String movieNameEN) {
        this.movieNameEN = movieNameEN;
    }

    public String getUrlPoster() {
        return urlPoster;
    }

    public void setUrlPoster(String urlPoster) {
        this.urlPoster = urlPoster;
    }

    public String getUrlTrailer() {
        return urlTrailer;
    }

    public void setUrlTrailer(String urlTrailer) {
        this.urlTrailer = urlTrailer;
    }

    public String getSynopsis() {
        return synopsis;
    }

    public void setSynopsis(String synopsis) {
        this.synopsis = synopsis;
    }

    public String getGenres() {
        return genres;
    }

    public void setGenres(String genres) {
        this.genres = genres;
    }

    public String getDirectors() {
        return directors;
    }

    public void setDirectors(String directors) {
        this.directors = directors;
    }

    public String getActors() {
        return actors;
    }

    public void setActors(String actors) {
        this.actors = actors;
    }

}
