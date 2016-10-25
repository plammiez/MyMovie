package com.augmentis.ayp.mymovie.Map;

import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.augmentis.ayp.mymovie.Cinema.MyLocationLab;
import com.augmentis.ayp.mymovie.Cinema.MyLocations;
import com.augmentis.ayp.mymovie.CinemaViewActivity;
import com.augmentis.ayp.mymovie.R;
import com.augmentis.ayp.mymovie.WebViewActivity;

import java.util.Formatter;
import java.util.List;
import java.util.Locale;

/**
 * Created by Wilailux on 10/12/2016.
 */

public class MapListFragment extends Fragment {

    private RecyclerView map_list;

    public static MapListFragment newInstance() {
        Bundle args = new Bundle();
        MapListFragment fragment = new MapListFragment();
        fragment.setArguments(args);
        return fragment;
    }

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.activity_map_list, container, false);
        map_list = (RecyclerView) view.findViewById(R.id.list_cinema);
        map_list.setLayoutManager(new LinearLayoutManager(getActivity()));
        MyLocationLab myLocationLab = MyLocationLab.getInstance(getActivity());
        List<MyLocations> myLocations = myLocationLab.getNearyLocation();
        map_list.setAdapter(new MapListAdapter(myLocations));
        return view;
    }

    public class MapListHolder extends RecyclerView.ViewHolder {

        MyLocations _myLocations;
        private TextView nameTH;
        private TextView tel;
        private TextView distance;

        public MapListHolder(View itemView) {
            super(itemView);
            nameTH = (TextView) itemView.findViewById(R.id.nameTH_Cinema);
            tel = (TextView) itemView.findViewById(R.id.cinema_tel);
            distance = (TextView) itemView.findViewById(R.id.distance_cinema);
        }

        public void bind(MyLocations myLocations) {
            _myLocations = myLocations;
            nameTH.setText(_myLocations.getNameTHOfLocation());
            nameTH.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    Intent intent = CinemaViewActivity.newIntent(getActivity(), _myLocations.getCinemaNumber());
                    startActivity(intent);
                }
            });
            tel.setText("Tel : " + _myLocations.getTel());
            distance.setText("Distance : " + new Formatter(Locale.US).format("%.2f", _myLocations.getDistance()).toString() + "km.");
        }
    }

    public class MapListAdapter extends RecyclerView.Adapter<MapListHolder> {

        private List<MyLocations> _myLocations;

        public MapListAdapter(List<MyLocations> myLocations) {
            this._myLocations = myLocations;
        }

        @Override
        public MapListHolder onCreateViewHolder(ViewGroup parent, int viewType) {
            LayoutInflater inflater = LayoutInflater.from(getActivity());
            View v = inflater.inflate(R.layout.list_item_cinema, parent, false);
            return new MapListHolder(v);
        }

        @Override
        public void onBindViewHolder(MapListHolder holder, int position) {
            MyLocations myLocations = _myLocations.get(position);
            holder.bind(myLocations);
        }

        @Override
        public int getItemCount() {
            return _myLocations.size();
        }
    }
}

