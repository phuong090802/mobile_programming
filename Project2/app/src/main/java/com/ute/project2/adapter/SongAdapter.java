package com.ute.project2.adapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.cardview.widget.CardView;
import androidx.recyclerview.widget.RecyclerView;

import com.ute.project2.R;
import com.ute.project2.SelectSongListener;
import com.ute.project2.model.Song;

import java.util.List;

public class SongAdapter extends RecyclerView.Adapter<SongAdapter.ViewHolder> {

    List<Song> songList;
    Context context;
    SelectSongListener listener;

    public SongAdapter(Context context, List<Song> songList, SelectSongListener listener) {
        this.context = context;
        this.songList = songList;
        this.listener = listener;
    }

    public SongAdapter(Context context, List<Song> songList) {
        this.songList = songList;
        this.context = context;
    }

    @NonNull
    @Override
    public SongAdapter.ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(context).inflate(R.layout.card_song_item, parent, false);
        return new SongAdapter.ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull SongAdapter.ViewHolder holder, int position) {
        Song song = songList.get(position);
        holder.ivSong.setImageResource(song.getSongImage());
        holder.tvSongName.setText(song.getSongName());
        holder.tvSingerName.setText(song.getArtistsName());
        if (listener != null) {
            holder.cvSongItem.setOnClickListener(view -> listener.onItemClicked(songList.get(position)));
        }
    }

    @Override
    public int getItemCount() {
        return songList.size();
    }

    public static class ViewHolder extends RecyclerView.ViewHolder {
        ImageView ivSong;
        TextView tvSongName;
        TextView tvSingerName;
        CardView cvSongItem;

        public ViewHolder(@NonNull View itemView) {
            super(itemView);
            ivSong = itemView.findViewById(R.id.ivSong);
            tvSongName = itemView.findViewById(R.id.tvSongName);
            tvSingerName = itemView.findViewById(R.id.tvSingerName);
            cvSongItem = itemView.findViewById(R.id.cvSongItem);

        }
    }
}
