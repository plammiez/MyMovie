package com.augmentis.ayp.mymovie;

import android.app.Fragment;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;


/**
 * Created by Waraporn on 10/4/2016.
 */

public class MapListFragment extends Fragment {

    private RecyclerView map_list;

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {

        View view = inflater.inflate(R.layout.activity_map_list, container, false);
        map_list = (RecyclerView) view.findViewById(R.id.list_cinema);
        map_list.setLayoutManager(new LinearLayoutManager(getActivity()));

        return view;
    }

    public class MapListHolder extends RecyclerView.ViewHolder{

        private TextView nameTH;
        private TextView tel;
        private TextView distance;

        public MapListHolder(View itemView) {
            super(itemView);

            nameTH = (TextView) itemView.findViewById(R.id.nameTH_Cinema);
            tel = (TextView) itemView.findViewById(R.id.cinema_tel);
            distance = (TextView) itemView.findViewById(R.id.distance_cinema);
        }

        public void bind () {

        }
    }

    public class MapListAdapter extends RecyclerView.Adapter<MapListHolder> {


        @Override
        public MapListHolder onCreateViewHolder(ViewGroup parent, int viewType) {
            return null;
        }

        @Override
        public void onBindViewHolder(MapListHolder holder, int position) {

        }

        @Override
        public int getItemCount() {
            return 0;
        }
    }
}
