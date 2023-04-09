package com.ute.project2.model;

public class Artist {
    private final String artistId;
    private final String artistName;
    private final String artistImage;

    public Artist(String artistId, String artistName, String artistImage) {
        this.artistId = artistId;
        this.artistName = artistName;
        this.artistImage = artistImage;
    }

    public String getArtistId() {
        return artistId;
    }

    public String getArtistName() {
        return artistName;
    }

    public String getArtistImage() {
        return artistImage;
    }

}
