package com.example.spotifyapplication;

import androidx.appcompat.app.AppCompatActivity;
import androidx.lifecycle.Observer;
import androidx.lifecycle.ViewModelProvider;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.content.Intent;
import android.net.Uri;
import android.os.AsyncTask;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.webkit.WebView;
import android.webkit.WebViewClient;
import android.widget.Button;

import com.example.spotifyapplication.utils.NetworkUtils;
import com.example.spotifyapplication.utils.SpotifyUtils;

import java.io.IOException;
import java.util.ArrayList;
import java.util.Arrays;

import okhttp3.OkHttpClient;
import okhttp3.Request;

public class MainActivity extends AppCompatActivity {

    private RecyclerView mTracksRV;
    private TrackAdapter mTrackAdapter;
    private Button loginButton;
    private SpotifyViewModel mViewModel;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        mTracksRV = findViewById(R.id.rv_tracks_list);

        loginButton = findViewById(R.id.login_button);

        mTracksRV.setLayoutManager(new LinearLayoutManager(this));
        mTracksRV.setHasFixedSize(true);

        mTrackAdapter = new TrackAdapter();
        mTracksRV.setAdapter(mTrackAdapter);

        mViewModel = new ViewModelProvider(this).get(SpotifyViewModel.class);
        mViewModel.getSearchResults().observe(this, new Observer<ArrayList<SpotifyUtils.Track>>() {
            @Override
            public void onChanged(ArrayList<SpotifyUtils.Track> tracks) {
                mTrackAdapter.updateTrackData(tracks);
            }
        });

        loginButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                doTopTracksQuery();
            }
        });
    }

    private void doTopTracksQuery(){
        mViewModel.loadSearchResults("50", "short_term");
    }


}
