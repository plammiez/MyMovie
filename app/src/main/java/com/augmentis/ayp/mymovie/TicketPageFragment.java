package com.augmentis.ayp.mymovie;

import android.net.Uri;
import android.os.Bundle;
import android.support.v4.app.Fragment;

/**
 * Created by Waraporn on 10/13/2016.
 */

public class TicketPageFragment extends Fragment {

    public static TicketPageFragment newInstance(Uri uri) {

        Bundle args = new Bundle();

        TicketPageFragment fragment = new TicketPageFragment();
        fragment.setArguments(args);
        return fragment;
    }
}
