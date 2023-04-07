package com.ute.project2;

import android.app.Notification;
import android.app.PendingIntent;
import android.app.Service;
import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
import android.graphics.BitmapFactory;
import android.media.MediaPlayer;
import android.net.Uri;
import android.os.IBinder;
import android.widget.RemoteViews;

import androidx.annotation.Nullable;
import androidx.core.app.NotificationCompat;
import androidx.localbroadcastmanager.content.LocalBroadcastManager;

import com.ute.project2.constant.Constant;
import com.ute.project2.database.Database;
import com.ute.project2.model.Song;
import com.ute.project2.sharedpreferences.StorageSingleton;

import java.util.Timer;
import java.util.TimerTask;

public class MyService extends Service {
    private boolean globalIsPlaying;
    private MediaPlayer mediaPlayer;
    private Song globalSong;
    private boolean renew;
    private Timer timer;

    @Nullable
    @Override
    public IBinder onBind(Intent intent) {
        return null;
    }

    @Override
    public int onStartCommand(Intent intent, int flags, int startId) {
        Song song = (Song) intent.getSerializableExtra("song");
        globalIsPlaying = intent.getBooleanExtra("isPlaying", false);
        renew = intent.getBooleanExtra("renew", false);
        if (song != null) {
            LocalBroadcastManager.getInstance(this).registerReceiver(mReceiver, new IntentFilter(Constant.ACTION_MEDIA_PLAYER_UPDATE));
            globalSong = song;
            createMediaPlayer(globalSong);
            sendNotification(globalSong);

        } else {
            if (mediaPlayer != null && globalIsPlaying) {
                mediaPlayer.start();
            }
            if (mediaPlayer != null && !globalIsPlaying) {
                mediaPlayer.pause();
            }
            sendNotification(globalSong);
        }

        return START_NOT_STICKY;
    }

    private final BroadcastReceiver mReceiver = new BroadcastReceiver() {
        @Override
        public void onReceive(Context context, Intent intent) {
            if(mediaPlayer != null) {
                int currentPosition = intent.getIntExtra("seekbarValue", 0);
                mediaPlayer.seekTo(currentPosition);
            }
        }
    };

    private void createMediaPlayer(Song song) {
        if (mediaPlayer == null) {
            mediaPlayer = MediaPlayer.create(this, Uri.parse(song.getSongSource()));
        } else if (renew) {
            sendDataToFragment(song);
            mediaPlayer.release();
            mediaPlayer = null;
            mediaPlayer = MediaPlayer.create(this, Uri.parse(song.getSongSource()));
        }
        StorageSingleton.putString(Constant.STORAGE_SONG_NAME, globalSong.getSongName());
        timer = new Timer();
        timer.scheduleAtFixedRate(new TimerTask() {
            @Override
            public void run() {
                if (mediaPlayer != null) {
                    int currentPosition = mediaPlayer.getCurrentPosition();
                    if (currentPosition == mediaPlayer.getDuration()) {
                        timer.cancel();
                    } else {
                        Intent intent = new Intent(Constant.ACTION_SEEKBAR_UPDATE);
                        intent.putExtra("currentPosition", currentPosition);
                        LocalBroadcastManager.getInstance(getBaseContext()).sendBroadcast(intent);
                    }
                }
            }
        }, 0, 1000);
        mediaPlayer.start();
        globalIsPlaying = true;
    }

    private void sendNotification(Song song) {
        RemoteViews remoteViews = new RemoteViews(getPackageName(), R.layout.custom_notification);

        remoteViews.setTextViewText(R.id.tvNotificationSongName, song.getSongName());
        remoteViews.setTextViewText(R.id.tvNotificationArtistName, song.getArtistsName());
        remoteViews.setImageViewResource(R.id.ivPrevious, R.drawable.md_skip_previous);
        remoteViews.setImageViewResource(R.id.ivPlayPause, R.drawable.md_pause_circle);
        remoteViews.setImageViewResource(R.id.ivNext, R.drawable.md_skip_next);

        Intent intent = new Intent(this, MainActivity.class);
        intent.addFlags(Intent.FLAG_ACTIVITY_REORDER_TO_FRONT | Intent.FLAG_ACTIVITY_CLEAR_TOP);
        intent.putExtra("song", globalSong);
        intent.putExtra("isPlaying", globalIsPlaying);

        remoteViews.setOnClickPendingIntent(R.id.ivPrevious, sendDataToReceiver(this));
        remoteViews.setOnClickPendingIntent(R.id.ivNext, sendDataToReceiver(this));

        PendingIntent pendingIntent = PendingIntent.getActivity(this, Constant.REQUEST_CODE, intent, PendingIntent.FLAG_UPDATE_CURRENT | PendingIntent.FLAG_IMMUTABLE);

        if (globalIsPlaying) {
            remoteViews.setOnClickPendingIntent(R.id.ivPlayPause, sendDataToReceiver(this, false));
            remoteViews.setImageViewResource(R.id.ivPlayPause, R.drawable.md_pause_circle);
            sendDataToFragment(false);

        } else {
            remoteViews.setOnClickPendingIntent(R.id.ivPlayPause, sendDataToReceiver(this, true));
            remoteViews.setImageViewResource(R.id.ivPlayPause, R.drawable.md_play_circle);
            sendDataToFragment(true);
        }
        remoteViews.setImageViewBitmap(R.id.my_image_view, BitmapFactory.decodeResource(getResources(), R.drawable.notification));
        Notification notification = new NotificationCompat.Builder(this, Constant.CHANEL_ID)
                .setSmallIcon(R.drawable.md_music_note)
                .setCustomContentView(remoteViews)
                .setContentIntent(pendingIntent)
                .setAutoCancel(true)
                .setPriority(NotificationCompat.PRIORITY_DEFAULT)
                .build();
        startForeground(Constant.ID_START_FOREGROUND, notification);

    }

    private void sendDataToFragment(boolean flag) {
        Intent intent = new Intent(Constant.ACTION);
        intent.putExtra("song", globalSong);
        intent.putExtra("isPlaying", flag);
        LocalBroadcastManager.getInstance(this).sendBroadcast(intent);
    }

    private void sendDataToFragment(Song song) {
        Intent intent = new Intent(Constant.ACTION);
        intent.putExtra("song", song);
        intent.putExtra("isPlaying", true);
        LocalBroadcastManager.getInstance(this).sendBroadcast(intent);
    }

    private PendingIntent sendDataToReceiver(Context context) {
        Song song = Database.getRandomElement(this);
        Intent intent = new Intent(context, ChangeSongReceiver.class);
        intent.putExtra("song", song);
        intent.putExtra("isPlaying", globalIsPlaying);
        return PendingIntent.getBroadcast(context.getApplicationContext(), Constant.REQUEST_CODE, intent, PendingIntent.FLAG_UPDATE_CURRENT | PendingIntent.FLAG_IMMUTABLE);
    }

    private PendingIntent sendDataToReceiver(Context context, boolean flag) {
        Intent intent = new Intent(context, MyReceiver.class);
        intent.putExtra("isPlaying", flag);
        return PendingIntent.getBroadcast(context.getApplicationContext(), Constant.REQUEST_CODE, intent, PendingIntent.FLAG_UPDATE_CURRENT | PendingIntent.FLAG_IMMUTABLE);
    }

    @Override
    public void onDestroy() {
        super.onDestroy();
        if (mediaPlayer != null) {
            mediaPlayer.release();
            mediaPlayer = null;
        }
        timer.cancel();
        StorageSingleton.putString(Constant.CURRENT_SONG_NAME, null);
        StorageSingleton.putString(Constant.STORAGE_SONG_NAME, null);
    }

}
