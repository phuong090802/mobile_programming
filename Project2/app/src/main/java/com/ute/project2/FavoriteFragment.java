package com.ute.project2;

import android.content.Context;
import android.os.Bundle;

import androidx.annotation.NonNull;
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
import com.ute.project2.adapter.SongAdapterFavorite;
import com.ute.project2.constant.Constant;
import com.ute.project2.decoration.ItemDecoration;
import com.ute.project2.event.OnViewClickListener;
import com.ute.project2.event.SelectSongListener;
import com.ute.project2.model.Artist;
import com.ute.project2.model.Favorite;
import com.ute.project2.model.Song;
import com.ute.project2.sharedpreferences.StorageSingleton;

import java.util.ArrayList;
import java.util.List;

public class FavoriteFragment extends Fragment implements SelectSongListener {
    private OnViewClickListener onViewClickListener;
    private TextView tvFavorite;
    private RecyclerView recyclerView;
    SongAdapterFavorite adapter;
    private List<Song> songListFavorite;
    private List<Artist> artistList;
    private List<Favorite> favoriteList;
    DatabaseReference favoriteDatabaseReference;
    DatabaseReference songDatabaseReference;
    DatabaseReference artistDatabaseReference;
    Context context;

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
        songListFavorite = new ArrayList<>();
        artistList = new ArrayList<>();
        favoriteList = new ArrayList<>();
        favoriteDatabaseReference = FirebaseDatabase.getInstance().getReference().child(Constant.ROOT_FAVORITE);
        favoriteDatabaseReference.addListenerForSingleValueEvent(favoriteValueEventListener);
        artistDatabaseReference = FirebaseDatabase.getInstance().getReference().child(Constant.ROOT_ARTIST);
        artistDatabaseReference.addListenerForSingleValueEvent(artistValueEventListener);
        songDatabaseReference = FirebaseDatabase.getInstance().getReference().child(Constant.ROOT_SONG);
        songDatabaseReference.addListenerForSingleValueEvent(songValueEventListener);

        View view = inflater.inflate(R.layout.fragment_favorite, container, false);
        tvFavorite = view.findViewById(R.id.text_view_favorite);
        recyclerView = view.findViewById(R.id.recycler_view_favorite);

        LinearLayoutManager linearLayoutManager = new LinearLayoutManager(getContext());
        recyclerView.setLayoutManager(linearLayoutManager);
        recyclerView.addItemDecoration(new ItemDecoration(22));
        return view;
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


    private final ValueEventListener songValueEventListener = new ValueEventListener() {
        @Override
        public void onDataChange(@NonNull DataSnapshot snapshot) {
            for (DataSnapshot dataSnapshot : snapshot.getChildren()) {
                String songId = dataSnapshot.getKey();
                for (Favorite favorite : favoriteList) {
                    if (favorite.getSongId().equals(songId)) {
                        String genreId = dataSnapshot.child(Constant.CHILD_SONG_GENRE_ID).getValue(String.class);
                        String songName = dataSnapshot.child(Constant.CHILD_SONG_NAME).getValue(String.class);
                        String songImage = dataSnapshot.child(Constant.CHILD_SONG_IMAGE).getValue(String.class);
                        String songSource = dataSnapshot.child(Constant.CHILD_SONG_SOURCE).getValue(String.class);
                        String artistsId = dataSnapshot.child(Constant.CHILD_SONG_ARTIST_ID).getValue(String.class);
                        String duration = dataSnapshot.child(Constant.CHILD_DURATION).getValue(String.class);
                        Song song = new Song(songId, songName, songImage, songSource, genreId, artistsId, duration);
                        songListFavorite.add(song);
                    }
                }
               for (Song song : songListFavorite) {
                   for (Artist artist : artistList) {
                       if (song.getArtist().equals(artist.getArtistId())) {
                           song.setArtist(artist.getArtistName());
                       }
                   }
               }
                setLayout();
                setFavoriteAdapter();
            }
        }

        @Override
        public void onCancelled(@NonNull DatabaseError error) {

        }
    };

    private void setFavoriteAdapter() {
        adapter = new SongAdapterFavorite(getContext(), songListFavorite, this);
        recyclerView.setAdapter(adapter);
    }

    private void setLayout() {
        if (songListFavorite.isEmpty()) {
            tvFavorite.setVisibility(View.VISIBLE);
            recyclerView.setVisibility(View.GONE);
        } else {
            recyclerView.setVisibility(View.VISIBLE);
            tvFavorite.setVisibility(View.GONE);
        }
    }

    private final ValueEventListener favoriteValueEventListener = new ValueEventListener() {
        @Override
        public void onDataChange(@NonNull DataSnapshot snapshot) {
            for (DataSnapshot dataSnapshot : snapshot.getChildren()) {
                String favoriteId = dataSnapshot.getKey();
                String songId = dataSnapshot.child(Constant.CHILD_FAVORITE_SONG_ID).getValue(String.class);
                Favorite favorite = new Favorite(favoriteId, songId);
                favoriteList.add(favorite);
            }
        }

        @Override
        public void onCancelled(@NonNull DatabaseError error) {

        }
    };


    @Override
    public void onItemClicked(Song song) {
        onViewClickListener.onItemSongClicked(song);
        StorageSingleton.putString(Constant.CURRENT_SONG_NAME, song.getSongName());
    }
}