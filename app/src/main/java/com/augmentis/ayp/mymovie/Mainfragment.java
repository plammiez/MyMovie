package com.augmentis.ayp.mymovie;

import android.content.Intent;
import android.location.Location;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.design.widget.FloatingActionButton;
import android.support.v4.app.Fragment;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.google.android.gms.common.api.GoogleApiClient;
import com.google.android.gms.location.LocationServices;

/**
 * Created by Waraporn on 9/20/2016.
 */
public class Mainfragment extends Fragment {

//    private RecyclerView movie_recycler_view;
    public ImageView movieImg;
    public int id =1;

    public FloatingActionButton fabBtn;

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container,
                             @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_main_list, container, false);

//        movie_recycler_view = (RecyclerView) view.findViewById(R.id.list_movie_recycler_view);
//        movie_recycler_view.setLayoutManager(new LinearLayoutManager(getActivity()));

        movieImg = (ImageView) view.findViewById(R.id.movie_img);
        movieImg.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Log.d("AA", "CLICK");
                Intent intent = DetailMovieActivity.newIntent(getActivity(), id);
                startActivity(intent);
            }
        });

        fabBtn = (FloatingActionButton) view.findViewById(R.id.fabBtn);
        fabBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = MapActivity.newIntent(getActivity());
                startActivity(intent);
            }
        });


        return view;
    }

    @Override
    public void onCreateOptionsMenu(Menu menu, MenuInflater inflater) {
        super.onCreateOptionsMenu(menu, inflater);
        inflater.inflate(R.menu.menu_search, menu);
    }

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setHasOptionsMenu(true);
    }

    private class MovieHolder extends RecyclerView.ViewHolder {

        public MovieHolder(View itemView) {
            super(itemView);


        }
    }

    private class MovieAdapter extends RecyclerView.Adapter<MovieHolder> {

        @Override
        public MovieHolder onCreateViewHolder(ViewGroup parent, int viewType) {

            LayoutInflater layoutInflater = LayoutInflater.from(getActivity());
            View v = layoutInflater.inflate(R.layout.list_item_movie, parent,false);

            return new MovieHolder(v);
        }

        @Override
        public void onBindViewHolder(MovieHolder holder, int position) {

        }

        @Override
        public int getItemCount() {
            return 0;
        }
    }
}
