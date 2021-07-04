package com.davidwidjaya.moxvie.ui.home.tv;

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
import com.davidwidjaya.moxvie.data.remote.TvResponse;
import com.davidwidjaya.moxvie.databinding.FragmentTvHomeBinding;
import com.davidwidjaya.moxvie.ui.search.SearchActivity;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;

public class TvHomeFragment extends Fragment {

    private FragmentTvHomeBinding fragmentTvHomeBinding;
    private ArrayList<TvResponse> arrTv;
    private RequestQueue reqQue;
    private AdapterTv adaptTv;
    private GridLayoutManager gridLayoutManager;

    private String url;
    private int PAGE = 1;
    private int totalItemCount;
    private int lastCompletelyVisibleItemPosition;
    private Parcelable recyclerViewState;
    private boolean load = false;

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        fragmentTvHomeBinding = FragmentTvHomeBinding.inflate(getLayoutInflater(), container, false);

        fragmentTvHomeBinding.clTvSearch.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent icl = new Intent(getContext(), SearchActivity.class);
                startActivity(icl);
            }
        });
        fragmentTvHomeBinding.ivTvSearch.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent icl = new Intent(getContext(), SearchActivity.class);
                startActivity(icl);
            }
        });
        fragmentTvHomeBinding.tvTvSearch.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent icl = new Intent(getContext(), SearchActivity.class);
                startActivity(icl);
            }
        });

        gridLayoutManager = new GridLayoutManager(getContext(), 3);
        fragmentTvHomeBinding.rvFragmentTvHome.setLayoutManager(gridLayoutManager);

        arrTv = new ArrayList<>();
        reqQue = Volley.newRequestQueue(getContext());

        parseJSON();

        return fragmentTvHomeBinding.getRoot();
    }


    private void parseJSON() {
        fragmentTvHomeBinding.progressBarTv.setVisibility(View.VISIBLE);

        new Handler().postDelayed(new Runnable() {
            @Override
            public void run() {
                url = "https://api.themoviedb.org/3/tv/popular?api_key=c0feffca73bfb6338bcbd69dc83d295d&page=" + PAGE;
                JsonObjectRequest req = new JsonObjectRequest(Request.Method.GET, url, null,
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

                                    arrTv.add(new TvResponse(tId, tImageUrl, tName, tRating, tBackdrop, tRelease, tOverview, tPopularity));
                                }

                                adaptTv = new AdapterTv(getContext(), arrTv);
                                fragmentTvHomeBinding.rvFragmentTvHome.setAdapter(adaptTv);
                                adaptTv.notifyDataSetChanged();
                                fragmentTvHomeBinding.rvFragmentTvHome.getLayoutManager().onRestoreInstanceState(recyclerViewState);
                            } catch (JSONException e) {
                                e.printStackTrace();
                            }
                        }, error -> error.printStackTrace());
                reqQue.add(req);

                fragmentTvHomeBinding.progressBarTv.setVisibility(View.GONE);
                pagination();

            }
        }, 1000);
    }

    private void pagination() {
        fragmentTvHomeBinding.rvFragmentTvHome.addOnScrollListener(new RecyclerView.OnScrollListener() {
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
//                        Toast.makeText(getContext(), lastCompletelyVisibleItemPosition + " com " + totalItemCount, Toast.LENGTH_SHORT).show();
                        if (!load) {
                            load = true;
                            PAGE++;
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
}