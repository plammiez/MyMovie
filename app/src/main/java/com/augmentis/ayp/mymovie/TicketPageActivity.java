package com.augmentis.ayp.mymovie;

import android.content.Context;
import android.content.Intent;
import android.net.Uri;
import android.support.v4.app.Fragment;

/**
 * Created by Waraporn on 10/13/2016.
 */

public class TicketPageActivity extends SingleFragmentActivity {

    public static Intent newIntent(Context context, Uri uri){
        Intent intent = new Intent(context, TicketPageActivity.class);
        intent.setData(uri);
        return intent;
    }

    @Override
    protected Fragment onCreateFragment() {
        Uri uri = getIntent().getData();
        return TicketPageFragment.newInstance(uri);
    }

}
