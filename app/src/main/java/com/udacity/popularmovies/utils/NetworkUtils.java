package com.udacity.popularmovies.utils;

/**
 * Created by suchitrasompalli on 5/11/2018.
 */

/*
 * Copyright (C) 2016 The Android Open Source Project
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 *      http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */

import android.net.Uri;
import android.util.Log;

import com.udacity.popularmovies.BuildConfig;

import java.io.IOException;
import java.io.InputStream;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.URL;
import java.util.Scanner;


/**
 * These utilities will be used to communicate with the movie db server.
 */
public final class NetworkUtils {

    private static final String TAG = NetworkUtils.class.getSimpleName();

    private static final String BASE_MOVIE_URL =
            "https://api.themoviedb.org/3/discover/movie";

    private static final String API_KEY = BuildConfig.ApiKey;


    private static final String language = "en-US";

    private static final String numPage = "1";

    final static String API_KEY_PARAM = "api_key";
    final static String LANG_PARAM = "language";
    final static String SORT_PARAM = "sort_by";
    final static String INCLUDE_ADULT_PARAM = "include_adult";
    final static String INCLUDE_VIDEO_PARAM = "include_video";
    final static String PAGE_PARAM = "page";

    /**
     * Builds the URL used to talk to the weather server using a location. This location is based
     * on the query capabilities of the weather provider that we are using.
     *
     * @param sort_by The location that will be queried for.
     * @return The URL to use to query the weather server.
     */
    public static URL buildUrl(String sort_by) {
        Uri builtUri = Uri.parse(BASE_MOVIE_URL).buildUpon()
                .appendQueryParameter(API_KEY_PARAM, API_KEY)
                .appendQueryParameter(LANG_PARAM, language)
                .appendQueryParameter(SORT_PARAM, sort_by)
                .appendQueryParameter(INCLUDE_ADULT_PARAM, Boolean.FALSE.toString())
                .appendQueryParameter(INCLUDE_VIDEO_PARAM, Boolean.FALSE.toString())
                .appendQueryParameter(PAGE_PARAM, numPage)
                .build();

        URL url = null;
        try {
            url = new URL(builtUri.toString());
        } catch (MalformedURLException e) {
            e.printStackTrace();
        }

        Log.v(TAG, "Built URI " + url);

        return url;
    }


    /**
     * This method returns the entire result from the HTTP response.
     *
     * @param url The URL to fetch the HTTP response from.
     * @return The contents of the HTTP response.
     * @throws IOException Related to network and stream reading
     */
    public static String getResponseFromHttpUrl(URL url) throws IOException {
        HttpURLConnection urlConnection = (HttpURLConnection) url.openConnection();
        try {
            InputStream in = urlConnection.getInputStream();

            Scanner scanner = new Scanner(in);
            scanner.useDelimiter("\\A");

            boolean hasInput = scanner.hasNext();
            if (hasInput) {
                return scanner.next();
            } else {
                return null;
            }
        } finally {
            urlConnection.disconnect();
        }
    }
}