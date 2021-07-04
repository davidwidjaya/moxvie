package com.davidwidjaya.moxvie.ui.favorite.movie;

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
import com.davidwidjaya.moxvie.data.local.entity.Movie;
import com.davidwidjaya.moxvie.data.local.room.AppDatabase;

import java.util.ArrayList;
import java.util.List;


public class MovieFavoriteFragment extends Fragment {

    ArrayList<Movie> arrMovie = new ArrayList<>();
    RecyclerView rv;
    AdapterMovieFavorite adaptMovie;
    AppDatabase db;

    // TODO: Rename and change types and number of parameters
    public static MovieFavoriteFragment newInstance(String param1, String param2) {
        MovieFavoriteFragment fragment = new MovieFavoriteFragment();
        return fragment;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View v = inflater.inflate(R.layout.fragment_movie_favorite, container, false);

        db = AppDatabase.getInstance(getActivity()); //cek database
        rv = v.findViewById(R.id.rv_fragmentMovieFavHome);
        rv.setLayoutManager(new GridLayoutManager(getContext(), 3));
        adaptMovie = new AdapterMovieFavorite(getContext(), arrMovie);

        new LoadMovieTask().execute();

        rv.setAdapter(adaptMovie);
        return v;
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
    }

    private class LoadMovieTask extends AsyncTask<Void, Void, List<Movie>> {
        @Override
        protected List<Movie> doInBackground(Void... voids) {
            return db.appDao().getAllFavoritedMovie();
        }

        @Override
        protected void onPostExecute(List<Movie> movies) {
            super.onPostExecute(movies);
            arrMovie.clear();
            arrMovie.addAll(movies);
            Log.d("arrMovie", "onPostExecute: " + arrMovie);
            adaptMovie.notifyDataSetChanged();
        }
    }

    @Override
    public void onResume() {
        super.onResume();
        new LoadMovieTask().execute();
        rv.setAdapter(adaptMovie);
    }
}


