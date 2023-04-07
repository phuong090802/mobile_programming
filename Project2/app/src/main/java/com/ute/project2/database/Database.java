package com.ute.project2.database;

import android.content.Context;

import com.ute.project2.R;
import com.ute.project2.model.Genre;
import com.ute.project2.model.Song;

import java.util.ArrayList;
import java.util.List;
import java.util.Objects;
import java.util.Random;

public class Database {
    public static List<Song> getSongListFavorite(Context context) {
        List<Song> songListFavorite = new ArrayList<>();
        songListFavorite.add(new Song("Neil", R.drawable.song, "android.resource://" + context.getResources().getResourceName(R.raw.edm_deep_house_ish_female_vocal_112184).replace(":", "/"), "Dax", "Skyler"));
        songListFavorite.add(new Song("Neil", R.drawable.song,"android.resource://" + context.getResources().getResourceName(R.raw.edm_deep_house_ish_female_vocal_112184).replace(":", "/"), "Dax", "Skyler"));
        songListFavorite.add(new Song("Salem", R.drawable.song, "android.resource://" + context.getResources().getResourceName(R.raw.groovy_light_future_bass_15881).replace(":", "/"), "Dax", "Mohammed"));
        songListFavorite.add(new Song("Remi", R.drawable.song, "android.resource://" + context.getResources().getResourceName(R.raw.lkb_one_hip_hop_beat_300_138129).replace(":", "/"), "Dax", "Dennis"));
        songListFavorite.add(new Song("Liam", R.drawable.song, "android.resource://" + context.getResources().getResourceName(R.raw.lonely_girl_beat_9522), "Dax", "Nixon"));
        songListFavorite.add(new Song("Noah", R.drawable.song, "android.resource://" + context.getResources().getResourceName(R.raw.solitarymaninblack_putin_99465).replace(":", "/"), "Dax", "Rex"));
        songListFavorite.add(new Song("Oliver", R.drawable.song, "android.resource://" + context.getResources().getResourceName(R.raw.soundhelix_song).replace(":", "/"), "Dax", "Uriah"));
        songListFavorite.add(new Song("Elijah", R.drawable.song, "android.resource://" + context.getResources().getResourceName(R.raw.soundhelix_song_10).replace(":", "/"), "Dax", "Lee"));
        songListFavorite.add(new Song("James", R.drawable.song,"android.resource://" + context.getResources().getResourceName(R.raw.soundhelix_song_11).replace(":", "/"), "Dax", "Louie"));
        return songListFavorite;
    }

    public static List<Song> getSongList(Context context) {
        List<Song> songList = new ArrayList<>();
        songList.add(new Song("Neil", R.drawable.song, "android.resource://" + context.getResources().getResourceName(R.raw.edm_deep_house_ish_female_vocal_112184).replace(":", "/"), "Dax", "Skyler"));
        songList.add(new Song("Neil", R.drawable.song,"android.resource://" + context.getResources().getResourceName(R.raw.edm_deep_house_ish_female_vocal_112184).replace(":", "/"), "Dax", "Skyler"));
        songList.add(new Song("Salem", R.drawable.song, "android.resource://" + context.getResources().getResourceName(R.raw.groovy_light_future_bass_15881).replace(":", "/"), "Dax", "Mohammed"));
        songList.add(new Song("Remi", R.drawable.song, "android.resource://" + context.getResources().getResourceName(R.raw.lkb_one_hip_hop_beat_300_138129).replace(":", "/"), "Dax", "Dennis"));
        songList.add(new Song("Liam", R.drawable.song, "android.resource://" + context.getResources().getResourceName(R.raw.lonely_girl_beat_9522), "Dax", "Nixon"));
        songList.add(new Song("Noah", R.drawable.song, "android.resource://" + context.getResources().getResourceName(R.raw.solitarymaninblack_putin_99465).replace(":", "/"), "Dax", "Rex"));
        songList.add(new Song("Oliver", R.drawable.song, "android.resource://" + context.getResources().getResourceName(R.raw.soundhelix_song).replace(":", "/"), "Dax", "Uriah"));
        songList.add(new Song("Elijah", R.drawable.song, "android.resource://" + context.getResources().getResourceName(R.raw.soundhelix_song_10).replace(":", "/"), "Dax", "Lee"));
        songList.add(new Song("James", R.drawable.song,"android.resource://" + context.getResources().getResourceName(R.raw.soundhelix_song_11).replace(":", "/"), "Dax", "Louie"));
        songList.add(new Song("William", R.drawable.song, "android.resource://" + context.getResources().getResourceName(R.raw.soundhelix_song_12).replace(":", "/"), "Dax", "Alberto"));
        songList.add(new Song("Benjamin", R.drawable.song, "android.resource://" + context.getResources().getResourceName(R.raw.soundhelix_song_13).replace(":", "/"), "Dax", "Reese"));
        songList.add(new Song("Lucas", R.drawable.song, "android.resource://" + context.getResources().getResourceName(R.raw.soundhelix_song_14).replace(":", "/"), "Dax", "Quinton"));
        songList.add(new Song("Henry", R.drawable.song, "android.resource://" + context.getResources().getResourceName(R.raw.soundhelix_song_15).replace(":", "/"), "Dax", "Kingsley"));
        songList.add(new Song("Theodore", R.drawable.song, "android.resource://" + context.getResources().getResourceName(R.raw.soundhelix_song_16).replace(":", "/"), "Francis", "Chaim"));
        songList.add(new Song("Jack", R.drawable.song, "android.resource://" + context.getResources().getResourceName(R.raw.soundhelix_song_17).replace(":", "/"), "Francis", "Alfredo"));
        songList.add(new Song("Levi", R.drawable.song, "android.resource://" + context.getResources().getResourceName(R.raw.soundhelix_song_2).replace(":", "/"), "Francis", "Mauricio"));
        songList.add(new Song("Alexander", R.drawable.song,"android.resource://" + context.getResources().getResourceName(R.raw.soundhelix_song_3).replace(":", "/"), "Francis", "Caspian"));
        songList.add(new Song("Jackson", R.drawable.song,"android.resource://" + context.getResources().getResourceName(R.raw.soundhelix_song_4).replace(":", "/"), "Francis", "Legacy"));
        songList.add(new Song("Mateo", R.drawable.song, "android.resource://" + context.getResources().getResourceName(R.raw.soundhelix_song_5).replace(":", "/"), "Francis", "Ocean"));
        songList.add(new Song("Daniel", R.drawable.song, "android.resource://" + context.getResources().getResourceName(R.raw.soundhelix_song_6).replace(":", "/"), "Francis", "Ozzy"));
        songList.add(new Song("Michael", R.drawable.song, "android.resource://" + context.getResources().getResourceName(R.raw.soundhelix_song_7).replace(":", "/"), "Francis", "Briar"));
        songList.add(new Song("Mason", R.drawable.song, "android.resource://" + context.getResources().getResourceName(R.raw.soundhelix_song_8).replace(":", "/"), "Francis", "Wilson"));
        songList.add(new Song("Sebastian", R.drawable.song, "android.resource://" + context.getResources().getResourceName(R.raw.soundhelix_song_9).replace(":", "/"), "Francis", "Forest"));
        songList.add(new Song("Ethan", R.drawable.song, "android.resource://" + context.getResources().getResourceName(R.raw.tomorrow_114848).replace(":", "/"), "Francis", "Grey"));
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

    public static Song getRandomElement(Context context) {
        Random rand = new Random();
        List<Song> songList = getSongList(context);
        int index = rand.nextInt(Objects.requireNonNull(songList).size());
        return songList.get(index);
    }

}
