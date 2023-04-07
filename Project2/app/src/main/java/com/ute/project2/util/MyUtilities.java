package com.ute.project2.util;

import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.Locale;

public class MyUtilities {
    public static String formatTime(int time) {
        DateFormat formatter = new SimpleDateFormat("mm:ss", Locale.getDefault());
        return formatter.format(time);
    }

    public static int timeToInt(String time) {
        String[] timeParts = time.split(":");
        int minus = Integer.parseInt(timeParts[0]);
        int seconds = Integer.parseInt(timeParts[1]);
        return (minus * 60) + seconds;
    }
}
