package com.ute.project2.sharedpreferences;

import android.content.Context;

public class StorageSingleton {
    private static StorageSingleton instance;
    private MyStorage myStorage;

    public static void initial(Context context) {
        instance = new StorageSingleton();
        instance.myStorage = new MyStorage(context);
    }

    public static StorageSingleton getInstance() {
        if (instance == null) {
            instance = new StorageSingleton();
        }
        return instance;
    }

    public static void putSong(String key, String value) {
        StorageSingleton.getInstance().myStorage.putString(key, value);
    }

    public static String getSong(String key) {
       return StorageSingleton.getInstance().myStorage.getString(key);
    }
//region
//    public static void putBoolean(String key, boolean value) {
//        StorageSingleton.getInstance().myStorage.putBoolean(key, value);
//    }
//
//    public static boolean getBoolean(String key) {
//        return StorageSingleton.getInstance().myStorage.getBoolean(key);
//    }
//    endregion
}
