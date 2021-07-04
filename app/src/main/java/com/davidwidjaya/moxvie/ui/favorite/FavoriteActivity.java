package com.davidwidjaya.moxvie.ui.favorite;

import android.os.Bundle;

import androidx.appcompat.app.ActionBar;
import androidx.appcompat.app.AppCompatActivity;

import com.davidwidjaya.moxvie.R;
import com.davidwidjaya.moxvie.databinding.ActivityFavoriteBinding;

public class FavoriteActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_favorite);

        ActivityFavoriteBinding activityFavoriteBinding = ActivityFavoriteBinding.inflate(getLayoutInflater());
        setContentView(activityFavoriteBinding.getRoot());

        SectionsPagerAdapterFavorite sectionsPagerAdapterFavorite = new SectionsPagerAdapterFavorite(this, getSupportFragmentManager());

        activityFavoriteBinding.viewPagerFavorite.setAdapter(sectionsPagerAdapterFavorite);
        activityFavoriteBinding.tabsFavorite.setupWithViewPager(activityFavoriteBinding.viewPagerFavorite);

        if (getSupportActionBar() != null) {
            getSupportActionBar().setElevation(0);
            ActionBar actionBar = getSupportActionBar();
            actionBar.setTitle("Your Favorited");
        }
    }
}