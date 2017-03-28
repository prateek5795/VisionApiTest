package com.example.prateek.visionapitest.Fragment;

import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.example.prateek.visionapitest.Adapter.TvAdapter;
import com.example.prateek.visionapitest.Model.Tv;
import com.example.prateek.visionapitest.Model.TvResponse;
import com.example.prateek.visionapitest.R;
import com.example.prateek.visionapitest.Rest.ApiClient;
import com.example.prateek.visionapitest.Rest.ApiInterface;

import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class TvTab extends Fragment {

    private final static String API_KEY = "eda6b16ebbfe1bb908ee2cf30ac8a979";

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View rootView = inflater.inflate(R.layout.tab_tv, container, false);

        final RecyclerView tvRecyclerView = (RecyclerView) rootView.findViewById(R.id.tv_recycler_view);
        tvRecyclerView.setLayoutManager(new LinearLayoutManager(getContext()));

        ApiInterface apiService = ApiClient.getClient().create(ApiInterface.class);

        Call<TvResponse> call = apiService.getTvAccGenre(API_KEY, "18");
        call.enqueue(new Callback<TvResponse>() {
            @Override
            public void onResponse(Call<TvResponse> call, Response<TvResponse> response) {
                final List<Tv> tv = response.body().getResults();
                tvRecyclerView.setAdapter(new TvAdapter(tv, R.layout.list_item_tv, getContext()));
            }

            @Override
            public void onFailure(Call<TvResponse> call, Throwable t) {

            }
        });

        return rootView;
    }
}
