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

import com.augmentis.ayp.mymovie.Decoration.BlurBuilder;
import com.augmentis.ayp.mymovie.Cinema.Cinema;
import com.augmentis.ayp.mymovie.Cinema.CinemaLab;
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
    private ShowtimeLab showtimeLab = ShowtimeLab.getInstance(getActivity());

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

        ShowtimeLab mShowtimeLab = ShowtimeLab.getInstance(getActivity());
//        Log.d(TAG, "LisT SHOWTIME : " + mShowtimeLab.getMyShowtimeList().size());
        List<Showtime> mShowtimes = mShowtimeLab.getMyShowtimeList();
        showtime_recycler.setAdapter(new ShowtimeAdapter(mShowtimes));

        Drawable drawable = getResources().getDrawable(R.drawable.wp8);
        Bitmap bitmap = ((BitmapDrawable)drawable).getBitmap();
        Bitmap blurredBitmap = BlurBuilder.blur( getActivity(), bitmap );

        view.setBackgroundDrawable( new BitmapDrawable( getResources(), blurredBitmap ) );

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
        Showtime showtime = new Showtime();
//        showtime.setMovieID(MOVIE_ID);
//
//        for (int i=0; i<element.size(); i++) {
//            showtime.setCinemaID(element.get(i).getCinema_Id());
//        }

//        Movie movie = MovieLab.getInstance(getActivity()).getMovieById(MOVIE_ID);
//        showtime.setNameMovie(movie.getMovieNameEN());

//        List<String> cID = new ArrayList<>();
//        for (int i=0; i<cinema.getElements().size(); i++) {
//            Cinema mCinema = CinemaLab.getInstance(getActivity()).getCinemaById(cinema.getElements().get(i));
//            cID.add(mCinema.getCinemaId());
//        }
//        showtime.setCinemaID(cID);
//
//        MyShowtime tShowtime = new MyShowtime();
//        showtime.setTime(tShowtime.getTime());
//        showtime.setAudio(tShowtime.getAudio());
        KeepTimeForCinema forCinema = new KeepTimeForCinema();
        try {
            JSONObject obj = new JSONObject(json);
            JSONArray jsonArray = obj.getJSONArray("elements");

            showtime.setMovieID(movieID);
            showtime.setNameMovie(MovieLab.getInstance(getActivity())
                    .getMovieById(movieID)
                    .getMovieNameEN());

            ArrayList<String> cID = new ArrayList<>();

            for (int i = 0; i < jsonArray.length(); i++) {

                JSONObject jsonObject = jsonArray.getJSONObject(i);
                cID.add(jsonObject.getString("cinema_id"));

                if (!matchCinema(cID.get(i)).equals(null)) {
                    JSONArray screenArray = jsonObject.getJSONArray("screens");
                    JSONObject screenObject = screenArray.getJSONObject(0);

                    JSONArray showtimeArray = screenObject.getJSONArray("showtimes");

                    ArrayList<String> time = new ArrayList<>();
                    ArrayList<String> audio = new ArrayList<>();

                    for (int j = 0; j < showtimeArray.length(); j++) {

                        JSONObject showtimeObject = showtimeArray.getJSONObject(j);

                        String mTime = showtimeObject.getString("time");
                        String[] myTime = mTime.split("T");
                        String[] onlyTime = myTime[1].split(":");
                        String thisTime = onlyTime[0] + ":" + onlyTime[1];

                        time.add(thisTime);

                        audio.add(showtimeObject.getString("audio"));

                        for (int k = 0; k < time.size(); k++) {
                            Log.d(TAG, "size" + time.size() + "TIME IS : " + time.get(j));
                            Log.d(TAG, "size" + audio.size() + "AUDIO IS : " + audio.get(j));
                        }
                    }
                    forCinema.setTime(time);
                    forCinema.setAudio(audio);
                }
                showtime.setCinemaID(cID);
                Log.d(TAG, "CINEMA ID : " + showtime.getCinemaID());
                showtime.setNameCinema(matchCinema(showtime.getCinemaID().get(i)));
                Log.d(TAG, "CINEMA IS : " + matchCinema(showtime.getCinemaID().get(i)));
                showtime.setTimeAudio(forCinema);

                showtimeLab.addShowtime(showtime);
            }
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
            showtimeLab.clearShowTime();

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

    public class ShowtimeHolder extends RecyclerView.ViewHolder implements View.OnClickListener {

        private ImageView imageView;
        private TextView movieName;
        private TextView cinemaName;
        private TextView time;
        private LinearLayout layout;

        Showtime _showtime;

        public ShowtimeHolder(View itemView) {
            super(itemView);
            imageView = (ImageView) itemView.findViewById(R.id.poster);
            imageView.setOnClickListener(this);
            movieName = (TextView) itemView.findViewById(R.id.movie_name_showtime_page);
            cinemaName = (TextView) itemView.findViewById(R.id.cinema_name_showtime_page);
            layout = (LinearLayout) itemView.findViewById(R.id.list_of_time);
            time = (TextView) itemView.findViewById(R.id.time);
        }

        @Override
        public void onClick(View v) {
//            Intent intent = new Intent(getActivity(), WebViewActivity.class);
            Intent intent = WebViewActivity.newIntent(getActivity());
            startActivity(intent);
        }

        public void bind(Showtime showtime) {

            Movie _movie = MovieLab.getInstance(getActivity()).getMovieById(showtime.getMovieID());
            _showtime = showtime;
            Log.d(TAG, "LIST : " + _showtime.getNameMovie());
//            Log.d(TAG, "LIST SIZE : " + MovieLab.getInstance(getActivity()).getMovieList().size());
            Glide.with(getActivity()).load(_movie.getUrlPoster()).into(imageView);

            movieName.setText(_movie.getMovieNameEN());

//            TextView time[] = null;
            //= new TextView[_showtime.getCinemaID().size()];

            Log.d(TAG, "S H O W T I M E : " + _showtime.getTimeAudio().getTime().size());

            for (int j = 0; j < _showtime.getCinemaID().size(); j++) {

                cinemaName.setText(CinemaLab.getInstance(getActivity())
                        .getCinemaById(_showtime.getCinemaID().get(j)).getNameENOfLocation());

                for (int i = 0; i < _showtime.getTimeAudio().getTime().size(); i++) {
//                    time[i] = new TextView(getActivity());
                    time.setText(_showtime.getTimeAudio().getTime().get(i));
//                TextView time = new TextView(getContext());
//                time.setText(_showtime.getTimeAudio().getTime().get(i));
                    Log.d(TAG, "S H O W T I M E : " + _showtime.getTimeAudio().getTime().get(i));
                    layout.addView(time);
                }
            }
////            TextView time[] = null;
//            for (int i=0; i<_showtime.getTimeAudio().getTime().size(); i++) {
////                time[i] = new TextView(getActivity());
////                time[i].setText(_showtime.getTimeAudio().getTime().get(i));
//                TextView time = new TextView(getContext());
//                time.setText(_showtime.getTimeAudio().getTime().get(i));
//                layout.addView(time);
//            }

            ArrayList<TextView> mTextViewList = new ArrayList<>();
            for (int i = 0; i<_showtime.getTimeAudio().getTime().size(); i++)
            {
                TextView tv = new TextView(getActivity());
                mTextViewList.add(tv);
                tv.setText(_showtime.getTimeAudio().getTime().get(i));
                layout.addView(tv);
            }
        }

    }

    public class ShowtimeAdapter extends RecyclerView.Adapter<ShowtimeHolder> {
        private List<Showtime> _Showtimes;

        public ShowtimeAdapter(List<Showtime> showtimes) {
            this._Showtimes = showtimes;
        }

        @Override
        public ShowtimeHolder onCreateViewHolder(ViewGroup parent, int viewType) {
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
