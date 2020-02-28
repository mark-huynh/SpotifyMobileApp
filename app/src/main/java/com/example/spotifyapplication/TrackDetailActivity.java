package com.example.spotifyapplication;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.widget.ImageView;
import android.widget.TextView;

import com.bumptech.glide.Glide;
import com.example.spotifyapplication.utils.SpotifyUtils;

public class TrackDetailActivity extends AppCompatActivity {
    public static final String EXTRA_TRACK = "TrackInfo";
    private SpotifyUtils.Track track;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_track_detail);

        Intent intent = getIntent();
        if (intent != null && intent.hasExtra(EXTRA_TRACK)) {
            track = (SpotifyUtils.Track)intent.getSerializableExtra(EXTRA_TRACK);
//            Log.d("DETAILS", track.preview_url);
//            Log.d("DETAILS", track.artists[0].name);
            TextView trackName = findViewById(R.id.detail_track_name);
            TextView albumName = findViewById(R.id.detailed_album_name);
            ImageView albumCover = findViewById(R.id.detailed_album_cover);
            TextView artists = findViewById(R.id.detailed_artists);

            trackName.setText(track.name);
            albumName.setText(track.album.name);
            String allArtists = "";
            for(SpotifyUtils.Artist a : track.artists) {
                allArtists += a.name + " ";
            }
            artists.setText(allArtists);
            Glide.with(albumCover.getContext()).load(track.album.images[1].url).into(albumCover);
//            Glide.with(mAlbumCover.getContext()).load(track.album.images[0].url).into(mAlbumCover);
        }
    }
}
