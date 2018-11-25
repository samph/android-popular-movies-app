package com.a1b19.popularmovies;

import android.util.Log;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;

public class Movies {

    private final ArrayList<Movie> mMovies;
    private final String mJSONString;
    private JSONArray mResultList;
    private TMDBConfig mConfig;

    public Movies(String jsonString, TMDBConfig config)
    {
        mJSONString = jsonString;
        mResultList = new JSONArray();

        try
        {
            JSONObject obj = new JSONObject(mJSONString);
            mResultList = obj.getJSONArray("results");
        }
        catch (JSONException e)
        {
            Log.e(this.getClass().getSimpleName(), "Json Exception!");
        }

        mMovies = new ArrayList<>();
        mConfig = config;
    }

    public String getPosterUrlAtIndex(int index)
    {
        if (index < mMovies.size())
        {
            return mMovies.get(index).posterURL();
        }
        return "";
    }

    public Movie addMovieAtIndex(int index)
    {
        Movie m = null;
        try
        {
            if (index < mResultList.length())
            {
                JSONObject item = mResultList.getJSONObject(index);
                m = new Movie(item, mConfig);
                mMovies.add(m);
            }

        }
        catch (JSONException e)
        {
            Log.e(this.getClass().getSimpleName(), "Json Exception!");
        }
        catch (NullPointerException npe)
        {
            Log.e(this.getClass().getSimpleName(), "NPE, prob empty json");
        }

        return m;
    }

    public int size()
    {
        return mMovies.size();
    }
}
