package com.ute.project2.event;

import com.ute.project2.model.Genre;
import com.ute.project2.item.ItemLibrary;
import com.ute.project2.model.Song;

public interface OnViewClickListener {
    void onSearchBarClicked();

    void onCardViewGenreClicked(Genre genre);

    void onItemSongClicked(Song song);
    void onItemLibraryOnClick(ItemLibrary itemLibrary);
}
