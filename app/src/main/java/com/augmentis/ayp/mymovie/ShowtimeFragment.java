package com.augmentis.ayp.mymovie;

import android.graphics.Bitmap;
import android.os.AsyncTask;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.IOException;
import java.io.InputStream;
import java.util.ArrayList;

/**
 * Created by Waraporn on 10/11/2016.
 */

public class ShowtimeFragment extends Fragment {

    private static final String MOVIE_ID = "MOVIE_ID";
    private static final String CINEMA_ID = "CINEMA_ID";
    private static final String TAG = "ShowTime";

    private String movieID;

    private MyShowTimeFetcher showTimeFetcher;

    private RecyclerView showtime_recycler;

    public static ShowtimeFragment newInstance() {
        Bundle args = new Bundle();
        ShowtimeFragment fragment = new ShowtimeFragment();
        fragment.setArguments(args);
        return fragment;
    }

    public static ShowtimeFragment newInstance(String movieID) {
        Bundle args = new Bundle();
        args.putString(MOVIE_ID, movieID);
//        args.putString(CINEMA_ID, cinemaID);
        ShowtimeFragment fragment = new ShowtimeFragment();
        fragment.setArguments(args);
        return fragment;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        if (getArguments() != null) {
            movieID = getArguments().getString(MOVIE_ID);
//            cinemaID = getArguments().getString(CINEMA_ID);
        }
        getCinema();
        getShowTime(movieID);
    }

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.showtime, container, false);
        showtime_recycler = (RecyclerView) view.findViewById(R.id.showtime_recycler);
        showtime_recycler.setLayoutManager(new LinearLayoutManager(getActivity()));
        return view;
    }

    public void getShowTime(String id) {
        showTimeFetcher = new MyShowTimeFetcher();
        showTimeFetcher.execute(id);
    }

    public void loadShowtime(String json) throws JSONException{

        MovieLab movie = MovieLab.getInstance(getActivity());

        try {
            JSONObject obj = new JSONObject(json);
            JSONArray jsonArray = obj.getJSONArray("elements");

            for (int i = 0; i < jsonArray.length(); i++) {
                JSONObject jsonObject = jsonArray.getJSONObject(i);
                String cinemas = jsonObject.getString("cinema_id");

                Log.d(TAG, "CINEMA IS : " + matchCinema(cinemas));
//                cinemaLab.getCinemaById(cinemas);
//                if (!matchCinema(cinemas).equals(null)) {
//                    JSONArray screenArray = jsonObject.getJSONArray("screens");
//                    JSONObject screenObject = screenArray.getJSONObject(i);
//
//                    JSONArray showtimeArray = screenObject.getJSONArray("showtimes");
//
//                    ArrayList<String> time = new ArrayList<>();
//                    ArrayList<String> audio = new ArrayList<>();
//
//                    for (int j = 0; j < showtimeArray.length(); j++) {
//
//                        JSONObject showtimeObject = showtimeArray.getJSONObject(j);
//                        time.add(showtimeObject.getString("time"));
//                        audio.add(showtimeObject.getString("audio"));
//
//                        for (int k = 0; k < time.size(); k++) {
//                            Log.d(TAG, "size" + time.size() + "TIME IS : " + time.get(j));
//                            Log.d(TAG, "size" + audio.size() + "AUDIO IS : " + audio.get(j));
//                        }
//                    }
//                }


            }
//            Log.d(TAG, "DATA LIST ADD : " + movies.getMovieList().size());

        } catch (JSONException e) {
            e.printStackTrace();
        }
    }

    public void getCinema() {
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
        }

        try {
            JSONObject obj = new JSONObject(json);
            JSONArray jsonArray = obj.getJSONArray("elements");

            for (int i = 0; i < jsonArray.length(); i++) {
                JSONObject jsonObject = jsonArray.getJSONObject(i);

                Cinema cinema = new Cinema();
                cinema.setCinemaId(jsonObject.getString("id"));
                cinema.setNameENOfLocation(jsonObject.getString("short_name_en"));
                cinema.setNameTHOfLocation(jsonObject.getString("short_name_th"));
                cinema.setTel(jsonObject.getString("tel"));

                cinema.setLocation(jsonObject.getString("location"));
//                Log.d("TAG", "AT LOCATION : " + cinema.getLocation());

                String[] latLon = cinema.getLocation().split(",");
                cinema.setLatitude(Double.parseDouble(latLon[0]));
                cinema.setLongitude(Double.parseDouble(latLon[1]));

                //Add your values in your `ArrayList` as below:
                CinemaLab.getInstance(getActivity()).addCinema(cinema);
            }

        } catch (JSONException e) {
            e.printStackTrace();
        }
    }

    public String matchCinema(String cinema_id) {
        CinemaLab cinemaLab = CinemaLab.getInstance(getActivity());

        return cinemaLab.getCinemaById(cinema_id).getNameENOfLocation();
    }

    private class MyShowTimeFetcher extends AsyncTask<String, Void, Bitmap> {

        String url = "http://movieplus.majorcineplex.com/api/movie";

        @Override
        protected Bitmap doInBackground(String... params) {

            String showTimeURL = url + "/" + params[0].toString() + "/showtime";
            Log.d("SHOWTIME", "URL : " + showTimeURL);

            ShowTimeFetcher showTimeFetcher = new ShowTimeFetcher();
            try {
                byte[] jsonStr = showTimeFetcher.getUrlBytes(showTimeURL);
                String str = new String(jsonStr);
                Log.d("SHOWTIME", "ST : " + str);
                loadShowtime(str);
            } catch (IOException e) {
                e.printStackTrace();
            } catch (JSONException e) {
                e.printStackTrace();
            }

            return null;
        }
    }
}
