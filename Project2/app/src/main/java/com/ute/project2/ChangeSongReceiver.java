package com.ute.project2;

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;

import com.ute.project2.model.Song;

public class ChangeSongReceiver extends BroadcastReceiver {
    @Override
    public void onReceive(Context context, Intent intent) {
        Song song = (Song) intent.getSerializableExtra("song");
        boolean isPlaying = intent.getBooleanExtra("isPlaying", true);
        Intent sendToService = new Intent(context, MyService.class);
        sendToService.putExtra("song", song);
        sendToService.putExtra("isPlaying", isPlaying);
        sendToService.putExtra("renew", true);
        context.startService(sendToService);
    }
}
