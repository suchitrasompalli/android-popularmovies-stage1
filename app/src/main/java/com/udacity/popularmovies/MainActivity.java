package com.udacity.popularmovies;

import android.graphics.Bitmap;
import android.os.AsyncTask;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.Toast;

import com.udacity.popularmovies.model.MoviePosterAdapter;
import com.udacity.popularmovies.utils.MovieJsonUtils;
import com.udacity.popularmovies.utils.NetworkUtils;

import java.net.URL;

public class MainActivity extends AppCompatActivity {

    private RecyclerView mRecyclerView;
    private MoviePosterAdapter mAdapter;
    private GridLayoutManager mLayoutManager;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        mRecyclerView = (RecyclerView) findViewById(R.id.my_recycler_view);

        // use this setting to improve performance if you know that changes
        // in content do not change the layout size of the RecyclerView
        mRecyclerView.setHasFixedSize(true);

        // use a linear layout manager
        mLayoutManager = new GridLayoutManager(this, 2);
        mRecyclerView.setLayoutManager(mLayoutManager);

        // specify an adapter (see also next example)
        String[] myDataset = {"test1", "test2", "test3", "test4"};
        mAdapter = new MoviePosterAdapter();
        mRecyclerView.setAdapter(mAdapter);
        mRecyclerView.setHasFixedSize(true);
        loadMovieData();

    }

    /**
     * This method will start the Async task.
     */
    private void loadMovieData() {
        String default_sort_by = "popularity.desc";
        new FetchMoviePosterTask().execute(default_sort_by);
    }
    /**
     * This method will make the error message visible
     */
    private void showErrorMessage() {
        Toast.makeText(this, R.string.error_message, Toast.LENGTH_SHORT).show();
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
                return(true);
            case R.id.top_rated:
                Toast.makeText(this, R.string.top_rated, Toast.LENGTH_LONG).show();
                return(true);

        }
        return(super.onOptionsItemSelected(item));
    }

    public class FetchMoviePosterTask extends AsyncTask<String, Void, Bitmap[]> {

        @Override
        protected void onPreExecute() {
            super.onPreExecute();
        }

        @Override
        protected Bitmap[] doInBackground(String... params) {

            /* If there's no zip code, there's nothing to look up. */
            if (params.length == 0) {
                return null;
            }

            String location = params[0];
            URL movieRequestUrl = NetworkUtils.buildUrl(location);

            try {
                String jsonMovieDataResponse = NetworkUtils
                        .getResponseFromHttpUrl(movieRequestUrl);

                Bitmap[] simpleJsonMovieData = MovieJsonUtils
                        .getBitmapFromJson(MainActivity.this, jsonMovieDataResponse);

                return simpleJsonMovieData;

            } catch (Exception e) {
                e.printStackTrace();
                return null;
            }
        }

        @Override
        protected void onPostExecute(Bitmap[] movieData) {
            if (movieData != null) {
                mAdapter.setMovieData(movieData);
            } else {
                showErrorMessage();
            }
        }
    }

}
