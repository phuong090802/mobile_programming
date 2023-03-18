package com.ute.project2;

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
import com.ute.project2.model.Song;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

public class ResultQueryFragment extends Fragment {

    ImageView ivBack;

    SearchView svSearch;

    List<Song> songList;

    RecyclerView rcvSong;
    TextView tvPlayWhatYouLove;
    TextView tvSearchFor;
    NestedScrollView nsvChoose;
    boolean choose;
    MaterialButton btSong;
    MaterialButton btArtist;
    SongAdapter songAdapter;

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
        songList = new ArrayList<>();
        songList.add(new Song("Neil", R.drawable.meow, R.raw.file_example, 2, "Skyler", "Skyler"));
        songList.add(new Song("Salem", R.drawable.meow, R.raw.file_example, 2, "Mohammed", "Mohammed"));
        songList.add(new Song("Remi", R.drawable.meow, R.raw.file_example, 2, "Dennis", "Dennis"));
        songList.add(new Song("Liam", R.drawable.meow, R.raw.file_example, 2, "Kareem", "Nixon"));
        songList.add(new Song("Noah", R.drawable.meow, R.raw.file_example, 2, "Category Name", "Rex"));
        songList.add(new Song("Oliver", R.drawable.meow, R.raw.file_example, 2, "Category Name", "Uriah"));
        songList.add(new Song("Elijah", R.drawable.meow, R.raw.file_example, 2, "Category Name", "Lee"));
        songList.add(new Song("James", R.drawable.meow, R.raw.file_example, 2, "Category Name", "Louie"));
        songList.add(new Song("William", R.drawable.meow, R.raw.file_example, 2, "Category Name", "Alberto"));
        songList.add(new Song("Benjamin", R.drawable.meow, R.raw.file_example, 2, "Category Name", "Reese"));
        songList.add(new Song("Lucas", R.drawable.meow, R.raw.file_example, 2, "Category Name", "Quinton"));
        songList.add(new Song("Henry", R.drawable.meow, R.raw.file_example, 2, "Category Name", "Kingsley"));
        songList.add(new Song("Theodore", R.drawable.meow, R.raw.file_example, 2, "Category Name", "Chaim"));
        songList.add(new Song("Jack", R.drawable.meow, R.raw.file_example, 2, "Category Name", "Alfredo"));
        songList.add(new Song("Levi", R.drawable.meow, R.raw.file_example, 2, "Category Name", "Mauricio"));
        songList.add(new Song("Alexander", R.drawable.meow, R.raw.file_example, 2, "Category Name", "Caspian"));
        songList.add(new Song("Jackson", R.drawable.meow, R.raw.file_example, 2, "Category Name", "Legacy"));
        songList.add(new Song("Mateo", R.drawable.meow, R.raw.file_example, 2, "Category Name", "Ocean"));
        songList.add(new Song("Daniel", R.drawable.meow, R.raw.file_example, 2, "Category Name", "Ozzy"));
        songList.add(new Song("Michael", R.drawable.meow, R.raw.file_example, 2, "Category Name", "Briar"));
        songList.add(new Song("Mason", R.drawable.meow, R.raw.file_example, 2, "Category Name", "Wilson"));
        songList.add(new Song("Sebastian", R.drawable.meow, R.raw.file_example, 2, "Category Name", "Forest"));
        songList.add(new Song("Ethan", R.drawable.meow, R.raw.file_example, 2, "Category Name", "Grey"));

        rcvSong.addItemDecoration(new SongItemDecoration(22, songList.size()));

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
                List<Song> list;
                if (choose) {
                    list = songList.stream().filter(song -> song.getSongName().contains(newText)).collect(Collectors.toList());
                } else {
                    list = songList.stream().filter(song -> song.getSingerName().contains(newText)).collect(Collectors.toList());
                }
                if (list.isEmpty()) {
                    tvPlayWhatYouLove.setVisibility(View.VISIBLE);
                    tvSearchFor.setVisibility(View.VISIBLE);
                    rcvSong.setVisibility(View.GONE);
                    nsvChoose.setVisibility(View.GONE);
                    StringBuilder builder = new StringBuilder();
                    builder.append("Couldn't find \"").append(newText).append("\"");
                    tvPlayWhatYouLove.setText(builder);
                    tvSearchFor.setText(R.string.try_searching);
                }
                songAdapter = new SongAdapter(getContext(), list);
                rcvSong.setAdapter(songAdapter);
            }
            return false;
        }
    };

    private final View.OnClickListener btSongOnClickListener = view -> {
        if (!choose) {
            choose = true;
            btSong.setBackgroundTintList(ColorStateList.valueOf(getResources().getColor(R.color.md_theme_dark_onPrimary, requireContext().getTheme())));
            btArtist.setBackgroundTintList(ColorStateList.valueOf(getResources().getColor(R.color.md_theme_light_primary, requireContext().getTheme())));
            songAdapter = new SongAdapter(getContext(), findByQuery(choose));
            rcvSong.setAdapter(songAdapter);
        }
    };

    private final View.OnClickListener btArtistOnClickListener = view -> {
        if (choose) {
            choose = false;
            btSong.setBackgroundTintList(ColorStateList.valueOf(getResources().getColor(R.color.md_theme_light_primary, requireContext().getTheme())));
            btArtist.setBackgroundTintList(ColorStateList.valueOf(getResources().getColor(R.color.md_theme_dark_onPrimary, requireContext().getTheme())));
            songAdapter = new SongAdapter(getContext(), findByQuery(choose));
            rcvSong.setAdapter(songAdapter);
        }
    };

    public List<Song> findByQuery(boolean flag) {
        String query = svSearch.getQuery().toString();
        if (flag) {
            return songList.stream().filter(song -> song.getSongName().contains(query)).collect(Collectors.toList());
        } else {
            return songList.stream().filter(song -> song.getSingerName().contains(query)).collect(Collectors.toList());
        }
    }

}