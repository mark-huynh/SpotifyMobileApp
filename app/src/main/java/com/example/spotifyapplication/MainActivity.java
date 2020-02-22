package com.example.spotifyapplication;

import androidx.appcompat.app.AppCompatActivity;

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

import okhttp3.OkHttpClient;
import okhttp3.Request;

public class MainActivity extends AppCompatActivity {

    Button loginButton;

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
            Log.d("ASDF", url);
            Log.d("ASDF", response);
            return response;
        }
    }

}
