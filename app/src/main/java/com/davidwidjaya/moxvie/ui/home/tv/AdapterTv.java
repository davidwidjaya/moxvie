package com.davidwidjaya.moxvie.ui.home.tv;

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
import com.davidwidjaya.moxvie.data.remote.TvResponse;
import com.davidwidjaya.moxvie.ui.detail.DetailMovieActivity;
import com.squareup.picasso.Picasso;

import java.util.ArrayList;

public class AdapterTv extends RecyclerView.Adapter<AdapterTv.TvViewHolder> {

    public class TvViewHolder extends RecyclerView.ViewHolder {
        public ImageView iv;
        public TextView tv;

        public TvViewHolder(@NonNull View v) {
            super(v);

            iv = v.findViewById(R.id.iv_adapter_tv_home);
            tv = v.findViewById(R.id.tv_adapter_tv_home);
        }
    }

    private Context context;
    private ArrayList<TvResponse> arrTv;

    public AdapterTv(Context context, ArrayList<TvResponse> arrTv) {
        this.context = context;
        this.arrTv = arrTv;
    }

    @NonNull
    @Override
    public TvViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View v = LayoutInflater.from(context).inflate(R.layout.adapter_tv_home, parent, false);
        return new TvViewHolder(v);
    }

    @Override
    public void onBindViewHolder(@NonNull TvViewHolder holder, int position) {
        TvResponse item = arrTv.get(position);

        String image = item.getImageUrl();
        String name = item.getName();

        holder.tv.setText(name);
        Picasso.get().load(image).fit().centerInside().into(holder.iv);

        holder.itemView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                Toast.makeText(context, name, Toast.LENGTH_SHORT).show();

                Intent i = new Intent(context, DetailMovieActivity.class);
                i.putParcelableArrayListExtra("arrTv", arrTv);
                i.putExtra("id", item.getId());
                context.startActivity(i);
            }
        });
    }

    @Override
    public int getItemCount() {
        return arrTv.size();
    }
}