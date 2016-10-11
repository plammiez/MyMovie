package com.augmentis.ayp.mymovie;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.support.v4.app.Fragment;

/**
 * Created by Waraporn on 10/11/2016.
 */

public class ShowtimeActivity extends SingleFragmentActivity {

    private static final String MOVIE_ID = "MOVIE_ID";
    private static final String CINEMA_ID = "CINEMA_ID";

    public static Intent newIntent(Context activity, String movieID, String cinemaID) {
        Intent intent = new Intent(activity, ShowtimeActivity.class);
        intent.putExtra(MOVIE_ID, movieID);
        intent.putExtra(CINEMA_ID, cinemaID);
        return intent;
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    @Override
    protected Fragment onCreateFragment() {
        if (getIntent() != null) {
            String movieID = getIntent().getStringExtra(MOVIE_ID);
            String cinemaID = getIntent().getStringExtra(CINEMA_ID);
            return ShowtimeFragment.newInstance(movieID, cinemaID);
        }
        return ShowtimeFragment.newInstance();
    }


}
