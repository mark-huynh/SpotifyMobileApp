package com.example.spotifyapplication;

import android.app.Application;

import androidx.lifecycle.AndroidViewModel;

import com.example.spotifyapplication.data.OAuthInfo;
import com.example.spotifyapplication.data.OAuthRepository;

public class OAuthViewModel extends AndroidViewModel {
    private OAuthRepository mRepo;

    public OAuthViewModel(Application application) {
        super(application);
        mRepo = new OAuthRepository(application);
    }

    public void insertOauth(OAuthInfo oAuthInfo) {
        mRepo.insertOauth(oAuthInfo);
    }
    public void deleteOauth(OAuthInfo oAuthInfo) {
        mRepo.deleteOauth(oAuthInfo);
    }
}
