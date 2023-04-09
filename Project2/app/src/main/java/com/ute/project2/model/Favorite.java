package com.ute.project2.model;

public class Favorite {
    private final String favoriteId;
    private final String songId;

    public Favorite(String favoriteId, String songId) {
        this.favoriteId = favoriteId;
        this.songId = songId;
    }

    public String getFavoriteId() {
        return favoriteId;
    }

    public String getSongId() {
        return songId;
    }

}
