package com.ute.project2.model;

import java.io.Serializable;

public class Genre implements Serializable {
    private final String genreName;
    private final int genreImage;

    public Genre(String genreName, int genreImage) {
        this.genreName = genreName;
        this.genreImage = genreImage;
    }

    public String getGenreName() {
        return genreName;
    }

    public int getGenreImage() {
        return genreImage;
    }
}
