package com.davidwidjaya.moxvie.ui.home;

import android.content.Intent;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;

import androidx.annotation.NonNull;
import androidx.appcompat.app.ActionBar;
import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.Fragment;

import com.davidwidjaya.moxvie.R;
import com.davidwidjaya.moxvie.ui.favorite.FavoriteActivity;
import com.davidwidjaya.moxvie.ui.home.movie.MovieHomeFragment;
import com.davidwidjaya.moxvie.ui.home.tv.TvHomeFragment;
import com.google.android.material.bottomnavigation.BottomNavigationView;

public class MainActivity extends AppCompatActivity {

    BottomNavigationView bottomNavigationView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        bottomNavigationView = findViewById(R.id.bottom_navigation);

        bottomNavigationView.setOnNavigationItemSelectedListener(bottomNavigationViewListener);

        if (savedInstanceState == null) {
            bottomNavigationView.setSelectedItemId(R.id.m_bMovie);
        }

        if (getSupportActionBar() != null) {
            getSupportActionBar().setElevation(0);
            ActionBar actionBar = getSupportActionBar();
            actionBar.setTitle("MOXVI");
        }
    }

    private BottomNavigationView.OnNavigationItemSelectedListener bottomNavigationViewListener =
            new BottomNavigationView.OnNavigationItemSelectedListener() {
                @Override
                public boolean onNavigationItemSelected(@NonNull MenuItem item) {
                    Fragment content = null;
                    switch (item.getItemId()) {
                        case R.id.m_bMovie:
                            content = new MovieHomeFragment();
                            break;
                        case R.id.m_bTvShow:
                            content = new TvHomeFragment();
                            break;
                    }
                    getSupportFragmentManager().beginTransaction().replace(R.id.fragment_container, content).commit();
                    return true;
                }
            };


    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.option_favorite, menu);
        return super.onCreateOptionsMenu(menu);
    }

    @Override
    public boolean onOptionsItemSelected(@NonNull MenuItem item) {
        if (item.getItemId() == R.id.act_list_fav) {
            //ke list favorite
            Intent intent = new Intent(getApplicationContext(), FavoriteActivity.class);
            startActivity(intent);
        }
        return super.onOptionsItemSelected(item);
    }
}