package com.augmentis.ayp.mymovie;

import android.content.Context;
import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.widget.VideoView;

/**
 * Created by Waraporn on 9/22/2016.
 */
public class DetailMovieActivity extends AppCompatActivity{

//    private static final String MOVIE_ID = "MOVIE_ID";
    private VideoView mVideo;

    public static Intent newIntent(Context activity) {
        Intent intent = new Intent(activity, DetailMovieActivity.class);
//        intent.putExtra(MOVIE_ID, id);
        return intent;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.detail_movie);

        mVideo = (VideoView) findViewById(R.id.movie_teaser);
        String videoURL = "https://youtu.be/1NhwGqvoKaU";
        Uri uri = Uri.parse(videoURL);
        mVideo.setVideoURI(uri);
        mVideo.start();
    }
}
