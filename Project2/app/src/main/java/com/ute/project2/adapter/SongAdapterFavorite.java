package com.ute.project2.adapter;

import android.annotation.SuppressLint;
import android.content.Context;
import android.content.Intent;
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

import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;
import com.squareup.picasso.Picasso;
import com.ute.project2.R;
import com.ute.project2.constant.Constant;
import com.ute.project2.event.SelectSongListener;
import com.ute.project2.model.Song;

import java.io.File;
import java.util.List;

public class SongAdapterFavorite extends RecyclerView.Adapter<SongAdapterFavorite.ViewHolder> {
    private final List<Song> songList;
    private final Context context;
    private final SelectSongListener listener;

    public void removeSong(int position) {
        songList.remove(position);
        notifyItemRemoved(position);
        notifyItemRangeChanged(position, getItemCount());
    }

    public SongAdapterFavorite(Context context, List<Song> songList, SelectSongListener listener) {
        this.songList = songList;
        this.context = context;
        this.listener = listener;
    }

    @NonNull
    @Override
    public SongAdapterFavorite.ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(context).inflate(R.layout.card_song_item, parent, false);
        return new SongAdapterFavorite.ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull SongAdapterFavorite.ViewHolder holder, int position) {
        Song song = songList.get(position);
        File cacheDir = context.getCacheDir();
        new File(cacheDir, "favorite-image-picasso-cache");
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
            SongAdapterFavorite adapterSearch = (SongAdapterFavorite) recyclerView.getAdapter();
            int position = recyclerView.getChildAdapterPosition(view);
            Song song = null;
            if (adapterSearch != null) {
                song = adapterSearch.songList.get(position);
            }
            MenuInflater inflater = new MenuInflater(view.getContext());
            inflater.inflate(R.menu.menu_context_favorite, contextMenu);
            Song finalSong = song;
            contextMenu.findItem(R.id.mnRemoveFavorite).setOnMenuItemClickListener(menuItem -> {
                if (finalSong != null) {
                    removeFavorite(finalSong.getSongId(), finalSong.getSongName(), view);
                    adapterSearch.removeSong(position);
                    return true;
                }
                return false;
            });
            contextMenu.findItem(R.id.mn_favorite_favorite).setOnMenuItemClickListener(menuItem -> {
                if (finalSong != null) {
                    String url = finalSong.getSongSource();
                    Intent shareIntent = new Intent(Intent.ACTION_SEND);
                    shareIntent.setType("text/plain");
                    shareIntent.putExtra(Intent.EXTRA_SUBJECT, finalSong.getSongName());
                    shareIntent.putExtra(Intent.EXTRA_TEXT, url);
                    adapterSearch.context.startActivity(Intent.createChooser(shareIntent, "Share music link."));
                }
                return false;
            });
        }
    }

    private static void removeFavorite(String songId, String songName, View view) {
        DatabaseReference favoriteDatabaseReference = FirebaseDatabase.getInstance().getReference(Constant.ROOT_FAVORITE);
        favoriteDatabaseReference.addListenerForSingleValueEvent(new ValueEventListener() {
            @SuppressLint("NotifyDataSetChanged")
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                for (DataSnapshot dataSnapshot : snapshot.getChildren()) {
                    String favoriteId = dataSnapshot.getKey();
                    String favoriteSongId = dataSnapshot.child(Constant.SONG_ID).getValue(String.class);
                    if (favoriteSongId != null && favoriteId != null) {
                        if (favoriteSongId.equals(songId)) {
                            favoriteDatabaseReference.child(favoriteId).removeValue();
                            Toast.makeText(view.getContext(), "Remove " + "\"" + songName + "\"" + " from favorites.", Toast.LENGTH_SHORT).show();
                        } else {
                            Toast.makeText(view.getContext(), "Remove " + "\"" + songName + "\"" + " fail.", Toast.LENGTH_SHORT).show();
                        }
                    }
                }
            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {
            }
        });

    }
}
