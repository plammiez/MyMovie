package com.augmentis.ayp.mymovie.Showtime;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.support.v4.app.Fragment;

import com.augmentis.ayp.mymovie.SingleFragmentActivity;

/**
 * Created by Waraporn on 10/11/2016.
 */

public class ShowtimeActivity extends SingleFragmentActivity {

    private static final String MOVIE_ID = "MOVIE_ID";

    public static Intent newIntent(Context activity, String movieID) {
        Intent intent = new Intent(activity, ShowtimeActivity.class);
        intent.putExtra(MOVIE_ID, movieID);
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
            return ShowtimeFragment.newInstance(movieID);
        }
        return ShowtimeFragment.newInstance();
    }
}
