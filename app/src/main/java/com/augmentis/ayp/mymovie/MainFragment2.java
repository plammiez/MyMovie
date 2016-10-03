package com.augmentis.ayp.mymovie;

import android.Manifest;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.graphics.Bitmap;
import android.graphics.drawable.BitmapDrawable;
import android.graphics.drawable.Drawable;
import android.location.Location;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.design.widget.FloatingActionButton;
import android.support.v4.app.Fragment;
import android.support.v4.util.LruCache;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v4.content.ContextCompat;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;

import java.io.IOException;
import java.io.InputStream;
import java.util.ArrayList;
import java.util.List;
import android.widget.Toast;
import com.google.android.gms.common.ConnectionResult;
import com.google.android.gms.common.GooglePlayServicesUtil;
import com.google.android.gms.common.api.GoogleApiClient;
import com.google.android.gms.location.LocationListener;
import com.google.android.gms.location.LocationRequest;
import com.google.android.gms.location.LocationServices;
import com.google.android.gms.maps.GoogleMap;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

/**
 * Created by Waraporn on 9/20/2016.
 */
public class MainFragment2 extends Fragment {

    public int id = 1;
    public ImageView movieImg;
    public FloatingActionButton fabBtn;
    public List<Object> posterList = new ArrayList<>();
    public List<Drawable> poster = new ArrayList<>();

    private RecyclerView movie_recycler_view;
    private GoogleApiClient mGoogleApiClient;
    private Location mLocation;
    private GoogleMap mGoogleMap;
    private static final int REQUEST_PERMISSION_LOCATION = 231;

    private double latitude;
    private double longitude;

    private MovieLab movieLab;

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container,
                             @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_main_list, container, false);

        movie_recycler_view = (RecyclerView) view.findViewById(R.id.list_movie_recycler_view);
        movie_recycler_view.setLayoutManager(new GridLayoutManager(getActivity(),2));
        movie_recycler_view.setAdapter(new MovieAdapter());

        fabBtn = (FloatingActionButton) view.findViewById(R.id.fabBtn);
        fabBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = MapActivity.newIntent(getActivity(),
                        mLocation);
                startActivity(intent);
            }
        });

        posterList.add(R.drawable.bridget);
        posterList.add(R.drawable.fanday);
        posterList.add(R.drawable.missperegrine);
        posterList.add(R.drawable.storks);

//        for (int i=0 ; i<movieLab.getMovieList().size() ; i++) {
//
//        }
        return view;
    }

    @Override
    public void onStart() {
        super.onStart();
        mGoogleApiClient.connect();
    }

    @Override
    public void onStop() {
        super.onStop();
        mGoogleApiClient.disconnect();
    }

    @Override
    public void onCreateOptionsMenu(Menu menu, MenuInflater inflater) {
        super.onCreateOptionsMenu(menu, inflater);
        inflater.inflate(R.menu.menu_search, menu);
    }

    @SuppressWarnings("all")
    private GoogleApiClient.ConnectionCallbacks mCallbacks =
            new GoogleApiClient.ConnectionCallbacks() {
        @Override
        public void onConnected(@Nullable Bundle bundle) {
            mLocation = LocationServices.FusedLocationApi.getLastLocation(mGoogleApiClient);
                findLocation();
        }

        @Override
        public void onConnectionSuspended(int i) {

        }
    };

    private LocationListener mLocationListener = new LocationListener() {
        @Override
        public void onLocationChanged(Location location) {
            mLocation = location;
            latitude = location.getLatitude();
            longitude = location.getLongitude();

            Toast.makeText(getActivity(), location.getLatitude() + "," +
                    location.getLongitude(), Toast.LENGTH_LONG).show();
        }
    };

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setHasOptionsMenu(true);

        mGoogleApiClient = new GoogleApiClient.Builder(getActivity())
                .addApi(LocationServices.API)
                .addConnectionCallbacks(mCallbacks)
                .build();
    }

    public ArrayList<Movie> loadMoviesFromJSON() {
        ArrayList<Movie> movies = new ArrayList<>();
        String json = null;
        try {
            InputStream inputStream = getActivity().getAssets().open("movie_all.json");
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

                Movie movie = new Movie();
                movie.setMovieId(jsonObject.getString("id"));
                movie.setMovieNameTH(jsonObject.getString("name_alt"));
                movie.setMovieNameEN(jsonObject.getString("name"));
                movie.setUrlPoster(jsonObject.getString("thumb_image"));
                movie.setUrlTrailer(jsonObject.getString("trailer"));
                movie.setSynopsis(jsonObject.getString("synopsis"));
                movie.setGenres(jsonObject.getString("genres"));
                movie.setDirectors(jsonObject.getString("directors"));
                movie.setActors(jsonObject.getString("actors"));

                Log.d("TAG", "MOVIE : " + movie.getActors());
                //Add your values in your `ArrayList` as below:
                movies.add(movie);

                // add data of location into arraylist of Class MyLocations
                movieLab = new MovieLab(getActivity());
                movieLab.addMovie(movie);
            }
        } catch (JSONException e) {
            e.printStackTrace();
        }

        return movies;
    }

    public class MovieHolder extends RecyclerView.ViewHolder{
        Object obj;

        public MovieHolder(View itemView) {
            super(itemView);
            movieImg = (ImageView) itemView.findViewById(R.id.list_movie_img);
            movieImg.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    Log.d("AA", "CLICK");
                    Intent intent = DetailMovieActivity.newIntent(getActivity(), id);
                    startActivity(intent);
                }
            });
        }

        public void bind (Object obj) {
            this.obj = obj;
            movieImg.setImageDrawable(getResources().getDrawable((int) obj,null));
        }
    }

    public class MovieAdapter extends RecyclerView.Adapter<MovieHolder> {

        @Override
        public MovieHolder onCreateViewHolder(ViewGroup parent, int viewType) {
            LayoutInflater inflater = LayoutInflater.from(getActivity());
            View v = inflater.inflate(R.layout.list_item_movie,parent,false);
            return new MovieHolder(v);
        }

        @Override
        public void onBindViewHolder(MovieHolder holder, int position) {
            Object obj = posterList.get(position);
            holder.bind(obj);
        }

        @Override
        public int getItemCount() {
            return posterList.size();
        }
    }

    private void findLocation() {
        if (hasPermission()) {
            requestLocation();
        }
    }

    private boolean hasPermission() {
        int permissionStatus =
                ContextCompat.checkSelfPermission(getActivity(),
                        Manifest.permission.ACCESS_FINE_LOCATION);

        if (permissionStatus == PackageManager.PERMISSION_GRANTED) {
            return true;
        }

        requestPermissions(new String[]{
                        Manifest.permission.ACCESS_FINE_LOCATION,
                        Manifest.permission.ACCESS_COARSE_LOCATION
                },
                REQUEST_PERMISSION_LOCATION);
        return false;
    }

    @Override
    public void onRequestPermissionsResult(int requestCode,
                                           @NonNull String[] permissions,
                                           @NonNull int[] grantResults) {
        if (requestCode == REQUEST_PERMISSION_LOCATION) {
            if (grantResults.length > 0 && grantResults[0] == PackageManager.PERMISSION_GRANTED) {
                requestLocation();
            }
        }
    }

    @SuppressWarnings("all")
    private void requestLocation() {
        if (GooglePlayServicesUtil.isGooglePlayServicesAvailable(getActivity())
                == ConnectionResult.SUCCESS) {

            LocationRequest request = LocationRequest.create();
            request.setPriority(LocationRequest.PRIORITY_HIGH_ACCURACY);
            request.setNumUpdates(50);
            request.setInterval(1000);

            LocationServices.FusedLocationApi.requestLocationUpdates(mGoogleApiClient,
                    request, mLocationListener);
        }
    }
}
