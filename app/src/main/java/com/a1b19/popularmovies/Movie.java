package com.a1b19.popularmovies;

import android.net.Uri;
import android.util.Log;

import org.json.JSONException;
import org.json.JSONObject;

public class Movie {
    private String mTitle;
    private Uri mPosterUri;

    public Movie(JSONObject movieJSON, TMDBConfig config)
    {
        mTitle = "";
        try
        {
            mTitle = movieJSON.getString("title");
            String filename = movieJSON.getString("poster_path");
            mPosterUri = Uri.parse(config.getBaseURL())
                .buildUpon()
                .appendPath(config.getSmallestPosterSize())
                .appendEncodedPath(filename)
                .build();
            Log.v(this.getClass().getSimpleName(), mPosterUri.toString());
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
        return mPosterUri.toString();
    }
}
