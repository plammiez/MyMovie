package com.augmentis.ayp.mymovie;

import android.annotation.SuppressLint;
import android.content.Context;
import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.drawable.BitmapDrawable;
import android.graphics.drawable.Drawable;
import android.location.Location;
import android.os.Bundle;
import android.os.PersistableBundle;
import android.support.v4.app.Fragment;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.webkit.WebChromeClient;
import android.webkit.WebSettings;
import android.webkit.WebView;
import android.widget.Button;
import android.widget.LinearLayout;
import android.widget.TextView;


/**
 * Created by Waraporn on 9/22/2016.
 */
public class DetailMovieActivity extends SingleFragmentActivity{

    private static final String MOVIE_ID = "MOVIE_ID";
    private static final String KEY_LOCATION = "LOCATION";

    private static final String TAG = "DetailMovieActivity";

    public static Intent newIntent(Context context, String movieId, Location location) {
        Intent intent = new Intent(context, DetailMovieActivity.class);
        intent.putExtra(MOVIE_ID, movieId);
        intent.putExtra(KEY_LOCATION, location);
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
            Location location = getIntent().getParcelableExtra(KEY_LOCATION);
            return DetailMovieFragment.newInstance(movieID, location);
        }
        return DetailMovieFragment.newInstance();
    }
}
