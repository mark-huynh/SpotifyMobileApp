package com.example.spotifyapplication.utils;

import java.io.IOException;

import okhttp3.FormBody;
import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.RequestBody;
import okhttp3.Response;

public class NetworkUtils {
    private static final OkHttpClient mHTTPClient = new OkHttpClient();



    public static String doHttpGet(String url) throws IOException {
        Request request = new Request.Builder().url(url).build();
        Response response = mHTTPClient.newCall(request).execute();
        try {
            return response.body().string();
        } finally {
            response.close();
        }
    }

    public static String doPostTokenRequest(String code) throws IOException{
        RequestBody requestBody = new FormBody.Builder()
                .add("grant_type", "authorization_code")
                .add("code", code)
                .add("redirect_uri", "testing://asdf")
                .add("client_id", SpotifyUtils.CLIENT_ID)
                .add("client_secret", Config.CLIENT_SECRET)
                .build();
        Request request = new Request.Builder()
                .url(SpotifyUtils.SPOTIFY_TOKEN_BASE_URL)
                .post(requestBody)
                .build();
        Response response = mHTTPClient.newCall(request).execute();
        try {
            return response.body().string();
        } finally {
            response.close();
        }
    }

    public static String doHttpGetHeaders(String url) throws IOException {
        Request request = new Request.Builder()
                .url(url)
                .addHeader("Authorization", "Bearer " + Config.ACCESS_TOKEN)
                .build();
        Response response = mHTTPClient.newCall(request).execute();
        try {
            return response.body().string();
        } finally {
            response.close();
        }
    }
}
