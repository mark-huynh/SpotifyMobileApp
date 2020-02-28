package com.example.spotifyapplication.data;

import android.os.AsyncTask;
import android.util.Log;

import com.example.spotifyapplication.utils.NetworkUtils;
import com.example.spotifyapplication.utils.SpotifyUtils;

import java.io.IOException;
import java.util.ArrayList;
import java.util.Arrays;

public class SpotifyAsyncTask extends AsyncTask<String, Void, String> {

    private Callback callback;

    public interface Callback {
        void onSearchFinished(ArrayList<SpotifyUtils.Track> searchResults);
    }

    public SpotifyAsyncTask(Callback callback) {
        this.callback = callback;
    }

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
        ArrayList<SpotifyUtils.Track> items = null;
        if(s != null) {
            SpotifyUtils.Track[] res = SpotifyUtils.parseTopTracksResults(s);
            items = new ArrayList<>(Arrays.asList(res));
        } else {
            Log.d("ASDF", "could not get results");
        }
        callback.onSearchFinished(items);
    }

//    TODO: Connect this with async task, do error status, make viewmodel
}
