package com.udacity.popularmovies.model;

import android.os.Parcel;
import android.os.Parcelable;

/**
 * Created by suchitrasompalli on 5/18/2018.
 */

public class Movie implements Parcelable {

    // Member variables
    private String title;
    private String overview;
    private String poster_path;
    private String release_date;
    private String user_rating;
    private int id;

    /**
     * @param id
     * @param title
     * @param poster_path
     * @param user_rating
     * @param release_date
     * @param overview
     */
    public Movie(int id, String title, String poster_path, String user_rating, String release_date,
                 String overview)
    {
        this.title = title;
        this.release_date = release_date;
        this.user_rating = user_rating;
        this.overview = overview;
        this.poster_path = poster_path;
        this.id = id;
    }

    /**
     * @param in
     */
    public Movie(Parcel in) {
        title = in.readString();
        overview = in.readString();
        release_date = in.readString();
        id = in.readInt();
        user_rating = in.readString();
        poster_path = in.readString();
    }

    /**
     * @return
     */
    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getOverview() {
        return overview;
    }

    public void setOverview(String overview) {
        this.overview = overview;
    }

    public String getPoster_path() {
        return poster_path;
    }

    public void setPoster_path(String poster_path) {
        this.poster_path = poster_path;
    }

    public String getRelease_date() {
        return release_date;
    }

    public void setRelease_date(String release_date) {
        this.release_date = release_date;
    }

    public String getUser_rating() {
        return user_rating;
    }

    public void setUser_rating(String user_rating) {
        this.user_rating = user_rating;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    /**
     * @param dest
     * @param flags
     */
    @Override
    public void writeToParcel(Parcel dest, int flags) {
        dest.writeString(title);
        dest.writeString(overview);
        dest.writeString(release_date);
        dest.writeInt(id);
        dest.writeString(user_rating);
        dest.writeString(poster_path);
    }

    /**
     * @return
     */
    @Override
    public int describeContents() {
        return 0;
    }

    // After implementing the `Parcelable` interface, we need to create the
    // `Parcelable.Creator<MyParcelable> CREATOR` constant for our class;
    // Notice how it has our class specified as its type.
    public static final Parcelable.Creator<Movie> CREATOR
            = new Parcelable.Creator<Movie>() {

        // This simply calls our new constructor (typically private) and
        // passes along the unmarshalled `Parcel`, and then returns the new object!
        @Override
        public Movie createFromParcel(Parcel in) {
            return new Movie(in);
        }

        // We just need to copy this and change the type to match our class.
        @Override
        public Movie[] newArray(int size) {
            return new Movie[size];
        }
    };

}
