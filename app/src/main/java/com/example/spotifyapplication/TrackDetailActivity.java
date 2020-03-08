package com.example.spotifyapplication;

import androidx.appcompat.app.AppCompatActivity;
import androidx.lifecycle.Observer;
import androidx.lifecycle.ViewModelProvider;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.widget.ImageView;
import android.widget.TextView;

import com.bumptech.glide.Glide;
import com.example.spotifyapplication.data.SpotifyAudioFeaturesAsync;
import com.example.spotifyapplication.utils.SpotifyUtils;

public class TrackDetailActivity extends AppCompatActivity {
    public static final String EXTRA_TRACK = "TrackInfo";
    private SpotifyUtils.Track track;

    private SpotifyViewModel mViewModel;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_track_detail);

        mViewModel = new ViewModelProvider(this).get(SpotifyViewModel.class);
        mViewModel.getAudioResults().observe(this, new Observer<SpotifyUtils.AudioFeaturesResults>() {
            @Override
            public void onChanged(SpotifyUtils.AudioFeaturesResults audioFeaturesResults) {
                if(audioFeaturesResults != null)
                {
                    Log.d("ASYC", "Dance" + audioFeaturesResults.danceability);
                    Log.d("ASYC", "En" + audioFeaturesResults.energy);
                    Log.d("ASYC", "Val" + audioFeaturesResults.valence);
                    Log.d("ASYC", "Liv" + audioFeaturesResults.liveness);
                }

            }
        });

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

            mViewModel.loadAudioFeatureResults(track.id);
//            String audioFeatURL = SpotifyUtils.buildAudioFeaturesURL(track.id);
//            new SpotifyAudioFeaturesAsync().execute(audioFeatURL);
        }
    }
}
