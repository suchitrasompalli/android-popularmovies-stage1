package com.udacity.popularmovies.utils;

/**
 * Created by suchitrasompalli on 5/11/2018.
 */

import android.content.Context;
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
    public static String[] getMoviePosterStringsFromJson(Context context, String movieJsonStr)
            throws JSONException {

        final String MOVIE_RESULTS = "results";
        final String POSTER_PATH = "poster_path";

        /* String array to hold movie poster String */
        String[] parsedMovieData = null;

        JSONObject movieJson = new JSONObject(movieJsonStr);
        JSONArray resultsArray = movieJson.getJSONArray(MOVIE_RESULTS);
        parsedMovieData = new String[resultsArray.length()];
        for (int i = 0; i < resultsArray.length(); i++) {
             /* Get the JSON object representing a movie */
            JSONObject movie = resultsArray.getJSONObject(i);
            parsedMovieData[i] = movie.getString(POSTER_PATH);
        }
        return parsedMovieData;
    }


}
