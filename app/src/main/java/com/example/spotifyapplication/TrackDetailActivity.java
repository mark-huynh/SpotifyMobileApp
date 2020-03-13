package com.example.spotifyapplication;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.lifecycle.Observer;
import androidx.lifecycle.ViewModelProvider;

import android.content.Intent;
import android.content.pm.PackageManager;
import android.content.pm.ResolveInfo;
import android.graphics.Color;
import android.net.Uri;
import android.os.Bundle;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.ImageView;
import android.widget.TextView;

import com.akexorcist.roundcornerprogressbar.IconRoundCornerProgressBar;
import com.akexorcist.roundcornerprogressbar.RoundCornerProgressBar;
import com.bumptech.glide.Glide;
import com.example.spotifyapplication.data.SpotifyAudioFeaturesAsync;
import com.example.spotifyapplication.utils.SpotifyUtils;

import java.util.List;

public class TrackDetailActivity extends AppCompatActivity {
    public static final String EXTRA_TRACK = "TrackInfo";
    private SpotifyUtils.Track track;

    private SpotifyViewModel mViewModel;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_track_detail);

        final IconRoundCornerProgressBar danceProgress = findViewById(R.id.danceability_progress);
        danceProgress.setProgressColor(Color.parseColor("#56d2c2"));
        danceProgress.setProgressBackgroundColor(Color.parseColor("#757575"));
        danceProgress.setMax(1);

        final IconRoundCornerProgressBar energyProgress = findViewById(R.id.energy_progress);
        energyProgress.setProgressColor(Color.parseColor("#56d2c2"));
        energyProgress.setProgressBackgroundColor(Color.parseColor("#757575"));
        energyProgress.setMax(1);

        final IconRoundCornerProgressBar valenceProgress = findViewById(R.id.valence_progress);
        valenceProgress.setProgressColor(Color.parseColor("#56d2c2"));
        valenceProgress.setProgressBackgroundColor(Color.parseColor("#757575"));
        valenceProgress.setMax(1);

        final IconRoundCornerProgressBar livenessProgress = findViewById(R.id.liveness_progress);
        livenessProgress.setProgressColor(Color.parseColor("#56d2c2"));
        livenessProgress.setProgressBackgroundColor(Color.parseColor("#757575"));
        livenessProgress.setMax(1);

        final IconRoundCornerProgressBar instrumentProgress = findViewById(R.id.instrumentalness_progress);
        instrumentProgress.setProgressColor(Color.parseColor("#56d2c2"));
        instrumentProgress.setProgressBackgroundColor(Color.parseColor("#757575"));
        instrumentProgress.setMax(1);

//        danceProgress.setIconBackgroundColor(Color.parseColor("#38c0ae"));

        mViewModel = new ViewModelProvider(this).get(SpotifyViewModel.class);
        mViewModel.getAudioResults().observe(this, new Observer<SpotifyUtils.AudioFeaturesResults>() {
            @Override
            public void onChanged(SpotifyUtils.AudioFeaturesResults audioFeaturesResults) {
                if(audioFeaturesResults != null)
                {
                    TextView dance = findViewById(R.id.danceability_tv);
                    danceProgress.setProgress((float)audioFeaturesResults.danceability);

                    TextView energy = findViewById(R.id.energy_tv);
                    energyProgress.setProgress((float)audioFeaturesResults.energy);

                    TextView valence = findViewById(R.id.valence_tv);
                    valenceProgress.setProgress((float)audioFeaturesResults.valence);

                    TextView live = findViewById(R.id.liveness_tv);
                    livenessProgress.setProgress((float)audioFeaturesResults.liveness);


                    TextView instr = findViewById(R.id.instrumentalness_tv);
                    instrumentProgress.setProgress((float)audioFeaturesResults.instrumentalness);

                }

            }
        });

        Intent intent = getIntent();
        if (intent != null && intent.hasExtra(EXTRA_TRACK)) {
            track = (SpotifyUtils.Track)intent.getSerializableExtra(EXTRA_TRACK);
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

            mViewModel.loadAudioFeatureResults(track.id);
        }
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.song_detail, menu);
        return true;
    }

    public void viewSongSpotify() {
        Uri spotifySong = Uri.parse(track.uri);
        Log.d("TRACK", track.uri);
        Intent webIntent = new Intent(Intent.ACTION_VIEW, spotifySong);
        PackageManager packageManager = getPackageManager();
        List<ResolveInfo> activities =
                packageManager.queryIntentActivities(
                        webIntent,
                        PackageManager.MATCH_DEFAULT_ONLY
                );
        if (activities.size() > 0) {
            startActivity(webIntent);
        }
    }

    public void viewAlbumSpotify() {
        Uri spotifySong = Uri.parse(track.album.uri);
        Log.d("ALBUM", track.album.uri);
        Intent webIntent = new Intent(Intent.ACTION_VIEW, spotifySong);
        PackageManager packageManager = getPackageManager();
        List<ResolveInfo> activities =
                packageManager.queryIntentActivities(
                        webIntent,
                        PackageManager.MATCH_DEFAULT_ONLY
                );
        if (activities.size() > 0) {
            startActivity(webIntent);
        }
    }

    @Override
    public boolean onOptionsItemSelected(@NonNull MenuItem item) {
        switch (item.getItemId()) {
            case R.id.action_open_song:
                viewSongSpotify();
                return true;
            case R.id.action_open_album:
                viewAlbumSpotify();
                return true;
            default:
                return super.onOptionsItemSelected(item);
        }
    }
}
