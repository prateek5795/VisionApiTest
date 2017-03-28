package com.example.prateek.visionapitest.UI;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.widget.TextView;

import com.example.prateek.visionapitest.Model.Movie;
import com.example.prateek.visionapitest.R;

public class MovieInfo extends AppCompatActivity {

    TextView tvId, tvTitle;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_movie_info);

        tvId = (TextView) findViewById(R.id.tvId);
        tvTitle = (TextView) findViewById(R.id.tvTitle);

        Intent intent = this.getIntent();
        Bundle bundle = intent.getExtras();

        Movie movie = (Movie) bundle.getSerializable("movieInfo");

        tvId.setText(movie.getId().toString());
        tvTitle.setText(movie.getTitle().toString());
    }
}
