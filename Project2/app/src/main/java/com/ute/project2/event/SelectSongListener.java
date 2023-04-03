package com.ute.project2.event;

import com.ute.project2.model.Song;

public interface SelectSongListener {
    void onItemClicked(Song song);
}
