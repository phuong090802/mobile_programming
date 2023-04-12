package com.ute.project2;

import android.content.Context;
import android.database.Cursor;
import android.media.MediaScannerConnection;
import android.os.Bundle;
import android.os.Environment;
import android.provider.MediaStore;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.ute.project2.adapter.SongAdapterDownload;
import com.ute.project2.constant.Constant;
import com.ute.project2.decoration.ItemDecoration;
import com.ute.project2.event.OnViewClickListener;
import com.ute.project2.event.SelectSongListener;
import com.ute.project2.model.Song;
import com.ute.project2.sharedpreferences.StorageSingleton;
import com.ute.project2.util.MyUtilities;

import java.io.File;
import java.util.ArrayList;
import java.util.List;

public class DownloadFragment extends Fragment implements SelectSongListener {
    private OnViewClickListener onViewClickListener;
    private List<Song> songList;
    private Context context;
    private View view;

    @Override
    public void onAttach(@NonNull Context context) {
        super.onAttach(context);
        if (context instanceof OnViewClickListener) {
            onViewClickListener = (OnViewClickListener) context;
        }
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        view = inflater.inflate(R.layout.fragment_download, container, false);
        context = getContext();
        songList = scanAudioFiles();
        initializeView();
        return view;
    }

    private void initializeView() {
        if (view != null) {
            TextView tvDownload = view.findViewById(R.id.text_view_download);
            RecyclerView recyclerView = view.findViewById(R.id.recycler_view_download);
            LinearLayoutManager linearLayoutManager = new LinearLayoutManager(context);
            recyclerView.setLayoutManager(linearLayoutManager);
            recyclerView.addItemDecoration(new ItemDecoration(22));
            recyclerView.setVisibility(View.VISIBLE);
            tvDownload.setVisibility(View.GONE);
            SongAdapterDownload songAdapterDownload = new SongAdapterDownload(context, songList, this);
            recyclerView.setAdapter(songAdapterDownload);
            if (songList.isEmpty()) {
                tvDownload.setVisibility(View.VISIBLE);
                recyclerView.setVisibility(View.GONE);
            }
        }
    }


    public List<Song> scanAudioFiles() {

        List<Song> songs = new ArrayList<>();
        String[] projection = {
                MediaStore.Audio.Media._ID,
                MediaStore.Audio.Media.TITLE,
                MediaStore.Audio.Media.DATA,
                MediaStore.Audio.Media.ARTIST,
                MediaStore.Audio.Media.DURATION
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
                String songId = cursor.getString(cursor.getColumnIndexOrThrow(MediaStore.Audio.Media._ID));
                String songName = cursor.getString(cursor.getColumnIndexOrThrow(MediaStore.Audio.Media.TITLE));
                String songSource = "file://" + cursor.getString(cursor.getColumnIndexOrThrow(MediaStore.Audio.Media.DATA));
                String artistsName = cursor.getString(cursor.getColumnIndexOrThrow(MediaStore.Audio.Media.ARTIST));
                int duration = cursor.getInt(cursor.getColumnIndexOrThrow(MediaStore.Audio.Media.DURATION));
                String genreName = "";
                MediaScannerConnection.scanFile(context, new String[]{songSource}, null, (s, uri) -> {
                    if (uri != null) {
                        Log.e("onScanCompleted", s + " | " + uri);
                    }
                });
                Cursor genreCursor = context.getContentResolver().query(MediaStore.Audio.Genres.EXTERNAL_CONTENT_URI, new String[]{MediaStore.Audio.Genres.NAME}, null, null, null);
                if (genreCursor != null && genreCursor.moveToFirst()) {
                    genreName = genreCursor.getString(genreCursor.getColumnIndexOrThrow(MediaStore.Audio.Genres.NAME));
                    genreCursor.close();
                }
                File file = new File(standardPath(songSource));
                Log.e("FILE", file.toString());
                if (file.exists()) {
                    songs.add(new Song(songId, songName, Constant.DEFAULT_IMAGE, songSource, genreName, artistsName, MyUtilities.formatTime(duration)));
                }
                cursor.moveToNext();
            }
            cursor.close();
        }
        File storageDir = Environment.getExternalStorageDirectory();
        scanDirectory(storageDir);
        return songs;
    }

    private static String standardPath(String path) {
        return path.replace("file:", "");
    }

    private void scanDirectory(File dir) {
        if (dir.exists()) {
            File[] files = dir.listFiles();
            if (files != null) {
                for (File file : files) {
                    if (file.isDirectory()) {
                        scanDirectory(file);
                    } else {
                        String path = file.getAbsolutePath();
                        if (path.endsWith(Constant.EXTENSION)) {

                            MediaScannerConnection.scanFile(context, new String[]{path}, null, (s, uri) -> {
                                if (uri != null) {
                                    Log.e("onScanCompleted", s + " | " + uri);
                                }
                            });
                        }
                    }
                }
            }
        }
    }



    @Override
    public void onItemClicked(Song song) {
        onViewClickListener.onItemSongClicked(song);
        StorageSingleton.putString(Constant.CURRENT_SONG_NAME, song.getSongName());
    }
}