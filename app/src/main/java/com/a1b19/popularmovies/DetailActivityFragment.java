package com.a1b19.popularmovies;

import android.content.Intent;
import android.support.v4.app.Fragment;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.bumptech.glide.Glide;

/**
 * A placeholder fragment containing a simple view.
 */
public class DetailActivityFragment extends Fragment {

    public DetailActivityFragment() {
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View rootView = inflater.inflate(R.layout.fragment_detail, container, false);
        Intent intent = getActivity().getIntent();
        Movie m = (Movie)intent.getSerializableExtra("MOVIE");


        TextView releaseTextView = rootView.findViewById(R.id.detail_release);
        TextView ratingTextView = rootView.findViewById(R.id.detail_rating);
        TextView overviewTextView = rootView.findViewById(R.id.detail_plot);
        ImageView posterView = rootView.findViewById(R.id.detail_poster);

        getActivity().setTitle(m.title());
        releaseTextView.setText(m.getReleaseDate());
        ratingTextView.setText(m.getRating() + " / 10");
        overviewTextView.setText(m.getOverview());
        String url = m.posterURL();

        Glide
                .with(this)
                .load(url)
                .into(posterView);


        return rootView;
    }
}
