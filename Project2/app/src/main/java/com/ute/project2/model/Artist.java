package com.ute.project2.model;

public class Artist {
    private final String artistName;
    private final int imageArtist;

    public Artist(String artistName, int imageArtist) {
        this.artistName = artistName;
        this.imageArtist = imageArtist;
    }

    public String getArtistName() {
        return artistName;
    }

    public int getImageArtist() {
        return imageArtist;
    }
}

