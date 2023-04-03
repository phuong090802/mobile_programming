package com.ute.project2;

import android.content.Context;
import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.core.widget.NestedScrollView;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.ute.project2.adapter.SongAdapter;
import com.ute.project2.database.Database;
import com.ute.project2.decoration.ItemDecoration;
import com.ute.project2.event.OnViewClickListener;
import com.ute.project2.event.SelectSongListener;
import com.ute.project2.model.Genre;
import com.ute.project2.model.Song;

import java.util.List;
import java.util.stream.Collectors;

public class GenreFragment extends Fragment implements SelectSongListener {
    private OnViewClickListener onViewClickListener;
    TextView tvGenreName;
    NestedScrollView nsvSongForGenre;
    TextView tvMainGenreName;
    RecyclerView recyclerView;
    List<Song> songList;
    Genre genre;

    @Override
    public void onAttach(@NonNull Context context) {
        super.onAttach(context);
        if (context instanceof OnViewClickListener) {
            onViewClickListener = (OnViewClickListener) context;
        }
    }


    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        Bundle bundle = getArguments();
        if (bundle != null) {
            genre = (Genre) bundle.get("genre");
            songList = Database.getSongList().stream().filter(song -> song.getGenreName().contains(genre.getGenreName())).collect(Collectors.toList());
        }
        View view = inflater.inflate(R.layout.fragment_genre, container, false);

        tvGenreName = view.findViewById(R.id.tvGenreName);
        tvGenreName.setOnClickListener(onClickListener);

        tvMainGenreName = view.findViewById(R.id.tvMainGenreName);
        tvMainGenreName.setText(genre.getGenreName());
        nsvSongForGenre = view.findViewById(R.id.nsvSongForGenre);
        nsvSongForGenre.setOnScrollChangeListener(onScrollChangeListener);

        recyclerView = view.findViewById(R.id.recyclerView);
        LinearLayoutManager linearLayoutManager = new LinearLayoutManager(getContext());
        recyclerView.setLayoutManager(linearLayoutManager);
        recyclerView.addItemDecoration(new ItemDecoration(22));
        SongAdapter adapter = new SongAdapter(getContext(), songList, this);
        recyclerView.setAdapter(adapter);
        return view;
    }

    private final View.OnClickListener onClickListener = view -> {
        if (getActivity() != null) {
            getActivity().onBackPressed();
        }
    };

    private final NestedScrollView.OnScrollChangeListener onScrollChangeListener = new NestedScrollView.OnScrollChangeListener() {
        @Override
        public void onScrollChange(@NonNull NestedScrollView v, int scrollX, int scrollY, int oldScrollX, int oldScrollY) {
            if (nsvSongForGenre.getScrollY() != 0) {
                tvGenreName.setText(genre.getGenreName());

            } else if (nsvSongForGenre.getScrollY() == 0) {
                tvGenreName.setText("");
            }
        }
    };

    @Override
    public void onItemClicked(Song song) {
        onViewClickListener.onItemSongClicked(song);
    }
}