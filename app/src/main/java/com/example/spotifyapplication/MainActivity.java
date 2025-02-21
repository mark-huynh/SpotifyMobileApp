package com.example.spotifyapplication;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.lifecycle.Observer;
import androidx.lifecycle.ViewModelProvider;
import androidx.recyclerview.widget.DefaultItemAnimator;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.content.Intent;
import android.net.Uri;
import android.os.AsyncTask;
import android.os.Bundle;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.webkit.WebView;
import android.webkit.WebViewClient;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.NumberPicker;
import android.widget.ProgressBar;
import android.widget.Spinner;
import android.widget.TextView;

import com.example.spotifyapplication.data.OAuthInfo;
import com.example.spotifyapplication.data.Status;
import com.example.spotifyapplication.utils.NetworkUtils;
import com.example.spotifyapplication.utils.SpotifyUtils;

import java.io.IOException;
import java.util.ArrayList;
import java.util.Arrays;

import okhttp3.OkHttpClient;
import okhttp3.Request;

public class MainActivity extends AppCompatActivity implements TrackAdapter.OnSearchResultClickListener, AdapterView.OnItemSelectedListener {

    private TextView mErrorMessageTV;
    private ProgressBar mLoadingIndicatorPB;

    private RecyclerView mTracksRV;
    private TrackAdapter mTrackAdapter;
    private SpotifyViewModel mViewModel;

    private Spinner timeRangeSpinner;
    private NumberPicker numPicker;

    private String timeRange;
    private String numTracks;

    private OAuthViewModel oAuthViewModel;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        oAuthViewModel = new ViewModelProvider(
                this,
                new ViewModelProvider.AndroidViewModelFactory(
                        getApplication()
                )
        ).get(OAuthViewModel.class);

        oAuthViewModel.getSingleOAuth().observe(this, new Observer<OAuthInfo>() {
            @Override
            public void onChanged(OAuthInfo oAuthInfo) {
                if(oAuthInfo == null) {
                    openLoginPage();
                }
            }
        });

        mLoadingIndicatorPB = findViewById(R.id.pb_loading_indicator);
        mErrorMessageTV = findViewById(R.id.tv_error_message);


        timeRangeSpinner = findViewById(R.id.time_range_spinner);

        ArrayList<String> timeRangeOptions = new ArrayList<>(Arrays.asList("Several Years", "Last 6 Months", "Last 4 Weeks"));
        ArrayAdapter<String> timeRangeSpinnerArr = new ArrayAdapter<>(this, android.R.layout.simple_spinner_item, timeRangeOptions);

        timeRangeSpinnerArr.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        timeRangeSpinner.setAdapter(timeRangeSpinnerArr);

        if(savedInstanceState != null && savedInstanceState.containsKey("term")) {
            int foo = savedInstanceState.getInt("term", 0);
            Log.d("MAIN", Integer.toString(foo));
            timeRangeSpinner.setSelection(foo);
        }

        timeRangeSpinner.setOnItemSelectedListener(this);

        numPicker = findViewById(R.id.num_picker);
        numPicker.setMaxValue(50);
        numPicker.setMinValue(1);

        numTracks = "50";
        numPicker.setValue(50);
        if(savedInstanceState != null && savedInstanceState.containsKey("num")) {
            int foo = savedInstanceState.getInt("num", 0);
            numPicker.setValue(foo);
            numTracks = Integer.toString(foo);
            Log.d("MAIN", "SAVEDINT");
        }

        numPicker.setOnValueChangedListener(new NumberPicker.OnValueChangeListener() {
            @Override
            public void onValueChange(NumberPicker picker, int oldVal, int newVal) {
                Log.d("MAIN", "new num: " + newVal);
                mTrackAdapter.changeNumDisplayed(newVal);
            }
        });



        mTracksRV = findViewById(R.id.rv_tracks_list);


        mTracksRV.setLayoutManager(new LinearLayoutManager(this));
        mTracksRV.setHasFixedSize(true);

        mTrackAdapter = new TrackAdapter(this);
        mTracksRV.setAdapter(mTrackAdapter);

        mTrackAdapter.changeNumDisplayed(numPicker.getValue());

        mViewModel = new ViewModelProvider(this).get(SpotifyViewModel.class);
        mViewModel.getSearchResults().observe(this, new Observer<ArrayList<SpotifyUtils.Track>>() {
            @Override
            public void onChanged(ArrayList<SpotifyUtils.Track> tracks) {
                mTrackAdapter.updateTrackData(tracks);
            }
        });

        mViewModel.getLoadingStatus().observe(this, new Observer<Status>() {
            @Override
            public void onChanged(Status status) {
                if (status == Status.LOADING) {
                    mLoadingIndicatorPB.setVisibility(View.VISIBLE);
                } else if (status == Status.SUCCESS) {
                    mLoadingIndicatorPB.setVisibility(View.INVISIBLE);
                    mTracksRV.setVisibility(View.VISIBLE);
                    mErrorMessageTV.setVisibility(View.INVISIBLE);
                } else {
                    mLoadingIndicatorPB.setVisibility(View.INVISIBLE);
                    mTracksRV.setVisibility(View.INVISIBLE);
                    mErrorMessageTV.setVisibility(View.VISIBLE);
                }
            }
        });
    }

    public void openLoginPage() {
        Intent intent = new Intent(this, LoginActivity.class);
        startActivity(intent);
    }

    private void doTopTracksQuery(){
        mViewModel.loadSearchResults("50", timeRange);
    }


    @Override
    public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
        String range = parent.getItemAtPosition(position).toString();
        Log.d("MAIN", range);
        if(range.equals("Several Years")) {
            timeRange = "long_term";
        } else if(range.equals("Last 6 Months")) {
            timeRange = "medium_term";
        } else if(range.equals("Last 4 Weeks")) {
            timeRange = "short_term";
        }
        doTopTracksQuery();
    }

    @Override
    public void onNothingSelected(AdapterView<?> parent) {

    }

    @Override
    public void onSearchResultClicked(SpotifyUtils.Track track) {
        Intent intent = new Intent(this, TrackDetailActivity.class);
        intent.putExtra(TrackDetailActivity.EXTRA_TRACK, track);
        startActivity(intent);
    }

    @Override
    protected void onSaveInstanceState(@NonNull Bundle outState) {
        super.onSaveInstanceState(outState);
        outState.putInt("term", timeRangeSpinner.getSelectedItemPosition());
        outState.putInt("num", numPicker.getValue());
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.main_logout, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(@NonNull MenuItem item) {
        if(item.getItemId() == R.id.action_logout) {
            oAuthViewModel.deleteAllOauthEntries();
            return true;
        }
        else{
            return super.onOptionsItemSelected(item);
        }
    }
}
