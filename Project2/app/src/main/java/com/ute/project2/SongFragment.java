package com.ute.project2;

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;
import androidx.localbroadcastmanager.content.LocalBroadcastManager;

import com.google.android.material.appbar.MaterialToolbar;
import com.ute.project2.constant.Constant;
import com.ute.project2.database.Database;
import com.ute.project2.model.Song;
import com.ute.project2.sharedpreferences.StorageSingleton;

import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.Locale;

public class SongFragment extends Fragment {
    Song globalSong;
    MaterialToolbar tbTop;
    ImageView ivSongImage;
    TextView tvEnd;
    boolean isPlaying;
    Context context;
    ImageView ivPrevious;
    ImageView ivPlayPause;
    ImageView ivNext;
    private String songName;


    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        Bundle bundle = getArguments();
        context = getContext();

        View view = inflater.inflate(R.layout.fragment_song, container, false);

        ivPrevious = view.findViewById(R.id.ivPrevious);
        ivPrevious.setOnClickListener(ivPreviousOnClickListener);

        ivPlayPause = view.findViewById(R.id.ivPlayPause);
        ivPlayPause.setOnClickListener(ivPlayPauseOnClickListener);

        ivNext = view.findViewById(R.id.ivNext);
        ivNext.setOnClickListener(ivNextOnClickListener);

        if (bundle != null) {
            globalSong = (Song) bundle.get("song");
            isPlaying = bundle.getBoolean("isPlaying", false);

            songName = StorageSingleton.getSong(Constant.KEY_SONG);
            Toast.makeText(context, "Storage: " + songName + ", Card: " + globalSong.getSongName(), Toast.LENGTH_SHORT).show();
            if (globalSong.getSongName().equals(songName)) {
                isPlaying = true;
            }
            if (isPlaying) {
                ivPlayPause.setImageResource(R.drawable.md_pause_circle);
            }
        }
        if (context != null) {
            LocalBroadcastManager.getInstance(context).registerReceiver(receiver, new IntentFilter(Constant.ACTION));
        }

        tbTop = view.findViewById(R.id.tbTop);
        tbTop.setTitle(globalSong.getSongName());
        tbTop.setSubtitle(globalSong.getArtistsName());

        ivSongImage = view.findViewById(R.id.ivSongImage);
        ivSongImage.setImageResource(globalSong.getSongImage());

        tvEnd = view.findViewById(R.id.tvEnd);
        DateFormat formatter = new SimpleDateFormat("mm:ss", Locale.getDefault());
        String time = formatter.format(new Date(globalSong.getSongTime()));
        tvEnd.setText(time);

        return view;
    }

    private final BroadcastReceiver receiver = new BroadcastReceiver() {
        @Override
        public void onReceive(Context context, Intent intent) {
            isPlaying = intent.getBooleanExtra("isPlaying", false);
            Song song = (Song) intent.getSerializableExtra("song");
            if (!globalSong.equals(song)) {
                globalSong = song;
                tbTop.setTitle(globalSong.getSongName());
                tbTop.setSubtitle(globalSong.getArtistsName());
                ivSongImage.setImageResource(globalSong.getSongImage());
            }
            if (isPlaying) {
                ivPlayPause.setImageResource(R.drawable.md_play_circle);
                isPlaying = false;
            } else {
                ivPlayPause.setImageResource(R.drawable.md_pause_circle);
                isPlaying = true;
            }
        }
    };


    private final View.OnClickListener ivPlayPauseOnClickListener = new View.OnClickListener() {
        @Override
        public void onClick(View view) {
            Intent intent = new Intent(context, MyService.class);
            if (!songName.equals("N/A") && !songName.equals(tbTop.getSubtitle().toString())) {
                context.stopService(intent);
            }
            if (!isPlaying) {
                ivPlayPause.setImageResource(R.drawable.md_pause_circle);
                intent.putExtra("song", globalSong);
                putData(globalSong);
                isPlaying = true;
            } else {
                ivPlayPause.setImageResource(R.drawable.md_play_circle);
                isPlaying = false;
            }
            intent.putExtra("isPlaying", isPlaying);
            context.startService(intent);

        }
    };

    private final View.OnClickListener ivNextOnClickListener = view -> changeSong();

    private final View.OnClickListener ivPreviousOnClickListener = view -> changeSong();


    @Override
    public void onDestroy() {
        super.onDestroy();
        if (getActivity() != null) {
            if (getActivity().isFinishing() && !isPlaying && songName.equals("N/A")) {
                LocalBroadcastManager.getInstance(context).unregisterReceiver(receiver);
                eventOnDestroy();
            }
        }
    }

    @Override
    public void onDetach() {
        super.onDetach();
        if (!isPlaying && songName.equals("N/A")) {
            eventOnDestroy();
        }
    }

    private void changeSong() {
        Intent intent = new Intent(context, MyService.class);
        context.stopService(intent);

        globalSong = Database.getRandomElement();

        tbTop.setTitle(globalSong.getSongName());
        tbTop.setSubtitle(globalSong.getArtistsName());
        ivSongImage.setImageResource(globalSong.getSongImage());

        intent.putExtra("song", globalSong);
        intent.putExtra("isPlaying", isPlaying);
        context.startService(intent);
    }

    private void eventOnDestroy() {
        Intent intent = new Intent(context, MyService.class);
        context.stopService(intent);
    }

    private void putData(Song song) {
        StorageSingleton.putSong(Constant.KEY_SONG, song.getSongName());
    }
//    region
//    private void putData(Song song, boolean flag) {
//        StorageSingleton.putSong(Constant.KEY_SONG, song);
//        StorageSingleton.putBoolean(Constant.KEY_BOOLEAN, flag);
//    }
//    endregion
}