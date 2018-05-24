package com.udacity.popularmovies;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.squareup.picasso.Picasso;
import com.udacity.popularmovies.model.Movie;

import butterknife.BindView;
import butterknife.ButterKnife;

/**
 * Created by suchitrasompalli on 5/17/2018.
 */

public class DetailActivity extends AppCompatActivity {

    @BindView(R.id.poster)
    ImageView mPosterDisplay;

    @BindView(R.id.title)
    TextView mTitle;

    @BindView(R.id.release_date)
    TextView mReleaseDate;

    @BindView(R.id.user_rating)
    TextView mUserRating;

    @BindView(R.id.overview)
    TextView mOverview;

    /**
     * @param savedInstanceState
     */
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_detail);
        ButterKnife.bind(this);

        Intent intentThatStartedThisActivity = getIntent();
        Movie mMovie = (Movie) intentThatStartedThisActivity.getParcelableExtra("Movie");
        final String MOVIE_URL = "http://image.tmdb.org/t/p/w185";
        String poster_path = mMovie.getPoster_path();
        Context context = mPosterDisplay.getContext();
        Picasso.with(context).load(MOVIE_URL + poster_path).into(mPosterDisplay);
        mTitle.setText(mMovie.getTitle());
        mReleaseDate.setText(mMovie.getRelease_date());
        mUserRating.setText(mMovie.getUser_rating());
        mOverview.setText(mMovie.getOverview());
    }
    /**
     * @param menu
     * @return boolean
     */
    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_main, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        Class destinationClass = MainActivity.class;
        Intent intent = new Intent(this, destinationClass);
        switch(item.getItemId()) {
            case R.id.most_popular:
                intent.putExtra("type", MainActivity.POPULAR);
                this.finish();
                startActivity(intent);
                return(true);
            case R.id.top_rated:
                intent.putExtra("type", MainActivity.TOP_RATED);
                this.finish();
                startActivity(intent);
                return(true);

        }
        return(super.onOptionsItemSelected(item));
    }

}
