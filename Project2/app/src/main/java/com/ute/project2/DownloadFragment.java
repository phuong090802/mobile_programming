package com.ute.project2;

import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.ute.project2.adapter.SongAdapter;
import com.ute.project2.database.Database;
import com.ute.project2.model.Song;

import java.util.List;

public class DownloadFragment extends Fragment {
    RecyclerView rcvDownload;

    SongAdapter songAdapter;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        return inflater.inflate(R.layout.fragment_dowload, container, false);
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        rcvDownload = view.findViewById(R.id.rcvDownload);
        LinearLayoutManager linearLayoutManager = new LinearLayoutManager(getContext());
        rcvDownload.setLayoutManager(linearLayoutManager);
        rcvDownload.addItemDecoration(new SongItemDecoration(15));
        List<Song> songList = Database.getSongListDownload();
        songAdapter = new SongAdapter(getContext(), songList);
        rcvDownload.setAdapter(songAdapter);
    }
}