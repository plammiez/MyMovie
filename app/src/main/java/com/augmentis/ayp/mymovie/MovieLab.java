package com.augmentis.ayp.mymovie;

import android.content.Context;
import android.os.Environment;

import java.io.File;
import java.util.ArrayList;
import java.util.List;

/**
 * Created by Waraporn on 10/3/2016.
 */

public class MovieLab {

    private static MovieLab instance;
    private List<Movie> movieList;

    public static MovieLab getInstance(Context context) {

        if (instance == null) {
            instance = new MovieLab(context);
        }
        return instance;
    }

    private Context context;

    public MovieLab(Context context) {
        this.context = context;
        movieList = new ArrayList<>();
    }

    public List<Movie> getMovieList() {
        return movieList;
    }

    public Movie getMovieById(String id) {
        for (Movie movie : movieList) {
            if (movie.getMovieId().equals(id)) {
                return movie;
            }
        }
        return null;
    }

    public Movie getMovieByNameTH(String nameTH) {
        for (Movie movie : movieList) {
            if (movie.getMovieNameTH().equals(nameTH)) {
                return movie;
            }
        }
        return null;
    }

    public Movie getMovieByNameEN(String nameEN) {
        for (Movie movie : movieList) {
            if (movie.getMovieNameEN().equals(nameEN)) {
                return movie;
            }
        }
        return null;
    }

    public void addMovie(Movie movie) {
        movieList.add(movie);
    }

    public File getPhotoFile(Movie movie) {
        File externalFilesDir = context.getExternalFilesDir(Environment.DIRECTORY_PICTURES);

        if (externalFilesDir == null) {
            return null;
        }
        return  new File(externalFilesDir, movie.getUrlPoster());
    }
}
