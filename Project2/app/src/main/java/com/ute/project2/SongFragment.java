package com.ute.project2;

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
import android.media.MediaPlayer;
import android.net.Uri;
import android.os.Bundle;
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

import com.google.android.material.appbar.MaterialToolbar;
import com.ute.project2.constant.Constant;
import com.ute.project2.database.Database;
import com.ute.project2.model.Song;
import com.ute.project2.sharedpreferences.StorageSingleton;
import com.ute.project2.util.MyUtilities;


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
    ImageView ivDownload;
    ImageView ivFavorite;
    TextView tvDuration;
    TextView tvCurrent;
    SeekBar seekBar;
    int duration;


    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {

        Bundle bundle = getArguments();
        context = getContext();
        View view = inflater.inflate(R.layout.fragment_song, container, false);

        tvDuration = view.findViewById(R.id.text_view_duration);
        tvCurrent = view.findViewById(R.id.text_view_current);

        seekBar = view.findViewById(R.id.seek_bar);

        ivDownload = view.findViewById(R.id.ivDownload);
        ivDownload.setOnClickListener(ivDownloadOnClickListener);

        ivFavorite = view.findViewById(R.id.ivFavorite);
        ivFavorite.setOnClickListener(ivFavoriteOnClickListener);


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
            if (globalSong != null) {
                duration = MediaPlayer.create(context, Uri.parse(globalSong.getSongSource())).getDuration();
                tvDuration.setText(globalSong.getDuration());
                seekBar.setMax(duration);
            }
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
            LocalBroadcastManager.getInstance(context).registerReceiver(mReceiver, new IntentFilter(Constant.ACTION_SEEKBAR_UPDATE));
        }

        tbTop = view.findViewById(R.id.tbTop);
        tbTop.setTitle(globalSong.getSongName());
        tbTop.setSubtitle(globalSong.getArtistsName());

        ivSongImage = view.findViewById(R.id.ivSongImage);
        ivSongImage.setImageResource(globalSong.getSongImage());
        return view;
    }

    private final SeekBar.OnSeekBarChangeListener onSeekBarChangeListener = new SeekBar.OnSeekBarChangeListener() {
        @Override
        public void onProgressChanged(SeekBar seekBar, int i, boolean b) {
            if(i == seekBar.getMax()) {
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

    private final BroadcastReceiver mReceiver = new BroadcastReceiver() {
        @Override
        public void onReceive(Context context, Intent intent) {
            if (globalCheck) {
                int currentPosition = intent.getIntExtra("currentPosition", 0);
                seekBar.setProgress(currentPosition);
                tvCurrent.setText(MyUtilities.formatTime(currentPosition));
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

    private final View.OnClickListener ivNextOnClickListener = view -> changeSong();

    private final View.OnClickListener ivPreviousOnClickListener = view -> changeSong();

    private final View.OnClickListener ivDownloadOnClickListener = view -> Toast.makeText(context, "Download " + "\"" + globalSong.getSongName() + "\"", Toast.LENGTH_SHORT).show();

    private final View.OnClickListener ivFavoriteOnClickListener = view -> Toast.makeText(context, "Add " + "\"" + globalSong.getSongName() + "\"" + " to favorites", Toast.LENGTH_SHORT).show();


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

        globalSong = Database.getRandomElement(context);

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
            seekBar.setOnSeekBarChangeListener(onSeekBarChangeListener);
        } else {
            ivPlayPause.setImageResource(R.drawable.md_play_circle);
            isPlaying = false;
        }
        intent.putExtra("isPlaying", isPlaying);
        context.startService(intent);
    }


}