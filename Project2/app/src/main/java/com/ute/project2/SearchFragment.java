package com.ute.project2;

import static com.ute.project2.constant.Constant.CHILD_GENRE_IMAGE;

import android.content.Context;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.DefaultItemAnimator;
import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.google.android.material.search.SearchBar;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;
import com.ute.project2.adapter.GenreAdapter;
import com.ute.project2.constant.Constant;
import com.ute.project2.decoration.GenreItemDecoration;
import com.ute.project2.event.OnViewClickListener;
import com.ute.project2.event.SelectGenreListener;
import com.ute.project2.model.Genre;

import java.util.ArrayList;
import java.util.List;


public class SearchFragment extends Fragment implements SelectGenreListener {
    private static OnViewClickListener onViewClickListener;
    private static RecyclerView recyclerView;
    private GenreAdapter genreAdapter;
    private static final List<Genre> genreList = new ArrayList<>();
    private View view;
    private Context context;

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
        context = getContext();
        view = inflater.inflate(R.layout.fragment_search, container, false);
        initializeView();
        loadDataFirebase();
        return view;
    }

    private void initializeView() {
        if (view != null) {
            SearchBar searchBar = view.findViewById(R.id.search_bar_fragment_search);
            searchBar.setOnClickListener(onClickListener);
            recyclerView = view.findViewById(R.id.recycler_view_fragment_search);
            GridLayoutManager gridLayoutManager = new GridLayoutManager(context, 2);
            recyclerView.setLayoutManager(gridLayoutManager);
            recyclerView.setItemAnimator(new DefaultItemAnimator());
            recyclerView.addItemDecoration(new GenreItemDecoration(22));
            setSearchFragmentAdapter();
        }
    }

    private void loadDataFirebase() {
        DatabaseReference genreDatabaseReference = FirebaseDatabase.getInstance().getReference().child(Constant.ROOT_GENRES);
        genreDatabaseReference.addListenerForSingleValueEvent(genreValueEventListener);
    }

    private void setSearchFragmentAdapter() {
        genreAdapter = new GenreAdapter(context, genreList, this);
        recyclerView.setAdapter(genreAdapter);
    }

    private final ValueEventListener genreValueEventListener = new ValueEventListener() {
        @Override
        public void onDataChange(@NonNull DataSnapshot snapshot) {
            if (genreList.isEmpty()) {
                for (DataSnapshot dataSnapshot : snapshot.getChildren()) {
                    String genreId = dataSnapshot.getKey();
                    String genreName = dataSnapshot.child(Constant.CHILD_GENRE_NAME).getValue(String.class);
                    String genreImage = dataSnapshot.child(CHILD_GENRE_IMAGE).getValue(String.class);
                    int position = genreList.size();
                    Genre genre = new Genre(genreId, genreName, genreImage);
                    addGenre(position, genre);
                }
            }
        }

        @Override
        public void onCancelled(@NonNull DatabaseError error) {
        }
    };

    private void addGenre(int postion, Genre genre) {
        genreList.add(postion, genre);
        genreAdapter.notifyItemInserted(postion);
        genreAdapter.notifyItemRangeInserted(postion, genreAdapter.getItemCount());
    }

    private final View.OnClickListener onClickListener = view -> onViewClickListener.onSearchBarClicked();

    @Override
    public void onItemClicked(Genre genre) {
        onViewClickListener.onCardViewGenreClicked(genre);
    }
}