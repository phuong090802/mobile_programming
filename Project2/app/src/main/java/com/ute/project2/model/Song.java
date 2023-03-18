package com.ute.project2.model;

public class Song {
    private final String songName;
    private final int songImage;
    private final int songSource;
    private final long songTime;
    private final String categoryName;
    private final String singerName;

    public Song(String songName, int songImage, int songSource, long songTime, String categoryName, String singer) {
        this.songName = songName;
        this.songImage = songImage;
        this.songSource = songSource;
        this.songTime = songTime;
        this.categoryName = categoryName;
        this.singerName = singer;
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

    public String getSingerName() {
        return singerName;
    }
}
