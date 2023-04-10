package com.ute.project2.adapter;

import android.content.Context;
import android.content.Intent;
import android.os.Environment;
import android.util.Log;
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

import com.google.android.gms.tasks.Task;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;
import com.google.firebase.storage.FirebaseStorage;
import com.google.firebase.storage.ListResult;
import com.google.firebase.storage.StorageReference;
import com.squareup.picasso.Picasso;
import com.ute.project2.R;
import com.ute.project2.constant.Constant;
import com.ute.project2.event.SelectSongListener;
import com.ute.project2.model.Favorite;
import com.ute.project2.model.Song;

import java.io.File;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

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
                    addToFavorite(finalSong.getSongId(), finalSong.getSongName(), view);
                    return true;
                }
                return false;
            });
            contextMenu.findItem(R.id.mnAddToDownload).setOnMenuItemClickListener(menuItem -> {
                if (finalSong != null) {
                    Toast.makeText(view.getContext(), "Download " + "\"" + finalSong.getSongName() + "\"", Toast.LENGTH_SHORT).show();
                    downloadSong(finalSong.getSongSource());
                    return true;
                }
                return false;
            });
            contextMenu.findItem(R.id.mnFavoriteShare).setOnMenuItemClickListener(menuItem -> {
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

    private static void downloadSong(String url) {
        if (url.contains(Constant.FIREBASE_STORAGE)) {
            StorageReference storageReference = FirebaseStorage.getInstance().getReference().child(Constant.SONG_DIRECTORY);
            Task<ListResult> listResultTask = storageReference.listAll();
            listResultTask.addOnSuccessListener(listResult -> {
                for (StorageReference item : listResult.getItems()) {
                    Log.e("ITEM", item.getName());
                    if (url.contains(item.getName())) {
                        StorageReference audio = FirebaseStorage.getInstance().getReference().child(Constant.SONG_SOURCE_LOCATION + item.getName());
                        File localFile = new File(Environment.getExternalStoragePublicDirectory(Environment.DIRECTORY_DOWNLOADS), item.getName());
                        audio.getFile(localFile).addOnSuccessListener(taskSnapshot -> Log.e("DOWNLOAD", "Download successful.")).addOnFailureListener(e -> Log.e("ERROR", e.getMessage()));
                    }
                }
            }).addOnFailureListener(e -> Log.e("FAIL", "addOnFailureListener."));
        }
    }

    private static void addToFavorite(String songId, String songName, View view) {
        DatabaseReference favoriteDatabaseReference = FirebaseDatabase.getInstance().getReference(Constant.ROOT_FAVORITE);
        favoriteDatabaseReference.addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                boolean flag = true;
                for (DataSnapshot dataSnapshot : snapshot.getChildren()) {
                    String favoriteSongId = dataSnapshot.child(Constant.SONG_ID).getValue(String.class);
                    if (favoriteSongId != null) {
                        if (favoriteSongId.equals(songId)) {
                            flag = false;
                            break;
                        }
                    }
                }
                if (flag) {
                    Favorite favorite = new Favorite(String.valueOf(System.currentTimeMillis()), songId);
                    favoriteDatabaseReference.child(favorite.getFavoriteId()).setValue(favorite.getSongId());
                    DatabaseReference databaseReference = FirebaseDatabase.getInstance().getReference(Constant.ROOT_FAVORITE).child(favorite.getFavoriteId());
                    Map<String, String> map = new HashMap<>();
                    map.put(Constant.SONG_ID, favorite.getSongId());
                    databaseReference.setValue(map);
                    Toast.makeText(view.getContext(), "Add " + "\"" + songName + "\"" + " to favorites.", Toast.LENGTH_SHORT).show();

                } else {
                    Toast.makeText(view.getContext(), "\"" + songName + "\"" + " existing in favorites.", Toast.LENGTH_SHORT).show();
                }
            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {

            }
        });

    }
}
