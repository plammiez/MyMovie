package com.augmentis.ayp.mymovie;

import android.annotation.SuppressLint;
import android.content.Context;
import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.drawable.BitmapDrawable;
import android.graphics.drawable.Drawable;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.webkit.WebChromeClient;
import android.webkit.WebSettings;
import android.webkit.WebView;
import android.widget.LinearLayout;
import android.widget.TextView;


/**
 * Created by Waraporn on 9/22/2016.
 */
public class DetailMovieActivity extends AppCompatActivity{

    private static final String MOVIE_ID = "MOVIE_ID";
    private static final String TAG = "DetailMovieActivity";

    String id;

    TextView txt_director;
    TextView txt_actor;
    TextView txt_story;
    TextView txt_movie_name;

    Movie movie;

    private WebView mVideo;

    public static Intent newIntent(Context context, String movieId) {
        Intent intent = new Intent(context, DetailMovieActivity.class);
        intent.putExtra(MOVIE_ID, movieId);
        return intent;
    }

    @SuppressLint("NewApi")
    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.detail_movie);
        LinearLayout linearLayout = (LinearLayout) findViewById(R.id.linear_movie_detail);

        Bundle bundle = getIntent().getExtras();
        if (bundle != null) {
            id = bundle.getString(MOVIE_ID);

            movie = MovieLab.getInstance(this).getMovieById(id);

            Log.d(TAG, "id : " + id);
            Log.d(TAG, "name : " + movie.getMovieNameEN());
        }


        mVideo = (WebView) findViewById(R.id.movie_teaser);
        mVideo.getSettings().setJavaScriptEnabled(true);//อณุญาตให้ใช้ javascript ได้
        mVideo.getSettings().setPluginState(WebSettings.PluginState.ON);
        mVideo.loadUrl(movie.getUrlTrailer());
        mVideo.setWebChromeClient(new WebChromeClient());

        txt_director = (TextView) findViewById(R.id.director);
        txt_actor = (TextView) findViewById(R.id.actor);
        txt_story = (TextView) findViewById(R.id.story);
        txt_movie_name = (TextView) findViewById(R.id.movie_name);

        txt_director.setText(movie.getDirectors());
        txt_actor.setText(movie.getActors());
        txt_story.setText(movie.getSynopsis());
        txt_movie_name.setText(movie.getMovieNameTH());

        Drawable drawable = getResources().getDrawable(R.drawable.wp6);
        Bitmap bitmap = ((BitmapDrawable)drawable).getBitmap();
        Bitmap blurredBitmap = BlurBuilder.blur( this, bitmap );

        linearLayout.setBackgroundDrawable( new BitmapDrawable( getResources(), blurredBitmap ) );
    }
}
