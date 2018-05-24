package com.udacity.popularmovies.utils;

/*
  Created by suchitrasompalli on 5/20/2018.
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

    private static final String JSON_MOVIE_RESULTS_KEY = "results";
    private static final String JSON_ID_KEY = "id";
    private static final String JSON_TITLE_KEY = "results";
    private static final String JSON_RELEASE_DATE_KEY = "release_date";
    private static final String JSON_POSTER_PATH_KEY = "poster_path";
    private static final String JSON_OVERVIEW_KEY = "overview";
    private static final String JSON_VOTE_KEY = "vote_average";

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

        /* String array to hold movie poster String */
        Movie[] parsedMovieData = null;

        JSONObject movieJson = new JSONObject(movieJsonStr);
        JSONArray resultsArray = movieJson.optJSONArray(JSON_MOVIE_RESULTS_KEY);

        parsedMovieData = new Movie[resultsArray.length()];

        for (int i = 0; i < resultsArray.length(); i++) {
             /* Get the JSON object representing a movie */
            JSONObject movie_json = resultsArray.optJSONObject(i);
            int id = Integer.parseInt(movie_json.optString(JSON_ID_KEY));
            String title = movie_json.optString(JSON_TITLE_KEY);
            String release_date = movie_json.optString(JSON_RELEASE_DATE_KEY);
            String poster_path = movie_json.optString(JSON_POSTER_PATH_KEY);
            String overview = movie_json.optString(JSON_OVERVIEW_KEY);
            String vote_average = movie_json.optString(JSON_VOTE_KEY);

            parsedMovieData[i] = new Movie(id, title, poster_path, vote_average, release_date,
                    overview);
        }
        return parsedMovieData;
    }

}
