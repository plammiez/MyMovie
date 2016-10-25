package com.augmentis.ayp.mymovie.Showtime;

import android.content.Context;
import android.util.Log;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by Waraporn on 10/3/2016.
 */

public class KeepTimeForCinemaLab {

    private static final String TAG = "KeeptimeLab";
    private static KeepTimeForCinemaLab instance;
    private List<KeepTimeForCinema> myKeeptimeList;

    public static KeepTimeForCinemaLab getInstance(Context context) {
        if (instance == null) {
            instance = new KeepTimeForCinemaLab(context);
        }
        return instance;
    }

    private Context context;

    public KeepTimeForCinemaLab(Context context) {
        this.context = context;
        myKeeptimeList = new ArrayList<>();
    }

    public List<KeepTimeForCinema> getMyKeepwtimeList() {
        return myKeeptimeList;
    }

    public KeepTimeForCinema getKeeptimeById(String id) {
        for (KeepTimeForCinema forCinema : myKeeptimeList) {
            if (forCinema.getCinemaID().equals(id)) {
                return forCinema;
            }
        }
        return null;
    }

    public void addKeeptime(KeepTimeForCinema forCinema) {
        if (myKeeptimeList.size() != 0) {
            for (KeepTimeForCinema mCinema : myKeeptimeList) {
                if (!mCinema.getCinemaID().equals(forCinema.getCinemaID())) {
                    myKeeptimeList.add(forCinema);
                    break;
                }
            }
        } else {
            myKeeptimeList.add(forCinema);
        }
    }

    public void clearKeepTime() {
        myKeeptimeList.clear();
    }

}

