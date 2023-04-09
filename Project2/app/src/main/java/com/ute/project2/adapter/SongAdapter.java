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

import com.squareup.picasso.Picasso;
import com.ute.project2.R;
import com.ute.project2.event.SelectSongListener;
import com.ute.project2.model.Song;

import java.io.File;
import java.util.List;

public class SongAdapter extends RecyclerView.Adapter<SongAdapter.ViewHolder> {
    private final List<Song> songList;
    private final Context context;
    private final SelectSongListener listener;

    public SongAdapter(Context context, List<Song> songList, SelectSongListener listener) {
        this.context = context;
        this.listener = listener;
        this.songList = songList;
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
        File cacheDir = context.getCacheDir();
        new File(cacheDir, "song-image-picasso-cache");
        Picasso.get().load(song.getSongImage()).into(holder.ivSong);
        holder.tvSongName.setText(song.getSongName());
        holder.tvArtistName.setText(song.getArtist());
        if (listener != null) {
            holder.cvSongItem.setOnClickListener(view -> listener.onItemClicked(song));
        }
    }

    @Override
    public int getItemCount() {
        return songList.size();
    }

    public static class ViewHolder extends RecyclerView.ViewHolder implements View.OnCreateContextMenuListener {
        private final ImageView ivSong;
        private final TextView tvSongName;
        private final TextView tvArtistName;
        private final CardView cvSongItem;

        public ViewHolder(@NonNull View itemView) {
            super(itemView);
            ivSong = itemView.findViewById(R.id.ivSong);
            tvSongName = itemView.findViewById(R.id.tvSongName);
            tvArtistName = itemView.findViewById(R.id.tvArtistName);
            cvSongItem = itemView.findViewById(R.id.card_view_song_item);
            cvSongItem.setOnCreateContextMenuListener(this);
        }

        @Override
        public void onCreateContextMenu(ContextMenu contextMenu, View view, ContextMenu.ContextMenuInfo contextMenuInfo) {
            RecyclerView recyclerView = (RecyclerView) view.getParent();
            SongAdapter adapterSearch = (SongAdapter) recyclerView.getAdapter();
            int position = recyclerView.getChildAdapterPosition(view);
            Song song = null;
            if (adapterSearch != null) {
                song = adapterSearch.songList.get(position);
            }
            MenuInflater inflater = new MenuInflater(view.getContext());
            inflater.inflate(R.menu.menu_context_search, contextMenu);
            Song finalSong = song;
            contextMenu.findItem(R.id.mnAddToFavorites).setOnMenuItemClickListener(menuItem -> {
                if (finalSong != null) {
                    Toast.makeText(view.getContext(), "Add " + "\"" + finalSong.getSongName() + "\"" + " to favorites", Toast.LENGTH_SHORT).show();
                    return true;
                }
                return false;
            });
            contextMenu.findItem(R.id.mnAddToDownload).setOnMenuItemClickListener(menuItem -> {
                if (finalSong != null) {
                    Toast.makeText(view.getContext(), "Download " + "\"" + finalSong.getSongName() + "\"", Toast.LENGTH_SHORT).show();
                    return true;
                }
                return false;
            });
        }
    }
}
