package com.example.prateek.visionapitest.Fragment;

import android.content.Intent;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Toast;

import com.example.prateek.visionapitest.Adapter.MovieAdapter;
import com.example.prateek.visionapitest.Listener.RecyclerTouchListener;
import com.example.prateek.visionapitest.Model.Movie;
import com.example.prateek.visionapitest.Model.MovieResponse;
import com.example.prateek.visionapitest.R;
import com.example.prateek.visionapitest.Rest.ApiClient;
import com.example.prateek.visionapitest.Rest.ApiInterface;
import com.example.prateek.visionapitest.UI.MovieInfo;

import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class MovieTab extends Fragment {

    private final static String API_KEY = "eda6b16ebbfe1bb908ee2cf30ac8a979";

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View rootView = inflater.inflate(R.layout.tab_movies, container, false);

        final RecyclerView recyclerView = (RecyclerView) rootView.findViewById(R.id.movies_recycler_view);
        recyclerView.setLayoutManager(new LinearLayoutManager(getContext()));

        ApiInterface apiService = ApiClient.getClient().create(ApiInterface.class);

        Call<MovieResponse> call = apiService.getMovieAccGenre(API_KEY, "28,12");

        call.enqueue(new Callback<MovieResponse>() {
            @Override
            public void onResponse(Call<MovieResponse> call, Response<MovieResponse> response) {
                //int statusCode = response.code();
                final List<Movie> movies = response.body().getResults();
                recyclerView.setAdapter(new MovieAdapter(movies, R.layout.list_item_movie, getContext()));

                recyclerView.addOnItemTouchListener(new RecyclerTouchListener(getContext(), recyclerView, new RecyclerTouchListener.ClickListener() {
                    @Override
                    public void onClick(View view, int position) {
                        Movie movie = movies.get(position);
                        //Toast.makeText(getApplicationContext(), movie.getTitle() + " is selected!", Toast.LENGTH_SHORT).show();

                        Intent infoIntent = new Intent(getContext(), MovieInfo.class);
                        Bundle bundle = new Bundle();
                        bundle.putSerializable("movieInfo", movie);
                        infoIntent.putExtras(bundle);
                        startActivity(infoIntent);
                    }

                    @Override
                    public void onLongClick(View view, int position) {

                    }
                }));
            }

            @Override
            public void onFailure(Call<MovieResponse> call, Throwable t) {

            }
        });

        return rootView;
    }
}
