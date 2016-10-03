package com.augmentis.ayp.mymovie;

import android.location.Location;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import com.google.android.gms.maps.CameraUpdate;
import com.google.android.gms.maps.CameraUpdateFactory;
import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.OnMapReadyCallback;
import com.google.android.gms.maps.SupportMapFragment;
import com.google.android.gms.maps.model.BitmapDescriptor;
import com.google.android.gms.maps.model.BitmapDescriptorFactory;
import com.google.android.gms.maps.model.LatLng;
import com.google.android.gms.maps.model.LatLngBounds;
import com.google.android.gms.maps.model.MarkerOptions;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.IOException;
import java.io.InputStream;
import java.util.ArrayList;

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

    public ArrayList<MyLocations> loadCinemaFromJSON() {
        ArrayList<MyLocations> locList = new ArrayList<>();
        String json = null;
        try {
            InputStream inputStream = getActivity().getAssets().open("cinema_all.json");
            int size = inputStream.available();
            byte[] buffer = new byte[size];
            inputStream.read(buffer);
            inputStream.close();
            json = new String(buffer, "UTF-8");
        } catch (IOException ex) {
            ex.printStackTrace();
            return null;
        }
        try {
            JSONObject obj = new JSONObject(json);
            JSONArray jsonArray = obj.getJSONArray("elements");

            for (int i = 0; i < jsonArray.length(); i++) {
                JSONObject jsonObject = jsonArray.getJSONObject(i);

                MyLocations location = new MyLocations();
                location.setCinemaId(jsonObject.getInt("cinema_id"));
                location.setNameOfLocation(jsonObject.getString("short_name_en"));

                String aLocate = jsonObject.getString("location");
                Log.d("TAG", "AT LOCATION : " + aLocate);

                String[] latLon = aLocate.split(",");
                location.setLatitude(Double.parseDouble(latLon[0]));
                location.setLongitude(Double.parseDouble(latLon[1]));

                Log.d("TAG", "AT LATITUDE : " + latLon[0]);
                Log.d("TAG", "AT LONGITUDE : " + latLon[1]);

                Location mLocate = new Location("");
                mLocate.setLatitude(Double.parseDouble(latLon[0]));
                mLocate.setLongitude(Double.parseDouble(latLon[1]));

                LatLngBounds.Builder builder = new LatLngBounds.Builder();
                plotMarker(mLocate, builder);

                //Add your values in your `ArrayList` as below:
                locList.add(location);

                MyLocationLab.getInstance(getActivity()).addLocation(location);

            }
        } catch (JSONException e) {
            e.printStackTrace();
        }

        return locList;
    }

    private void updateMapUI() {
        mGoogleMap.clear();

        LatLngBounds.Builder builder = new LatLngBounds.Builder();

        if (mLocation != null) {
            plotMyMarker(mLocation, builder);
            loadCinemaFromJSON();
        }

//        int margin = getResources().getDimensionPixelSize(R.dimen.map_inset_margin);
        CameraUpdate cameraUpdate = CameraUpdateFactory
                .newLatLngZoom(new LatLng(mLocation.getLatitude(), mLocation.getLongitude()), 13.2f);

        mGoogleMap.animateCamera(cameraUpdate);
    }

    private void plotMyMarker(Location location, final LatLngBounds.Builder builder) {
        LatLng itemPoint = new LatLng(location.getLatitude(), location.getLongitude());
        BitmapDescriptor myIcon = BitmapDescriptorFactory.fromResource(R.drawable.ic_launcher);
        MarkerOptions itemMarkerOptions = new MarkerOptions()
                .icon(myIcon)
                .position(itemPoint);
        mGoogleMap.addMarker(itemMarkerOptions);
        builder.include(itemPoint);
    }

    private void plotMarker(Location location, final LatLngBounds.Builder builder) {
        LatLng itemPoint = new LatLng(location.getLatitude(), location.getLongitude());
        MarkerOptions itemMarkerOptions = new MarkerOptions().position(itemPoint);
        mGoogleMap.addMarker(itemMarkerOptions);
        builder.include(itemPoint);
    }
}