package com.ute.project2;

import android.content.Context;
import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.ute.project2.adapter.SongAdapter;
import com.ute.project2.constant.Constant;
import com.ute.project2.database.Database;
import com.ute.project2.decoration.ItemDecoration;
import com.ute.project2.event.OnViewClickListener;
import com.ute.project2.event.SelectSongListener;
import com.ute.project2.model.Song;
import com.ute.project2.sharedpreferences.StorageSingleton;

import java.util.List;

public class FavoriteFragment extends Fragment implements SelectSongListener {
    private OnViewClickListener onViewClickListener;
    TextView tvFavorite;
    RecyclerView recyclerView;
    SongAdapter adapter;
    List<Song> songList;

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
        View view = inflater.inflate(R.layout.fragment_favorite, container, false);
        tvFavorite = view.findViewById(R.id.text_view_favorite);
        recyclerView = view.findViewById(R.id.recycler_view_favorite);

        LinearLayoutManager linearLayoutManager = new LinearLayoutManager(getContext());
        recyclerView.setLayoutManager(linearLayoutManager);
        recyclerView.addItemDecoration(new ItemDecoration(22));
        songList = Database.getSongListFavorite();
        if (songList.isEmpty()) {
            tvFavorite.setVisibility(View.VISIBLE);
            recyclerView.setVisibility(View.GONE);
        } else {
            setSongAdapter();
        }
        return view;
    }

    private void setSongAdapter() {
        recyclerView.setVisibility(View.VISIBLE);
        tvFavorite.setVisibility(View.GONE);
        adapter = new SongAdapter(getContext(), songList, this);
        recyclerView.setAdapter(adapter);
    }

    @Override
    public void onItemClicked(Song song) {
        onViewClickListener.onItemSongClicked(song);
        StorageSingleton.putString(Constant.CURRENT_SONG_NAME, song.getSongName());
    }
}