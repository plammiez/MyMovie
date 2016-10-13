package com.augmentis.ayp.mymovie;

import android.content.Context;
import android.util.Log;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by Waraporn on 10/3/2016.
 */

public class CinemaLab {

    private static final String TAG = "CinemaLab";
    private static CinemaLab instance;
    private List<Cinema> myCinemaList;

    public static CinemaLab getInstance(Context context) {

        if (instance == null) {
            instance = new CinemaLab(context);
        }
        return instance;
    }

    private Context context;

    public CinemaLab(Context context) {
        this.context = context;
        myCinemaList = new ArrayList<>();
    }

    public List<Cinema> getMyCinemaList() {
        return myCinemaList;
    }

    public Cinema getCinemaById(String id) {
        for (Cinema cinema : myCinemaList) {
            if (cinema.getCinemaId().equals(id)) {
                return cinema;
            }
        }
        Log.d(TAG,id);
        return null;
    }


    public void addCinema(Cinema cinema) {
        myCinemaList.add(cinema);
    }

}
