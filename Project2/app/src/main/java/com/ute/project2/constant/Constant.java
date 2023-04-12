package com.ute.project2.constant;

import com.ute.project2.R;
import com.ute.project2.item.ItemLibrary;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

public class Constant {
    public static final String DEFAULT_IMAGE = "android.resource://com.ute.project2/drawable/song_image_default";
    public static final String CHANEL_ID = "CHANEL_ID";
    public static final int ID_START_FOREGROUND = 1;
    public static final int REQUEST_CODE = 1;
    public static final String ACTION = "fromMyService";
    public static final String STORAGE_SONG_NAME = "STORAGE_SONG_NAME";
    public static final String CURRENT_SONG_NAME = "CURRENT_SONG_NAME";
    public static final List<ItemLibrary> ITEM_LIBRARY_LIST = new ArrayList<>(Arrays.asList(new ItemLibrary(R.drawable.md_favorite, R.string.favorites),
            new ItemLibrary(R.drawable.md_download, R.string.download)));
    public static final String EXTENSION = ".mp3";
    public static final int REQUEST_CODE_READ_AND_WRITE = 10;
    public static final String ACTION_SEEKBAR_UPDATE = "actionSeekbarUpdate";
    public static final String ACTION_MEDIA_PLAYER_UPDATE = "actionMediaPlayerUpdate";
    public static final String ROOT_GENRES = "genres";
    public static final String CHILD_GENRE_NAME = "genreName";
    public static final String CHILD_GENRE_IMAGE = "genreImage";
    public static final String ROOT_SONG = "song";
    public static final String CHILD_SONG_NAME = "songName";
    public static final String CHILD_SONG_IMAGE = "songImage";
    public static final String CHILD_SONG_SOURCE = "songSource";
    public static final String CHILD_SONG_GENRE_ID = "genreId";
    public static final String CHILD_SONG_ARTIST_ID = "artirstId";
    public static final String CHILD_DURATION = "duration";
    public static final String ROOT_ARTIST = "artist";
    public static final String CHILD_ARTIST_NAME = "artistName";
    public static final String CHILD_ARTIST_IMAGE = "artistImage";
    public static final String ROOT_FAVORITE = "favorite";
    public static final String CHILD_FAVORITE_SONG_ID = "songId";
    public static final String SONG_DIRECTORY = "songSource";
    public static final String SONG_SOURCE_LOCATION = "songSource/";
    public static final String FIREBASE_STORAGE = "firebasestorage";
    public static final String SONG_ID = "songId";

}
