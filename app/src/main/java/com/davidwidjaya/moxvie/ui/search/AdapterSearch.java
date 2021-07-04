package com.davidwidjaya.moxvie.ui.search;

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
import com.davidwidjaya.moxvie.data.remote.SearchResponse;
import com.davidwidjaya.moxvie.ui.detail.DetailMovieActivity;
import com.squareup.picasso.Picasso;

import java.util.ArrayList;

public class AdapterSearch extends RecyclerView.Adapter<AdapterSearch.SearchViewHolder> {

    public class SearchViewHolder extends RecyclerView.ViewHolder {
        public ImageView iv;
        public TextView tv;

        public SearchViewHolder(@NonNull View v) {
            super(v);

            iv = v.findViewById(R.id.iv_adapter_tv_home);
            tv = v.findViewById(R.id.tv_adapter_tv_home);
        }
    }

    private Context context;
    private ArrayList<SearchResponse> arrSearch;

    public AdapterSearch(Context context, ArrayList<SearchResponse> arrSearch) {
        this.context = context;
        this.arrSearch = arrSearch;
    }


    @NonNull
    @Override
    public AdapterSearch.SearchViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View v = LayoutInflater.from(context).inflate(R.layout.adapter_tv_home, parent, false);
        return new AdapterSearch.SearchViewHolder(v);
    }

    @Override
    public void onBindViewHolder(@NonNull AdapterSearch.SearchViewHolder holder, int position) {
        SearchResponse item = arrSearch.get(position);

        String image = item.getImageUrl();
        String name = item.getName();

        holder.tv.setText(name);
        Picasso.get().load(image).fit().centerInside().into(holder.iv);

        holder.itemView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                Toast.makeText(context, name, Toast.LENGTH_SHORT).show();

                Intent i = new Intent(context, DetailMovieActivity.class);
                i.putParcelableArrayListExtra("arrSearch", arrSearch);
                i.putExtra("id", item.getId());
                context.startActivity(i);
            }
        });
    }

    @Override
    public int getItemCount() {
        return arrSearch.size();
    }
}
