package com.example.spotifyapplication.data;

import android.util.Log;

import androidx.lifecycle.LiveData;
import androidx.lifecycle.MutableLiveData;

import com.example.spotifyapplication.utils.SpotifyUtils;

import java.util.ArrayList;

public class TopTracksRepository implements SpotifyAsyncTask.Callback{
    private MutableLiveData<ArrayList<SpotifyUtils.Track>> mTrackResults;
    private MutableLiveData<Status> mLoadingStatus;
    private String prevLimit;
    private String prevRange;


    public TopTracksRepository() {
        mTrackResults = new MutableLiveData<>();
        mTrackResults.setValue(null);
        prevLimit = "";
        prevRange = "";

        mLoadingStatus = new MutableLiveData<>();
        mLoadingStatus.setValue(Status.SUCCESS);
    }

    public LiveData<Status> getLoadingStatus() {
        return mLoadingStatus;
    }

    public MutableLiveData<ArrayList<SpotifyUtils.Track>> getTrackResults() {
        return mTrackResults;
    }

    public void loadSearchResults(String limit, String range) {
        if(!(prevLimit.equals(limit) && prevRange.equals(range))) {
            mLoadingStatus.setValue(Status.LOADING);
            prevRange = range;
            prevLimit = limit;
            mTrackResults.setValue(null);
            String topTracksURL = SpotifyUtils.buildTopTracksURL(limit, range);
            new SpotifyAsyncTask(this).execute(topTracksURL);
        } else {
            Log.d("Repository", "Using cached results");
        }

    }

    @Override
    public void onSearchFinished(ArrayList<SpotifyUtils.Track> searchResults) {
        this.mTrackResults.setValue(searchResults);
        if(searchResults != null) {
            mLoadingStatus.setValue(Status.SUCCESS);
        } else {
            mLoadingStatus.setValue(Status.ERROR);
        }
    }
}
