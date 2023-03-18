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
import androidx.recyclerview.widget.DefaultItemAnimator;
import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.google.android.material.search.SearchBar;
import com.ute.project2.adapter.RecyclerViewAdapter;
import com.ute.project2.model.Category;

import java.util.ArrayList;
import java.util.List;

public class SearchFragment extends Fragment {

    RecyclerView recyclerView;
    RecyclerViewAdapter recyclerViewAdapter;
    SearchBar sbSearch;
    NestedScrollView nestedScrollView;
    TextView tvSearch;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        return inflater.inflate(R.layout.fragment_search, container, false);
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        recyclerView = view.findViewById(R.id.rcCategory);
        tvSearch = view.findViewById(R.id.tvSearch);
        nestedScrollView = view.findViewById(R.id.nested_scroll_view);
        sbSearch = view.findViewById(R.id.sbSearch);
        GridLayoutManager gridLayoutManager = new GridLayoutManager(getContext(), 2);
        recyclerView.setLayoutManager(gridLayoutManager);
        recyclerView.setItemAnimator(new DefaultItemAnimator());
        List<Category> categoryList = new ArrayList<>();
        sbSearch.setOnClickListener(onClickListener);
        categoryList.add(new Category("Category1", R.drawable.meow));
        categoryList.add(new Category("Category2", R.drawable.meow));
        categoryList.add(new Category("Category3", R.drawable.meow));
        categoryList.add(new Category("Category4", R.drawable.meow));
        categoryList.add(new Category("Category5", R.drawable.meow));
        categoryList.add(new Category("Category6", R.drawable.meow));
        categoryList.add(new Category("Category7", R.drawable.meow));
        categoryList.add(new Category("Category8", R.drawable.meow));
        categoryList.add(new Category("Category9", R.drawable.meow));
        categoryList.add(new Category("Category10", R.drawable.meow));
        categoryList.add(new Category("Category11", R.drawable.meow));
        categoryList.add(new Category("Category12", R.drawable.meow));
        categoryList.add(new Category("Category13", R.drawable.meow));
        categoryList.add(new Category("Category14", R.drawable.meow));
        categoryList.add(new Category("Category15", R.drawable.meow));
        categoryList.add(new Category("Category16", R.drawable.meow));
        categoryList.add(new Category("Category17", R.drawable.meow));
        categoryList.add(new Category("Category18", R.drawable.meow));
        categoryList.add(new Category("Category19", R.drawable.meow));
        recyclerView.addItemDecoration(new SpacesItemDecoration(22));
        recyclerViewAdapter = new RecyclerViewAdapter(getContext(), categoryList);
        recyclerView.setAdapter(recyclerViewAdapter);
        nestedScrollView.setOnScrollChangeListener(setOnScrollChangeListener);

    }

    private final View.OnClickListener onClickListener = view -> {
        Intent intent = new Intent(getContext(), SearchActivity.class);
        startActivity(intent);
    };

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
}