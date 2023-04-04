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

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;
import androidx.localbroadcastmanager.content.LocalBroadcastManager;

import com.google.android.material.appbar.MaterialToolbar;
import com.ute.project2.constant.Constant;
import com.ute.project2.database.Database;
import com.ute.project2.model.Song;
import com.ute.project2.sharedpreferences.StorageSingleton;

public class SongFragment extends Fragment {
    Song globalSong;
    MaterialToolbar tbTop;
    ImageView ivSongImage;
    boolean isPlaying;
    Context context;
    ImageView ivPrevious;
    ImageView ivPlayPause;
    ImageView ivNext;
    private boolean globalCheck;

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

            String current = StorageSingleton.getString(Constant.CURRENT_SONG_NAME);
            String storage = StorageSingleton.getString(Constant.STORAGE_SONG_NAME);
            globalCheck = current.equals(storage);
            if (storage == null) {
                globalCheck = true;
            }
            if (globalCheck && storage != null) {
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

            if (!globalCheck) {
                context.stopService(intent);
                globalCheck = true;
                if(!isPlaying) {
                    context.stopService(intent);
                }
            }
            sendDataToService(intent);
        }
    };

    private final View.OnClickListener ivNextOnClickListener = view -> changeSong();

    private final View.OnClickListener ivPreviousOnClickListener = view -> changeSong();


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

    private void sendDataToService(Intent intent) {
        if (!isPlaying) {
            ivPlayPause.setImageResource(R.drawable.md_pause_circle);
            intent.putExtra("song", globalSong);
            isPlaying = true;

        } else {
            ivPlayPause.setImageResource(R.drawable.md_play_circle);
            isPlaying = false;
        }
        intent.putExtra("isPlaying", isPlaying);
        context.startService(intent);
    }
}