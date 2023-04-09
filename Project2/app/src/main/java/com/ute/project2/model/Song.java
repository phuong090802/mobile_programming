package com.ute.project2.model;

import java.io.Serializable;

public class Song implements Serializable {
    private final String songId;
    private final String songName;
    private String songImage;
    private final String songSource;
    private final String genre;
    private String artist;
    private final String duration;


    public Song(String songId, String songName, String songImage, String songSource, String genre, String artist, String duration) {
        this.songId = songId;
        this.songName = songName;
        this.songImage = songImage;
        this.songSource = songSource;
        this.genre = genre;
        this.artist = artist;
        this.duration = duration;
    }

    public String getSongId() {
        return songId;
    }

    public String getSongName() {
        return songName;
    }

    public String getSongImage() {
        return songImage;
    }

    public String getSongSource() {
        return songSource;
    }

    public String getGenre() {
        return genre;
    }

    public String getArtist() {
        return artist;
    }

    public String getDuration() {
        return duration;
    }

    public void setSongImage(String songImage) {
        this.songImage = songImage;
    }

    public void setArtist(String artist) {
        this.artist = artist;
    }

}
