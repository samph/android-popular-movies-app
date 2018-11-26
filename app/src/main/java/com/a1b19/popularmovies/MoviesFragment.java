package com.a1b19.popularmovies;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.net.Uri;
import android.os.AsyncTask;
import android.preference.PreferenceManager;
import android.support.v4.app.Fragment;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.BaseAdapter;
import android.widget.GridView;
import android.widget.ImageView;
import android.widget.Toast;

import com.bumptech.glide.Glide;
import com.bumptech.glide.request.RequestOptions;

import org.json.JSONException;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.lang.annotation.Target;
import java.net.HttpURLConnection;
import java.net.URL;
import java.util.ArrayList;

/**
 * A placeholder fragment containing a simple view.
 */
public class MoviesFragment extends Fragment {

    private ArrayAdapter<String> mMovieAdaptor;
    private String API_BASE = "https://api.themoviedb.org/3/";
    private String API_MOVIE_ENDPOINT = "movie";
    private String API_KEY_KEY = "api_key";
    private String API_REGION = "region";
    private String REGION_GB = "GB";
    private String API_CONFIG_ENDPOINT = "configuration";

    private String mSortEndpoint = "top_rated";
    private TMDBConfig mConfig;
    private Movies mMovies;

    public class ImageAdapter extends BaseAdapter {
        private Context mContext;
        private LayoutInflater inflater;

        public ImageAdapter(Context c) {
            mContext = c;
            inflater = LayoutInflater.from(c);

        }

        public int getCount() {
            return mMovies.size();
        }

        public Object getItem(int position) {
            return null;
        }

        public long getItemId(int position) {
            return 0;
        }

        // create a new ImageView for each item referenced by the Adapter
        @Override
        public View getView(int position, View recycled, ViewGroup container) {
            ImageView myImageView;
            if (recycled == null) {
                myImageView = new ImageView(mContext);
            } else {
                myImageView = (ImageView) recycled;
            }

            String url = mMovies.getPosterUrlAtIndex(position);

            Glide
                    .with(MoviesFragment.this)
                    .load(url)
                    .into(myImageView);

            return myImageView;
        }

    }


    public MoviesFragment() {
    }

    @Override
    public void onStart() {
        super.onStart();
        new GetConfigTask().execute();
        refreshMovies();

    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        final View rootView = inflater.inflate(R.layout.fragment_main, container, false);


        return rootView;
    }

    public void refreshMovies()
    {
        GetMoviesTask t = new GetMoviesTask();
        SharedPreferences prefs = PreferenceManager.getDefaultSharedPreferences(getActivity());
        mSortEndpoint = prefs.getString(getString(R.string.sort_key), getString(R.string.sort_default));

        t.execute(mSortEndpoint);
    }

    private String getUri(Uri uri)
    {
        HttpURLConnection urlConnection = null;
        BufferedReader reader = null;

        String urlString = uri.toString();
        Log.v(this.getClass().getSimpleName(), urlString);
        String responseString = "";
        try {
            URL url = new URL(urlString);
            urlConnection = (HttpURLConnection) url.openConnection();
            urlConnection.setRequestMethod("GET");
            urlConnection.connect();
            int responseCode = urlConnection.getResponseCode();
            Log.v(this.getClass().getSimpleName(), responseCode+"");
            if (responseCode != 200)
            {
                return null;
            }
            InputStream inputStream = urlConnection.getInputStream();
            StringBuffer buffer = new StringBuffer();
            if (inputStream == null) {
                return null;
            }

            reader = new BufferedReader(new InputStreamReader(inputStream));

            String line;
            while ((line = reader.readLine()) != null) {
                buffer.append(line + "\n");
            }

            if (buffer.length() == 0) {
                return null;
            }

            responseString = buffer.toString();
        } catch (IOException ioe) {
            Log.e(this.getClass().getSimpleName(), "Error", ioe);
            return null;
        } finally {
            if (urlConnection != null) {
                urlConnection.disconnect();
            }
            if (reader != null) {
                try {
                    reader.close();
                } catch (final IOException e) {
                    Log.e(this.getClass().getSimpleName(), "Error closing stream", e);
                }
            }
        }
        Log.v(this.getClass().getSimpleName(), responseString);
        return responseString;
    }

    class GetConfigTask extends AsyncTask<Void, Void, Void >
    {

        @Override
        protected Void doInBackground(Void... endpoints) {
            String api_key = getString(R.string.api_key);
            Uri uri = Uri.parse(API_BASE)
                    .buildUpon()
                    .appendPath(API_CONFIG_ENDPOINT)
                    .appendQueryParameter(API_KEY_KEY, api_key)
                    .build();

            String configResponseString = getUri(uri);
            mConfig = new TMDBConfig(configResponseString);

            return null;
        }
    }

    class GetMoviesTask extends AsyncTask<String, Void, ArrayList<String> >
    {
        @Override
        protected void onPostExecute(ArrayList<String> result) {

            GridView gridview = getActivity().findViewById(R.id.gridview_movies);

            ImageAdapter movieAdaptor = new ImageAdapter(getActivity());
            gridview.setAdapter(movieAdaptor);
            gridview.setOnItemClickListener(new AdapterView.OnItemClickListener() {
                public void onItemClick(AdapterView parent, View v, int position, long id) {
                    Toast.makeText(getActivity(),"" + position , Toast.LENGTH_SHORT).show();
                    Intent detailIntent = new Intent(getActivity(), DetailActivity.class);
                    detailIntent.putExtra("MOVIE", mMovies.getMovieAtIndex(position));
                    startActivity(detailIntent);
                }
            });

        }

        @Override
        protected ArrayList<String> doInBackground(String... endpoints) {
            String api_key = getString(R.string.api_key);
            String endpoint = endpoints[0];
            Uri uri = Uri.parse(API_BASE)
                    .buildUpon()
                    .appendPath(API_MOVIE_ENDPOINT)
                    .appendPath(endpoint)
                    .appendQueryParameter(API_KEY_KEY, api_key)
                    .appendQueryParameter(API_REGION, REGION_GB)
                    .build();

            String moviesResponseString = getUri(uri);
            ArrayList<String> movies = new ArrayList<>();
            if (moviesResponseString != null)
            {

                mMovies = new Movies(moviesResponseString, mConfig);
                for(int i = 0; i< 20; i++)
                {
                    Movie m = mMovies.addMovieAtIndex(i);
                    movies.add(m.title());
                }
            }

            return movies;
        }
    }
}
