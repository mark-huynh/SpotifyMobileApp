package com.example.spotifyapplication.utils;

import android.net.Uri;

import com.google.gson.Gson;

import java.io.Serializable;

public class SpotifyUtils {
    private final static String SPOTIFY_ACCOUNT_BASE_URL = "https://accounts.spotify.com/authorize";
    public final static String SPOTIFY_TOKEN_BASE_URL = "https://accounts.spotify.com/api/token";

    private final static String SPOTIFY_API_BASE_URL = "https://api.spotify.com/v1";

    private final static String CLIENT_ID_QUERYPARAM = "client_id";
    private final static String SCOPE_QUERYPARAM = "scope";
    private final static String RESPONSE_TYPE_QUERYPARAM = "response_type";

    //TODO: Find URI for application
    private final static String REDIRECT_URI_QUERYPARAM = "redirect_uri";

    public final static String CLIENT_ID = "c9638677336d4bbf83dd57259fb5ac7a";

    //TODO: Test if can work with multiple scopes that are space separated
    private final static String SCOPES = "user-top-read";


    public static String buildAuthURL() {
        return Uri.parse(SPOTIFY_ACCOUNT_BASE_URL).buildUpon()
                .appendQueryParameter(CLIENT_ID_QUERYPARAM, CLIENT_ID)
                .appendQueryParameter(SCOPE_QUERYPARAM, SCOPES)
                .appendQueryParameter(RESPONSE_TYPE_QUERYPARAM, "code")
                .appendQueryParameter(REDIRECT_URI_QUERYPARAM, "testing://asdf")
                .build()
                .toString();
    }

    /*
    String limit = num from 1-50 (inclusive at both ends), will determine number of entities to return
    String range = either long_term, medium_term, or short_term
     */
    //TODO: Determine if limit can be int with toString or if this is better
    public static String buildTopTracksURL(String limit, String range) {
        return Uri.parse(SPOTIFY_API_BASE_URL).buildUpon()
                .appendPath("me")
                .appendPath("top")
                //This can change to be artists as well
                .appendPath("tracks")
                .appendQueryParameter("limit", limit)
                .appendQueryParameter("time_range", range)
                .build()
                .toString();
    }

    public static String buildAudioFeaturesURL(String id) {
        return Uri.parse(SPOTIFY_API_BASE_URL).buildUpon()
                .appendPath("audio-features")
                .appendPath(id)
                .build()
                .toString();
    }

    public static class TopTracksResults {
        public Track[] items;
    }

    public static class Track implements Serializable {
        public Album album;
        public Artist[] artists;
        public boolean explicit;
        public String name;
        public int popularity;
        public String preview_url;
        public String uri;
        public String id;
    }

    public static class Album implements Serializable {
        public Artist[] artists;
        public Image[] images;
        public String name;
        public String uri;
//        Maybe will add release date
    }

    public static class Artist implements Serializable {
        public String name;
        public String uri;
    }

    public static class Image implements Serializable {
        public String url;
    }


    public static Track[] parseTopTracksResults(String json) {
        Gson gson = new Gson();
        TopTracksResults results = gson.fromJson(json, TopTracksResults.class);
        if(results != null && results.items != null) {
            return results.items;
        } else {
            return null;
        }
    }

    public static class AudioFeaturesResults {
        public double danceability;
        public double energy;
        public double valence;
        public double liveness;
        public double instrumentalness;
    }

    public static AudioFeaturesResults parseAudioFeaturesResult(String json) {
        Gson gson = new Gson();
        AudioFeaturesResults results = gson.fromJson(json, AudioFeaturesResults.class);
        if(results != null) {
            return results;
        } else {
            return null;
        }
    }



}
