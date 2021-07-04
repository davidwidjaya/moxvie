package com.davidwidjaya.moxvie.ui.home.movie;

import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.os.Parcelable;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.toolbox.JsonObjectRequest;
import com.android.volley.toolbox.Volley;
import com.davidwidjaya.moxvie.data.remote.MovieResponse;
import com.davidwidjaya.moxvie.databinding.FragmentMovieHomeBinding;
import com.davidwidjaya.moxvie.ui.search.SearchActivity;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;

public class MovieHomeFragment extends Fragment {

    private ArrayList<MovieResponse> arrMovie;
    private RequestQueue reqQue;
    private AdapterMovie adaptMovie;
    private GridLayoutManager gridLayoutManager;

    private int page = 1;
    private boolean load = false;
    private int lastCompleteVisibleItemPosition;
    private int totalItemCount;
    private Parcelable recyclerViewState;
    private FragmentMovieHomeBinding fragmentMovieHomeBinding;

    public MovieHomeFragment() {
        // Required empty public constructor
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
//        View v = inflater.inflate(R.layout.fragment_movie_home, container, false);
        fragmentMovieHomeBinding = FragmentMovieHomeBinding.inflate(getLayoutInflater(), container, false);

        fragmentMovieHomeBinding.clMovieSearch.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent icl = new Intent(getContext(), SearchActivity.class);
                startActivity(icl);
            }
        });
        fragmentMovieHomeBinding.ivMovieSearch.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent icl = new Intent(getContext(), SearchActivity.class);
                startActivity(icl);
            }
        });
        fragmentMovieHomeBinding.tvMovieSearch.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent icl = new Intent(getContext(), SearchActivity.class);
                startActivity(icl);
            }
        });

        gridLayoutManager = new GridLayoutManager(getContext(), 3);
        fragmentMovieHomeBinding.rvFragmentMovieHome.setLayoutManager(gridLayoutManager);

        arrMovie = new ArrayList<>();
        reqQue = Volley.newRequestQueue(getContext());

        parseJSON();

        return fragmentMovieHomeBinding.getRoot();
    }

    private void parseJSON() {
        fragmentMovieHomeBinding.progressBarMovie.setVisibility(View.VISIBLE);

        new Handler().postDelayed(new Runnable() {
            @Override
            public void run() {
                String url = "https://api.themoviedb.org/3/movie/popular?api_key=c0feffca73bfb6338bcbd69dc83d295d&page=" + page;
//                Toast.makeText(getContext(), "Loading halaman "+page, Toast.LENGTH_SHORT).show();

                JsonObjectRequest req = new JsonObjectRequest(Request.Method.GET, url, null,
                        response -> {
                            try {
                                JSONArray jsonArray = response.getJSONArray("results");

                                for (int i = 0; i < jsonArray.length(); i++) {
                                    JSONObject res = jsonArray.getJSONObject(i);

                                    int mId = res.getInt("id");
                                    String mImageUrl = "https://image.tmdb.org/t/p/w500" + res.getString("poster_path");
                                    String mName = res.getString("original_title");
                                    Float mRating = Float.parseFloat(res.getString("vote_average"));
                                    String mBackdrop = "https://image.tmdb.org/t/p/w500" + res.getString("backdrop_path");
                                    String mOverview = res.getString("overview");
                                    String mRelease = "Coming Soon";

                                    if (res.has("release_date")) {
                                        Log.d("no value", "run: Masukk");
                                        mRelease = res.getString("release_date");
                                    }

                                    Float mPopularity = Float.parseFloat(res.getString("popularity"));

                                    arrMovie.add(new MovieResponse(mId, mImageUrl, mName, mRating, mBackdrop, mRelease, mOverview, mPopularity));
                                }

                                adaptMovie = new AdapterMovie(getContext(), arrMovie);

                                fragmentMovieHomeBinding.rvFragmentMovieHome.setAdapter(adaptMovie);
                                adaptMovie.notifyDataSetChanged();

                                fragmentMovieHomeBinding.rvFragmentMovieHome.getLayoutManager().onRestoreInstanceState(recyclerViewState);
                            } catch (JSONException e) {
                                e.printStackTrace();
                            }
                        }, error -> error.printStackTrace());

                reqQue.add(req);

                fragmentMovieHomeBinding.progressBarMovie.setVisibility(View.GONE);
                pagination();
            }
        }, 1000);
    }

    private void pagination() {
        fragmentMovieHomeBinding.rvFragmentMovieHome.addOnScrollListener(new RecyclerView.OnScrollListener() {
            @Override
            public void onScrollStateChanged(@NonNull RecyclerView recyclerView, int newState) {
                super.onScrollStateChanged(recyclerView, newState);
                recyclerViewState = recyclerView.getLayoutManager().onSaveInstanceState();
            }

            @Override
            public void onScrolled(@NonNull RecyclerView recyclerView, int dx, int dy) {
                super.onScrolled(recyclerView, dx, dy);

                load = false;
                lastCompleteVisibleItemPosition = 0;

                totalItemCount = gridLayoutManager.getItemCount();
                lastCompleteVisibleItemPosition = gridLayoutManager.findLastCompletelyVisibleItemPosition();
                Log.d("jum", totalItemCount + "," + lastCompleteVisibleItemPosition);

                try {
                    if (lastCompleteVisibleItemPosition == totalItemCount - 1) {
//                        Toast.makeText(getContext(), lastCompleteVisibleItemPosition + " com " + totalItemCount, Toast.LENGTH_SHORT).show();

                        if (!load) {
//                            Toast.makeText(getContext(), "Persiapan Loading Halaman "+ (page+1) +" "+ load, Toast.LENGTH_SHORT).show();

                            load = true;

                            page++;
                            recyclerViewState = recyclerView.getLayoutManager().onSaveInstanceState();

                            parseJSON();
                        }
                    }
                } catch (Exception e) {
                    e.printStackTrace();
                }

            }
        });
    }
};