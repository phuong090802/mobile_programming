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
import android.util.Log;
import android.widget.RemoteViews;

import androidx.annotation.Nullable;
import androidx.core.app.NotificationCompat;
import androidx.localbroadcastmanager.content.LocalBroadcastManager;

import com.google.android.gms.tasks.Task;
import com.google.firebase.storage.FirebaseStorage;
import com.google.firebase.storage.ListResult;
import com.google.firebase.storage.StorageReference;
import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;
import com.ute.project2.constant.Constant;
import com.ute.project2.model.Song;
import com.ute.project2.sharedpreferences.StorageSingleton;

import java.io.File;
import java.io.IOException;
import java.lang.reflect.Type;
import java.util.List;
import java.util.Timer;
import java.util.TimerTask;

public class MyService extends Service {
    private boolean globalIsPlaying;
    private MediaPlayer mediaPlayer;
    private Song globalSong;
    private boolean renew;
    private Timer timer;
    private static List<Song> songList;

    @Nullable
    @Override
    public IBinder onBind(Intent intent) {
        return null;
    }

    @Override
    public int onStartCommand(Intent intent, int flags, int startId) {
        String json = intent.getStringExtra("json");
        if (json != null) {
            Gson gson = new Gson();
            Type type = new TypeToken<List<Song>>() {
            }.getType();
            songList = gson.fromJson(json, type);
        }
        Song song = (Song) intent.getSerializableExtra("song");
        globalIsPlaying = intent.getBooleanExtra("isPlaying", false);
        Log.e("IS PLAYING", globalIsPlaying + "");
        Log.e("NULL", (mediaPlayer == null) + "");
        Log.e("SONG", (song == null) + "");
        renew = intent.getBooleanExtra("renew", false);
        if (song != null) {
            LocalBroadcastManager.getInstance(this).registerReceiver(mReceiver, new IntentFilter(Constant.ACTION_MEDIA_PLAYER_UPDATE));
            globalSong = song;
            createMediaPlayer(globalSong);
            sendNotification(globalSong);

        } else {
            if (mediaPlayer != null && !globalIsPlaying) {
                mediaPlayer.pause();
            }
            sendNotification(globalSong);
        }
        if (mediaPlayer != null && globalIsPlaying) {
            mediaPlayer.start();
        }

        return START_NOT_STICKY;
    }

    private final BroadcastReceiver mReceiver = new BroadcastReceiver() {
        @Override
        public void onReceive(Context context, Intent intent) {
            if (mediaPlayer != null) {
                int currentPosition = intent.getIntExtra("seekbarValue", 0);
                mediaPlayer.seekTo(currentPosition);
            }
        }
    };

    private String getExtension(String fileName) {
        return fileName.substring(fileName.lastIndexOf("."));
    }

    private void handleMediaPlayer(String url) {
        if (url.contains(Constant.FIREBASE_STORAGE)) {
            StorageReference storageReference = FirebaseStorage.getInstance().getReference().child(Constant.SONG_DIRECTORY);
            Task<ListResult> listResultTask = storageReference.listAll();
            listResultTask.addOnSuccessListener(listResult -> {
                for (StorageReference item : listResult.getItems()) {
                    if (url.contains(item.getName())) {
                        StorageReference audio = FirebaseStorage.getInstance().getReference().child(Constant.SONG_SOURCE_LOCATION + item.getName());
                        try {
                            File localFile = File.createTempFile(String.valueOf(System.currentTimeMillis()), getExtension(item.getName()));
                            audio.getFile(localFile).addOnSuccessListener(taskSnapshot -> {
                                mediaPlayer = MediaPlayer.create(this, Uri.parse("file://" + localFile.getAbsolutePath()));
                                mediaPlayer.start();
                                globalIsPlaying = true;
                                sendTime();
                            }).addOnFailureListener(e -> Log.e("ERROR", e.getMessage()));
                        } catch (IOException e) {
                            e.printStackTrace();
                        }
                    }
                }
            }).addOnFailureListener(e -> Log.e("FAIL", "addOnFailureListener."));
        } else {
            mediaPlayer = MediaPlayer.create(this, Uri.parse(url));
            mediaPlayer.start();
            globalIsPlaying = true;
            sendTime();

        }

    }

    private void sendTime() {
        StorageSingleton.putString(Constant.STORAGE_SONG_NAME, globalSong.getSongName());
        timer = new Timer();
        timer.scheduleAtFixedRate(new TimerTask() {
            @Override
            public void run() {
                try {
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
                } catch (Exception e) {
                    e.printStackTrace();
                    stopSelf();
                }
            }
        }, 0, 1000);
    }


    private void createMediaPlayer(Song song) {
        if (mediaPlayer == null) {
            handleMediaPlayer(song.getSongSource());
        } else if (renew) {
            sendDataToFragment(song);
            mediaPlayer.release();
            mediaPlayer = null;
            handleMediaPlayer(song.getSongSource());
        }
    }

    private void sendNotification(Song song) {
        RemoteViews remoteViews = new RemoteViews(getPackageName(), R.layout.custom_notification);

        remoteViews.setTextViewText(R.id.tvNotificationSongName, song.getSongName());
        remoteViews.setTextViewText(R.id.tvNotificationArtistName, song.getArtist());
        remoteViews.setImageViewResource(R.id.ivPrevious, R.drawable.md_previous);
        remoteViews.setImageViewResource(R.id.ivPlayPause, R.drawable.md_pause);
        remoteViews.setImageViewResource(R.id.ivNext, R.drawable.md_next);

        Intent intent = new Intent(this, MainActivity.class);
        intent.putExtra("song", globalSong);
        intent.putExtra("isPlaying", globalIsPlaying);

        remoteViews.setOnClickPendingIntent(R.id.ivPrevious, sendDataToReceiver(this, previousSong()));
        remoteViews.setOnClickPendingIntent(R.id.ivNext, sendDataToReceiver(this, nextSong()));
        remoteViews.setImageViewResource(R.id.ivPlayPause, R.drawable.md_pause);

        PendingIntent pendingIntent = PendingIntent.getActivity(this, Constant.REQUEST_CODE, intent,  PendingIntent.FLAG_UPDATE_CURRENT | PendingIntent.FLAG_CANCEL_CURRENT | PendingIntent.FLAG_IMMUTABLE);

        if (globalIsPlaying) {
            remoteViews.setOnClickPendingIntent(R.id.ivPlayPause, sendDataToReceiver(this, false));
            remoteViews.setImageViewResource(R.id.ivPlayPause, R.drawable.md_pause);
            sendDataToFragment(false);

        } else {
            remoteViews.setOnClickPendingIntent(R.id.ivPlayPause, sendDataToReceiver(this, true));
            remoteViews.setImageViewResource(R.id.ivPlayPause, R.drawable.md_play);
            sendDataToFragment(true);
        }
        remoteViews.setImageViewBitmap(R.id.image_view_notification, BitmapFactory.decodeResource(getResources(), R.drawable.notification_image));
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

    private Song nextSong() {
        int index = 0;
        for (Song song : songList) {
            if (song.getSongId().equals(globalSong.getSongId())) {
                break;
            }
            index++;
        }
        if (index + 1 < songList.size()) {
            index += 1;
        } else {
            index = 0;
        }
        return songList.get(index);
    }

    private Song previousSong() {
        int index = 0;
        for (Song song : songList) {
            if (song.getSongId().equals(globalSong.getSongId())) {
                break;
            }
            index++;
        }
        if (index - 1 > 0) {
            index -= 1;
        } else {
            index = songList.size() - 1;
        }
        return songList.get(index);
    }


    private PendingIntent sendDataToReceiver(Context context, Song song) {
        Intent intent = new Intent(context, ChangeSongReceiver.class);
        intent.putExtra("song", song);
        intent.putExtra("isPlaying", true);
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
        if(timer != null) {
            timer.cancel();
        }
        StorageSingleton.putString(Constant.CURRENT_SONG_NAME, null);
        StorageSingleton.putString(Constant.STORAGE_SONG_NAME, null);
    }

}
