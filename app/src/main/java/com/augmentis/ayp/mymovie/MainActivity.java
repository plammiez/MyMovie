package com.augmentis.ayp.mymovie;

import android.support.v4.app.Fragment;

public class MainActivity extends SingleFragmentActivity {

    @Override
    protected Fragment onCreateFragment() {
        return new MainFragment2();
    }
}
