package com.augmentis.ayp.mymovie;

import android.support.v4.app.Fragment;
import android.os.Bundle;

public class MainActivity extends SingleFragmentActivity {

    @Override
    protected Fragment onCreateFragment() {
        return new Mainfragment();
    }
}
