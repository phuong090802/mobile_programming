package com.ute.project2;

import android.content.Intent;
import android.content.res.ColorStateList;
import android.os.Bundle;
import android.text.TextUtils;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.widget.SearchView;
import androidx.core.widget.NestedScrollView;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.google.android.material.button.MaterialButton;
import com.ute.project2.adapter.SongAdapter;
import com.ute.project2.database.Database;
import com.ute.project2.model.Song;

import java.util.List;
import java.util.stream.Collectors;

public class ResultQueryFragment extends Fragment implements SelectSongListener {

    ImageView ivBack;

    SearchView svSearch;

    RecyclerView rcvSong;
    TextView tvPlayWhatYouLove;
    TextView tvSearchFor;
    NestedScrollView nsvChoose;
    boolean choose;
    MaterialButton btSong;
    MaterialButton btArtist;
    SongAdapter songAdapter;

    List<Song> songList;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        return inflater.inflate(R.layout.fragment_result_query, container, false);
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        choose = true;
        super.onViewCreated(view, savedInstanceState);
        svSearch = view.findViewById(R.id.svSearch);
        rcvSong = view.findViewById(R.id.rcvSong);
        btSong = view.findViewById(R.id.btSong);
        btArtist = view.findViewById(R.id.btArtist);
        btSong.setOnClickListener(btSongOnClickListener);
        btArtist.setOnClickListener(btArtistOnClickListener);
        nsvChoose = view.findViewById(R.id.nsvChoose);
        tvPlayWhatYouLove = view.findViewById(R.id.tvPlayWhatYouLove);
        tvSearchFor = view.findViewById(R.id.tvSearchFor);
        LinearLayoutManager linearLayoutManager = new LinearLayoutManager(getContext());
        rcvSong.setLayoutManager(linearLayoutManager);
        svSearch.setOnQueryTextFocusChangeListener(onFocusChangeListener);
        svSearch.setOnQueryTextListener(onQueryTextListener);
        ivBack = view.findViewById(R.id.ivBack);
        ivBack.setOnClickListener(onClickListener);


        rcvSong.addItemDecoration(new SongItemDecoration(22));

    }

    private final View.OnClickListener onClickListener = view -> {
        if (getActivity() != null) {
            getActivity().finish();
        }
    };

    private final View.OnFocusChangeListener onFocusChangeListener = (view, b) -> {
        if (b) {
            ivBack.setVisibility(View.VISIBLE);
        } else {
            ivBack.setVisibility(View.GONE);
        }
    };

    private final SearchView.OnQueryTextListener onQueryTextListener = new SearchView.OnQueryTextListener() {

        @Override
        public boolean onQueryTextSubmit(String query) {
            return false;
        }

        @Override
        public boolean onQueryTextChange(String newText) {
            if (TextUtils.isEmpty(newText)) {
                tvPlayWhatYouLove.setText(R.string.play_what_you_love);
                tvSearchFor.setText(R.string.search_for_artists_songs);
                tvPlayWhatYouLove.setVisibility(View.VISIBLE);
                tvSearchFor.setVisibility(View.VISIBLE);
                rcvSong.setVisibility(View.GONE);
                nsvChoose.setVisibility(View.GONE);
            } else {
                rcvSong.setVisibility(View.VISIBLE);
                nsvChoose.setVisibility(View.VISIBLE);
                tvPlayWhatYouLove.setVisibility(View.GONE);
                tvSearchFor.setVisibility(View.GONE);
                if (choose) {
                    songList = Database.getSongList().stream().filter(song -> song.getSongName().contains(newText)).collect(Collectors.toList());
                } else {
                    songList = Database.getSongList().stream().filter(song -> song.getArtistsName().contains(newText)).collect(Collectors.toList());
                }
                if (songList.isEmpty()) {
                    tvPlayWhatYouLove.setVisibility(View.VISIBLE);
                    tvSearchFor.setVisibility(View.VISIBLE);
                    rcvSong.setVisibility(View.GONE);
                    nsvChoose.setVisibility(View.GONE);
                    StringBuilder builder = new StringBuilder();
                    builder.append("Couldn't find \"").append(newText).append("\"");
                    tvPlayWhatYouLove.setText(builder);
                    tvSearchFor.setText(R.string.try_searching);
                }
                findByQuery(choose);
            }
            return false;
        }
    };


    private final View.OnClickListener btSongOnClickListener = view -> {
        if (!choose) {
            choose = true;
            btSong.setBackgroundTintList(ColorStateList.valueOf(getResources().getColor(R.color.md_theme_dark_onPrimary, requireContext().getTheme())));
            btArtist.setBackgroundTintList(ColorStateList.valueOf(getResources().getColor(R.color.md_theme_light_primary, requireContext().getTheme())));
            findByQuery(choose);
        }
    };

    private final View.OnClickListener btArtistOnClickListener = view -> {
        if (choose) {
            choose = false;
            btSong.setBackgroundTintList(ColorStateList.valueOf(getResources().getColor(R.color.md_theme_light_primary, requireContext().getTheme())));
            btArtist.setBackgroundTintList(ColorStateList.valueOf(getResources().getColor(R.color.md_theme_dark_onPrimary, requireContext().getTheme())));
            findByQuery(choose);
        }
    };

    public void findByQuery(boolean flag) {
        if (songList != null) {
            songList.clear();
        }
        String query = svSearch.getQuery().toString();
        if (flag) {
            songList = Database.getSongList().stream().filter(song -> song.getSongName().contains(query)).collect(Collectors.toList());
        } else {
            songList = Database.getSongList().stream().filter(song -> song.getArtistsName().contains(query)).collect(Collectors.toList());
        }
        songAdapter = new SongAdapter(getContext(), songList, this);
        rcvSong.setAdapter(songAdapter);
    }

    @Override
    public void onItemClicked(Song song) {
        Intent intent = new Intent(getContext(), SongActivity.class);
        intent.putExtra("song", song);
        startActivity(intent);
    }
}