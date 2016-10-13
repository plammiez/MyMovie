package com.augmentis.ayp.mymovie;

import android.content.Context;
import android.util.Log;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by Waraporn on 10/3/2016.
 */

public class ShowtimeLab {

    private static final String TAG = "ShowtimeLab";
    private static ShowtimeLab instance;
    private List<Showtime> myShowtimeList;

    public static ShowtimeLab getInstance(Context context) {
        if (instance == null) {
            instance = new ShowtimeLab(context);
        }
        return instance;
    }

    private Context context;

    public ShowtimeLab(Context context) {
        this.context = context;
        myShowtimeList = new ArrayList<>();
    }

    public List<Showtime> getMyShowtimeList() {
        return myShowtimeList;
    }

    public Showtime getShowtimeByMovieId(String id) {
        for (Showtime showtime : myShowtimeList) {
            if (showtime.getMovieID().equals(id)) {
                return showtime;
            }
        }
        Log.d(TAG,id);
        return null;
    }


    public void addShowtime(Showtime showtime) {
        myShowtimeList.add(showtime);
    }

    public void clearShowTime() {
        myShowtimeList.clear();
    }

}
