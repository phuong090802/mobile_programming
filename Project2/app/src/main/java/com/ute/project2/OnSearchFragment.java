package com.ute.project2;

import android.content.Context;
import android.content.res.ColorStateList;
import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.appcompat.widget.SearchView;
import androidx.core.widget.NestedScrollView;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.text.TextUtils;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.google.android.material.button.MaterialButton;
import com.ute.project2.adapter.SongAdapter;
import com.ute.project2.constant.Constant;
import com.ute.project2.database.Database;
import com.ute.project2.decoration.ItemDecoration;
import com.ute.project2.event.OnViewClickListener;
import com.ute.project2.event.SelectSongListener;
import com.ute.project2.model.Song;
import com.ute.project2.sharedpreferences.StorageSingleton;

import java.util.List;
import java.util.stream.Collectors;

public class OnSearchFragment extends Fragment implements SelectSongListener {
    private OnViewClickListener onViewClickListener;
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
    public void onAttach(@NonNull Context context) {
        super.onAttach(context);
        if (context instanceof OnViewClickListener) {
            onViewClickListener = (OnViewClickListener) context;
        }
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {

        View view = inflater.inflate(R.layout.fragment_on_search, container, false);

        choose = true;

        svSearch = view.findViewById(R.id.svSearch);
        svSearch.setOnQueryTextFocusChangeListener(onFocusChangeListener);
        svSearch.setOnQueryTextListener(onQueryTextListener);

        ivBack = view.findViewById(R.id.ivBack);
        ivBack.setOnClickListener(onClickListener);

        rcvSong = view.findViewById(R.id.rcvSong);
        LinearLayoutManager linearLayoutManager = new LinearLayoutManager(getContext());
        rcvSong.setLayoutManager(linearLayoutManager);
        rcvSong.addItemDecoration(new ItemDecoration(22));

        btSong = view.findViewById(R.id.btSong);
        btSong.setOnClickListener(btSongOnClickListener);

        btArtist = view.findViewById(R.id.btArtist);
        btArtist.setOnClickListener(btArtistOnClickListener);

        nsvChoose = view.findViewById(R.id.nsvChoose);
        tvPlayWhatYouLove = view.findViewById(R.id.tvPlayWhatYouLove);
        tvSearchFor = view.findViewById(R.id.tvSearchFor);


        return view;
    }

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
                return true;
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
                } else {
                    setSongAdapter();
                }
            }
            return false;
        }
    };

    private final View.OnClickListener onClickListener = view -> {
        if (getActivity() != null) {
            getActivity().onBackPressed();
        }
    };

    @Override
    public void onItemClicked(Song song) {
        onViewClickListener.onItemSongClicked(song);
        StorageSingleton.putString(Constant.CURRENT_SONG_NAME, song.getSongName());
    }

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
        setSongAdapter();
    }

    private void setSongAdapter() {
        songAdapter = new SongAdapter(getContext(), songList, this);
        rcvSong.setAdapter(songAdapter);
    }

    private final View.OnClickListener btSongOnClickListener = view -> {
        if (!choose) {
            choose = true;
            btSong.setBackgroundTintList(ColorStateList.valueOf(getResources().getColor(R.color.md_theme_dark_primaryContainer, requireContext().getTheme())));
            btSong.setTextColor(ColorStateList.valueOf(getResources().getColor(R.color.md_theme_light_onPrimary, requireContext().getTheme())));
            btArtist.setBackgroundTintList(ColorStateList.valueOf(getResources().getColor(R.color.md_theme_light_onPrimary, requireContext().getTheme())));
            btArtist.setTextColor(ColorStateList.valueOf(getResources().getColor(R.color.md_theme_dark_primaryContainer, requireContext().getTheme())));
            findByQuery(choose);
        }
    };

    private final View.OnClickListener btArtistOnClickListener = view -> {
        if (choose) {
            choose = false;
            btArtist.setBackgroundTintList(ColorStateList.valueOf(getResources().getColor(R.color.md_theme_dark_primaryContainer, requireContext().getTheme())));
            btArtist.setTextColor(ColorStateList.valueOf(getResources().getColor(R.color.md_theme_light_onPrimary, requireContext().getTheme())));
            btSong.setBackgroundTintList(ColorStateList.valueOf(getResources().getColor(R.color.md_theme_light_onPrimary, requireContext().getTheme())));
            btSong.setTextColor(ColorStateList.valueOf(getResources().getColor(R.color.md_theme_dark_primaryContainer, requireContext().getTheme())));
            findByQuery(choose);
        }
    };


}