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

public class SongAdapterSearch extends RecyclerView.Adapter<SongAdapterSearch.ViewHolder> {
    List<Song> songList;
    Context context;
    SelectSongListener listener;

    public SongAdapterSearch(Context context, List<Song> songList, SelectSongListener listener) {
        this.songList = songList;
        this.context = context;
        this.listener = listener;
    }

    @NonNull
    @Override
    public SongAdapterSearch.ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(context).inflate(R.layout.card_song_item, parent, false);
        return new SongAdapterSearch.ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull SongAdapterSearch.ViewHolder holder, int position) {
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

    public static class ViewHolder extends RecyclerView.ViewHolder implements View.OnCreateContextMenuListener {
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
            RecyclerView recyclerView = (RecyclerView) view.getParent();
            SongAdapterSearch adapterSearch = (SongAdapterSearch) recyclerView.getAdapter();
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
