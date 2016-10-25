package com.augmentis.ayp.mymovie.Map;

import android.content.Context;
import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.drawable.BitmapDrawable;
import android.graphics.drawable.Drawable;
import android.location.Location;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.LinearLayout;
import android.widget.Toast;

import com.augmentis.ayp.mymovie.Decoration.BlurBuilder;
import com.augmentis.ayp.mymovie.R;

/**
 * Created by Wilailux on 10/12/2016.
 */

public class MapAndListActivity extends AppCompatActivity implements MapFragment.CallBack {

    private static final String KEY_LOCATION = "LOCATION";
    LinearLayout map_and_list;

    public static Intent newIntent(Context activity, Location location) {
        Intent intent = new Intent(activity, MapAndListActivity.class);
        intent.putExtra(KEY_LOCATION, location);
        return intent;
    }

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.map_and_list);
        map_and_list = (LinearLayout) findViewById(R.id.map_list);

        if (getIntent() != null) {
            Location location = getIntent().getParcelableExtra(KEY_LOCATION);

            FragmentManager fragmentManager = getSupportFragmentManager();
            Fragment fragment = fragmentManager.findFragmentById(R.id.fragment_container_1);

            if (fragment == null) {
                Fragment newFragment = MapFragment.newInstance(location);
                fragmentManager.beginTransaction().add(R.id.fragment_container_1, newFragment).commit();
            }

            View fragmentContainer2 = findViewById(R.id.fragment_container_2);
            if (fragmentContainer2 != null) {
                fragment = fragmentManager.findFragmentById(R.id.fragment_container_2);

                if (fragment == null) {
                    Fragment newFragment = MapListFragment.newInstance();
                    fragmentManager.beginTransaction().add(R.id.fragment_container_2, newFragment).commit();
                }
            }
        }

        Drawable drawable = getResources().getDrawable(R.drawable.wp8);
        Bitmap bitmap = ((BitmapDrawable) drawable).getBitmap();
        Bitmap blurredBitmap = BlurBuilder.blur(this, bitmap);

        map_and_list.setBackgroundDrawable(new BitmapDrawable(getResources(), blurredBitmap));
    }

    @Override
    public void refreshList() {
        Toast.makeText(this, "Refresh", Toast.LENGTH_SHORT).show();
        FragmentManager fragmentManager = getSupportFragmentManager();
        Fragment newFragment = MapListFragment.newInstance();
        fragmentManager.beginTransaction().replace(R.id.fragment_container_2, newFragment).commit();
    }
}
