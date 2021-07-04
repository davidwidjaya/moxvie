package com.davidwidjaya.moxvie.ui.search;

import android.os.Bundle;
import android.os.Handler;
import android.os.Parcelable;
import android.util.Log;
import android.view.View;
import android.widget.ImageButton;
import android.widget.ProgressBar;
import android.widget.SearchView;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.toolbox.JsonObjectRequest;
import com.android.volley.toolbox.Volley;
import com.davidwidjaya.moxvie.R;
import com.davidwidjaya.moxvie.data.remote.SearchResponse;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;

public class SearchActivity extends AppCompatActivity {

    SearchView sv;
    ImageButton iBBack;
    RecyclerView rvSearch;
    ProgressBar pbSearch;

    private ArrayList<SearchResponse> arrSearch;
    private RequestQueue reqQue;
    private AdapterSearch adaptTv;
    private GridLayoutManager gridLayoutManager;

    private int PAGE = 1;
    private int totalItemCount;
    private int lastCompletelyVisibleItemPosition;
    private Parcelable recyclerViewState;
    private boolean load = false;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_search);

        sv = findViewById(R.id.searchView);
        rvSearch = findViewById(R.id.rvSearch);
        pbSearch = findViewById(R.id.pbSearch);
        pbSearch.setVisibility(View.GONE);

        iBBack = findViewById(R.id.iBBack);
        iBBack.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finish();
            }
        });

        gridLayoutManager = new GridLayoutManager(this, 3);
        rvSearch.setLayoutManager(gridLayoutManager);
        arrSearch = new ArrayList<>();
        reqQue = Volley.newRequestQueue(SearchActivity.this);

        sv.setFocusable(true);
        sv.setIconified(false);

        sv.setOnQueryTextListener(new SearchView.OnQueryTextListener() {
            @Override
            public boolean onQueryTextSubmit(String query) {
                arrSearch = new ArrayList<>();
                reqQue = Volley.newRequestQueue(SearchActivity.this);
                parseJSON(query);
                return true;
            }

            @Override
            public boolean onQueryTextChange(String newText) {
                return false;
            }
        });
    }

    private void parseJSON(String query) {
        pbSearch.setVisibility(View.VISIBLE);
        adaptTv = new AdapterSearch(SearchActivity.this, arrSearch);

        new Handler().postDelayed(new Runnable() {
            @Override
            public void run() {
                // SEARCH MOVIE
                String urlMovie = "https://api.themoviedb.org/3/search/movie?api_key=c0feffca73bfb6338bcbd69dc83d295d&query=" + query + "&page=" + PAGE;
                Log.d("23gg URL", "run: " + urlMovie);

                JsonObjectRequest reqMovie = new JsonObjectRequest(Request.Method.GET, urlMovie, null,
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
                                        mRelease = res.getString("release_date");
                                    }

                                    Float mPopularity = Float.parseFloat(res.getString("popularity"));

                                    arrSearch.add(new SearchResponse(mId, mImageUrl, mName, mRating, mBackdrop, mRelease, mOverview, mPopularity, "movie"));
                                }

                                rvSearch.setAdapter(adaptTv);
                                rvSearch.getLayoutManager().onRestoreInstanceState(recyclerViewState);
                                adaptTv.notifyDataSetChanged();
                                pbSearch.setVisibility(View.GONE);
                                pagination(query);

                            } catch (JSONException e) {
                                e.printStackTrace();
                            }
                        }, error -> error.printStackTrace());

                reqQue.add(reqMovie);

                // SEARCH TV SHOW
                String urlTv = "https://api.themoviedb.org/3/search/tv?api_key=c0feffca73bfb6338bcbd69dc83d295d&query=" + query + "&page=" + PAGE;
                JsonObjectRequest reqTV = new JsonObjectRequest(Request.Method.GET, urlTv, null,
                        response -> {
                            try {
                                JSONArray jsonArray = response.getJSONArray("results");

                                for (int i = 0; i < jsonArray.length(); i++) {
                                    JSONObject res = jsonArray.getJSONObject(i);

                                    int tId = res.getInt("id");
                                    String tImageUrl = "https://image.tmdb.org/t/p/w500" + res.getString("poster_path");
                                    String tName = res.getString("original_name");
                                    Float tRating = Float.parseFloat(res.getString("vote_average"));
                                    String tBackdrop = "https://image.tmdb.org/t/p/w500" + res.getString("backdrop_path");
                                    String tOverview = res.getString("overview");
                                    String tRelease = res.getString("first_air_date");
                                    Float tPopularity = Float.parseFloat(res.getString("popularity"));

                                    arrSearch.add(new SearchResponse(tId, tImageUrl, tName, tRating, tBackdrop, tRelease, tOverview, tPopularity, "tv"));
                                }

                                rvSearch.setAdapter(adaptTv);
                                rvSearch.getLayoutManager().onRestoreInstanceState(recyclerViewState);
                                adaptTv.notifyDataSetChanged();
                                pbSearch.setVisibility(View.GONE);
                                pagination(query);

                            } catch (JSONException e) {
                                e.printStackTrace();
                            }
                        }, error -> error.printStackTrace());
                reqQue.add(reqTV);
            }
        }, 500);

    }

    private void pagination(String query) {
        rvSearch.addOnScrollListener(new RecyclerView.OnScrollListener() {
            @Override
            public void onScrollStateChanged(@NonNull RecyclerView recyclerView, int newState) {
                super.onScrollStateChanged(recyclerView, newState);
                recyclerViewState = recyclerView.getLayoutManager().onSaveInstanceState();
            }

            @Override
            public void onScrolled(@NonNull RecyclerView recyclerView, int dx, int dy) {
                super.onScrolled(recyclerView, dx, dy);

                load = false;
                lastCompletelyVisibleItemPosition = 0;

                totalItemCount = gridLayoutManager.getItemCount();
                lastCompletelyVisibleItemPosition = gridLayoutManager.findLastCompletelyVisibleItemPosition();
                Log.d("jum", totalItemCount + "," + lastCompletelyVisibleItemPosition);

                try {
                    if (lastCompletelyVisibleItemPosition == totalItemCount - 1) {

                        if (!load) {
                            load = true;

                            PAGE++;
                            recyclerViewState = recyclerView.getLayoutManager().onSaveInstanceState();

                            parseJSON(query);
                        }
                    }
                } catch (Exception e) {
                    e.printStackTrace();
                }

            }
        });
    }
}