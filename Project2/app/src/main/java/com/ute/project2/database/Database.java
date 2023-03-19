package com.ute.project2.database;

import com.ute.project2.R;
import com.ute.project2.model.Category;
import com.ute.project2.model.Song;

import java.util.ArrayList;
import java.util.List;

public class Database {
    private static final List<Song> songList = new ArrayList<>();

    public static List<Song> getSongList() {
        songList.add(new Song("Neil", R.drawable.song, R.raw.file_example, 2, "Dax", "Skyler"));
        songList.add(new Song("Salem", R.drawable.song, R.raw.file_example, 2, "Dax", "Mohammed"));
        songList.add(new Song("Remi", R.drawable.song, R.raw.file_example, 2, "Dax", "Dennis"));
        songList.add(new Song("Liam", R.drawable.song, R.raw.file_example, 2, "Dax", "Nixon"));
        songList.add(new Song("Noah", R.drawable.song, R.raw.file_example, 2, "Dax", "Rex"));
        songList.add(new Song("Oliver", R.drawable.song, R.raw.file_example, 2, "Dax", "Uriah"));
        songList.add(new Song("Elijah", R.drawable.song, R.raw.file_example, 2, "Dax", "Lee"));
        songList.add(new Song("James", R.drawable.song, R.raw.file_example, 2, "Dax", "Louie"));
        songList.add(new Song("William", R.drawable.song, R.raw.file_example, 2, "Dax", "Alberto"));
        songList.add(new Song("Benjamin", R.drawable.song, R.raw.file_example, 2, "Dax", "Reese"));
        songList.add(new Song("Lucas", R.drawable.song, R.raw.file_example, 2, "Dax", "Quinton"));
        songList.add(new Song("Henry", R.drawable.song, R.raw.file_example, 2, "Dax", "Kingsley"));
        songList.add(new Song("Theodore", R.drawable.song, R.raw.file_example, 2, "Francis", "Chaim"));
        songList.add(new Song("Jack", R.drawable.song, R.raw.file_example, 2, "Francis", "Alfredo"));
        songList.add(new Song("Levi", R.drawable.song, R.raw.file_example, 2, "Francis", "Mauricio"));
        songList.add(new Song("Alexander", R.drawable.song, R.raw.file_example, 2, "Francis", "Caspian"));
        songList.add(new Song("Jackson", R.drawable.song, R.raw.file_example, 2, "Francis", "Legacy"));
        songList.add(new Song("Mateo", R.drawable.song, R.raw.file_example, 2, "Francis", "Ocean"));
        songList.add(new Song("Daniel", R.drawable.song, R.raw.file_example, 2, "Francis", "Ozzy"));
        songList.add(new Song("Michael", R.drawable.song, R.raw.file_example, 2, "Francis", "Briar"));
        songList.add(new Song("Mason", R.drawable.song, R.raw.file_example, 2, "Francis", "Wilson"));
        songList.add(new Song("Sebastian", R.drawable.song, R.raw.file_example, 2, "Francis", "Forest"));
        songList.add(new Song("Ethan", R.drawable.song, R.raw.file_example, 2, "Francis", "Grey"));
        return songList;
    }

    private static final List<Category> categoryList = new ArrayList<>();

    public static List<Category> getCategoryList() {
        categoryList.add(new Category("Dax", R.drawable.category));
        categoryList.add(new Category("Francis", R.drawable.category));
        categoryList.add(new Category("Levi", R.drawable.category));
        categoryList.add(new Category("Deacon", R.drawable.category));
        categoryList.add(new Category("Alexis", R.drawable.category));
        categoryList.add(new Category("Princeton", R.drawable.category));
        categoryList.add(new Category("Iker", R.drawable.category));
        categoryList.add(new Category("Wells", R.drawable.category));
        categoryList.add(new Category("Nikolai", R.drawable.category));
        categoryList.add(new Category("Moshe", R.drawable.category));
        categoryList.add(new Category("Cassius", R.drawable.category));
        categoryList.add(new Category("Gregory", R.drawable.category));
        categoryList.add(new Category("Lewis", R.drawable.category));
        categoryList.add(new Category("Kieran", R.drawable.category));
        categoryList.add(new Category("Isaias", R.drawable.category));
        categoryList.add(new Category("Seth", R.drawable.category));
        categoryList.add(new Category("Marcos", R.drawable.category));
        categoryList.add(new Category("Omari", R.drawable.category));
        categoryList.add(new Category("Keegan", R.drawable.category));
        return categoryList;
    }
}
