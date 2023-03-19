package com.ute.project2;

import android.content.res.ColorStateList;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.core.widget.NestedScrollView;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.ute.project2.adapter.ArtistAdapter;
import com.ute.project2.adapter.SongAdapter;
import com.ute.project2.database.Database;
import com.ute.project2.model.Artist;
import com.ute.project2.model.Song;

import java.util.List;

public class FavoriteFragment extends Fragment {
    NestedScrollView nsvFavorite;
    Button btSongFavorite;
    Button btArtistFavorite;
    RecyclerView rcvFavorite;
    boolean choose;
    List<Artist> artistList;
    List<Song> songList;
    ArtistAdapter artistAdapter;
    SongAdapter songAdapter;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        return inflater.inflate(R.layout.fragment_favorite, container, false);
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        choose = true;
        nsvFavorite = view.findViewById(R.id.nsvFavorite);
        btSongFavorite = view.findViewById(R.id.btSongFavorite);
        btArtistFavorite = view.findViewById(R.id.btArtistFavorite);
        rcvFavorite = view.findViewById(R.id.rcvFavorite);
        btSongFavorite.setOnClickListener(btSongFavoriteOnClickListener);
        btArtistFavorite.setOnClickListener(ArtistFavoriteOnClickListener);
        LinearLayoutManager linearLayoutManager = new LinearLayoutManager(getContext());
        rcvFavorite.setLayoutManager(linearLayoutManager);
        rcvFavorite.addItemDecoration(new SongItemDecoration(5));
        setDataForRecycleView(choose);
    }

    private final View.OnClickListener btSongFavoriteOnClickListener = view -> {
        if (!choose) {
            btSongFavorite.setBackgroundTintList(ColorStateList.valueOf(getResources().getColor(R.color.md_theme_dark_onPrimary, requireContext().getTheme())));
            btArtistFavorite.setBackgroundTintList(ColorStateList.valueOf(getResources().getColor(R.color.md_theme_light_primary, requireContext().getTheme())));
            choose = true;
            if (songList != null) {
                songList.clear();
            }
            setDataForRecycleView(true);
        }
    };

    private final View.OnClickListener ArtistFavoriteOnClickListener = view -> {
        if (choose) {
            btSongFavorite.setBackgroundTintList(ColorStateList.valueOf(getResources().getColor(R.color.md_theme_light_primary, requireContext().getTheme())));
            btArtistFavorite.setBackgroundTintList(ColorStateList.valueOf(getResources().getColor(R.color.md_theme_dark_onPrimary, requireContext().getTheme())));
            choose = false;
            if (artistList != null) {
                artistList.clear();
            }
            setDataForRecycleView(false);
        }
    };

    private void setDataForRecycleView(boolean flag) {
        if (flag) {
            songList = Database.getSongList();
            songAdapter = new SongAdapter(getContext(), songList);
            rcvFavorite.setAdapter(songAdapter);

        } else {
            artistList = Database.getArtistList();
            artistAdapter = new ArtistAdapter(getContext(), artistList);

            rcvFavorite.setAdapter(artistAdapter);
        }
    }
}