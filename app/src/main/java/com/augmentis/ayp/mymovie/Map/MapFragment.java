package com.augmentis.ayp.mymovie.Map;

import android.location.Location;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.augmentis.ayp.mymovie.Cinema.MyLocationLab;
import com.augmentis.ayp.mymovie.Cinema.MyLocations;
import com.augmentis.ayp.mymovie.R;
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
import java.util.Collections;
import java.util.List;

/**
 * Created by Waraporn on 9/29/2016.
 */

public class MapFragment extends SupportMapFragment {

    private static final String KEY_LOCATION = "LOCATION";

    private GoogleMap mGoogleMap;
    private Location mLocation;

    private CallBack callBack;

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
    interface CallBack{
        void refreshList();
    }

    @Override
    public void onCreate(Bundle bundle) {
        super.onCreate(bundle);
        setHasOptionsMenu(true);
        callBack = (CallBack)getActivity();

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

    public static double toRadian(double degrees){
        return (degrees * Math.PI) / 180.0d;
    }

    public double calculateDistance(double lat, double lon) {

        // KM: use mile here if you want mile result
        double earthRadius = 6371.0d;

        // Latitude and Longitude of here
        double myLat = mLocation.getLatitude();
        double myLon = mLocation.getLongitude();

        // Calculate
        double dLat = toRadian(lat - myLat);
        double dLng = toRadian(lon - myLon);

        double a = Math.pow(Math.sin(dLat/2), 2)  +
                Math.cos(toRadian(myLat)) * Math.cos(toRadian(lat)) *
                        Math.pow(Math.sin(dLng/2), 2);

        double c = 2 * Math.atan2(Math.sqrt(a), Math.sqrt(1-a));

        // returns result kilometers
        return earthRadius * c;
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
                location.setCinemaId(jsonObject.getString("id"));
                location.setNameENOfLocation(jsonObject.getString("short_name_en"));
                location.setNameTHOfLocation(jsonObject.getString("short_name_th"));
                location.setTel(jsonObject.getString("tel"));

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

                double distance = calculateDistance(location.getLatitude(), location.getLongitude());

                location.setDistance(distance);
                plotMarker(mLocate, location.getNameENOfLocation(), distance, builder);

                //Add your values in your `ArrayList` as below:
                locList.add(location);
                MyLocationLab.getInstance(getActivity()).addLocation(location);
            }

        } catch (JSONException e) {
            e.printStackTrace();
        }
        return locList;
    }

    private List<Double> orderDistance() {
        List<MyLocations> myLocationsList = MyLocationLab.getInstance(getActivity()).getMyLocationsList();

        List<Double> distance = new ArrayList<>();

        for (int i=0 ; i < myLocationsList.size() ; i++) {
            distance.add(myLocationsList.get(i).getDistance());
        }

        Collections.sort(distance);
        return distance;
    }

    private MyLocations queryDistance(double distance) {
        MyLocations location = MyLocationLab.getInstance(getActivity()).getLocationByDistance(distance);
        return location;
    }

    private void printDistance() {
        List<Double> distance = orderDistance();

        MyLocationLab mLab = MyLocationLab.getInstance(getActivity());
        mLab.clearNearyLocation();

        for (int i=0 ; i < 5 ; i++) {
            MyLocations myPlace = queryDistance(distance.get(i));
            mLab.addNearyLocation(queryDistance(distance.get(i)));
            Log.d("TAG", "distance near at " + i + " : " + myPlace.getNameENOfLocation()
                    + " km : " + myPlace.getDistance());
        }

        callBack.refreshList();
    }

    private void updateMapUI() {
        mGoogleMap.clear();

        LatLngBounds.Builder builder = new LatLngBounds.Builder();

        if (mLocation != null) {
            plotMyMarker(mLocation, builder);
            loadCinemaFromJSON();
            printDistance();
        }

//        int margin = getResources().getDimensionPixelSize(R.dimen.map_inset_margin);
        CameraUpdate cameraUpdate = CameraUpdateFactory
                .newLatLngZoom(new LatLng(mLocation.getLatitude(), mLocation.getLongitude()), 12.5f);

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

    private void plotMarker(Location location, String nameEN,
                            double distance, final LatLngBounds.Builder builder) {
        LatLng itemPoint = new LatLng(location.getLatitude(), location.getLongitude());
        MarkerOptions itemMarkerOptions = new MarkerOptions()
                .position(itemPoint)
                .title(nameEN)
                .snippet(String.format("%.2f km", distance));

        mGoogleMap.addMarker(itemMarkerOptions);
        builder.include(itemPoint);
    }
}
