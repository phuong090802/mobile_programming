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
    private TextView textViewGenreName;
    TextView textViewMainGenreName;
    RecyclerView recyclerView;
    private static List<Song> songList;
    private static final List<Artist> artistList = new ArrayList<>();
    private static Genre genre;
    private Context context;
    private SongAdapter songAdapter;
    private View view;

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
        view = inflater.inflate(R.layout.fragment_genre, container, false);
        songList = new ArrayList<>();
        context = getContext();
        loadDataArtist();
        Bundle bundle = getArguments();
        if (bundle != null) {
            genre = (Genre) bundle.get("genre");
        }
        initializeView();
        loadDataSong();
        return view;
    }

    private void loadDataSong() {
        DatabaseReference songDatabaseReference = FirebaseDatabase.getInstance().getReference().child(Constant.ROOT_SONG);
        songDatabaseReference.addListenerForSingleValueEvent(songValueEventListener);
    }

    private void loadDataArtist() {
        DatabaseReference artistDatabaseReference = FirebaseDatabase.getInstance().getReference().child(Constant.ROOT_ARTIST);
        artistDatabaseReference.addListenerForSingleValueEvent(artistValueEventListener);
    }

    private void initializeView() {
        if (view != null) {
            textViewGenreName = view.findViewById(R.id.text_view_genre_name);
            textViewGenreName.setOnClickListener(onClickListener);

            textViewMainGenreName = view.findViewById(R.id.text_view_main_genre_name);
            textViewMainGenreName.setText(genre.getGenreName());
            NestedScrollView nestedScrollView = view.findViewById(R.id.nested_scroll_view_fragment_genre);
            nestedScrollView.setOnScrollChangeListener(onScrollChangeListener);

            recyclerView = view.findViewById(R.id.recycler_view_fragment_genre);
            LinearLayoutManager linearLayoutManager = new LinearLayoutManager(context);
            recyclerView.setLayoutManager(linearLayoutManager);
            recyclerView.addItemDecoration(new ItemDecoration(22));
            setGenreFragmentAdapter();

        }
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
                    int position = songList.size();
                    Song song = new Song(songId, songName, songImage, songSource, genreId, artistsId, duration);
                    artistList.forEach(artist -> {
                        if (artist.getArtistId().equals(artistsId)) {
                            song.setArtist(artist.getArtistName());
                        }
                    });
                    addSong(position, song);
                }
            }
        }


        @Override
        public void onCancelled(@NonNull DatabaseError error) {
        }
    };

    private void addSong(int position, Song song) {
        songList.add(position, song);
        songAdapter.notifyItemInserted(position);
        songAdapter.notifyItemChanged(position, songAdapter.getItemCount());
    }

    private void setGenreFragmentAdapter() {
        songAdapter = new SongAdapter(context, songList, this);
        recyclerView.setAdapter(songAdapter);
    }

    private final ValueEventListener artistValueEventListener = new ValueEventListener() {
        @Override
        public void onDataChange(@NonNull DataSnapshot snapshot) {
            if (artistList.isEmpty()) {
                for (DataSnapshot dataSnapshot : snapshot.getChildren()) {
                    String artistId = dataSnapshot.getKey();
                    String artistName = dataSnapshot.child(Constant.CHILD_ARTIST_NAME).getValue(String.class);
                    String artistImage = dataSnapshot.child(Constant.CHILD_ARTIST_NAME).getValue(String.class);
                    artistList.add(new Artist(artistId, artistName, artistImage));
                }
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
            if (scrollY != 0) {
                textViewGenreName.setText(genre.getGenreName());
            } else {
                textViewGenreName.setText("");
            }
        }
    };

    @Override
    public void onItemClicked(Song song) {
        onViewClickListener.onItemSongClicked(song);
        StorageSingleton.putString(Constant.CURRENT_SONG_NAME, song.getSongName());
    }
}