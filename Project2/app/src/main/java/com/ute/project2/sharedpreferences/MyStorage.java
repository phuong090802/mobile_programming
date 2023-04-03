package com.ute.project2.sharedpreferences;

import android.content.Context;
import android.content.SharedPreferences;

import com.ute.project2.constant.Constant;

public class MyStorage {
    private final Context context;

    public MyStorage(Context context) {
        this.context = context;
    }

    public void putString(String key, String value) {
        SharedPreferences sharedPreferences = context.getSharedPreferences(Constant.STORAGE_NAME, Context.MODE_PRIVATE);
        SharedPreferences.Editor editor = sharedPreferences.edit();
        editor.putString(key, value);
        editor.apply();
    }

    public String getString(String key) {
        SharedPreferences sharedPreferences = context.getSharedPreferences(Constant.STORAGE_NAME, Context.MODE_PRIVATE);
        return sharedPreferences.getString(key, "");
    }
//region
//    public void putBoolean(String key, boolean value) {
//        SharedPreferences sharedPreferences = context.getSharedPreferences(Constant.STORAGE_NAME, Context.MODE_PRIVATE);
//        SharedPreferences.Editor editor = sharedPreferences.edit();
//        editor.putBoolean(key, value);
//        editor.apply();
//    }
//
//    public boolean getBoolean(String key) {
//        SharedPreferences sharedPreferences = context.getSharedPreferences(Constant.STORAGE_NAME, Context.MODE_PRIVATE);
//        return sharedPreferences.getBoolean(key, false);
//    }
//    endregion
}