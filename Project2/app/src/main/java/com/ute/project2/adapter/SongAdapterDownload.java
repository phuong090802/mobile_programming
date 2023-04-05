package com.ute.project2.adapter;

import android.content.Context;
import android.view.ContextMenu;
import android.view.LayoutInflater;
import android.view.MenuInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.cardview.widget.CardView;
import androidx.recyclerview.widget.RecyclerView;

import com.ute.project2.R;
import com.ute.project2.event.SelectSongListener;
import com.ute.project2.model.Song;

import java.util.List;

public class SongAdapterDownload extends RecyclerView.Adapter<SongAdapterDownload.ViewHolder> {
    List<Song> songList;
    Context context;
    SelectSongListener listener;

    public SongAdapterDownload(Context context, List<Song> songList, SelectSongListener listener) {
        this.songList = songList;
        this.context = context;
        this.listener = listener;
    }

    @NonNull
    @Override
    public SongAdapterDownload.ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(context).inflate(R.layout.card_song_item, parent, false);
        return new SongAdapterDownload.ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull SongAdapterDownload.ViewHolder holder, int position) {
        Song song = songList.get(position);
        holder.ivSong.setImageResource(song.getSongImage());
        holder.tvSongName.setText(song.getSongName());
        holder.tvArtistName.setText(song.getArtistsName());
        if (listener != null) {
            holder.cvSongItem.setOnClickListener(view -> listener.onItemClicked(song));
        }
    }

    @Override
    public int getItemCount() {
        return songList.size();
    }

    public static class ViewHolder extends RecyclerView.ViewHolder implements View.OnCreateContextMenuListener  {
        ImageView ivSong;
        TextView tvSongName;
        TextView tvArtistName;
        CardView cvSongItem;

        public ViewHolder(@NonNull View itemView) {
            super(itemView);
            ivSong = itemView.findViewById(R.id.ivSong);
            tvSongName = itemView.findViewById(R.id.tvSongName);
            tvArtistName = itemView.findViewById(R.id.tvArtistName);
            cvSongItem = itemView.findViewById(R.id.cvSongItem);
            cvSongItem.setOnCreateContextMenuListener(this);
        }

        @Override
        public void onCreateContextMenu(ContextMenu contextMenu, View view, ContextMenu.ContextMenuInfo contextMenuInfo) {
            MenuInflater inflater = new MenuInflater(view.getContext());
            inflater.inflate(R.menu.menu_context_download, contextMenu);
            contextMenu.findItem(R.id.mnRemove).setOnMenuItemClickListener(menuItem -> {
                Toast.makeText(view.getContext(), "Remove", Toast.LENGTH_SHORT).show();
                return false;
            });
        }
    }
}
