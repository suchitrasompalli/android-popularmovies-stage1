package com.udacity.popularmovies;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.AsyncTask;
import android.os.Bundle;
import android.preference.PreferenceManager;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.ProgressBar;
import android.widget.TextView;
import android.widget.Toast;

import com.udacity.popularmovies.model.Movie;
import com.udacity.popularmovies.utils.MovieJsonUtils;
import com.udacity.popularmovies.utils.NetworkUtils;

import java.net.URL;
import butterknife.BindView;
import butterknife.ButterKnife;

/**
 * Main class to display a set of movie posters with default choice of most popular movies.
 */
public class MainActivity extends AppCompatActivity implements MovieAdapter.MovieAdapterOnClickHandler {

    // the 2 choice are Popular and Top rated movies.
    public static final String POPULAR = "popular";
    public static final String TOP_RATED = "top_rated";

    private MovieAdapter mAdapter;
    private GridLayoutManager mLayoutManager;
    private String movieType;
    private Movie[] movies;

    @BindView(R.id.recyclerView)
    RecyclerView mRecyclerView;

    @BindView(R.id.error_message_display)
    TextView mErrorMessageDisplay;

    @BindView(R.id.pb_loading_indicator)
    ProgressBar mLoadingIndicator;

    private SharedPreferences sharedPref = null;
    /**
     * @param savedInstanceState
     */
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
        sharedPref = PreferenceManager.getDefaultSharedPreferences(this);
        movieType = sharedPref.getString(getString(R.string.movie_type), POPULAR);
        
        Log.d("on create","movies");
        if (movies == null) {
            loadMovieData();
        }
    }

    @Override
    protected void onSaveInstanceState(Bundle outState) {
        super.onSaveInstanceState(outState);
        outState.putParcelableArray("movieData", movies);
    }

    @Override
    protected void onRestoreInstanceState(Bundle savedInstanceState) {
        super.onRestoreInstanceState(savedInstanceState);
        movies = (Movie[]) savedInstanceState.getParcelableArray("movieData");
    }

    @Override
    protected void onNewIntent(Intent intent) {
        String type = intent.getStringExtra("type");
        if (type != null) {
            movieType = type;
        }
        loadMovieData();
    }

    /**
     * @return movieType
     */
    public String getMovieType() {
        return this.movieType;
    }

    /**
     * @param movieType
     */
    public void setMovieType(String movieType) {
        this.movieType = movieType;
    }

    /**
     * When a poster image is clicked a intent is used to start the detail activity.
     * @param movie
     */
    @Override
    public void onClick(Movie movie) {
        Context context = this;
        Class destinationClass = DetailActivity.class;
        Intent intentToStartDetailActivity = new Intent(context, destinationClass);
        intentToStartDetailActivity.putExtra("Movie", movie);
        startActivity(intentToStartDetailActivity);
    }

    /**
     * This method will start the Async task.
     *
     */
    private void loadMovieData() {
        showMovieDataView();
        new FetchMovieDataTask().execute(movieType);

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
        switch(item.getItemId()) {
            case R.id.most_popular:
                Toast.makeText(this, R.string.most_popular, Toast.LENGTH_LONG).show();
                if (movieType.equals(POPULAR) != true) {
                    SharedPreferences.Editor editor = sharedPref.edit();
                    editor.putString(getString(R.string.movie_type), POPULAR);
                    editor.commit();
                    movieType = POPULAR;
                    loadMovieData();
                }
                return(true);
            case R.id.top_rated:
                Toast.makeText(this, R.string.top_rated, Toast.LENGTH_LONG).show();
                if (movieType.equals(TOP_RATED) != true) {
                    SharedPreferences.Editor editor = sharedPref.edit();
                    editor.putString(getString(R.string.movie_type), TOP_RATED);
                    editor.commit();
                    movieType = TOP_RATED;
                    loadMovieData();
                }
                movieType = TOP_RATED;
                loadMovieData();
                return(true);

        }
        return(super.onOptionsItemSelected(item));
    }

    /**
     * AsynTask that gets movie data using Network Utils.
     */
    public class FetchMovieDataTask extends AsyncTask<String, Void, Movie[]> {

        @Override
        protected void onPreExecute() {
            mLoadingIndicator.setVisibility(View.VISIBLE);
            super.onPreExecute();
        }

        /**
         * @param params will have the criteria for movies display
         * @return Movie[]
         */
        @Override
        protected Movie[] doInBackground(String... params) {

            /* If there's no sort_by, there's nothing to look up. */
            if (params.length == 0) {
                return null;
            }

            String sort_by_type = params[0];
            URL movieRequestUrl = NetworkUtils.buildUrl(sort_by_type);

            try {
                String jsonResponse = NetworkUtils
                        .getResponseFromHttpUrl(movieRequestUrl);

                Movie[] movies = MovieJsonUtils
                            .getMoviesFromJson(MainActivity.this, jsonResponse);

                return movies;

            } catch (Exception e) {
                e.printStackTrace();
                return null;
            }
        }

        /**
         * Hides loading indicator and sends movieData to MovieAdapter
         * @param movieData
         */
        @Override
        protected void onPostExecute(Movie[] movieData) {
            mLoadingIndicator.setVisibility(View.INVISIBLE);
            if (movieData != null) {
                movies = movieData;
                mAdapter.setMovieData(movieData);
            } else {
                showErrorMessage();
            }
        }
    }

}
