package com.example.spotifyapplication.data;

import androidx.lifecycle.LiveData;
import androidx.room.Dao;
import androidx.room.Delete;
import androidx.room.Insert;
import androidx.room.Query;

@Dao
public interface OAuthDao {
    @Insert
    void insert(OAuthInfo oauth);

    @Delete
    void delete(OAuthInfo oauth);

    @Query("SELECT * FROM oauth LIMIT 1")
    LiveData<OAuthInfo> getSingleOAuth();

    @Query("DELETE FROM oauth")
    void deleteAllOauthEntries();
}
