package com.udacity.popularmovies.model;

import android.content.Context;
import android.graphics.Bitmap;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;

import com.squareup.picasso.Picasso;
import com.udacity.popularmovies.R;

import java.util.ArrayList;

/**
 * {@link MoviePosterAdapter} provides movie poster images to Recycler view.
 * {@link android.support.v7.widget.RecyclerView}
 */
public class MoviePosterAdapter extends RecyclerView.Adapter<MoviePosterAdapter.MoviePosterAdapterViewHolder> {

    private String[] mMovieData;
    ArrayList mValues;
    Context mContext;
    protected ItemListener mListener;

    public MoviePosterAdapter(Context context, ArrayList values, ItemListener itemListener) {

        mValues = values;
        mContext = context;
        mListener=itemListener;
    }


    public class MoviePosterAdapterViewHolder extends RecyclerView.ViewHolder {

        public final ImageView mImageView;

        public MoviePosterAdapterViewHolder(View view) {
            super(view);
            mImageView = (ImageView) view.findViewById(R.id.movie_data);
        }
    }

    /**
     * This gets called when each new ViewHolder is created. This happens when the RecyclerView
     * is laid out. Enough ViewHolders will be created to fill the screen and allow for scrolling.
     *
     * @param viewGroup The ViewGroup that these ViewHolders are contained within.
     * @param viewType  If your RecyclerView has more than one type of item (which ours doesn't) you
     *                  can use this viewType integer to provide a different layout. See
     *                  {@link android.support.v7.widget.RecyclerView.Adapter#getItemViewType(int)}
     *                  for more details.
     * @return A new MoviePosterAdapterViewHolder that holds the View for each list item
     */
    @Override
    public MoviePosterAdapterViewHolder onCreateViewHolder(ViewGroup viewGroup, int viewType) {
        Context context = viewGroup.getContext();
        int layoutIdForListItem = R.layout.view_item;
        LayoutInflater inflater = LayoutInflater.from(context);
        boolean shouldAttachToParentImmediately = false;

        View view = inflater.inflate(layoutIdForListItem, viewGroup, shouldAttachToParentImmediately);
        return new MoviePosterAdapterViewHolder(view);
    }

    /**
     * OnBindViewHolder is called by the RecyclerView to display the data at the specified
     * position. In this method, we update the contents of the ViewHolder to display the movie
     * details for this particular position, using the "position" argument that is conveniently
     * passed into us.
     *
     * @param mMoviePosterAdapterViewHolder The ViewHolder which should be updated to represent the
     *                                  contents of the item at the given position in the data set.
     * @param position                  The position of the item within the adapter's data set.
     */
    @Override
    public void onBindViewHolder(MoviePosterAdapterViewHolder mMoviePosterAdapterViewHolder, int position) {
        final String MOVIE_URL = "http://image.tmdb.org/t/p/w185";
        String poster_path = mMovieData[position];
        Context context =  mMoviePosterAdapterViewHolder.mImageView.getContext();
        Picasso.with(context).load(MOVIE_URL + poster_path).into(mMoviePosterAdapterViewHolder.mImageView);
    }

    /**
     * This method simply returns the number of items to display. It is used behind the scenes
     * to help layout our Views and for animations.
     *
     */
    @Override
    public int getItemCount() {
        if (null == mMovieData) return 0;
        return mMovieData.length;
    }

    /**
     * This method is used to set the movie MoviePoster on a MoviePosterAdapter if we've already
     * created one. This is handy when we get new data from the web but don't want to create a
     * new MoviePosterAdapter to display it.
     *
     * @param movieData The new movie data to be displayed.
     */
    public void setMovieData(String[] movieData) {
        mMovieData = movieData;
        notifyDataSetChanged();
    }
}