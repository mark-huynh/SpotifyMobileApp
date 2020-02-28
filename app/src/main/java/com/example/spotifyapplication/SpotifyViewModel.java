package com.example.spotifyapplication;

import androidx.lifecycle.LiveData;
import androidx.lifecycle.ViewModel;

import com.example.spotifyapplication.data.TopTracksRepository;
import com.example.spotifyapplication.utils.SpotifyUtils;

import java.util.ArrayList;

public class SpotifyViewModel extends ViewModel {
    private TopTracksRepository mRepo;
    private LiveData<ArrayList<SpotifyUtils.Track>> mSearchResults;

    public SpotifyViewModel() {
        mRepo = new TopTracksRepository();
        mSearchResults = mRepo.getTrackResults();
    }

    public void loadSearchResults(String limit, String range) {
        mRepo.loadSearchResults(limit, range);
    }

    public LiveData<ArrayList<SpotifyUtils.Track>> getSearchResults() {
        return mSearchResults;
    }
}
