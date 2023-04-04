package com.ute.project2.sharedpreferences;

import android.content.Context;
import android.content.SharedPreferences;

public class MyStorage {
    private final Context context;

    public MyStorage(Context context) {
        this.context = context;
    }

    public void putString(String key, String value) {
        SharedPreferences sharedPreferences = context.getSharedPreferences(key, Context.MODE_PRIVATE);
        SharedPreferences.Editor editor = sharedPreferences.edit();
        editor.putString(key, value);
        editor.apply();
    }

    public String getString(String key) {
        SharedPreferences sharedPreferences = context.getSharedPreferences(key, Context.MODE_PRIVATE);
        return sharedPreferences.getString(key, null);
    }

}