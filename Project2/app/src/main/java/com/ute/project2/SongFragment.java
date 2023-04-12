package com.ute.project2;

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
import android.media.MediaPlayer;
import android.net.Uri;
import android.os.Bundle;
import android.os.Environment;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.SeekBar;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;
import androidx.localbroadcastmanager.content.LocalBroadcastManager;

import com.google.android.gms.tasks.Task;
import com.google.android.material.appbar.MaterialToolbar;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;
import com.google.firebase.storage.FirebaseStorage;
import com.google.firebase.storage.ListResult;
import com.google.firebase.storage.StorageReference;
import com.google.gson.Gson;
import com.squareup.picasso.Picasso;
import com.ute.project2.constant.Constant;
import com.ute.project2.model.Artist;
import com.ute.project2.model.Favorite;
import com.ute.project2.model.Song;
import com.ute.project2.sharedpreferences.StorageSingleton;
import com.ute.project2.util.MyUtilities;

import java.io.File;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;


public class SongFragment extends Fragment {
    private Song globalSong;
    private MaterialToolbar tbTop;
    private ImageView ivSongImage;
    private boolean isPlaying;
    private Context context;
    private ImageView ivPlayPause;
    private boolean globalCheck;
    private TextView tvDuration;
    private TextView tvCurrent;
    private SeekBar seekBar;
    int duration;
    private static final List<Song> songList = new ArrayList<>();
    private static final List<Artist> artistList = new ArrayList<>();
    private View view;

    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        view = inflater.inflate(R.layout.fragment_song, container, false);
        context = getContext();
        loadDataArtist();
        loadDataSong();
        initialViewPart1();
        Bundle bundle = getArguments();
        if (bundle != null) {
            globalSong = (Song) bundle.get("song");
            isPlaying = bundle.getBoolean("isPlaying", false);
            String current = StorageSingleton.getString(Constant.CURRENT_SONG_NAME);
            String storage = StorageSingleton.getString(Constant.STORAGE_SONG_NAME);
            globalCheck = current.equals(storage);
            setDurationView(globalSong);
            if (storage == null) {
                globalCheck = true;
            }
            if (globalCheck && storage != null) {
                isPlaying = true;
            }
            if (isPlaying) {
                ivPlayPause.setImageResource(R.drawable.md_pause);
            }
        }
        if (context != null) {
            LocalBroadcastManager.getInstance(context).registerReceiver(receiver, new IntentFilter(Constant.ACTION));
            LocalBroadcastManager.getInstance(context).registerReceiver(mReceiver, new IntentFilter(Constant.ACTION_SEEKBAR_UPDATE));
        }
        initialViewPart2();
        return view;
    }

    private void setDurationView(Song song) {
        if (song != null) {
            try {
                duration = MediaPlayer.create(context, Uri.parse(song.getSongSource())).getDuration();
                seekBar.setMax(duration);
                tvDuration.setText(song.getDuration());
            } catch (Exception e) {
                e.printStackTrace();
            }
        }
    }

    private void initialViewPart1() {
        tvDuration = view.findViewById(R.id.text_view_duration);
        tvCurrent = view.findViewById(R.id.text_view_current);

        seekBar = view.findViewById(R.id.seek_bar);

        ImageView ivDownload = view.findViewById(R.id.ivDownload);
        ivDownload.setOnClickListener(ivDownloadOnClickListener);

        ImageView ivFavorite = view.findViewById(R.id.ivFavorite);
        ivFavorite.setOnClickListener(ivFavoriteOnClickListener);

        ImageView ivPrevious = view.findViewById(R.id.ivPrevious);
        ivPrevious.setOnClickListener(ivPreviousOnClickListener);

        ivPlayPause = view.findViewById(R.id.ivPlayPause);
        ivPlayPause.setOnClickListener(ivPlayPauseOnClickListener);

        ImageView ivNext = view.findViewById(R.id.ivNext);
        ivNext.setOnClickListener(ivNextOnClickListener);
    }

    private void initialViewPart2() {
        tbTop = view.findViewById(R.id.tbTop);
        tbTop.setTitle(globalSong.getSongName());
        tbTop.setSubtitle(globalSong.getArtist());

        ivSongImage = view.findViewById(R.id.ivSongImage);
        File cacheDir = context.getCacheDir();
        new File(cacheDir, "song-image-picasso-cache");
        Picasso.get().load(globalSong.getSongImage()).into(ivSongImage);
    }

    private void loadDataArtist() {
        DatabaseReference artistDatabaseReference = FirebaseDatabase.getInstance().getReference().child(Constant.ROOT_ARTIST);
        artistDatabaseReference.addListenerForSingleValueEvent(artistValueEventListener);
    }

    private void loadDataSong() {
        DatabaseReference songDatabaseReference = FirebaseDatabase.getInstance().getReference().child(Constant.ROOT_SONG);
        songDatabaseReference.addListenerForSingleValueEvent(songValueEventListener);
    }

    private final ValueEventListener songValueEventListener = new ValueEventListener() {
        @Override
        public void onDataChange(@NonNull DataSnapshot snapshot) {
            if (songList.isEmpty()) {
                for (DataSnapshot dataSnapshot : snapshot.getChildren()) {
                    String genreId = dataSnapshot.child(Constant.CHILD_SONG_GENRE_ID).getValue(String.class);
                    String songId = dataSnapshot.getKey();
                    String songName = dataSnapshot.child(Constant.CHILD_SONG_NAME).getValue(String.class);
                    String songImage = dataSnapshot.child(Constant.CHILD_SONG_IMAGE).getValue(String.class);
                    String songSource = dataSnapshot.child(Constant.CHILD_SONG_SOURCE).getValue(String.class);
                    String artistsId = dataSnapshot.child(Constant.CHILD_SONG_ARTIST_ID).getValue(String.class);
                    String duration = dataSnapshot.child(Constant.CHILD_DURATION).getValue(String.class);
                    Song song = new Song(songId, songName, songImage, songSource, genreId, artistsId, duration);
                    for (Artist artist : artistList) {
                        if (artist.getArtistId().equals(artistsId)) {
                            song.setArtist(artist.getArtistName());
                        }
                    }
                    songList.add(song);
                }
            }
        }

        @Override
        public void onCancelled(@NonNull DatabaseError error) {

        }
    };

    private final ValueEventListener artistValueEventListener = new ValueEventListener() {
        @Override
        public void onDataChange(@NonNull DataSnapshot snapshot) {
            if (artistList.isEmpty()) {
                for (DataSnapshot dataSnapshot : snapshot.getChildren()) {
                    String artistId = dataSnapshot.getKey();
                    String artistName = dataSnapshot.child(Constant.CHILD_ARTIST_NAME).getValue(String.class);
                    String artistImage = dataSnapshot.child(Constant.CHILD_ARTIST_NAME).getValue(String.class);
                    Artist artist = new Artist(artistId, artistName, artistImage);
                    artistList.add(artist);
                }
            }
        }

        @Override
        public void onCancelled(@NonNull DatabaseError error) {
        }
    };

    private final SeekBar.OnSeekBarChangeListener onSeekBarChangeListener = new SeekBar.OnSeekBarChangeListener() {
        @Override
        public void onProgressChanged(SeekBar seekBar, int i, boolean b) {
            if (i == seekBar.getMax()) {
                ivPlayPause.setImageResource(R.drawable.md_replay);
                isPlaying = false;
            }
        }

        @Override
        public void onStartTrackingTouch(SeekBar seekBar) {

        }

        @Override
        public void onStopTrackingTouch(SeekBar seekBar) {
            Intent intent = new Intent(Constant.ACTION_MEDIA_PLAYER_UPDATE);
            intent.putExtra("seekbarValue", seekBar.getProgress());
            LocalBroadcastManager.getInstance(context).sendBroadcast(intent);
        }
    };


    private final BroadcastReceiver receiver = new BroadcastReceiver() {
        @Override
        public void onReceive(Context context, Intent intent) {
            isPlaying = intent.getBooleanExtra("isPlaying", false);
            Song song = (Song) intent.getSerializableExtra("song");
            if (!globalSong.equals(song)) {
                globalSong = song;
                tbTop.setTitle(globalSong.getSongName());
                tbTop.setSubtitle(globalSong.getArtist());
                File cacheDir = context.getCacheDir();
                new File(cacheDir, "song-image-picasso-cache");
                Picasso.get().load(globalSong.getSongImage()).into(ivSongImage);
            }
            if (isPlaying) {
                ivPlayPause.setImageResource(R.drawable.md_play);
                isPlaying = false;
            } else {
                ivPlayPause.setImageResource(R.drawable.md_pause);
                isPlaying = true;
            }
        }
    };

    private final BroadcastReceiver mReceiver = new BroadcastReceiver() {
        @Override
        public void onReceive(Context context, Intent intent) {
            if (globalCheck) {
                int currentPosition = intent.getIntExtra("currentPosition", 0);
                seekBar.setProgress(currentPosition);
                tvCurrent.setText(MyUtilities.formatTime(currentPosition));
                Log.e("TIME", MyUtilities.formatTime(currentPosition));
            }
        }
    };


    private final View.OnClickListener ivPlayPauseOnClickListener = new View.OnClickListener() {
        @Override
        public void onClick(View view) {
            Intent intent = new Intent(context, MyService.class);
            if (!globalCheck) {
                context.stopService(intent);
                globalCheck = true;
                if (!isPlaying) {
                    context.stopService(intent);
                }
            }
            sendDataToService(intent);
        }
    };

    private final View.OnClickListener ivNextOnClickListener = view -> {
        resetValue();
        Song song = MyUtilities.nextSong(songList, globalSong);
        changeSong(song);
        setDurationView(song);

    };

    private void resetValue() {
        seekBar.setProgress(0);
        tvCurrent.setText(R.string._00_00);
    }

    private final View.OnClickListener ivPreviousOnClickListener = view -> {
        resetValue();
        Song song = MyUtilities.previousSong(songList, globalSong);
        changeSong(song);
        setDurationView(song);
    };

    private final View.OnClickListener ivDownloadOnClickListener = view -> {
        Toast.makeText(context, "Download " + "\"" + globalSong.getSongName() + ".\"", Toast.LENGTH_SHORT).show();
        downloadSong(globalSong.getSongSource());
    };

    private final View.OnClickListener ivFavoriteOnClickListener = view -> addToFavorite(globalSong.getSongId(), globalSong.getSongName());

    private void addToFavorite(String songId, String songName) {
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
                    Toast.makeText(context, "Add " + "\"" + songName + "\"" + " to favorites.", Toast.LENGTH_SHORT).show();

                } else {
                    Toast.makeText(context, "\"" + songName + "\"" + " existing in favorites.", Toast.LENGTH_SHORT).show();
                }
            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {

            }
        });

    }

    private void downloadSong(String url) {
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


    @Override
    public void onDestroy() {
        super.onDestroy();
        if (getActivity() != null) {
            if (getActivity().isFinishing() && !isPlaying) {
                LocalBroadcastManager.getInstance(context).unregisterReceiver(receiver);
                eventOnDestroy();
            }
        }
    }

    private void changeSong(Song song) {
        Intent intent = new Intent(context, MyService.class);
        context.stopService(intent);
        globalSong = song;
        tbTop.setTitle(globalSong.getSongName());
        tbTop.setSubtitle(globalSong.getArtist());
        File cacheDir = context.getCacheDir();
        new File(cacheDir, "song-image-picasso-cache");
        Picasso.get().load(globalSong.getSongImage()).into(ivSongImage);
        intent.putExtra("song", globalSong);
        intent.putExtra("isPlaying", true);
        Gson gson = new Gson();
        String json = gson.toJson(songList);
        intent.putExtra("json", json);
        ivPlayPause.setImageResource(R.drawable.md_pause);
        context.startService(intent);
    }


    private void eventOnDestroy() {
        Intent intent = new Intent(context, MyService.class);
        context.stopService(intent);
    }

    private void sendDataToService(Intent intent) {
        if (!isPlaying) {
            ivPlayPause.setImageResource(R.drawable.md_pause);
            intent.putExtra("song", globalSong);
            isPlaying = true;
            seekBar.setOnSeekBarChangeListener(onSeekBarChangeListener);
        } else {
            ivPlayPause.setImageResource(R.drawable.md_play);
            isPlaying = false;
        }
        Gson gson = new Gson();
        String json = gson.toJson(songList);
        intent.putExtra("json", json);
        intent.putExtra("isPlaying", isPlaying);
        context.startService(intent);
    }
}