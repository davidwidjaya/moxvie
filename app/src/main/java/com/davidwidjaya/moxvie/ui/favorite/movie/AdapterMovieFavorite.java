package com.davidwidjaya.moxvie.ui.favorite.movie;

import android.content.Context;
import android.content.Intent;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.RatingBar;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.davidwidjaya.moxvie.R;
import com.davidwidjaya.moxvie.data.local.entity.Movie;
import com.davidwidjaya.moxvie.data.remote.MovieResponse;
import com.davidwidjaya.moxvie.ui.favorite.DetailMovieFavoriteActivity;
import com.squareup.picasso.Picasso;

import java.util.ArrayList;

public class AdapterMovieFavorite extends RecyclerView.Adapter<AdapterMovieFavorite.MovieViewHolder> {

    public class MovieViewHolder extends RecyclerView.ViewHolder {
        public ImageView iv;
        public TextView tv;
        public RatingBar rb;

        public MovieViewHolder(@NonNull View v) {
            super(v);

            iv = v.findViewById(R.id.ivAdapterMovieHome);
            tv = v.findViewById(R.id.tvAdapterMovieHome);
            rb = v.findViewById(R.id.rbAdapterMovieHome);
        }
    }

    private Context context;
    private ArrayList<Movie> arrMovie;
    private ArrayList<MovieResponse> arrMovieResponse;

    public AdapterMovieFavorite(Context context, ArrayList<Movie> arrMovie) {
        this.context = context;
        this.arrMovie = arrMovie;
    }

    @NonNull
    @Override
    public MovieViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View v = LayoutInflater.from(context).inflate(R.layout.adapter_movie_home, parent, false);
        Log.d("arrMovie", "AdapterMovieFavorite: " + this.arrMovie);

        return new MovieViewHolder(v);
    }

    @Override
    public void onBindViewHolder(@NonNull MovieViewHolder holder, int position) {
        Movie item = arrMovie.get(position);
        Log.d("MovieFavoriteFragment", item.getName());

        String image = item.getImageUrl();
        String name = item.getName();
        Float rating = item.getRating();

        holder.tv.setText(name);
        holder.rb.setRating(rating / 2);
        Picasso.get().load(image).fit().centerInside().into(holder.iv);

        arrMovieResponse = new ArrayList<>();
        for (int i = 0; i < arrMovie.size(); i++) {
            int mId = arrMovie.get(i).getId();
            String mImageUrl = arrMovie.get(i).getImageUrl();
            String mName = arrMovie.get(i).getName();
            Float mRating = arrMovie.get(i).getRating();
            String mBackdrop = arrMovie.get(i).getBackdrop();
            String mOverview = arrMovie.get(i).getOverview();
            String mRelease = arrMovie.get(i).getRelease();
            Float mPopularity = arrMovie.get(i).getPopularity();
            arrMovieResponse.add(new MovieResponse(mId, mImageUrl, mName, mRating, mBackdrop, mRelease, mOverview, mPopularity));
        }

        holder.itemView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                Toast.makeText(context, name, Toast.LENGTH_SHORT).show();

                Intent i = new Intent(context, DetailMovieFavoriteActivity.class);
                i.putParcelableArrayListExtra("arrMovie", arrMovieResponse);
                i.putExtra("id", item.getId());
                context.startActivity(i);
            }
        });
    }

    @Override
    public int getItemCount() {
        return arrMovie.size();
    }
}