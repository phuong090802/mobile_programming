package com.ute.project2.scanner;

import android.content.Context;
import android.database.Cursor;
import android.media.MediaScannerConnection;
import android.os.Environment;
import android.provider.MediaStore;

import com.ute.project2.R;
import com.ute.project2.constant.Constant;
import com.ute.project2.model.Song;

import java.io.File;
import java.util.ArrayList;
import java.util.List;

public class AudioScanner {
    public static List<Song> scanAudioFiles(Context context) {
        List<Song> songs = new ArrayList<>();
        String[] projection = {
                MediaStore.Audio.Media.TITLE,
                MediaStore.Audio.Media.DATA,
                MediaStore.Audio.Media.ARTIST
        };
        Cursor cursor = context.getContentResolver().query(
                MediaStore.Audio.Media.EXTERNAL_CONTENT_URI,
                projection,
                null,
                null,
                null
        );

        if (cursor != null) {
            cursor.moveToFirst();
            while (!cursor.isAfterLast()) {
                String songName = cursor.getString(cursor.getColumnIndexOrThrow(MediaStore.Audio.Media.TITLE));
                String songSource = "file://" + cursor.getString(cursor.getColumnIndexOrThrow(MediaStore.Audio.Media.DATA));
                String artistsName = cursor.getString(cursor.getColumnIndexOrThrow(MediaStore.Audio.Media.ARTIST));
                String genreName = "";
                MediaScannerConnection.scanFile(context, new String[]{songSource}, null, null);
                Cursor genreCursor = context.getContentResolver().query(MediaStore.Audio.Genres.EXTERNAL_CONTENT_URI, new String[]{MediaStore.Audio.Genres.NAME}, null, null, null);
                if (genreCursor != null && genreCursor.moveToFirst()) {
                    genreName = genreCursor.getString(genreCursor.getColumnIndexOrThrow(MediaStore.Audio.Genres.NAME));
                    genreCursor.close();
                }
                songs.add(new Song(songName, R.drawable.song, songSource, genreName, artistsName));
                cursor.moveToNext();
            }
            cursor.close();
        }
        File storageDir = Environment.getExternalStorageDirectory();
        scanDirectory(context, storageDir);
        return songs;
    }

    private static void scanDirectory(Context context, File dir) {
        if (dir.exists()) {
            File[] files = dir.listFiles();
            if (files != null) {
                for (File file : files) {
                    if (file.isDirectory()) {
                        scanDirectory(context, file);
                    } else {
                        String path = file.getAbsolutePath();
                        if (path.endsWith(Constant.EXTENSION)) {
                            MediaScannerConnection.scanFile(context, new String[]{path}, null, null);
                        }
                    }
                }
            }
        }
    }
}
