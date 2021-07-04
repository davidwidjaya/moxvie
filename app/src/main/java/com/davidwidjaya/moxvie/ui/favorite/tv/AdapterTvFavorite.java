package com.davidwidjaya.moxvie.ui.favorite.tv;

import android.content.Context;
import android.content.Intent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.davidwidjaya.moxvie.R;
import com.davidwidjaya.moxvie.data.local.entity.Tv;
import com.davidwidjaya.moxvie.data.remote.TvResponse;
import com.davidwidjaya.moxvie.ui.favorite.DetailMovieFavoriteActivity;
import com.squareup.picasso.Picasso;

import java.util.ArrayList;

public class AdapterTvFavorite extends RecyclerView.Adapter<AdapterTvFavorite.TvFavoriteViewHolder> {

    public class TvFavoriteViewHolder extends RecyclerView.ViewHolder {
        public ImageView ivTvF;
        public TextView tvTvF;

        public TvFavoriteViewHolder(@NonNull View itemView) {
            super(itemView);

            ivTvF = itemView.findViewById(R.id.iv_adapter_tv_home);
            tvTvF = itemView.findViewById(R.id.tv_adapter_tv_home);
        }
    }

    private Context context;
    private ArrayList<Tv> arrTv;
    private ArrayList<TvResponse> arrTvResponse;

    public AdapterTvFavorite(Context context, ArrayList<Tv> arrTv) {
        this.context = context;
        this.arrTv = arrTv;
    }

    @NonNull
    @Override
    public TvFavoriteViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View v = LayoutInflater.from(context).inflate(R.layout.adapter_tv_home, parent, false);
        return new TvFavoriteViewHolder(v);
    }

    @Override
    public void onBindViewHolder(@NonNull TvFavoriteViewHolder holder, int position) {
        Tv res = arrTv.get(position);

        String image = res.getImageUrl();
        String name = res.getName();

        holder.tvTvF.setText(name);
        Picasso.get().load(image).fit().centerInside().into(holder.ivTvF);

        arrTvResponse = new ArrayList<>();

        for (int j = 0; j < arrTv.size(); j++) {
            int tId = arrTv.get(j).getId();
            String tImageUrl = arrTv.get(j).getImageUrl();
            String tName = arrTv.get(j).getName();
            Float tRating = arrTv.get(j).getRating();
            String tBackdrop = arrTv.get(j).getBackdrop();
            String tOverview = arrTv.get(j).getOverview();
            String tRelease = arrTv.get(j).getRelease();
            Float tPopularity = arrTv.get(j).getPopularity();

            arrTvResponse.add(new TvResponse(tId, tImageUrl, tName, tRating, tBackdrop, tRelease, tOverview, tPopularity));
        }

        holder.itemView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                Toast.makeText(context, name, Toast.LENGTH_SHORT).show();

                Intent i = new Intent(context, DetailMovieFavoriteActivity.class);
                i.putParcelableArrayListExtra("arrTv", arrTvResponse);
                i.putExtra("id", res.getId());
                context.startActivity(i);
            }
        });
    }

    @Override
    public int getItemCount() {
        return arrTv.size();
    }


}
