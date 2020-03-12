package com.example.spotifyapplication.data;

import androidx.annotation.NonNull;
import androidx.room.Entity;
import androidx.room.PrimaryKey;

import java.io.Serializable;

@Entity(tableName = "oauth")
public class OAuthInfo implements Serializable {
    @PrimaryKey
    @NonNull
    public String client_secret;
}
