package com.udacity.popularmovies.utils;

/**
 * Created by suchitrasompalli on 5/11/2018.
 */

import android.content.Context;

import com.udacity.popularmovies.model.Movie;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.net.HttpURLConnection;


/**
 * Utility functions to handle Movie JSON data.
 */
public final class MovieJsonUtils {

    /**
     * This method parses JSON from a web response and returns an array of movie poster url
     * representing movie poster.
     * <p/>
     * @param movieJsonStr JSON response from server
     *
     * @return Array of Strings with the link to poster jpeg
     *
     * @throws JSONException If JSON data cannot be properly parsed
     */
    public static Movie[] getMoviesFromJson(Context context, String movieJsonStr)
            throws JSONException {

        final String MOVIE_RESULTS = "results";

        /* String array to hold movie poster String */
        Movie[] parsedMovieData = null;

        JSONObject movieJson = new JSONObject(movieJsonStr);
        JSONArray resultsArray = movieJson.getJSONArray(MOVIE_RESULTS);

        parsedMovieData = new Movie[resultsArray.length()];

        for (int i = 0; i < resultsArray.length(); i++) {
             /* Get the JSON object representing a movie */
            JSONObject movie_json = resultsArray.getJSONObject(i);
            int id = Integer.parseInt(movie_json.getString("id"));
            String title = movie_json.getString("title");
            String release_date = movie_json.getString("release_date");
            String poster_path = movie_json.getString("poster_path");
            String overview = movie_json.getString("overview");
            String vote_average = movie_json.getString("vote_average");

            parsedMovieData[i] = new Movie(id, title, poster_path, vote_average, release_date,
                    overview);
        }
        return parsedMovieData;
    }

}
