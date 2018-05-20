package com.udacity.popularmovies;

import android.content.Context;
import android.content.Intent;
import android.media.Image;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.widget.ImageView;

import com.squareup.picasso.Picasso;
import com.udacity.popularmovies.model.Movie;

import butterknife.BindView;
import butterknife.ButterKnife;

/**
 * Created by suchitrasompalli on 5/17/2018.
 */

public class DetailActivity extends AppCompatActivity {

    private Movie movie;

    @BindView(R.id.poster)
    ImageView mPosterDisplay;

    @BindView(R.id.title)
    ImageView mTitle;

    @BindView(R.id.release_date)
    ImageView mReleaseDate;

    @BindView(R.id.user_rating)
    ImageView mUserRating;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_detail);
        ButterKnife.bind(this);

        Intent intentThatStartedThisActivity = getIntent();
        Movie mMovie = (Movie) getIntent().getParcelableExtra("moviedata");
        final String MOVIE_URL = "http://image.tmdb.org/t/p/w185";
        String poster_path = movie.getPoster_path();
        Context context = mPosterDisplay.getContext();
        Picasso.with(context).load(MOVIE_URL + poster_path).into(mPosterDisplay);
    }

}
