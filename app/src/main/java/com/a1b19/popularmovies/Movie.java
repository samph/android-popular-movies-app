package com.a1b19.popularmovies;

import android.net.Uri;
import android.util.Log;

import org.json.JSONException;
import org.json.JSONObject;

import java.io.Serializable;

public class Movie implements Serializable {
    private String mTitle;
    private String mPosterUrl;

    public Double getRating() {
        return mRating;
    }

    public String getOverview() {
        return mOverview;
    }

    public String getReleaseDate() {
        return mReleaseDate;
    }

    private Double mRating;
    private String mOverview;
    private String mReleaseDate;

    public Movie(JSONObject movieJSON, TMDBConfig config)
    {
        mTitle = "";
        try
        {
            mTitle = movieJSON.getString("title");
            String filename = movieJSON.getString("poster_path");
            mRating = movieJSON.getDouble("vote_average");
            mOverview = movieJSON.getString("overview");
            mReleaseDate = movieJSON.getString("release_date");

            Uri posterUri = Uri.parse(config.getBaseURL())
                .buildUpon()
                .appendPath(config.getSmallestPosterSize())
                .appendEncodedPath(filename)
                .build();
            mPosterUrl = posterUri.toString();
            Log.v(this.getClass().getSimpleName(), posterUri.toString());
        }
        catch (JSONException e)
        {
            Log.e(this.getClass().getSimpleName(), "Movie JSON Except");
        }

        // TODO JSON parsing.
    }

    @Override
    public String toString() {
        return mTitle;
    }

    public String title()
    {
        return mTitle;
    }

    public String posterURL()
    {
        return mPosterUrl;
    }


}
