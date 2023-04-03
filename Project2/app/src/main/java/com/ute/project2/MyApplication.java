package com.ute.project2;

import static com.ute.project2.constant.Constant.CHANEL_ID;

import android.app.Application;
import android.app.NotificationChannel;
import android.app.NotificationManager;

public class MyApplication extends Application {
    @Override
    public void onCreate() {
        super.onCreate();
        createNotificationChanel();
    }
    private void createNotificationChanel() {
        CharSequence name = getString(R.string.chanel_name);
        int importance = NotificationManager.IMPORTANCE_DEFAULT;
        NotificationChannel channel = new NotificationChannel(CHANEL_ID, name, importance);
        channel.setSound(null, null);
        NotificationManager manager = getSystemService(NotificationManager.class);
        manager.createNotificationChannel(channel);
    }
}
