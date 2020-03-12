package com.example.spotifyapplication;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.net.Uri;
import android.os.AsyncTask;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;

import com.example.spotifyapplication.utils.Config;
import com.example.spotifyapplication.utils.NetworkUtils;
import com.example.spotifyapplication.utils.SpotifyUtils;

import org.json.JSONException;
import org.json.JSONObject;

import java.io.IOException;

public class LoginActivity extends AppCompatActivity {

    Button loginButton;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);

        Intent intent = getIntent();
        Uri data = intent.getData();

//        Need to fix if external intent comes from source other than Spotify
        if(data != null) {
            String code = Uri.parse(data.toString()).getQueryParameter("code");
            new LoginAsyncTask().execute(code);
        }

        loginButton = findViewById(R.id.login_button);

        loginButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String url = SpotifyUtils.buildAuthURL();
                Uri oAuthURL = Uri.parse(url);
                Intent webIntent = new Intent(Intent.ACTION_VIEW, oAuthURL);
                startActivity(webIntent);
            }
        });

    }

    public void openMainApplication() {
        Intent intent = new Intent(this, MainActivity.class);
        startActivity(intent);
    }

    public class LoginAsyncTask extends AsyncTask<String, Void, String> {

        @Override
        protected String doInBackground(String... strings) {
            String code = strings[0];
            String response = null;
            try{
                response = NetworkUtils.doPostTokenRequest(code);
            } catch (IOException e) {
                e.printStackTrace();
            }
            return response;
        }

        @Override
        protected void onPostExecute(String s) {
            super.onPostExecute(s);
            if(s != null) {
                Log.d("AUTH RESPONSE: ", s);
                try {
                    JSONObject mainObj = new JSONObject(s);
                    String refresh = mainObj.getString("refresh_token");
                    String access = mainObj.getString("access_token");
                    Log.d("ACCESS: ", access);
                    Log.d("REFRESH: ", refresh);

//                    TODO: Store refresh key in storage and use it to determine if login is needed
                    Config.ACCESS_TOKEN = access;
                    openMainApplication();

                } catch (JSONException e) {
                    e.printStackTrace();
                    Log.d("Auth: ", "Error parsing refresh JSON");

                }
            } else {
                Log.d("AUTH", "could not get results");
            }
        }

    }

}
