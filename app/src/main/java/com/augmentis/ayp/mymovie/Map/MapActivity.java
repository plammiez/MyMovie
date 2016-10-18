package com.augmentis.ayp.mymovie.Map;

import android.content.Context;
import android.content.Intent;
import android.location.Location;
import android.os.Bundle;
import android.support.v4.app.Fragment;

import com.augmentis.ayp.mymovie.SingleFragmentActivity;


public class MapActivity extends SingleFragmentActivity {

    private static final String KEY_LOCATION = "LOCATION";

    public static Intent newIntent(Context activity, Location location) {
        Intent intent = new Intent(activity, MapActivity.class);
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
            Location location = getIntent().getParcelableExtra(KEY_LOCATION);
            return MapFragment.newInstance(location);
        }
        return MapFragment.newInstance();
    }

}
