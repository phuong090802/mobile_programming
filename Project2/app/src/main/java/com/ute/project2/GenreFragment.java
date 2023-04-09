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

import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;
import com.ute.project2.adapter.SongAdapter;
import com.ute.project2.constant.Constant;
import com.ute.project2.decoration.ItemDecoration;
import com.ute.project2.event.OnViewClickListener;
import com.ute.project2.event.SelectSongListener;
import com.ute.project2.model.Artist;
import com.ute.project2.model.Genre;
import com.ute.project2.model.Song;
import com.ute.project2.sharedpreferences.StorageSingleton;

import java.util.ArrayList;
import java.util.List;

public class GenreFragment extends Fragment implements SelectSongListener {
    private OnViewClickListener onViewClickListener;
    private TextView tvGenreName;
    private NestedScrollView nsvSongForGenre;
    TextView tvMainGenreName;
    RecyclerView recyclerView;
    private List<Song> songList;
    private List<Artist> artistList;
    private Genre genre;
    Context context;
    DatabaseReference songDatabaseReference;
    DatabaseReference artistDatabaseReference;
    SongAdapter adapter;

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
        context = getContext();
        songList = new ArrayList<>();
        artistList = new ArrayList<>();
        artistDatabaseReference = FirebaseDatabase.getInstance().getReference().child(Constant.ROOT_ARTIST);
        artistDatabaseReference.addListenerForSingleValueEvent(artistValueEventListener);
        Bundle bundle = getArguments();
        if (bundle != null) {
            genre = (Genre) bundle.get("genre");
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


        songDatabaseReference = FirebaseDatabase.getInstance().getReference().child(Constant.ROOT_SONG);
        songDatabaseReference.addListenerForSingleValueEvent(songValueEventListener);


        return view;
    }

    private final ValueEventListener songValueEventListener = new ValueEventListener() {
        @Override
        public void onDataChange(@NonNull DataSnapshot snapshot) {
            for (DataSnapshot dataSnapshot : snapshot.getChildren()) {
                String genreId = dataSnapshot.child(Constant.CHILD_SONG_GENRE_ID).getValue(String.class);
                if (genre.getGenreId().equals(genreId)) {
                    String songId = dataSnapshot.getKey();
                    String songName = dataSnapshot.child(Constant.CHILD_SONG_NAME).getValue(String.class);
                    String songImage = dataSnapshot.child(Constant.CHILD_SONG_IMAGE).getValue(String.class);
                    String songSource = dataSnapshot.child(Constant.CHILD_SONG_SOURCE).getValue(String.class);
                    String artistsId = dataSnapshot.child(Constant.CHILD_SONG_ARTIST_ID).getValue(String.class);
                    String duration = dataSnapshot.child(Constant.CHILD_DURATION).getValue(String.class);
                    Song song = new Song(songId, songName, songImage, songSource, genreId, artistsId, duration);
                    for (Artist artist : artistList) {
                        if (artist.getArtistId().equals(artistsId)) {
                            song.setArtist(artist.getArtistName());
                        }
                    }
                    songList.add(song);
                    setGenreAdapter();
                }

            }
        }

        @Override
        public void onCancelled(@NonNull DatabaseError error) {

        }
    };

    private void setGenreAdapter() {
        adapter = new SongAdapter(getContext(), songList, this);
        recyclerView.setAdapter(adapter);
    }

    private final ValueEventListener artistValueEventListener = new ValueEventListener() {
        @Override
        public void onDataChange(@NonNull DataSnapshot snapshot) {
            for (DataSnapshot dataSnapshot : snapshot.getChildren()) {
                String artistId = dataSnapshot.getKey();
                String artistName = dataSnapshot.child(Constant.CHILD_ARTIST_NAME).getValue(String.class);
                String artistImage = dataSnapshot.child(Constant.CHILD_ARTIST_NAME).getValue(String.class);
                Artist artist = new Artist(artistId, artistName, artistImage);
                artistList.add(artist);
            }
        }

        @Override
        public void onCancelled(@NonNull DatabaseError error) {

        }
    };

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
        StorageSingleton.putString(Constant.CURRENT_SONG_NAME, song.getSongName());
    }
}