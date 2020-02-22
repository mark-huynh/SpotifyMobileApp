package com.example.spotifyapplication.utils;

import android.net.Uri;

public class SpotifyUtils {
    private final static String SPOTIFY_ACCOUNT_BASE_URL = "https://accounts.spotify.com/authorize";
    private final static String SPOTIFY_TOKEN_BASE_URL = "https://accounts.spotify.com/api/token";

    private final static String SPOTIFY_API_BASE_URL = "https://api.spotify.com/v1";

    private final static String CLIENT_ID_QUERYPARAM = "client_id";
    private final static String SCOPE_QUERYPARAM = "scope";
    private final static String RESPONSE_TYPE_QUERYPARAM = "response_type";

    //TODO: Find URI for application
    private final static String REDIRECT_URI_QUERYPARAM = "redirect_uri";

    private final static String CLIENT_ID = "c9638677336d4bbf83dd57259fb5ac7a";

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
}
