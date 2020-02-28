package com.example.spotifyapplication;

import androidx.appcompat.app.AppCompatActivity;
import androidx.lifecycle.Observer;
import androidx.lifecycle.ViewModelProvider;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.content.Intent;
import android.media.MediaPlayer;
import android.net.Uri;
import android.os.AsyncTask;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.webkit.WebView;
import android.webkit.WebViewClient;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.Spinner;

import com.example.spotifyapplication.utils.NetworkUtils;
import com.example.spotifyapplication.utils.SpotifyUtils;

import java.io.IOException;
import java.util.ArrayList;
import java.util.Arrays;

import okhttp3.OkHttpClient;
import okhttp3.Request;

public class MainActivity extends AppCompatActivity{

    private RecyclerView mTracksRV;
    private TrackAdapter mTrackAdapter;
    private Button loginButton;
    private SpotifyViewModel mViewModel;

    private Spinner timeRangeSpinner;

    private String timeRange;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);


        loginButton = findViewById(R.id.login_button);


        loginButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                doTopTracksQuery();
            }
        });
    }

    private void doTopTracksQuery() {
        MediaPlayer mp = new MediaPlayer();
        try{
            mp.setDataSource("https://p.scdn.co/mp3-preview/483355f39bb264b9828633561ab14a7a48e75270?cid=c9638677336d4bbf83dd57259fb5ac7a");
            mp.prepare();
            mp.start();
        } catch (IOException e) {
            Log.e("MAIN", "prepare() failed");
        }

    }


}