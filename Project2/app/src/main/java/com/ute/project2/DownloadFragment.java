package com.ute.project2;

import android.content.Context;
import android.os.Bundle;
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
import com.ute.project2.scanner.AudioScanner;
import com.ute.project2.sharedpreferences.StorageSingleton;

import java.util.List;

public class DownloadFragment extends Fragment implements SelectSongListener {
    private OnViewClickListener onViewClickListener;
    TextView tvDownload;
    RecyclerView recyclerView;
    SongAdapterDownload adapter;
    List<Song> songList;
    Context context;

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
        context = getContext();
        View view = inflater.inflate(R.layout.fragment_download, container, false);

        tvDownload = view.findViewById(R.id.text_view_download);
        recyclerView = view.findViewById(R.id.recycler_view_download);

        LinearLayoutManager linearLayoutManager = new LinearLayoutManager(context);
        recyclerView.setLayoutManager(linearLayoutManager);
        recyclerView.addItemDecoration(new ItemDecoration(22));

        initialRecyclerView();

        return view;
    }

    private void initialRecyclerView() {
        songList = AudioScanner.scanAudioFiles(context);
        if (songList.isEmpty()) {
            tvDownload.setVisibility(View.VISIBLE);
            recyclerView.setVisibility(View.GONE);
        } else {
            setSongAdapter();
        }
    }

    private void setSongAdapter() {
        recyclerView.setVisibility(View.VISIBLE);
        tvDownload.setVisibility(View.GONE);
        adapter = new SongAdapterDownload(context, songList, this);
        recyclerView.setAdapter(adapter);
    }


    @Override
    public void onItemClicked(Song song) {
        onViewClickListener.onItemSongClicked(song);
        StorageSingleton.putString(Constant.CURRENT_SONG_NAME, song.getSongName());
    }

}