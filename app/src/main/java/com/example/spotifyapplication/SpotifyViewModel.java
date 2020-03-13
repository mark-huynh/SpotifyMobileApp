package com.example.spotifyapplication;

import androidx.lifecycle.LiveData;
import androidx.lifecycle.ViewModel;

import com.example.spotifyapplication.data.AudioFeaturesRepository;
import com.example.spotifyapplication.data.Status;
import com.example.spotifyapplication.data.TopTracksRepository;
import com.example.spotifyapplication.utils.SpotifyUtils;

import java.util.ArrayList;

public class SpotifyViewModel extends ViewModel {
    private TopTracksRepository mRepo;
    private LiveData<ArrayList<SpotifyUtils.Track>> mSearchResults;
    private LiveData<Status> mLoadingStatus;

    private AudioFeaturesRepository mAudioRepo;
    private LiveData<SpotifyUtils.AudioFeaturesResults> mAudioResults;

    public SpotifyViewModel() {
        mRepo = new TopTracksRepository();
        mSearchResults = mRepo.getTrackResults();
        mLoadingStatus = mRepo.getLoadingStatus();

        mAudioRepo = new AudioFeaturesRepository();
        mAudioResults = mAudioRepo.getAudioResults();
    }

    public void loadSearchResults(String limit, String range) {
        mRepo.loadSearchResults(limit, range);
    }

    public LiveData<ArrayList<SpotifyUtils.Track>> getSearchResults() {
        return mSearchResults;
    }

    public void loadAudioFeatureResults(String trackID) {
        mAudioRepo.loadSearchResults(trackID);
    }

    public LiveData<SpotifyUtils.AudioFeaturesResults> getAudioResults() {
        return mAudioResults;
    }

    public LiveData<Status> getLoadingStatus() {
        return mLoadingStatus;
    }
}
