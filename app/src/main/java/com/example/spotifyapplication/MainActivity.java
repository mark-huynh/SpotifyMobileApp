package com.example.spotifyapplication;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.webkit.WebView;
import android.webkit.WebViewClient;
import android.widget.Button;

import com.example.spotifyapplication.utils.SpotifyUtils;

import okhttp3.OkHttpClient;
import okhttp3.Request;

public class MainActivity extends AppCompatActivity {

    Button loginButton;
    WebView webView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        loginButton = findViewById(R.id.login_button);
        webView = findViewById(R.id.webview);
        webView.getSettings().setJavaScriptEnabled(true);
        webView.getSettings().setBuiltInZoomControls(true);
//        webView.setWebViewClient(new WebViewClient());
        loginButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                String url = SpotifyUtils.buildAuthURL();
//                Log.d("FOO", "onClick: " + url);
//                webView.loadUrl("https://accounts.spotify.com/en/authorize?client_id=c9638677336d4bbf83dd57259fb5ac7a&scope=user-top-read&response_type=code&redirect_uri=testing:%2F%2Fasdf");
//                loginButton.setVisibility(View.INVISIBLE);
                Uri oAuthURL = Uri.parse(url);
                Intent webIntent = new Intent(Intent.ACTION_VIEW, oAuthURL);
                startActivity(webIntent);
            }
        });
//        OkHttpClient client = new OkHttpClient();
//        Request req = new Request.Builder().url("https://accounts.spotify.com/authorize?client_id=c9638677336d4bbf83dd57259fb5ac7a&response_type=code&redirect_uri=http://afsjdkl.com/callback&scope=user-read-currently-playing user-top-read").build();
    }

}
