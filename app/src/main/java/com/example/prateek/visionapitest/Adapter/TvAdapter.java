package com.example.prateek.visionapitest.Adapter;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.example.prateek.visionapitest.Model.Tv;
import com.example.prateek.visionapitest.R;

import java.util.List;

public class TvAdapter extends RecyclerView.Adapter<TvAdapter.TvViewHolder> {

    private List<Tv> tv;
    private int rowLayout;
    private Context context;

    public static class TvViewHolder extends RecyclerView.ViewHolder {

        LinearLayout tvLayout;
        TextView tvTitle;
        TextView firstAirDate;
        TextView tvOvervire;

        public TvViewHolder(View v) {
            super(v);

            tvLayout = (LinearLayout) v.findViewById(R.id.tv_layout);
            tvTitle = (TextView) v.findViewById(R.id.title);
            firstAirDate = (TextView) v.findViewById(R.id.firstAirDate);
            tvOvervire = (TextView) v.findViewById(R.id.overview);
        }
    }

    public TvAdapter(List<Tv> tv, int rowLayout, Context context) {
        this.tv = tv;
        this.rowLayout = rowLayout;
        this.context = context;
    }

    @Override
    public TvViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(rowLayout, parent, false);
        return new TvViewHolder(view);
    }

    @Override
    public void onBindViewHolder(TvViewHolder holder, int position) {
        holder.tvTitle.setText(tv.get(position).getName());
        holder.firstAirDate.setText(tv.get(position).getFirstAirDate());
        holder.tvOvervire.setText(tv.get(position).getOverview());
    }

    @Override
    public int getItemCount() {
        return tv.size();
    }
}
