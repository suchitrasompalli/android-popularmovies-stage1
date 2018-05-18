package com.udacity.popularmovies;

import android.content.Context;
import android.content.Intent;
import android.media.Image;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.widget.ImageView;

import com.squareup.picasso.Picasso;

import butterknife.BindView;
import butterknife.ButterKnife;

/**
 * Created by suchitrasompalli on 5/17/2018.
 */

public class DetailActivity extends AppCompatActivity {

    private String poster_path;

    @BindView(R.id.poster)
    ImageView mPosterDisplay;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_detail);
        ButterKnife.bind(this);

        Intent intentThatStartedThisActivity = getIntent();

        // COMPLETED (2) Display the weather forecast that was passed from MainActivity
        if (intentThatStartedThisActivity != null) {
            if (intentThatStartedThisActivity.hasExtra(Intent.EXTRA_TEXT)) {
                poster_path = intentThatStartedThisActivity.getStringExtra(Intent.EXTRA_TEXT);
                final String MOVIE_URL = "http://image.tmdb.org/t/p/w185";

                Context context =  mPosterDisplay.getContext();
                Picasso.with(context).load(MOVIE_URL + poster_path).into(mPosterDisplay);
            }
        }
    }
}
