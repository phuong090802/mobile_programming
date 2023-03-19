package com.ute.project2.model;

import java.io.Serializable;

public class Song implements Serializable {
    private final String songName;
    private final int songImage;
    private final int songSource;
    private final long songTime;
    private final String categoryName;
    private final String artistsName;

    public Song(String songName, int songImage, int songSource, long songTime, String categoryName, String singer) {
        this.songName = songName;
        this.songImage = songImage;
        this.songSource = songSource;
        this.songTime = songTime;
        this.categoryName = categoryName;
        this.artistsName = singer;
    }

    public String getSongName() {
        return songName;
    }

    public int getSongImage() {
        return songImage;
    }

    public int getSongSource() {
        return songSource;
    }

    public long getSongTime() {
        return songTime;
    }

    public String getCategoryName() {
        return categoryName;
    }

    public String getArtistsName() {
        return artistsName;
    }
}
