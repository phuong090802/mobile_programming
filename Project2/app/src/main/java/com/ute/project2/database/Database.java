package com.ute.project2.database;

import com.ute.project2.R;
import com.ute.project2.model.Genre;
import com.ute.project2.model.Song;

import java.util.ArrayList;
import java.util.List;
import java.util.Random;

public class Database {
    public static List<Song> getSongListDownload() {
        List<Song> songListDownload = new ArrayList<>();
        songListDownload.add(new Song("Neil", R.drawable.song, R.raw.file_example, "Dax", "Skyler"));
        songListDownload.add(new Song("Remi", R.drawable.song, R.raw.file_example, "Dax", "Dennis"));
        songListDownload.add(new Song("Liam", R.drawable.song, R.raw.file_example, "Dax", "Nixon"));
        return songListDownload;
    }

    public static List<Song> getSongListFavorite() {
        List<Song> songListFavorite = new ArrayList<>();
        songListFavorite.add(new Song("Neil", R.drawable.song, R.raw.edm_deep_house_ish_female_vocal_112184, "Dax", "Skyler"));
        songListFavorite.add(new Song("Salem", R.drawable.song, R.raw.groovy_light_future_bass_15881, "Dax", "Mohammed"));
        songListFavorite.add(new Song("Remi", R.drawable.song, R.raw.lkb_one_hip_hop_beat_300_138129, "Dax", "Dennis"));
        songListFavorite.add(new Song("Liam", R.drawable.song, R.raw.lonely_girl_beat_9522, "Dax", "Nixon"));
        songListFavorite.add(new Song("Noah", R.drawable.song, R.raw.solitarymaninblack_putin_99465, "Dax", "Rex"));
        songListFavorite.add(new Song("Oliver", R.drawable.song, R.raw.soundhelix_song, "Dax", "Uriah"));
        songListFavorite.add(new Song("Elijah", R.drawable.song, R.raw.soundhelix_song_10, "Dax", "Lee"));
        songListFavorite.add(new Song("James", R.drawable.song, R.raw.soundhelix_song_11, "Dax", "Louie"));
        return songListFavorite;
    }

    public static List<Song> getSongList() {
        List<Song> songList = new ArrayList<>();
        songList.add(new Song("Neil", R.drawable.song, R.raw.edm_deep_house_ish_female_vocal_112184, "Dax", "Skyler"));
        songList.add(new Song("Salem", R.drawable.song, R.raw.groovy_light_future_bass_15881, "Dax", "Mohammed"));
        songList.add(new Song("Remi", R.drawable.song, R.raw.lkb_one_hip_hop_beat_300_138129, "Dax", "Dennis"));
        songList.add(new Song("Liam", R.drawable.song, R.raw.lonely_girl_beat_9522, "Dax", "Nixon"));
        songList.add(new Song("Noah", R.drawable.song, R.raw.solitarymaninblack_putin_99465, "Dax", "Rex"));
        songList.add(new Song("Oliver", R.drawable.song, R.raw.soundhelix_song, "Dax", "Uriah"));
        songList.add(new Song("Elijah", R.drawable.song, R.raw.soundhelix_song_10, "Dax", "Lee"));
        songList.add(new Song("James", R.drawable.song, R.raw.soundhelix_song_11, "Dax", "Louie"));
        songList.add(new Song("William", R.drawable.song, R.raw.soundhelix_song_12, "Dax", "Alberto"));
        songList.add(new Song("Benjamin", R.drawable.song, R.raw.soundhelix_song_13, "Dax", "Reese"));
        songList.add(new Song("Lucas", R.drawable.song, R.raw.soundhelix_song_14, "Dax", "Quinton"));
        songList.add(new Song("Henry", R.drawable.song, R.raw.soundhelix_song_15, "Dax", "Kingsley"));
        songList.add(new Song("Theodore", R.drawable.song, R.raw.soundhelix_song_16, "Francis", "Chaim"));
        songList.add(new Song("Jack", R.drawable.song, R.raw.soundhelix_song_17, "Francis", "Alfredo"));
        songList.add(new Song("Levi", R.drawable.song, R.raw.soundhelix_song_2, "Francis", "Mauricio"));
        songList.add(new Song("Alexander", R.drawable.song, R.raw.soundhelix_song_3, "Francis", "Caspian"));
        songList.add(new Song("Jackson", R.drawable.song, R.raw.soundhelix_song_4, "Francis", "Legacy"));
        songList.add(new Song("Mateo", R.drawable.song, R.raw.soundhelix_song_5, "Francis", "Ocean"));
        songList.add(new Song("Daniel", R.drawable.song, R.raw.soundhelix_song_6, "Francis", "Ozzy"));
        songList.add(new Song("Michael", R.drawable.song, R.raw.soundhelix_song_7, "Francis", "Briar"));
        songList.add(new Song("Mason", R.drawable.song, R.raw.soundhelix_song_8, "Francis", "Wilson"));
        songList.add(new Song("Sebastian", R.drawable.song, R.raw.soundhelix_song_9, "Francis", "Forest"));
        songList.add(new Song("Ethan", R.drawable.song, R.raw.tomorrow_114848, "Francis", "Grey"));
        return songList;
    }


    public static List<Genre> getGenreList() {
        List<Genre> GenreList = new ArrayList<>();
        GenreList.add(new Genre("Dax", R.drawable.genre));
        GenreList.add(new Genre("Francis", R.drawable.genre));
        GenreList.add(new Genre("Levi", R.drawable.genre));
        GenreList.add(new Genre("Deacon", R.drawable.genre));
        GenreList.add(new Genre("Alexis", R.drawable.genre));
        GenreList.add(new Genre("Princeton", R.drawable.genre));
        GenreList.add(new Genre("Iker", R.drawable.genre));
        GenreList.add(new Genre("Wells", R.drawable.genre));
        GenreList.add(new Genre("Nikolai", R.drawable.genre));
        GenreList.add(new Genre("Moshe", R.drawable.genre));
        GenreList.add(new Genre("Cassius", R.drawable.genre));
        GenreList.add(new Genre("Gregory", R.drawable.genre));
        GenreList.add(new Genre("Lewis", R.drawable.genre));
        GenreList.add(new Genre("Kieran", R.drawable.genre));
        GenreList.add(new Genre("Isaias", R.drawable.genre));
        GenreList.add(new Genre("Seth", R.drawable.genre));
        GenreList.add(new Genre("Marcos", R.drawable.genre));
        GenreList.add(new Genre("Omari", R.drawable.genre));
        GenreList.add(new Genre("Keegan", R.drawable.genre));
        return GenreList;
    }

    public static Song getRandomElement() {
        Random rand = new Random();
        List<Song> songList = getSongList();
        int index = rand.nextInt(songList.size());
        return songList.get(index);
    }
}
