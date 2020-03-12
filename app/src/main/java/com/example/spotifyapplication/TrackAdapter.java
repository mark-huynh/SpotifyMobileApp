package com.example.spotifyapplication;

import android.media.MediaPlayer;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.bumptech.glide.Glide;
import com.example.spotifyapplication.utils.SpotifyUtils;

import java.io.IOException;
import java.util.ArrayList;

public class TrackAdapter extends RecyclerView.Adapter<TrackAdapter.TrackViewHolder>{
    private ArrayList<SpotifyUtils.Track> mTracks;
    private OnSearchResultClickListener mResultClickListener;
    private MediaPlayer mp;
    private int numDisplayed;

    interface OnSearchResultClickListener {
        void onSearchResultClicked(SpotifyUtils.Track track);
    }

    public TrackAdapter(OnSearchResultClickListener listener) {
        mTracks = new ArrayList<>();
        mResultClickListener = listener;

        mp = new MediaPlayer();

    }

    public void changeNumDisplayed(int numDisplayed) {
        this.numDisplayed = numDisplayed;
        notifyDataSetChanged();
    }

    public void updateTrackData(ArrayList<SpotifyUtils.Track> tracks) {
        mTracks = tracks;
        notifyDataSetChanged();
    }

    @NonNull
    @Override
    public TrackViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        LayoutInflater inflater = LayoutInflater.from(parent.getContext());
        View view = inflater.inflate(R.layout.track_list_item, parent, false);
        TrackViewHolder viewHolder = new TrackViewHolder(view);
        return viewHolder;
    }

    @Override
    public void onBindViewHolder(@NonNull TrackViewHolder holder, int position) {
        holder.bind(mTracks.get(position));
    }

    @Override
    public int getItemCount() {
        if(mTracks != null) {
            return Math.min(mTracks.size(), numDisplayed);
        } else {
            return 0;
        }
    }

    class TrackViewHolder extends RecyclerView.ViewHolder {
        private TextView mTrackName;
        private TextView mExplicitText;
        private ImageView mAlbumCover;

        public TrackViewHolder(View itemView) {
            super(itemView);
            mTrackName = itemView.findViewById(R.id.list_track_name);
            mExplicitText = itemView.findViewById(R.id.list_is_explicit);
            mAlbumCover = itemView.findViewById(R.id.list_album_cover);

            itemView.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    mResultClickListener.onSearchResultClicked(
                            mTracks.get(getAdapterPosition())
                    );
                }
            });
        }

        public void bind(final SpotifyUtils.Track track) {
            mTrackName.setText(track.name);
            if(track.explicit) {
                mExplicitText.setVisibility(View.VISIBLE);
            } else {
                mExplicitText.setVisibility(View.INVISIBLE);
            }
            Glide.with(mAlbumCover.getContext()).load(track.album.images[0].url).into(mAlbumCover);

            mAlbumCover.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    try{
                        if(mp.isPlaying()) {
                            mp.reset();
                            mp.stop();
                        }
                        mp.reset();
                        mp.setDataSource(track.preview_url);
                        mp.prepare();
                        mp.start();
                    } catch (IOException e) {
                        Log.e("MAIN", "prepare() failed");
                    }
                }
            });
        }
    }

//            try{
//        if(mp.isPlaying()){
//            mp.reset();
//            mp.stop();
//        }
//        mp.setDataSource("https://p.scdn.co/mp3-preview/483355f39bb264b9828633561ab14a7a48e75270?cid=c9638677336d4bbf83dd57259fb5ac7a");
//        mp.prepare();
//        mp.start();
//    } catch (
//    IOException e) {
//        Log.e("MAIN", "prepare() failed");
//    }

}
