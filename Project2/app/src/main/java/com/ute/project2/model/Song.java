package com.ute.project2.model;

import java.io.Serializable;

public class Song implements Serializable {
    private final String songName;
    private final int songImage;
    private final String songSource;
    private final String genreName;
    private final String artistsName;

    public Song(String songName, int songImage, String songSource, String genreName, String artistsName) {
        this.songName = songName;
        this.songImage = songImage;
        this.songSource = songSource;
        this.genreName = genreName;
        this.artistsName = artistsName;
    }

    public String getSongName() {
        return songName;
    }

    public int getSongImage() {
        return songImage;
    }

    public String getSongSource() {
        return songSource;
    }

    public String getGenreName() {
        return genreName;
    }

    public String getArtistsName() {
        return artistsName;
    }

}
