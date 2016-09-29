package com.augmentis.ayp.mymovie;

import android.location.Location;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import com.google.android.gms.maps.CameraUpdate;
import com.google.android.gms.maps.CameraUpdateFactory;
import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.OnMapReadyCallback;
import com.google.android.gms.maps.SupportMapFragment;
import com.google.android.gms.maps.model.LatLng;
import com.google.android.gms.maps.model.LatLngBounds;
import com.google.android.gms.maps.model.MarkerOptions;

/**
 * Created by Waraporn on 9/29/2016.
 */

public class MapFragment extends SupportMapFragment {

    private static final String KEY_LOCATION = "LOCATION";

    private GoogleMap mGoogleMap;
    private Location mLocation;

    public static MapFragment newInstance() {
        Bundle args = new Bundle();
        MapFragment fragment = new MapFragment();
        fragment.setArguments(args);
        return fragment;
    }

    public static MapFragment newInstance(Location location) {
        Bundle args = new Bundle();
        args.putParcelable(KEY_LOCATION, location);
        MapFragment fragment = new MapFragment();
        fragment.setArguments(args);
        return fragment;
    }

    @Override
    public void onCreate(Bundle bundle) {
        super.onCreate(bundle);
        setHasOptionsMenu(true);

        if (getArguments() != null) {
            mLocation = getArguments().getParcelable(KEY_LOCATION);
        }

        getMapAsync(new OnMapReadyCallback() {
            @Override
            public void onMapReady(GoogleMap googleMap) {
                mGoogleMap = googleMap;
                updateMapUI();
            }
        });
    }

    @Override
    public View onCreateView(LayoutInflater layoutInflater, ViewGroup viewGroup, Bundle bundle) {
        return super.onCreateView(layoutInflater, viewGroup, bundle);
    }

    private void updateMapUI() {
        mGoogleMap.clear();

        LatLngBounds.Builder builder = new LatLngBounds.Builder();

        if (mLocation != null) {
            plotMarker(mLocation, builder);
        }

        int margin = getResources().getDimensionPixelSize(R.dimen.map_inset_margin);
        CameraUpdate cameraUpdate = CameraUpdateFactory.newLatLngBounds(
                builder.build(), margin);

        mGoogleMap.animateCamera(cameraUpdate);
    }

    private void plotMarker(Location location, final LatLngBounds.Builder builder) {
        LatLng itemPoint = new LatLng(location.getLatitude(), location.getLongitude());
        MarkerOptions itemMarkerOptions = new MarkerOptions().position(itemPoint);
        mGoogleMap.addMarker(itemMarkerOptions);
        builder.include(itemPoint);
    }
}
