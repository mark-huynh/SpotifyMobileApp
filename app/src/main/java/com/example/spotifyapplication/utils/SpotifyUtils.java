package com.example.spotifyapplication.utils;

import android.net.Uri;

public class SpotifyUtils {
    private final static String SPOTIFY_ACCOUNT_BASE_URL = "https://accounts.spotify.com/authorize";
    private final static String SPOTIFY_TOKEN_BASE_URL = "https://accounts.spotify.com/api/token";

    private final static String CLIENT_ID_QUERYPARAM = "client_id";
    private final static String SCOPE_QUERYPARAM = "scope";
    private final static String RESPONSE_TYPE_QUERYPARAM = "response_type";

    //TODO: Find URI for application
    private final static String REDIRECT_URI_QUERYPARAM = "google.com";

    private final static String CLIENT_ID = "c9638677336d4bbf83dd57259fb5ac7a";

    //TODO: Test if can work with multiple scopes that are space separated
    private final static String SCOPES = "user-top-read";

    public static String buildAuthURL() {
        return Uri.parse(SPOTIFY_ACCOUNT_BASE_URL).buildUpon()
                .appendQueryParameter(CLIENT_ID_QUERYPARAM, CLIENT_ID)
                .appendQueryParameter(SCOPE_QUERYPARAM, SCOPES)
                .appendQueryParameter(RESPONSE_TYPE_QUERYPARAM, "code")
                .appendQueryParameter(REDIRECT_URI_QUERYPARAM, REDIRECT_URI_QUERYPARAM)
                .build()
                .toString();
    }
}
