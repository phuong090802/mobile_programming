package com.ute.project2;

import android.content.Intent;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.core.widget.NestedScrollView;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.ute.project2.adapter.SongAdapter;
import com.ute.project2.database.Database;
import com.ute.project2.model.Song;

import java.util.List;
import java.util.stream.Collectors;

public class SongForCategoryFragment extends Fragment implements SelectSongListener {

    TextView tvCategoryName;
    NestedScrollView nsvSongForCategory;
    TextView tvMainCategoryName;
    RecyclerView rcvSongForCategory;
    List<Song> songList;
    String categoryName;

    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        Bundle bundle = getArguments();
        if (bundle != null) {
            categoryName = bundle.getString("categoryName");
            songList = Database.getSongList().stream().filter(song -> song.getCategoryName().contains(categoryName)).collect(Collectors.toList());
        }
        return inflater.inflate(R.layout.fragment_song_for_category, container, false);
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        tvCategoryName = view.findViewById(R.id.tvCategoryName);
        tvCategoryName.setOnClickListener(onClickListener);
        tvMainCategoryName = view.findViewById(R.id.tvMainCategoryName);
        nsvSongForCategory = view.findViewById(R.id.nsvSongForCategory);
        nsvSongForCategory.setOnScrollChangeListener(onScrollChangeListener);
        rcvSongForCategory = view.findViewById(R.id.rcvSongForCategory);
        tvMainCategoryName.setText(categoryName);
        LinearLayoutManager linearLayoutManager = new LinearLayoutManager(getContext());
        rcvSongForCategory.setLayoutManager(linearLayoutManager);
        rcvSongForCategory.addItemDecoration(new SongItemDecoration(22));
        SongAdapter songAdapter = new SongAdapter(getContext(), songList, this);
        rcvSongForCategory.setAdapter(songAdapter);
    }

    private final View.OnClickListener onClickListener = view -> {
        if (getActivity() != null) {
            getActivity().finish();
        }
    };

    private final NestedScrollView.OnScrollChangeListener onScrollChangeListener = new NestedScrollView.OnScrollChangeListener() {
        @Override
        public void onScrollChange(@NonNull NestedScrollView v, int scrollX, int scrollY, int oldScrollX, int oldScrollY) {
            if (nsvSongForCategory.getScrollY() != 0) {
                tvCategoryName.setText(categoryName);

            } else if (nsvSongForCategory.getScrollY() == 0) {
                tvCategoryName.setText("");
            }
        }
    };

    @Override
    public void onItemClicked(Song song) {
        Intent intent = new Intent(getContext(), SongActivity.class);
        intent.putExtra("song", song);
        startActivity(intent);
    }
}