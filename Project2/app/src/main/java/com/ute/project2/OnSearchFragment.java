package com.ute.project2;

import android.content.Context;
import android.content.res.ColorStateList;
import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.appcompat.widget.SearchView;
import androidx.core.widget.NestedScrollView;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.text.TextUtils;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.google.android.material.button.MaterialButton;
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
import com.ute.project2.model.Song;
import com.ute.project2.sharedpreferences.StorageSingleton;

import java.util.ArrayList;
import java.util.List;

public class OnSearchFragment extends Fragment implements SelectSongListener {
    private OnViewClickListener onViewClickListener;
    private ImageView ivBack;
    private SearchView svSearch;
    private RecyclerView rcvSong;
    private TextView tvPlayWhatYouLove;
    private TextView tvSearchFor;
    private NestedScrollView nsvChoose;
    private boolean choose;
    private MaterialButton btSong;
    private MaterialButton btArtist;
    SongAdapter songAdapter;
    private List<Song> songListSearchSong;
    private List<Song> songListSearchArtist;
    private List<Artist> artistList;
    List<Song> resultQuery;
    Context context;
    DatabaseReference songDatabaseReference;
    DatabaseReference artistDatabaseReference;

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
        artistList = new ArrayList<>();
        songListSearchSong = new ArrayList<>();
        songListSearchArtist = new ArrayList<>();
        resultQuery = new ArrayList<>();
        artistDatabaseReference = FirebaseDatabase.getInstance().getReference().child(Constant.ROOT_ARTIST);
        artistDatabaseReference.addListenerForSingleValueEvent(artistValueEventListener);
        View view = inflater.inflate(R.layout.fragment_on_search, container, false);

        choose = true;

        svSearch = view.findViewById(R.id.svSearch);
        svSearch.setOnQueryTextFocusChangeListener(onFocusChangeListener);
        svSearch.setOnQueryTextListener(onQueryTextListener);

        ivBack = view.findViewById(R.id.ivBack);
        ivBack.setOnClickListener(onClickListener);

        rcvSong = view.findViewById(R.id.rcvSong);
        LinearLayoutManager linearLayoutManager = new LinearLayoutManager(getContext());
        rcvSong.setLayoutManager(linearLayoutManager);
        rcvSong.addItemDecoration(new ItemDecoration(22));

        btSong = view.findViewById(R.id.btSong);
        btSong.setOnClickListener(btSongOnClickListener);

        btArtist = view.findViewById(R.id.btArtist);
        btArtist.setOnClickListener(btArtistOnClickListener);

        nsvChoose = view.findViewById(R.id.nsvChoose);
        tvPlayWhatYouLove = view.findViewById(R.id.tvPlayWhatYouLove);
        tvSearchFor = view.findViewById(R.id.tvSearchFor);
        songDatabaseReference = FirebaseDatabase.getInstance().getReference().child(Constant.ROOT_SONG);
        songDatabaseReference.addListenerForSingleValueEvent(songValueEventListener);
        return view;
    }

    private final ValueEventListener artistValueEventListener = new ValueEventListener() {
        @Override
        public void onDataChange(@NonNull DataSnapshot snapshot) {
            for (DataSnapshot dataSnapshot : snapshot.getChildren()) {
                String artistId = dataSnapshot.getKey();
                String artistName = dataSnapshot.child(Constant.CHILD_ARTIST_NAME).getValue(String.class);
                String artistImage = dataSnapshot.child(Constant.CHILD_ARTIST_IMAGE).getValue(String.class);
                Artist artist = new Artist(artistId, artistName, artistImage);
                artistList.add(artist);
            }
        }

        @Override
        public void onCancelled(@NonNull DatabaseError error) {

        }
    };

    @Override
    public void onResume() {
        super.onResume();
        svSearch.setQuery("", false);
        svSearch.requestFocus();
    }

    private final ValueEventListener songValueEventListener = new ValueEventListener() {
        @Override
        public void onDataChange(@NonNull DataSnapshot snapshot) {
            for (DataSnapshot dataSnapshot : snapshot.getChildren()) {
                String genreId = dataSnapshot.child(Constant.CHILD_SONG_GENRE_ID).getValue(String.class);
                String songId = dataSnapshot.getKey();
                String songName = dataSnapshot.child(Constant.CHILD_SONG_NAME).getValue(String.class);
                String songImage = dataSnapshot.child(Constant.CHILD_SONG_IMAGE).getValue(String.class);
                String songSource = dataSnapshot.child(Constant.CHILD_SONG_SOURCE).getValue(String.class);
                String artistsId = dataSnapshot.child(Constant.CHILD_SONG_ARTIST_ID).getValue(String.class);
                String duration = dataSnapshot.child(Constant.CHILD_DURATION).getValue(String.class);
                Song songForSongList = new Song(songId, songName, songImage, songSource, genreId, artistsId, duration);
                for (Artist artist : artistList) {
                    if (artist.getArtistId().equals(artistsId)) {
                        songForSongList.setArtist(artist.getArtistName());
                    }
                }
                songListSearchSong.add(songForSongList);
                Song songForAristList = new Song(songId, songName, songImage, songSource, genreId, artistsId, duration);
                for (Artist artist : artistList) {
                    if (artist.getArtistId().equals(artistsId)) {
                        songForAristList.setArtist(artist.getArtistName());
                        songForAristList.setSongImage(artist.getArtistImage());
                    }
                }
                songListSearchArtist.add(songForAristList);
            }
        }

        @Override
        public void onCancelled(@NonNull DatabaseError error) {

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
                return true;
            } else {
                rcvSong.setVisibility(View.VISIBLE);
                nsvChoose.setVisibility(View.VISIBLE);
                tvPlayWhatYouLove.setVisibility(View.GONE);
                tvSearchFor.setVisibility(View.GONE);
                if (choose) {
                    resultQuery.clear();
                    for (Song song : songListSearchSong) {
                        if (song.getSongName().contains(newText)) {
                            resultQuery.add(song);
                            setSongAdapter();
                        }
                    }
                } else {
                    resultQuery.clear();
                    for (Song song : songListSearchArtist) {
                        if (song.getArtist().contains(newText)) {
                            resultQuery.add(song);
                            setSongAdapter();
                        }
                    }
                }
                if (resultQuery.isEmpty()) {
                    tvPlayWhatYouLove.setVisibility(View.VISIBLE);
                    tvSearchFor.setVisibility(View.VISIBLE);
                    rcvSong.setVisibility(View.GONE);
                    nsvChoose.setVisibility(View.GONE);
                    StringBuilder builder = new StringBuilder();
                    builder.append("Couldn't find \"").append(newText).append("\"");
                    tvPlayWhatYouLove.setText(builder);
                    tvSearchFor.setText(R.string.try_searching);
                }
            }
            return false;
        }
    };

    private void setSongAdapter() {
        songAdapter = new SongAdapter(getContext(), resultQuery, this);
        rcvSong.setAdapter(songAdapter);
    }

    private final View.OnClickListener onClickListener = view -> {
        if (getActivity() != null) {
            getActivity().onBackPressed();
        }
    };

    @Override
    public void onItemClicked(Song song) {
        onViewClickListener.onItemSongClicked(song);
        StorageSingleton.putString(Constant.CURRENT_SONG_NAME, song.getSongName());
    }

    public void findByQuery(boolean flag) {
        if (resultQuery != null) {
            resultQuery.clear();
        }
        String query = svSearch.getQuery().toString();
        if (flag) {
            resultQuery.clear();
            for (Song song : songListSearchSong) {
                if (song.getSongName().contains(query)) {
                    resultQuery.add(song);
                    setSongAdapter();
                }
            }
        } else {
            resultQuery.clear();
            for (Song song : songListSearchArtist) {
                String artistName = song.getArtist();
                if (artistName.contains(query)) {
                    resultQuery.add(song);
                    setSongAdapter();
                }
            }
        }

    }

    private final View.OnClickListener btSongOnClickListener = view -> {
        if (!choose) {
            choose = true;
            btSong.setBackgroundTintList(ColorStateList.valueOf(getResources().getColor(R.color.md_theme_dark_primaryContainer, requireContext().getTheme())));
            btSong.setTextColor(ColorStateList.valueOf(getResources().getColor(R.color.md_theme_light_onPrimary, requireContext().getTheme())));
            btArtist.setBackgroundTintList(ColorStateList.valueOf(getResources().getColor(R.color.md_theme_light_onPrimary, requireContext().getTheme())));
            btArtist.setTextColor(ColorStateList.valueOf(getResources().getColor(R.color.md_theme_dark_primaryContainer, requireContext().getTheme())));
            findByQuery(choose);
        }
    };

    private final View.OnClickListener btArtistOnClickListener = view -> {
        if (choose) {
            choose = false;
            btArtist.setBackgroundTintList(ColorStateList.valueOf(getResources().getColor(R.color.md_theme_dark_primaryContainer, requireContext().getTheme())));
            btArtist.setTextColor(ColorStateList.valueOf(getResources().getColor(R.color.md_theme_light_onPrimary, requireContext().getTheme())));
            btSong.setBackgroundTintList(ColorStateList.valueOf(getResources().getColor(R.color.md_theme_light_onPrimary, requireContext().getTheme())));
            btSong.setTextColor(ColorStateList.valueOf(getResources().getColor(R.color.md_theme_dark_primaryContainer, requireContext().getTheme())));
            findByQuery(choose);
        }
    };


}