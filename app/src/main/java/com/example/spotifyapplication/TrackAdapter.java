package com.example.spotifyapplication;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.bumptech.glide.Glide;
import com.example.spotifyapplication.utils.SpotifyUtils;

import java.util.ArrayList;

public class TrackAdapter extends RecyclerView.Adapter<TrackAdapter.TrackViewHolder>{
    private ArrayList<SpotifyUtils.Track> mTracks;
    private OnSearchResultClickListener mResultClickListener;

    interface OnSearchResultClickListener {
        void onSearchResultClicked(SpotifyUtils.Track track);
    }

    public TrackAdapter(OnSearchResultClickListener listener) {
        mTracks = new ArrayList<>();
        mResultClickListener = listener;
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
            return mTracks.size();
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

        public void bind(SpotifyUtils.Track track) {
            mTrackName.setText(track.name);
            if(track.explicit) {
                mExplicitText.setVisibility(View.VISIBLE);
            } else {
                mExplicitText.setVisibility(View.INVISIBLE);
            }
            Glide.with(mAlbumCover.getContext()).load(track.album.images[0].url).into(mAlbumCover);
        }
    }
}
