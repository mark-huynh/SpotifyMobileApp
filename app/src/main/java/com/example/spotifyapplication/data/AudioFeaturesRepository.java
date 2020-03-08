package com.example.spotifyapplication.data;

import androidx.lifecycle.MutableLiveData;

import com.example.spotifyapplication.utils.SpotifyUtils;

public class AudioFeaturesRepository implements SpotifyAudioFeaturesAsync.Callback {
    private MutableLiveData<SpotifyUtils.AudioFeaturesResults> mResults;

    public AudioFeaturesRepository() {
        mResults = new MutableLiveData<>();
        mResults.setValue(null);
    }

    public void loadSearchResults(String trackID) {
        String audioFeatURL = SpotifyUtils.buildAudioFeaturesURL(trackID);
        new SpotifyAudioFeaturesAsync(this).execute(audioFeatURL);
    }

    public MutableLiveData<SpotifyUtils.AudioFeaturesResults> getAudioResults() {
        return mResults;
    }

    @Override
    public void onSearchFinished(SpotifyUtils.AudioFeaturesResults searchResults) {
        this.mResults.setValue(searchResults);
        if(searchResults != null) {
//            update status
        } else {
//            update status
        }
    }
}
