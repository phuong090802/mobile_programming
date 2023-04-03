package com.ute.project2;

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;

public class MyReceiver extends BroadcastReceiver {
    @Override
    public void onReceive(Context context, Intent intent) {
        boolean isPlaying = intent.getBooleanExtra("isPlaying", true);
        Intent newIntent = new Intent(context, MyService.class);
        newIntent.putExtra("isPlaying", isPlaying);
        context.startService(newIntent);
    }
}
