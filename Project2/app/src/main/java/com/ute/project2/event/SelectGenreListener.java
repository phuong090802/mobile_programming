package com.ute.project2.event;

import com.ute.project2.model.Genre;

public interface SelectGenreListener {
    void onItemClicked(Genre genre);
}
