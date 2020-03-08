package com.example.spotifyapplication.data;

import android.os.AsyncTask;
import android.util.Log;

import com.example.spotifyapplication.utils.NetworkUtils;
import com.example.spotifyapplication.utils.SpotifyUtils;

import java.io.IOException;

public class SpotifyAudioFeaturesAsync extends AsyncTask<String, Void, String> {

    private Callback callback;

    public interface Callback {
        void onSearchFinished(SpotifyUtils.AudioFeaturesResults searchResults);
    }

    public SpotifyAudioFeaturesAsync(Callback callback) {
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
        SpotifyUtils.AudioFeaturesResults results = null;
        if(s != null)
        {
             results = SpotifyUtils.parseAudioFeaturesResult(s);
        } else {
            Log.d("ASDF", "could not get results");
        }
        callback.onSearchFinished(results);

//        Log.d("ASYC", "Dance" + results.danceability);
//        Log.d("ASYC", "En" + results.energy);
//        Log.d("ASYC", "Val" + results.valence);
//        Log.d("ASYC", "Liv" + results.liveness);
    }
}
