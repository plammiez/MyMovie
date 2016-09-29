package com.augmentis.ayp.mymovie;

import android.content.Context;
import android.content.Intent;
import android.location.Location;
import android.support.v4.app.Fragment;
import android.os.Bundle;


public class MapActivity extends SingleFragmentActivity{

    private static final int REQUEST_PERMISSION_LOCATION = 231;
    private static final String KEY_LOCATION = "LOCATION";
    private static final String KEY_LAT = "LAT";
    private static final String KEY_LONG = "LONG";

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
