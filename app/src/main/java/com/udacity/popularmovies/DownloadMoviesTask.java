package com.udacity.popularmovies;

import android.os.AsyncTask;

import com.squareup.picasso.Downloader;

import java.net.URL;

/**
 * Created by suchitrasompalli on 5/8/2018.
 */

public class DownloadMoviesTask extends AsyncTask<URL, Integer, Long> {

    protected Long doInBackground(URL... urls) {
        int count = urls.length;
        long totalSize = 0;
        for (int i = 0; i < count; i++) {
            //totalSize += Downloader.Response.load(urls[i]);
            publishProgress((int) ((i / (float) count) * 100));
            // Escape early if cancel() is called
            if (isCancelled()) break;
        }
        return totalSize;
    }

    protected void onProgressUpdate(Integer... progress) {
        //setProgressPercent(progress[0]);
    }

    protected void onPostExecute(Long result) {
        //showDialog("Downloaded " + result + " bytes");
    }
}
