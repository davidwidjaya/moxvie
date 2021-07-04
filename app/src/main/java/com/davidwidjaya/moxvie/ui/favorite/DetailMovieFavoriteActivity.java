package com.davidwidjaya.moxvie.ui.favorite;

import android.content.Intent;
import android.os.AsyncTask;
import android.os.Build;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.ImageButton;
import android.widget.Toast;

import androidx.annotation.RequiresApi;
import androidx.appcompat.app.AppCompatActivity;

import com.davidwidjaya.moxvie.R;
import com.davidwidjaya.moxvie.data.local.entity.Movie;
import com.davidwidjaya.moxvie.data.local.entity.Tv;
import com.davidwidjaya.moxvie.data.local.room.AppDatabase;
import com.davidwidjaya.moxvie.data.remote.MovieResponse;
import com.davidwidjaya.moxvie.data.remote.TvResponse;
import com.davidwidjaya.moxvie.databinding.ActivityDetailMovieBinding;
import com.squareup.picasso.Picasso;

import java.util.ArrayList;

public class DetailMovieFavoriteActivity extends AppCompatActivity {

    ArrayList<MovieResponse> arrMovie;
    ArrayList<TvResponse> arrTv;
    int id;
    MovieResponse tempObjMovie;
    TvResponse tempObjTv;
    private ImageButton img;
    String section;

    Movie tempMovie;
    Tv tempTv;

    AppDatabase db;
    boolean isFavorite = false;

    @RequiresApi(api = Build.VERSION_CODES.M)
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_detail_movie);
        db = AppDatabase.getInstance(getApplicationContext()); //cek database

        ActivityDetailMovieBinding activityDetailMovieBinding = ActivityDetailMovieBinding.inflate(getLayoutInflater());
        setContentView(activityDetailMovieBinding.getRoot());
        img = activityDetailMovieBinding.btnActivityDetailMovieFavorite;

        Intent i = getIntent();
        if (i.getExtras() != null) {
            //jika tidak kosong
            id = i.getIntExtra("id", -1);
            Log.d("cev", isFavorite + "");

            if (i.hasExtra("arrMovie")) {
                new DetailMovieFavoriteActivity.LoadIsFavoriteMovie().execute(); //cek apa sudah di favorite sebelumnya
                section = "arrMovie";

                arrMovie = i.getParcelableArrayListExtra("arrMovie");
                for (int j = 0; j < arrMovie.size(); j++) {
                    if (id == arrMovie.get(j).getId()) {
                        tempObjMovie = arrMovie.get(j);
                    }
                }
                Picasso.get().load(tempObjMovie.getBackdrop()).fit().centerInside().into(activityDetailMovieBinding.ivBackdropActivityDetailMovie);
                Picasso.get().load(tempObjMovie.getImageUrl()).fit().centerInside().into(activityDetailMovieBinding.ivPoster);
                activityDetailMovieBinding.tvActivityDetailMovieName.setText(tempObjMovie.getName());
                activityDetailMovieBinding.tvActivityDetailMoviePopularity.setText(tempObjMovie.getPopularity() + " Views");
                activityDetailMovieBinding.tvActivityDetailMovieRating.setText(tempObjMovie.getRating() + "");
                activityDetailMovieBinding.tvActivityDetailMovieRelease.setText(tempObjMovie.getRelease());
                activityDetailMovieBinding.tvActivityDetailMovieOverview.setText(tempObjMovie.getOverview());

            } else if (i.hasExtra("arrTv")) {
                new DetailMovieFavoriteActivity.LoadIsFavoriteTv().execute();
                section = "arrTv";

                arrTv = i.getParcelableArrayListExtra("arrTv");
                for (int j = 0; j < arrTv.size(); j++) {
                    if (id == arrTv.get(j).getId()) {
                        tempObjTv = arrTv.get(j);
                    }
                }

                Picasso.get().load(tempObjTv.getBackdrop()).fit().centerInside().into(activityDetailMovieBinding.ivBackdropActivityDetailMovie);
                Picasso.get().load(tempObjTv.getImageUrl()).fit().centerInside().into(activityDetailMovieBinding.ivPoster);
                activityDetailMovieBinding.tvActivityDetailMovieName.setText(tempObjTv.getName());
                activityDetailMovieBinding.tvActivityDetailMoviePopularity.setText(tempObjTv.getPopularity() + " Views");
                activityDetailMovieBinding.tvActivityDetailMovieRating.setText(tempObjTv.getRating() + "");
                activityDetailMovieBinding.tvActivityDetailMovieRelease.setText(tempObjTv.getRelease());
                activityDetailMovieBinding.tvActivityDetailMovieOverview.setText(tempObjTv.getOverview());

            }

        }

        activityDetailMovieBinding.btnActivityDetailMovieBack.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finish();
            }
        });

        activityDetailMovieBinding.btnActivityDetailMovieFavorite.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (tempObjTv != null && section.equals("arrTv")) {
                    tempTv = new Tv(tempObjTv.getId(), tempObjTv.getImageUrl(), tempObjTv.getName(), tempObjTv.getRating(), tempObjTv.getBackdrop(), tempObjTv.getRelease(), tempObjTv.getOverview(), tempObjTv.getPopularity());
                    if (isFavorite) {
                        //jika sudah di favorit
                        new DetailMovieFavoriteActivity.DeleteTvTask().execute(tempTv);
                    } else {
                        //add
                        new DetailMovieFavoriteActivity.AddTvTask().execute(tempTv);
                    }
                }
                if (tempObjMovie != null && section.equals("arrMovie")) {
                    tempMovie = new Movie(tempObjMovie.getId(), tempObjMovie.getImageUrl(), tempObjMovie.getName(), tempObjMovie.getRating(), tempObjMovie.getBackdrop(), tempObjMovie.getRelease(), tempObjMovie.getOverview(), tempObjMovie.getPopularity());
                    if (isFavorite) {
                        //jika sudah di favorit
                        new DetailMovieFavoriteActivity.DeleteMovieTask().execute(tempMovie);
                    } else {
                        //add
                        new DetailMovieFavoriteActivity.AddMovieTask().execute(tempMovie);
                    }
                }

            }
        });
    }


    private class AddTvTask extends AsyncTask<Tv, Void, Void> {
        @Override
        protected Void doInBackground(Tv... tvs) {
            db.appDao().insert(tvs[0]);
            return null;
        }

        @Override
        protected void onPostExecute(Void aVoid) {
            super.onPostExecute(aVoid);
            isFavorite = true;
            setFavoriteState(isFavorite);
            Toast.makeText(DetailMovieFavoriteActivity.this, "Success Favorited", Toast.LENGTH_SHORT).show();
        }
    }

    private class DeleteTvTask extends AsyncTask<Tv, Void, Void> {
        @Override
        protected Void doInBackground(Tv... tvs) {
            db.appDao().delete(tvs[0]);
            return null;
        }

        @Override
        protected void onPostExecute(Void aVoid) {
            super.onPostExecute(aVoid);
            isFavorite = false;
            setFavoriteState(isFavorite);
            Toast.makeText(DetailMovieFavoriteActivity.this, "Success Deleted", Toast.LENGTH_SHORT).show();
        }
    }

    private class AddMovieTask extends AsyncTask<Movie, Void, Void> {
        @Override
        protected Void doInBackground(Movie... movies) {
            db.appDao().insertMovie(movies[0]);
            return null;
        }

        @Override
        protected void onPostExecute(Void aVoid) {
            super.onPostExecute(aVoid);
            isFavorite = true;
            setFavoriteState(isFavorite);
            Toast.makeText(DetailMovieFavoriteActivity.this, "Success Favorited", Toast.LENGTH_SHORT).show();
        }
    }

    private class DeleteMovieTask extends AsyncTask<Movie, Void, Void> {
        @Override
        protected Void doInBackground(Movie... movies) {
            db.appDao().deleteMovie(movies[0]);
            return null;
        }

        @Override
        protected void onPostExecute(Void aVoid) {
            super.onPostExecute(aVoid);
            isFavorite = false;
            setFavoriteState(isFavorite);
            Toast.makeText(DetailMovieFavoriteActivity.this, "Success Deleted", Toast.LENGTH_SHORT).show();
        }
    }

    private class LoadIsFavoriteMovie extends AsyncTask<Void, Void, Movie> {
        @Override
        protected Movie doInBackground(Void... voids) {
            return db.appDao().getFavoritedMovieById(id);
        }

        @Override
        protected void onPostExecute(Movie movies) {
            super.onPostExecute(movies);
            if (movies != null) {
                //sudah pernah difavoritkan di room
                isFavorite = true;
                setFavoriteState(isFavorite);
            }
            Log.d("setgambarLoadRoom", isFavorite + "");
        }
    }

    private class LoadIsFavoriteTv extends AsyncTask<Void, Void, Tv> {
        @Override
        protected void onPostExecute(Tv tv) {
            super.onPostExecute(tv);
            if (tv != null) {
                isFavorite = true;
                setFavoriteState(isFavorite);
            }
        }

        @Override
        protected Tv doInBackground(Void... voids) {
            return db.appDao().getFavoritedTVById(id + "");
        }
    }

    private void setFavoriteState(boolean state) {
        if (img == null) return;
        Log.d("setgambar", state + "");
        if (state) {
            //difavoritkan
            img.setImageResource(R.drawable.ic_favorite);
        } else {
            img.setImageResource(R.drawable.ic_unfavorite);
        }
    }
}