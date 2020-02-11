package com.example.spotifyapplication;

import androidx.appcompat.app.AppCompatActivity;

import android.net.Uri;
import android.os.Bundle;
import android.widget.Button;

import okhttp3.OkHttpClient;
import okhttp3.Request;

public class MainActivity extends AppCompatActivity {

    Button loginButton = findViewById(R.id.login_button);

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

//        OkHttpClient client = new OkHttpClient();
//        Request req = new Request.Builder().url("https://accounts.spotify.com/authorize?client_id=c9638677336d4bbf83dd57259fb5ac7a&response_type=code&redirect_uri=http://afsjdkl.com/callback&scope=user-read-currently-playing user-top-read").build();
    }

}
