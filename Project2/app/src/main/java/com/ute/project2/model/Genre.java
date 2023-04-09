package com.ute.project2.model;

import java.io.Serializable;

public class Genre implements Serializable {
    private final String genreId;
    private final String genreName;
    private final String genreImage;

    public Genre(String genreId, String genreName, String genreImage) {
        this.genreId = genreId;
        this.genreName = genreName;
        this.genreImage = genreImage;
    }

    public String getGenreId() {
        return genreId;
    }

    public String getGenreName() {
        return genreName;
    }

    public String getGenreImage() {
        return genreImage;
    }

}
