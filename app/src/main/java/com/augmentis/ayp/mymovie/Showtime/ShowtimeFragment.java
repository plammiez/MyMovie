package com.augmentis.ayp.mymovie.Showtime;

import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.drawable.BitmapDrawable;
import android.graphics.drawable.Drawable;
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
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.augmentis.ayp.mymovie.ComingSoon;
import com.augmentis.ayp.mymovie.Decoration.BlurBuilder;
import com.augmentis.ayp.mymovie.Cinema.Cinema;
import com.augmentis.ayp.mymovie.Cinema.CinemaLab;
import com.augmentis.ayp.mymovie.Map.MapAndListActivity;
import com.augmentis.ayp.mymovie.Movie.Movie;
import com.augmentis.ayp.mymovie.Movie.MovieLab;
import com.augmentis.ayp.mymovie.R;
import com.augmentis.ayp.mymovie.WebViewActivity;
import com.bumptech.glide.Glide;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.IOException;
import java.io.InputStream;
import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;

/**
 * Created by Waraporn on 10/11/2016.
 */

public class ShowtimeFragment extends Fragment {

    private static final String MOVIE_ID = "MOVIE_ID";
    private static final String CINEMA_ID = "CINEMA_ID";
    private static final String TAG = "ShowTime";

    private String movieID;

    private MyShowTimeFetcher showTimeFetcher;
//    private ShowtimeLab showtimeLab = ShowtimeLab.getInstance(getActivity());

    private RecyclerView showtime_recycler;

    private ShowtimeAdapter _adapter;

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

    @Override
    public void onPause() {
        super.onPause();
        ShowtimeLab.getInstance(getActivity()).clearShowTime();
    }

    @Override
    public void onResume() {
        super.onResume();
        updateUI();
    }

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.showtime, container, false);
        showtime_recycler = (RecyclerView) view.findViewById(R.id.showtime_recycler);
        showtime_recycler.setLayoutManager(new LinearLayoutManager(getActivity()));

//        ShowtimeLab mShowtimeLab = ShowtimeLab.getInstance(getActivity());
//        Log.d(TAG, "LisT SHOWTIME : " + showtimeLab.getMyShowtimeList().size());
//        List<Showtime> mShowtimes = showtimeLab.getMyShowtimeList();
//        showtime_recycler.setAdapter(new ShowtimeAdapter(mShowtimes));

        Drawable drawable = getResources().getDrawable(R.drawable.wp8);
        Bitmap bitmap = ((BitmapDrawable) drawable).getBitmap();
        Bitmap blurredBitmap = BlurBuilder.blur(getActivity(), bitmap);

        view.setBackgroundDrawable(new BitmapDrawable(getResources(), blurredBitmap));

        return view;
    }

    public void getShowTime(String id) {
        showTimeFetcher = new MyShowTimeFetcher();
        showTimeFetcher.execute(id);
    }

    public void loadShowtime(String json) throws JSONException {

//        Gson gson = new Gson();
//        MyCinema cinema = gson.fromJson(json, MyCinema.class);
//        List<Element> element = cinema.getElements();

        try {
            JSONObject obj = new JSONObject(json);
            JSONArray jsonArray = obj.getJSONArray("elements");

            Log.d(TAG, "JSON : " + jsonArray.length());
            if (jsonArray.toString().equals("[]")) {
                Intent intent = ComingSoon.newIntent(getActivity(), MOVIE_ID);
                startActivity(intent);
            } else {

                CinemaLab cinemaLab = CinemaLab.getInstance(getActivity());
                for (int i = 0; i < jsonArray.length(); i++) {

                    Showtime showtime = new Showtime();
                    showtime.setMovieID(movieID);
                    showtime.setNameMovie(MovieLab.getInstance(getActivity())
                            .getMovieById(movieID)
                            .getMovieNameEN());

                    JSONObject jsonObject = jsonArray.getJSONObject(i);
                    showtime.setCinemaID(jsonObject.getString("cinema_id"));

                    if (!cinemaLab.getCinemaById(showtime.getCinemaID()).equals(null)) {
                        showtime.setNameCinema(cinemaLab.getCinemaById
                                (showtime.getCinemaID()).getNameENOfLocation());
                        JSONArray screenArray = jsonObject.getJSONArray("screens");
                        JSONObject screenObject = screenArray.getJSONObject(0);
                        JSONArray showtimeArray = screenObject.getJSONArray("showtimes");
                        ArrayList<String> time = new ArrayList<>();
                        ArrayList<String> audio = new ArrayList<>();
                        for (int j = 0; j < showtimeArray.length(); j++) {

                            KeepTimeForCinema mKeepTime = new KeepTimeForCinema();

                            mKeepTime.setCinemaID(showtime.getCinemaID());

                            JSONObject showtimeObject = showtimeArray.getJSONObject(j);
                            String mTime = showtimeObject.getString("time");
                            String[] myTime = mTime.split("T");
                            String[] onlyTime = myTime[1].split(":");
                            String thisTime = onlyTime[0] + ":" + onlyTime[1];
                            time.add(thisTime);
                            audio.add(showtimeObject.getString("audio"));

                            mKeepTime.setCinemaID(showtime.getCinemaID());
                            mKeepTime.setTime(time);
                            mKeepTime.setAudio(audio);

                            showtime.setTimeAudio(mKeepTime);
                            KeepTimeForCinemaLab.getInstance(getActivity()).addKeeptime(mKeepTime);
                        }


                        Log.d(TAG, "loadShowtime: TIMEAUDIO " + showtime.getTimeAudio().getTime().size());
                    }

                    Log.d(TAG, "loadShowtime: LAB " + KeepTimeForCinemaLab.getInstance(getActivity()).getMyKeepwtimeList().size());
                    ShowtimeLab.getInstance(getActivity()).addShowtime(showtime);
                    printLogShowTimeLab();
                }
            }
        } catch (JSONException e) {
            e.printStackTrace();
        }
    }

    private void printLogShowTimeLab() {
        for (Showtime m_Showtime : ShowtimeLab.getInstance(getActivity()).getMyShowtimeList()) {
            Log.d(TAG, "MY M SHOWTIME : " + m_Showtime.getNameCinema());
            Log.d(TAG, "M Y I D SHOWTIME : " + m_Showtime.getCinemaID());
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
                cinema.setCinemaNumber(jsonObject.getString("cinema_id"));
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

    private class MyShowTimeFetcher extends AsyncTask<String, Void, Bitmap> {

        String url = "http://movieplus.majorcineplex.com/api/movie";

        @Override
        protected Bitmap doInBackground(String... params) {
//            showtimeLab.clearShowTime();

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

            ShowtimeLab.getInstance(getActivity()).getMyShowtimeList();

            return null;
        }

        @Override
        protected void onPostExecute(Bitmap bitmap) {
            super.onPostExecute(bitmap);
            updateUI();
        }
    }

    public void updateUI() {

        Log.d("khem", "showtimes" + ShowtimeLab.getInstance(getActivity()).getMyShowtimeList().size());
        if (_adapter == null) {
            _adapter = new ShowtimeAdapter((ShowtimeLab.getInstance(getActivity()).getMyShowtimeList()));
            showtime_recycler.setAdapter(_adapter);
        } else {
            _adapter.setShowtime((ShowtimeLab.getInstance(getActivity()).getMyShowtimeList()));
//            showtime_recycler.setAdapter(_adapter);
            _adapter.notifyDataSetChanged();
        }
    }

    public class ShowtimeHolder extends RecyclerView.ViewHolder implements View.OnClickListener {

        private ImageView imageView;
        private TextView movieName;
        private TextView cinemaName;
        private TextView time;
        private TextView audio;
//        private LinearLayout layout;

        Showtime _showtime;

        public ShowtimeHolder(View itemView) {
            super(itemView);
            imageView = (ImageView) itemView.findViewById(R.id.poster);
            imageView.setOnClickListener(this);
            movieName = (TextView) itemView.findViewById(R.id.movie_name_showtime_page);
            cinemaName = (TextView) itemView.findViewById(R.id.cinema_name_showtime_page);
            time = (TextView) itemView.findViewById(R.id.time);
            audio = (TextView) itemView.findViewById(R.id.audio);
        }

        @Override
        public void onClick(View v) {
//            Intent intent = new Intent(getActivity(), WebViewActivity.class);
            Intent intent = WebViewActivity.newIntent(getActivity());
            startActivity(intent);
        }

        public void bind(Showtime showtime) {
            _showtime = showtime;
            Movie _movie = MovieLab.getInstance(getActivity()).getMovieById(showtime.getMovieID());
//            for (int j = 0; j < showtime.getCinemaID().size(); j++) {
//
//                Log.d(TAG, "LIST : " + _showtime.getNameMovie());
////            Log.d(TAG, "LIST SIZE : " + MovieLab.getInstance(getActivity()).getMovieList().size());
//                movieName.setText(_movie.getMovieNameEN());
//                Glide.with(getActivity()).load(_movie.getUrlPoster()).into(imageView);
//
////                Log.d(TAG, "S H O W T I M E : " + _showtime.getTimeAudio().getTime().size());
//                cinemaName.setText(matchCinema(showtime.getCinemaID().get(j)));
//                Log.d(TAG, "I D : " + showtime.getCinemaID().get(j));
//                Log.d(TAG, "I D NAME : " + matchCinema(showtime.getCinemaID().get(j)));
            movieName.setText(_movie.getMovieNameEN());
            Glide.with(getActivity()).load(_movie.getUrlPoster()).into(imageView);
            cinemaName.setText(showtime.getNameCinema());

            time.setText("");
            audio.setText("  ");

            KeepTimeForCinema kt = KeepTimeForCinemaLab.getInstance(getActivity())
                    .getKeeptimeById(showtime.getCinemaID());

            Log.d(TAG, "bind: " + KeepTimeForCinemaLab.getInstance(getActivity()).getMyKeepwtimeList().size());

            for (int i = 0; i < kt.getTime().size(); i++) {
                Log.d(TAG, "TIME RECORD : " + showtime.getTimeAudio().getTime().size());
                Log.d(TAG, "TIME  : " + showtime.getTimeAudio().getTime());
                time.append(kt.getTime().get(i));
                time.append("   ");

                if (kt.getAudio().get(i).equals("ENGLISH")) {
                    audio.append("ENG");
                    audio.append("     ");
                } else if (kt.getAudio().get(i).equals("THAI")) {
                    audio.append("TH");
                    audio.append("       ");
                } else if (kt.getAudio().get(i).equals("SOUNDTRACK")) {
                    audio.append("ST");
                    audio.append("       ");
                }
            }
        }
    }

    public class ShowtimeAdapter extends RecyclerView.Adapter<ShowtimeHolder> {

        private List<Showtime> _Showtimes = new ArrayList<>();
        private int _viewCreatingCount;

        private Showtime myShowtime;

        public ShowtimeAdapter(List<Showtime> showtimes) {
            _Showtimes = showtimes;
        }

        public void setShowtime(List<Showtime> showtimes) {
            _Showtimes = showtimes;
        }

        @Override
        public ShowtimeHolder onCreateViewHolder(ViewGroup parent, int viewType) {
            _viewCreatingCount++;

            LayoutInflater inflater = LayoutInflater.from(getActivity());
            View v = inflater.inflate(R.layout.list_showtime, parent, false);
            return new ShowtimeHolder(v);
        }

        @Override
        public void onBindViewHolder(ShowtimeHolder holder, int position) {
            Showtime mShowtime = _Showtimes.get(position);
            holder.bind(mShowtime);
        }

        @Override
        public int getItemCount() {
            return _Showtimes.size();
        }
    }
}
