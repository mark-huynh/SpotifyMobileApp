package com.example.spotifyapplication.data;

import androidx.room.Dao;
import androidx.room.Delete;
import androidx.room.Insert;

@Dao
public interface OAuthDao {
    @Insert
    void insert(OAuthInfo oauth);

    @Delete
    void delete(OAuthInfo oauth);
}
