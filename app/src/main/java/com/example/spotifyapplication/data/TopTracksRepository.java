package com.example.spotifyapplication.data;

import androidx.lifecycle.MutableLiveData;

import com.example.spotifyapplication.utils.SpotifyUtils;

import java.util.ArrayList;

public class TopTracksRepository implements SpotifyAsyncTask.Callback{
    private MutableLiveData<ArrayList<SpotifyUtils.Track>> mTrackResults;

    public TopTracksRepository() {
        mTrackResults = new MutableLiveData<>();
        mTrackResults.setValue(null);
    }

    public MutableLiveData<ArrayList<SpotifyUtils.Track>> getTrackResults() {
        return mTrackResults;
    }

    public void loadSearchResults(String limit, String range) {
        mTrackResults.setValue(null);
        String topTracksURL = SpotifyUtils.buildTopTracksURL(limit, range);
        new SpotifyAsyncTask(this).execute(topTracksURL);
    }

    @Override
    public void onSearchFinished(ArrayList<SpotifyUtils.Track> searchResults) {
        this.mTrackResults.setValue(searchResults);
        if(searchResults != null) {
//            update status
        } else {
//            update status
        }
    }
}
