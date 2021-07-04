package com.davidwidjaya.moxvie.ui.home.movie;

import android.content.Context;
import android.content.Intent;
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
import com.davidwidjaya.moxvie.data.remote.MovieResponse;
import com.davidwidjaya.moxvie.ui.detail.DetailMovieActivity;
import com.squareup.picasso.Picasso;

import java.util.ArrayList;

public class AdapterMovie extends RecyclerView.Adapter<AdapterMovie.MovieViewHolder> {

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
    private ArrayList<MovieResponse> arrMovie;

    public AdapterMovie(Context context, ArrayList<MovieResponse> arrMovie) {
        this.context = context;
        this.arrMovie = arrMovie;
    }

    @NonNull
    @Override
    public MovieViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View v = LayoutInflater.from(context).inflate(R.layout.adapter_movie_home, parent, false);
        return new MovieViewHolder(v);
    }

    @Override
    public void onBindViewHolder(@NonNull MovieViewHolder holder, int position) {
        MovieResponse item = arrMovie.get(position);

        String image = item.getImageUrl();
        String name = item.getName();
        Float rating = item.getRating();

        holder.tv.setText(name);
        holder.rb.setRating(rating / 2);
        Picasso.get().load(image).fit().centerInside().into(holder.iv);

        holder.itemView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                Toast.makeText(context, name, Toast.LENGTH_SHORT).show();

                Intent i = new Intent(context, DetailMovieActivity.class);
                i.putParcelableArrayListExtra("arrMovie", arrMovie);
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