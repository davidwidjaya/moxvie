package com.davidwidjaya.moxvie.ui.favorite.tv;

import android.os.AsyncTask;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.davidwidjaya.moxvie.R;
import com.davidwidjaya.moxvie.data.local.entity.Tv;
import com.davidwidjaya.moxvie.data.local.room.AppDatabase;

import java.util.ArrayList;
import java.util.List;

public class TvFavoriteFragment extends Fragment {

    ArrayList<Tv> arrTv = new ArrayList<>();
    RecyclerView rvTv;
    AdapterTvFavorite adaptTv;
    AppDatabase db;

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View v = inflater.inflate(R.layout.fragment_tv_favorite, container, false);

        db = AppDatabase.getInstance(getActivity());
        rvTv = v.findViewById(R.id.rv_fragmentTvFavorite);
        rvTv.setLayoutManager(new GridLayoutManager(getContext(), 3));

        adaptTv = new AdapterTvFavorite(getContext(), arrTv);
        new LoadTvTask().execute();
        rvTv.setAdapter(adaptTv);
        return v;
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
    }


    private class LoadTvTask extends AsyncTask<Void, Void, List<Tv>> {
        @Override
        protected List<Tv> doInBackground(Void... voids) {
            return db.appDao().getAllFavoritedTV();
        }

        @Override
        protected void onPostExecute(List<Tv> tvs) {
            super.onPostExecute(tvs);
            arrTv.clear();
            arrTv.addAll(tvs);
            Log.d("arrTv", "onPostExecute: " + arrTv);
            adaptTv.notifyDataSetChanged();
        }
    }


    @Override
    public void onResume() {
        super.onResume();
        new LoadTvTask().execute();
        rvTv.setAdapter(adaptTv);
    }

}