package com.a1b19.popularmovies;

import android.util.Log;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;

public class TMDBConfig {

    private String mBaseURL;
    private ArrayList<String> mPosterSizes = new ArrayList<>();

    public TMDBConfig(String jsonString)
    {
        try
        {
            JSONObject obj = new JSONObject(jsonString);
            JSONObject images = obj.getJSONObject("images");
            mBaseURL = images.getString("secure_base_url");
            JSONArray posterSizes = images.getJSONArray("poster_sizes");

            for (int i =0; i < posterSizes.length(); i++)
            {
                mPosterSizes.add(posterSizes.getString(i));
            }
        }
        catch (JSONException e)
        {
            Log.e(this.getClass().getSimpleName(), "Json Config Exception!");
        }
        catch (NullPointerException npe)
        {
            Log.e(this.getClass().getSimpleName(), "NPE, prob empty json");
        }

    }

    public String getBaseURL()
    {
        return mBaseURL;
    }

    public String getSmallestPosterSize()
    {
        return mPosterSizes.get(1);
    }
}
