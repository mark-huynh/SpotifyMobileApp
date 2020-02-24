package com.example.spotifyapplication;

import androidx.appcompat.app.AppCompatActivity;
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
    Button loginButton;

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

        loginButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                doTopTracksQuery();
            }
        });
    }

    private void doTopTracksQuery(){
        String url = SpotifyUtils.buildTopTracksURL("2", "short_term");
        new SpotifyAsyncTask().execute(url);
    }

    public class SpotifyAsyncTask extends AsyncTask<String, Void, String> {
        @Override
        protected String doInBackground(String... strings) {
            String url = strings[0];
            String response = null;
            try{
                response = NetworkUtils.doHttpGetHeaders(url);
            } catch (IOException e) {
                e.printStackTrace();
            }
            return response;
        }

        @Override
        protected void onPostExecute(String s) {
            super.onPostExecute(s);

            if(s != null) {
                SpotifyUtils.Track[] res = SpotifyUtils.parseTopTracksResults(s);
                ArrayList<SpotifyUtils.Track> a = new ArrayList<>(Arrays.asList(res));
                mTrackAdapter.updateTrackData(a);
            } else {
                Log.d("ERROR", "could not get results");
            }

        }
    }

}
