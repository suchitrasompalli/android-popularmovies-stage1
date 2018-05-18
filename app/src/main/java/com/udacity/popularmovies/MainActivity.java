package com.udacity.popularmovies;

import android.content.Context;
import android.content.Intent;
import android.os.AsyncTask;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.ProgressBar;
import android.widget.TextView;
import android.widget.Toast;

import com.udacity.popularmovies.model.MovieAdapter;
import com.udacity.popularmovies.utils.MovieJsonUtils;
import com.udacity.popularmovies.utils.NetworkUtils;

import java.net.URL;
import butterknife.BindView;
import butterknife.ButterKnife;

public class MainActivity extends AppCompatActivity implements MovieAdapter.MovieAdapterOnClickHandler {

    private static final String POPULAR = "popular";
    private static final String TOP_RATED = "top_rated";

    private MovieAdapter mAdapter;
    private GridLayoutManager mLayoutManager;

    @BindView(R.id.recyclerView)
    RecyclerView mRecyclerView;

    @BindView(R.id.error_message_display)
    TextView mErrorMessageDisplay;

    @BindView(R.id.pb_loading_indicator)
    ProgressBar mLoadingIndicator;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        ButterKnife.bind(this);

        // use this setting to improve performance if you know that changes
        // in content do not change the layout size of the RecyclerView
        mRecyclerView.setHasFixedSize(true);

        // use a grid layout manager
        mLayoutManager = new GridLayoutManager(this, 2);
        mRecyclerView.setLayoutManager(mLayoutManager);

        // specify an adapter
        mAdapter = new MovieAdapter(this);
        mRecyclerView.setAdapter(mAdapter);
        mRecyclerView.setHasFixedSize(true);
        loadMovieData(POPULAR);
    }

    @Override
    public void onClick(String poster) {
        Context context = this;
        Toast.makeText(context, "onclick", Toast.LENGTH_SHORT)
                .show();
        Class destinationClass = DetailActivity.class;
        Intent intentToStartDetailActivity = new Intent(context, destinationClass);
        startActivity(intentToStartDetailActivity);
    }

    /**
     * This method will start the Async task.
     */
    private void loadMovieData(String type) {
        showMovieDataView();
        new FetchMoviePosterTask().execute(type);
    }

    /**
     * This method will make the View for the movie data visible and
     * hide the error message.
     * <p>
     * Since it is okay to redundantly set the visibility of a View, we don't
     * need to check whether each view is currently visible or invisible.
     */
    private void showMovieDataView() {
        /* First, make sure the error is invisible */
        mErrorMessageDisplay.setVisibility(View.INVISIBLE);
        /* Then, make sure the movie data is visible */
        mRecyclerView.setVisibility(View.VISIBLE);
    }

    /**
     * This method will make the error message visible and hide the weather
     * View.
     */
    private void showErrorMessage() {
        mRecyclerView.setVisibility(View.INVISIBLE);
        mErrorMessageDisplay.setVisibility(View.VISIBLE);
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_main, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch(item.getItemId()) {
            case R.id.most_popular:
                Toast.makeText(this, R.string.most_popular, Toast.LENGTH_LONG).show();
                loadMovieData(POPULAR);
                return(true);
            case R.id.top_rated:
                Toast.makeText(this, R.string.top_rated, Toast.LENGTH_LONG).show();
                loadMovieData(TOP_RATED);
                return(true);

        }
        return(super.onOptionsItemSelected(item));
    }

    public class FetchMoviePosterTask extends AsyncTask<String, Void, String[]> {

        @Override
        protected void onPreExecute() {
            mLoadingIndicator.setVisibility(View.VISIBLE);
            super.onPreExecute();
        }

        @Override
        protected String[] doInBackground(String... params) {

            /* If there's no sort_by, there's nothing to look up. */
            if (params.length == 0) {
                return null;
            }

            String sort_by = params[0];
            URL movieRequestUrl = NetworkUtils.buildUrl(sort_by);

            try {
                String jsonResponse = NetworkUtils
                        .getResponseFromHttpUrl(movieRequestUrl);

                String[] jsonMoviePosterData = MovieJsonUtils
                        .getMoviePosterStringsFromJson(MainActivity.this, jsonResponse);

                return jsonMoviePosterData;

            } catch (Exception e) {
                e.printStackTrace();
                return null;
            }
        }

        @Override
        protected void onPostExecute(String[] movieData) {
            mLoadingIndicator.setVisibility(View.INVISIBLE);
            if (movieData != null) {
                mAdapter.setMovieData(movieData);
            } else {
                showErrorMessage();
            }
        }
    }

}
