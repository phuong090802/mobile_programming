package com.ute.project2;

import android.content.Context;
import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.core.widget.NestedScrollView;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.DefaultItemAnimator;
import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.google.android.material.search.SearchBar;
import com.ute.project2.adapter.GenreAdapter;
import com.ute.project2.database.Database;
import com.ute.project2.decoration.GenreItemDecoration;
import com.ute.project2.event.OnViewClickListener;
import com.ute.project2.event.SelectGenreListener;
import com.ute.project2.model.Genre;


public class SearchFragment extends Fragment implements SelectGenreListener {
    private OnViewClickListener onViewClickListener;
    RecyclerView recyclerView;
    SearchBar sbSearch;
    NestedScrollView nestedScrollView;
    TextView tvSearch;
    GenreAdapter adapter;

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
        View view = inflater.inflate(R.layout.fragment_search, container, false);

        recyclerView = view.findViewById(R.id.rcGenre);
        tvSearch = view.findViewById(R.id.tvSearch);

        nestedScrollView = view.findViewById(R.id.nested_scroll_view);
        nestedScrollView.setOnScrollChangeListener(setOnScrollChangeListener);

        sbSearch = view.findViewById(R.id.sbSearch);
        sbSearch.setOnClickListener(onClickListener);

        GridLayoutManager gridLayoutManager = new GridLayoutManager(getContext(), 2);
        recyclerView.setLayoutManager(gridLayoutManager);
        recyclerView.setItemAnimator(new DefaultItemAnimator());
        recyclerView.addItemDecoration(new GenreItemDecoration(22));
        adapter = new GenreAdapter(getContext(), Database.getGenreList(), this);
        recyclerView.setAdapter(adapter);

        return view;
    }

    private final NestedScrollView.OnScrollChangeListener setOnScrollChangeListener = new NestedScrollView.OnScrollChangeListener() {
        @Override
        public void onScrollChange(@NonNull NestedScrollView v, int scrollX, int scrollY, int oldScrollX, int oldScrollY) {
            if (nestedScrollView.getScrollY() != 0) {
                tvSearch.setVisibility(View.GONE);

            } else if (nestedScrollView.getScrollY() == 0) {
                tvSearch.setVisibility(View.VISIBLE);
            }
        }
    };

    private final View.OnClickListener onClickListener = view -> onViewClickListener.onSearchBarClicked();

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);

    }

    @Override
    public void onItemClicked(Genre genre) {
        onViewClickListener.onCardViewGenreClicked(genre);
    }

}