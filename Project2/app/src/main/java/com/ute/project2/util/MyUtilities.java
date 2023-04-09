package com.ute.project2.util;

import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.Locale;

public class MyUtilities {
    public static String formatTime(int time) {
        DateFormat formatter = new SimpleDateFormat("mm:ss", Locale.getDefault());
        return formatter.format(time);
    }

}
