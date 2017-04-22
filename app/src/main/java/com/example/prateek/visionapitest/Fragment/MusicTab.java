package com.example.prateek.visionapitest.Fragment;

import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.example.prateek.visionapitest.Adapter.MusicAdapter;
import com.example.prateek.visionapitest.Model.MusicResponse;
import com.example.prateek.visionapitest.Model.Track;
import com.example.prateek.visionapitest.R;
import com.example.prateek.visionapitest.Rest.ApiClient2;
import com.example.prateek.visionapitest.Rest.ApiInterface;

import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class MusicTab extends Fragment {

    private final static String API_KEY = "39f3bbed1188f9e3ca4bfc7c63e51769";

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View rootView = inflater.inflate(R.layout.tab_music, container, false);

        final RecyclerView recyclerView = (RecyclerView) rootView.findViewById(R.id.music_recycler_view);
        recyclerView.setLayoutManager(new LinearLayoutManager(getContext()));

        ApiInterface apiService = ApiClient2.getClient().create(ApiInterface.class);

        Call<MusicResponse> call = apiService.getTracks("disco", API_KEY, "json");

        call.enqueue(new Callback<MusicResponse>() {
            @Override
            public void onResponse(Call<MusicResponse> call, Response<MusicResponse> response) {
                List<Track> tracks = response.body().tracks.getTrack();
                recyclerView.setAdapter(new MusicAdapter(tracks, R.layout.list_item_track, getContext()));
            }

            @Override
            public void onFailure(Call<MusicResponse> call, Throwable t) {

            }
        });

        return rootView;
    }
}
