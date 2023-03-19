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
import com.ute.project2.adapter.CategoryAdapter;
import com.ute.project2.database.Database;
import com.ute.project2.model.Category;

public class SearchFragment extends Fragment implements SelectCategoryListener {

    RecyclerView recyclerView;
    CategoryAdapter recyclerViewAdapter;
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
        sbSearch.setOnClickListener(onClickListener);
        recyclerView.addItemDecoration(new CategoryItemDecoration(22));
        recyclerViewAdapter = new CategoryAdapter(getContext(), Database.getCategoryList(), this);
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

    @Override
    public void onItemClicked(Category category) {
        Intent intent = new Intent(getContext(), CategoryActivity.class);
        intent.putExtra("category", category);
        startActivity(intent);
    }
}