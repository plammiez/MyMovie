package com.augmentis.ayp.mymovie;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.FragmentActivity;
import android.support.v7.app.AppCompatActivity;

/**
 * Created by Wilailux on 10/19/2016.
 */

public class ComingSoon extends AppCompatActivity {

    private static final String KEY_COMINGSOON = "COMINGSOON";

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.coming_soon);
    }

    public static Intent newIntent(Context context, String mID) {
        Intent intent = new Intent(context, ComingSoon.class);
        intent.putExtra(KEY_COMINGSOON, mID);
        return intent;
    }
}
